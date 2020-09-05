package tp.tacs.api.dominio;

public class Municipio {

    private Integer cantGauchos;
    private boolean bloqueado;
    private Especializacion especializacion;
    private MunicipioReal municipioReal;
    private Partida partida;
    private Usuario duenio;

    public Integer getCantGauchos() {
        return cantGauchos;
    }

    public void setCantGauchos(Integer cantGauchos) {
        this.cantGauchos = cantGauchos;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Especializacion getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(Especializacion especializacion) {
        this.especializacion = especializacion;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Usuario getDuenio() {
        return duenio;
    }

    public void setDuenio(Usuario duenio) {
        this.duenio = duenio;
    }

    public MunicipioReal getMunicipioReal() {
        return municipioReal;
    }

    public void setMunicipioReal(MunicipioReal municipioReal) {
        this.municipioReal = municipioReal;
    }

    public Long getId() {
        return this.municipioReal.getId();
    }

    public Double getLatitud() {
        return this.municipioReal.getLatitud();
    }

    public Double getLongitud() {
        return this.municipioReal.getLongitud();
    }

    public Float getAltura() {
        return this.municipioReal.getAltura();
    }

    public String getImagenPath() {
        return this.municipioReal.getImagenPath();
    }

    public void producir() {
        this.especializacion.producir(this);
    }

    public void atacar(Municipio municipio) {
        //TODO
        /*
        Integer gauchosAtacantesFinal = Math.round(this.cantGauchos * this.partida.getModoDeJuego().getMultDist()
                - municipio.getCantGauchos() * this.partida.getModoDeJuego().multAltura() * this.partida.getModoDeJuego().getMultDefensa());
        Integer gauchosDefensoresFinal = Math.round((municipio.getCantGauchos() * this.partida.getModoDeJuego().getMultAltura())/(this.partida.getModoDeJuego().getMultAltura() * this.partida.getModoDeJuego().getMultDefensa()));
        */
    }


    public void moverGauchos(Municipio municipio, Integer cantidad) {
        this.sacarGauchos(cantidad);
        municipio.agregarGauchos(cantidad);
    }

    public void agregarGauchos(Integer cantGauchos) {
        this.cantGauchos += cantGauchos;
    }

    public void sacarGauchos(Integer cantGauchos) {
        this.cantGauchos -= cantGauchos;
    }
}
