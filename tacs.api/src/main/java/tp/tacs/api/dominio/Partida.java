package tp.tacs.api.dominio;

import com.google.common.collect.Sets;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

//TODO manejar estadisticas de usuario
public class Partida {

    private List<Usuario> participantes;
    private Integer usuarioJugandoIndiceLista = 0;
    private Estado estado;
    private String provincia;
    private List<Municipio> municipios;
    private ModoDeJuego modoDeJuego;
    private Date fechaCreacion;
    private Usuario ganador;

    public Partida(List<Usuario> participantes, Estado estado, String provincia,
                   List<Municipio> municipios, ModoDeJuego modoDeJuego, Date fechaCreacion) {
        this.participantes = participantes;
        this.estado = estado;
        this.provincia = provincia;
        this.municipios = municipios;
        this.modoDeJuego = modoDeJuego;
        this.fechaCreacion = fechaCreacion;
        RepoPartidas.instance().agregarPartida(this);
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
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


    private void asignarProximoTurno() {
        if (this.usuarioJugandoIndiceLista < this.participantes.size() - 1) {
            this.usuarioJugandoIndiceLista++;
        } else {
            this.usuarioJugandoIndiceLista = 0;
        }
    }

    private Usuario usuarioEnTurnoActual() {
        return this.participantes.get(this.usuarioJugandoIndiceLista);
    }

    private void desBloquearMunicipios() {
        this.municipios.forEach(Municipio::desbloquear);
    }

    private boolean esDuenioDeTodo(Usuario usuario) {
        return this.municipios.stream().allMatch(municipio -> municipio.esDe(usuario));
    }

    public void pasarTurno() {
        if (this.esDuenioDeTodo(this.usuarioEnTurnoActual())) {
            this.terminar();
            this.ganador = this.usuarioEnTurnoActual();
            this.ganador.aumentarPartidasGanadas();
            this.ganador.aumentarRachaActual();
            this.participantes.stream()
                    .filter(usuario -> !usuario.equals(this.ganador))
                    .forEach(Usuario::reiniciarRacha);
        }

        this.asignarProximoTurno();
        this.desBloquearMunicipios();
    }

    public void terminar() {
        this.estado = Estado.TERMINADA;
        this.participantes.forEach(Usuario::aumentarPartidasJugadas);
    }

    public boolean estaEnCurso() {
        return this.estado == Estado.EN_CURSO;
    }

    public void cancelar() {
        this.estado = Estado.CANCELADA;
    }

    public Usuario participanteActual() {
        return this.participantes.get(this.usuarioJugandoIndiceLista);
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
        combinaciones.forEach(arrayLists -> {
            var list = new ArrayList<>(arrayLists);
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
        return 1 - (distanciaMunicipios - minDist)/(2*(maxDist-minDist));
    }

    public Float multAltura(Municipio municipioDefensor) {
        Float alturaMunicipioDefensor = municipioDefensor.getAltura();
        var minAltura = this.minAltura();
        var maxAltura = this.maxAltura();
        return 1 + (alturaMunicipioDefensor - minAltura)/(2*(maxAltura-minAltura));
    }

}
