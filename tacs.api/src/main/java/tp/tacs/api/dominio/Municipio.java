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

    private Integer gauchosAtacantesFinal(Municipio municipio) {
        //TODO ver redondeo
        Integer gauchosAtacantesFinal = Math.round(this.cantGauchos * this.partida.getMultDist(this, municipio)
                - municipio.getCantGauchos() * this.partida.getMultAltura(municipio) * this.especializacion.multDensa());
        return gauchosAtacantesFinal;
    }

    private Integer gauchosDefensoresFinal(Municipio municipio) {
        //TODO ver redondeo
        Integer gauchosDefensoresFinal = Math.round((municipio.getCantGauchos() * this.partida.getMultAltura(municipio) * this.especializacion.multDensa()
                - this.cantGauchos * this.partida.getMultDist(this, municipio))
                / (this.partida.getMultAltura(municipio) * this.especializacion.multDensa()));
        return gauchosDefensoresFinal;
    }

    public void atacar(Municipio municipio) {
        Integer gauchosAtacantesFinal = gauchosAtacantesFinal(municipio);
        Integer gauchosDefensoresFinal = gauchosDefensoresFinal(municipio);

        this.setCantGauchos(gauchosAtacantesFinal);
        municipio.setCantGauchos(gauchosDefensoresFinal);
        if (this.ataqueExitoso(municipio)) {
            municipio.setDuenio(this.duenio);
        }
    }

    public boolean ataqueExitoso(Municipio municipio) {
        Integer gauchosDefensoresFinal = gauchosDefensoresFinal(municipio);
        return gauchosDefensoresFinal <= 0;
    }

    public void moverGauchos(Municipio municipio, Integer cantidad) {
        //TODO verificar si son del mismo dueño
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
