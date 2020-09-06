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

    public boolean estaBloqueado() {
        return bloqueado;
    }

    public void desbloquear() {
        this.bloqueado = false;
    }

    public void bloquear() {
        this.bloqueado = true;
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
        Integer gauchosAtacantesFinal = (int) Math.floor(this.cantGauchos * this.partida.multDist(this, municipio)
                - municipio.getCantGauchos() * this.partida.multAltura(municipio) * this.especializacion.multDensa());
        return gauchosAtacantesFinal;
    }

    private Integer gauchosDefensoresFinal(Municipio municipio) {
        Integer gauchosDefensoresFinal = (int) Math.round(Math.ceil(
                (municipio.getCantGauchos() * this.partida.multAltura(municipio) * this.especializacion.multDensa())
                        - (this.cantGauchos * this.partida.multDist(this, municipio)))
                / (this.partida.multAltura(municipio) * this.especializacion.multDensa()));
        return gauchosDefensoresFinal;
    }

    public void atacar(Municipio municipio) {
        //TODO ver si es su turno
        if (this.mismoDuenio(municipio)) {
            throw new RuntimeException("No puede atacar a sus propios municipios");
        }
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

    private boolean mismoDuenio(Municipio municipio) {
        return this.duenio == municipio.getDuenio();
    }

    public void moverGauchos(Municipio municipio, Integer cantidad) {
        //TODO ver si es su turno
        if (this.mismoDuenio(municipio)) {
            throw new RuntimeException("Debe ser el duenio del municipio para poder mover gauchos");
        }
        if (this.estaBloqueado()) {
            throw new RuntimeException("Un municipio bloqueado no puede mover gauchos");
        }
        this.sacarGauchos(cantidad);
        municipio.agregarGauchos(cantidad);
        municipio.bloquear();
    }

    public void agregarGauchos(Integer cantGauchos) {
        this.cantGauchos += cantGauchos;
    }

    public void sacarGauchos(Integer cantGauchos) {
        this.cantGauchos -= cantGauchos;
    }

    public boolean esDe(Usuario usuario) {
        return this.duenio == usuario;
    }
}
