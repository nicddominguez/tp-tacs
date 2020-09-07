package tp.tacs.api.dominio.municipio;

import com.google.common.collect.Lists;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;

import java.util.ArrayList;

public class Municipio {

    private String idMunicipioReal;
    private Integer cantGauchos = 0;
    private boolean bloqueado = false;
    private Especializacion especializacion = new Produccion();
    private Partida partida;
    private Usuario duenio;
    RepoMunicipios repoMunicipios = RepoMunicipios.instance();

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

    public Boolean estaBacante() {
        return duenio == null;
    }

    public String getIdMunicipioReal() {
        return this.idMunicipioReal;
    }

    public void setIdMunicipioReal(String idMunicipioReal) {
        this.idMunicipioReal = idMunicipioReal;
    }

    public RepoMunicipios getRepoMunicipios() {
        return repoMunicipios;
    }

    public void setRepoMunicipios(RepoMunicipios repoMunicipios) {
        this.repoMunicipios = repoMunicipios;
    }

    public Double getLatitud() {
        return this.repoMunicipios.getLatitud(idMunicipioReal);
    }

    public Double getLongitud() {
        return this.repoMunicipios.getLongitud(idMunicipioReal);
    }

    public ArrayList<Double> getCoordenadas() {
        return Lists.newArrayList(this.getLatitud(), this.getLongitud());
    }

    public Float getAltura() {
        return this.repoMunicipios.getAltura(idMunicipioReal);
    }

    public String getPathImagen() {
        return this.repoMunicipios.getPathImagen(idMunicipioReal);
    }

    public void producir() {
        this.especializacion.producir(this);
    }

    private Integer gauchosAtacantesFinal(Municipio municipio) {
        return (Integer) (int) Math.floor(
                this.cantGauchos * this.partida.multDist(this, municipio)
                        - municipio.getCantGauchos() * this.partida.multAltura(municipio) * this.especializacion.multDefensa(this.partida)
        );
    }

    private Integer gauchosDefensoresFinal(Municipio municipio) {
        return (Integer) (int) Math.round(Math.ceil(
                (municipio.getCantGauchos() * this.partida.multAltura(municipio) * this.especializacion.multDefensa(this.partida))
                        - (this.cantGauchos * this.partida.multDist(this, municipio)))
                / (this.partida.multAltura(municipio) * this.especializacion.multDefensa(this.partida)));
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
            municipio.setCantGauchos(0);
            municipio.setDuenio(this.duenio);
        }
        this.partida.pasarTurno();
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
        if (!this.mismoDuenio(municipio)) {
            throw new RuntimeException("Debe ser el duenio del municipio para poder mover gauchos");
        }
        if (this.estaBloqueado()) {
            throw new RuntimeException("Un municipio bloqueado no puede mover gauchos");
        }
        if (cantidad > this.cantGauchos || cantidad < 0) {
            throw new RuntimeException("Cantidad Erronea");
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
