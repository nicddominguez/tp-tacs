package tp.tacs.api.dominio.partida;

import com.google.common.collect.Sets;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.dominio.municipio.Municipio;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

//TODO manejar estadisticas de usuario
public class Partida {

    private List<Usuario> jugadores;
    private Integer usuarioJugandoIndiceLista = 0;
    private Estado estado;
    private String provincia;
    private List<Municipio> municipios;
    private ModoDeJuego modoDeJuego;
    private Date fechaCreacion;
    private Usuario ganador;

    public Partida(List<Usuario> jugadores, Estado estado, String provincia,
                   List<Municipio> municipios, ModoDeJuego modoDeJuego, Date fechaCreacion) {
        this.jugadores = jugadores;
        this.estado = estado;
        this.provincia = provincia;
        this.municipios = municipios;
        this.modoDeJuego = modoDeJuego;
        this.fechaCreacion = fechaCreacion;
        RepoPartidas.instance().agregarPartida(this);
    }

    public List<Usuario> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Usuario> jugadores) {
        this.jugadores = jugadores;
    }

    public Integer getUsuarioJugandoIndiceLista() {
        return usuarioJugandoIndiceLista;
    }

    public void setUsuarioJugandoIndiceLista(Integer usuarioJugandoIndiceLista) {
        this.usuarioJugandoIndiceLista = usuarioJugandoIndiceLista;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public ModoDeJuego getModoDeJuego() {
        return modoDeJuego;
    }

    public void setModoDeJuego(ModoDeJuego modoDeJuego) {
        this.modoDeJuego = modoDeJuego;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getGanador() {
        return ganador;
    }

    public void setGanador(Usuario ganador) {
        this.ganador = ganador;
    }

    public void repartirMunicipios() {
        Integer cantidadInicial = (int) Math.floor((double) municipios.size() / jugadores.size());

        jugadores.forEach(usuario -> {
            for (int i = 0; i < cantidadInicial; i++) {
                municipios.stream()
                        .filter(Municipio::estaBacante)
                        .findAny()
                        .ifPresent(municipio -> municipio.setDuenio(usuario));
            }
        });

    }

    private void asignarProximoTurno() {
        if (this.usuarioJugandoIndiceLista < this.jugadores.size() - 1) {
            this.usuarioJugandoIndiceLista++;
        } else {
            this.usuarioJugandoIndiceLista = 0;
        }
    }

    public void asignarProximoTurnoA(Usuario usuario) {
        var indice = jugadores.indexOf(usuario);
        if (indice >= 0) {
            this.setUsuarioJugandoIndiceLista(indice);
        } else {
            throw new RuntimeException("No se pudo asignar el turno al usuario: no forma parte de la partida");
        }
    }

    public Usuario usuarioEnTurnoActual() {
        return this.jugadores.get(this.usuarioJugandoIndiceLista);
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
            throw new RuntimeException("No se puede pasar el turno de una partida que no este en curso");

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

    public Float minAltura() {
        return (float) this.municipios.stream()
                .mapToDouble(Municipio::getAltura)
                .min()
                .orElseThrow(NoSuchElementException::new);
    }

    public Float maxAltura() {
        return (float) this.municipios.stream()
                .mapToDouble(Municipio::getAltura)
                .max()
                .orElseThrow(NoSuchElementException::new);
    }

    public Float maxDist() {
        HashSet<Float> distancias = distanciasTotales();
        return distancias
                .stream()
                .max(Float::compareTo)
                .orElseThrow(NoSuchElementException::new);
    }

    public Float minDist() {
        HashSet<Float> distancias = distanciasTotales();
        return distancias
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
        Float alturaMunicipioDefensor = municipioDefensor.getAltura();
        var minAltura = this.minAltura();
        var maxAltura = this.maxAltura();
        return 1 + (alturaMunicipioDefensor - minAltura) / (2 * (maxAltura - minAltura));
    }

}
