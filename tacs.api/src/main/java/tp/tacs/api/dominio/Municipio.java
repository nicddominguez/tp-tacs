package tp.tacs.api.dominio;

public class Municipio {

    private Integer cantGauchos;
    private boolean bloqueado;
    private Especializacion especializacion;
    private MunicipioReal municipioReal;

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

    public Integer getAltura() {
        return this.municipioReal.getAltura();
    }

    public String getImagenPath() {
        return this.municipioReal.getImagenPath();
    }

    public void producir() {
        this.especializacion.producir(this);
    }

    public Municipio atacar(Municipio municipio) {
        return null;
    }

    public Municipio moverGauchos(Municipio municipio, Integer cantidad) {
        return null;
    }

    private void agregarGauchos(Integer cantGauchos) {
        this.cantGauchos += cantGauchos;
    }
}
