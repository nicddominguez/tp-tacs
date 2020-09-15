package tp.tacs.api.dominio.partida;

import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.dominio.municipio.AtaqueMunicipiosResponse;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.handler.PartidaException;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

@Builder
@Getter
@Setter
public class Partida {

    private Long id;
    private List<Long> jugadoresIds;
    @Builder.Default
    private Integer usuarioJugandoIndiceLista = 0;
    private Estado estado;
    private String nombreProvincia;
    private String idProvincia;
    private List<Long> municipios;
    private ModoDeJuego modoDeJuego;
    private Date fechaCreacion;
    private Usuario ganador;
    private Float minAltura;
    private Float maxAltura;
    private Float maxDist;
    private Float minDist;

    public void setUsuarioJugandoIndiceLista(Integer usuarioJugandoIndiceLista) {
        this.usuarioJugandoIndiceLista = usuarioJugandoIndiceLista;
    }

    private void asignarProximoTurno() {
        if (this.usuarioJugandoIndiceLista < jugadoresIds.size() - 1) {
            this.usuarioJugandoIndiceLista++;
        } else {
            this.usuarioJugandoIndiceLista = 0;
        }
    }

    public void asignarProximoTurnoA(Usuario usuario) {
        var indice = jugadoresIds.indexOf(usuario.getId());
        if (indice >= 0) {
            this.setUsuarioJugandoIndiceLista(indice);
        } else {
            throw new PartidaException("No se pudo asignar el turno al usuario: no forma parte de la partida");
        }
    }

    public Long idUsuarioEnTurnoActual() {
        return jugadoresIds.get(usuarioJugandoIndiceLista);
    }

    private void desBloquearMunicipios() {
        this.municipios.forEach(Municipio::desbloquear);
    }

    private boolean esDuenioDeTodo(Usuario usuario) {
        return this.municipios.stream().allMatch(municipio -> municipio.esDe(usuario));
    }

    public boolean hayGanador() {
        return jugadores.stream().anyMatch(this::esDuenioDeTodo);
    }

    private void manejarPasajeDeTurno() {
        if (this.hayGanador()) {
            this.terminar();
        } else {
            this.asignarProximoTurno();
            this.desBloquearMunicipios();
            municipios.forEach(Municipio::producir);
        }
    }

    public void pasarTurno() {
        if (this.estaEnCurso()) {
            this.manejarPasajeDeTurno();
        } else
            throw new PartidaException("No se puede pasar el turno de una partida que no este en curso");

    }

    public void validarAccion(String accion, Usuario duenio) {
        if (!this.getEstado().equals(Estado.EN_CURSO)) {
            throw new PartidaException("La partida no está en curso. No se pudo " + accion);
        }
        if (usuarioEnTurnoActual().getId() != duenio.getId()) {
            throw new PartidaException("No es el turno del dueño del municipio.");
        }
    }

    public AtaqueMunicipiosResponse atacar(Municipio municipioAtacante, Municipio municipioDefensor) {
        validarAccion("atacar", municipioAtacante.getDuenio());
        if (mismoDuenio(municipioAtacante, municipioDefensor)) {
            throw new PartidaException("No puede atacar a sus propios municipios");
        }
        Integer gauchosAtacantesFinal = gauchosAtacantesFinal(municipioAtacante, municipioDefensor);
        Integer gauchosDefensoresFinal = gauchosDefensoresFinal(municipioAtacante, municipioDefensor);

        municipioAtacante.setCantGauchos(gauchosAtacantesFinal);
        municipioDefensor.setCantGauchos(gauchosDefensoresFinal);
        if (gauchosDefensoresFinal <= 0) {
            municipioDefensor.setCantGauchos(0);
            municipioDefensor.setDuenio(municipioAtacante.getDuenio());
        }
        pasarTurno();
        return AtaqueMunicipiosResponse.builder()
                .municipioAtacante(municipioAtacante)
                .municipioDefensor(municipioDefensor)
                .build();
    }
    private Integer gauchosAtacantesFinal(Municipio municipioAtacante, Municipio municipioDefensor) {
        var multDist = this.multDist(municipioAtacante, municipioDefensor);
        var multAltura = this.multAltura(municipioDefensor);
        var multDefensa = municipioDefensor.getEspecializacion().multDefensa(this);

        return (int) Math.floor(municipioAtacante.getCantGauchos() * multDist - municipioDefensor.getCantGauchos() * multAltura * multDefensa);
    }

    private Integer gauchosDefensoresFinal(Municipio municipioAtacante, Municipio municipioDefensor) {
        var multAltura = this.multAltura(municipioDefensor);
        var multDefensa = municipioDefensor.getEspecializacion().multDefensa(this);
        var multDist = this.multDist(municipioAtacante, municipioDefensor);
        var gauchosDefensores = municipioDefensor.getCantGauchos();

        return (int) Math.round(Math.ceil(
                (gauchosDefensores * multAltura * multDefensa) - (municipioAtacante.getCantGauchos() * multDist))
                / (multAltura * multDefensa));
    }

    private boolean mismoDuenio(Municipio municipio1, Municipio municipio2) {
        return  municipio1.getDuenio().getId() == municipio2.getDuenio().getId();
    }

    public Usuario usuarioConMasMunicipios() {
        var municipiosConDuenio = municipios.stream()
                .filter(municipio -> municipio.getDuenio() != null).collect(Collectors.toSet());

        //Agrupa por usuario sumando la cantidad de municipios que tenga
        var ganadosPorUsuario = municipiosConDuenio.stream()
                .collect(Collectors.groupingBy(Municipio::getDuenio, Collectors.counting()));

        return Collections
                .max(ganadosPorUsuario.entrySet(), Map.Entry.comparingByValue())
                .getKey();
    }

    public void terminar() {
        setEstado(Estado.TERMINADA);
        jugadores.forEach(Usuario::aumentarPartidasJugadas);
        ganador = this.usuarioConMasMunicipios();
        ganador.aumentarPartidasGanadas();
        ganador.aumentarRachaActual();
        jugadores.stream()
                .filter(usuario -> !usuario.equals(this.ganador))
                .forEach(Usuario::reiniciarRacha);
    }

    public boolean estaEnCurso() {
        return this.estado == Estado.EN_CURSO;
    }

    public void cancelar() {
        this.estado = Estado.CANCELADA;
    }

    public void calcularDistancias() {
        HashSet<Float> distancias = distanciasTotales();
        this.maxDist = distancias
                .stream()
                .max(Float::compareTo)
                .orElseThrow(NoSuchElementException::new);
        this.minDist = distancias
                .stream()
                .min(Float::compareTo)
                .orElseThrow(NoSuchElementException::new);

    }

    private HashSet<Float> distanciasTotales() {
        var coordenadas = this.municipios
                .stream()
                .map(Municipio::getCoordenadas)
                .collect(Collectors.toSet());
        var combinaciones = Sets.combinations(coordenadas, 2);

        var distancias = new HashSet<Float>();
        combinaciones.forEach(combinacion -> {
            var list = new ArrayList<>(combinacion);
            var distancia = distanciaEntre(list.get(0), list.get(1));
            distancias.add(distancia);
        });
        return distancias;
    }

    private Float distanciaEntre(ArrayList<Double> coordenadasDesde, ArrayList<Double> coordenadasHasta) {
        return (float) Point2D.distance(
                coordenadasDesde.get(0), coordenadasDesde.get(1),
                coordenadasHasta.get(0), coordenadasHasta.get(1));
    }

    private Float distanciaEntreMunicipios(Municipio unMunicipio, Municipio otroMunicipio) {
        return this.distanciaEntre(unMunicipio.getCoordenadas(), otroMunicipio.getCoordenadas());
    }

    public Float multDist(Municipio municipioOrigen, Municipio municipioDestino) {
        Float distanciaMunicipios = this.distanciaEntreMunicipios(municipioOrigen, municipioDestino);
        var minDist = this.minDist();
        var maxDist = this.maxDist();
        return 1 - (distanciaMunicipios - minDist) / (2 * (maxDist - minDist));
    }

    public Float multAltura(Municipio municipioDefensor) {
        try {
            sleep(1000); //TODO Cachear para no tener que asfixiar a la API
            Float alturaMunicipioDefensor = municipioDefensor.getAltura();
            var minAltura = this.minAltura();
            var maxAltura = this.maxAltura();
            return 1 + (alturaMunicipioDefensor - minAltura) / (2 * (maxAltura - minAltura));
        } catch (Exception e) {
            throw new PartidaException();
        }
    }

}
