package tp.tacs.api.dominio;

import java.util.List;
import java.util.NoSuchElementException;

public class Partida {
    private Integer cantMunicipios;
    private List<Usuario> participantes;
    private Integer usuarioJugando = 0;
    private Estados estado;
    private String provincia;
    private List<Municipio> municipios;
    private ModoDeJuego modoDeJuego;

    public Partida(Integer cantMunicipios, List<Usuario> participantes, String provincia, ModoDeJuego modoDeJuego) {
        this.cantMunicipios = cantMunicipios;
        this.participantes = participantes;
        this.provincia = provincia;
        this.modoDeJuego = modoDeJuego;
        this.estado = Estados.EN_JUEGO;
        RepoPartidas.instance().agregarPartida(this);
    }

    public Integer getCantMunicipios() {
        return cantMunicipios;
    }

    public void setCantMunicipios(Integer cantMunicipios) {
        this.cantMunicipios = cantMunicipios;
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

    public void pasarTurno(){
        this.asignarProximoTurno();
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

    public Float getMultDefensaDefensa(){
        return this.modoDeJuego.getMultDefensaDefensa();
    }

    public Float getMultDefensaProduccion(){
        return this.modoDeJuego.getMultDefnesaProduccion();
    }

    public Float getMultDist(Municipio municipioOrigen, Municipio municipioDestino){
        Float distanciaEntreMunicipios = this.distanciaEntreMunicipios(municipioOrigen, municipioDestino);
        return this.modoDeJuego.getMultDist(distanciaEntreMunicipios, this.minDist(), this.maxDist());
    }

    private Float distanciaEntreMunicipios(Municipio unMunicipio, Municipio otroMunicipio){
        return 100f;
    }

    public Float getMultAltura(Municipio municipioDefensor){
        Float alturaMunicipioDefensor = municipioDefensor.getAltura();
        return this.modoDeJuego.getMultAltura(alturaMunicipioDefensor, this.minAltura(), this.maxAltura());
    }

}
