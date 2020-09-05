package tp.tacs.api.dominio;

import java.util.List;

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

    public Float maxAltura(){
        return 10f;
    }

    public Float minAltura(){
        return 2.5f;
    }

    public void pasarTurno(){
        this.asignarProximoTurno();
    }

    private void asignarProximoTurno(){
        if(this.usuarioJugando < participantes.size() - 1) {
            this.usuarioJugando++;
        }
        else {
            this.usuarioJugando = 0;
        }
    }

    public Usuario participanteActual(){
        return this.participantes.get(usuarioJugando);
    }

    public Float maxDist(){
        return 200f;
    }

    public Float minDist(){
        return 2f;
    }
}
