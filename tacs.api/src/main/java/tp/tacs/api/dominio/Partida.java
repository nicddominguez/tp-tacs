package tp.tacs.api.dominio;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.NoSuchElementException;

public class Partida {
    private List<Usuario> participantes;
    private Integer usuarioJugando = 0;
    private Estados estado;
    private String provincia;
    private List<Municipio> municipios;
    private ModoDeJuego modoDeJuego;

    public Partida(List<Usuario> participantes, Estados estado, String provincia,
                   List<Municipio> municipios, ModoDeJuego modoDeJuego) {
        this.participantes = participantes;
        this.estado = estado;
        this.provincia = provincia;
        this.municipios = municipios;
        this.modoDeJuego = modoDeJuego;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
    }

    public Integer getUsuarioJugando() {
        return usuarioJugando;
    }

    public void setUsuarioJugando(Integer usuarioJugando) {
        this.usuarioJugando = usuarioJugando;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
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

    private void asignarProximoTurno(){
        if(this.usuarioJugando < participantes.size() - 1) {
            this.usuarioJugando++;
        }
        else {
            this.usuarioJugando = 0;
        }
    }

    private Usuario usuarioEnTurnoActual() {
        return this.participantes.get(usuarioJugando);
    }

    private void desBloquearMunicipios() {
        this.municipios.stream().forEach(municipio -> municipio.desbloquear());
    }

    public void pasarTurno(){
        this.asignarProximoTurno();
        this.desBloquearMunicipios();
    }

    public Usuario participanteActual(){
        return this.participantes.get(usuarioJugando);
    }

    public Float minAltura(){
        return (float) this.municipios.stream()
                .mapToDouble(Municipio::getAltura)
                .min()
                .orElseThrow(NoSuchElementException::new);
    }

    public Float maxAltura(){
        return (float) this.municipios.stream()
                .mapToDouble(Municipio::getAltura)
                .max()
                .orElseThrow(NoSuchElementException::new);
    }

    public Float maxDist(){
        return 200f;
    }

    public Float minDist(){
        return 2f;
    }

    public Float multDist(Municipio municipioOrigen, Municipio municipioDestino){
        Float distanciaEntreMunicipios = this.distanciaEntreMunicipios(municipioOrigen, municipioDestino);
        return this.modoDeJuego.getMultDist(distanciaEntreMunicipios, this.minDist(), this.maxDist());
    }

    private Float distanciaEntreMunicipios(Municipio unMunicipio, Municipio otroMunicipio){
        var lat1 = unMunicipio.getLatitud();
        var long1 = unMunicipio.getLongitud();
        var lat2 = otroMunicipio.getLatitud();
        var long2 = otroMunicipio  .getLongitud();

        return (float) Point2D.distance(lat1, long1,lat2 ,long2);
    }

    public Float multAltura(Municipio municipioDefensor){
        Float alturaMunicipioDefensor = municipioDefensor.getAltura();
        return this.modoDeJuego.getMultAltura(alturaMunicipioDefensor, this.minAltura(), this.maxAltura());
    }

}
