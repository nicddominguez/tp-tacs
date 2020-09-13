package tp.tacs.api.dominio.municipio;

import com.google.common.collect.Lists;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.http.externalApis.ExternalApis;

import java.util.ArrayList;

public class Municipio {

    private Long id;
    private String idMunicipioReal = "2";
    private Integer cantGauchos = 15;
    private boolean bloqueado = false;
    private Especializacion especializacion = new Produccion();
    private Partida partida;
    private Usuario duenio;
    ExternalApis repoMunicipios = ExternalApis.instance();

    public Municipio() {
        new MunicipioDao().save(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getNombre() {
        return this.repoMunicipios.getNombre(this.idMunicipioReal);
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

    public void setRepoMunicipios(ExternalApis repoMunicipios) {
        this.repoMunicipios = repoMunicipios;
    }

    public Double getLatitud() {
//        sleep(1000);
        return this.repoMunicipios.getLatitud(this.idMunicipioReal);
    }

    public Double getLongitud() {
//        sleep(1000);
        return this.repoMunicipios.getLongitud(this.idMunicipioReal);
    }

    public ArrayList<Double> getCoordenadas() {
        return Lists.newArrayList(this.getLatitud(), this.getLongitud());
    }

    public Float getAltura() {
        return this.repoMunicipios.getAltura(this.idMunicipioReal);
    }

    public String getPathImagen() {
        return this.repoMunicipios.getPathImagen(this.idMunicipioReal);
    }

    public void producir() {
        this.especializacion.producir(this);
    }

    private Integer gauchosAtacantesFinal(Municipio municipio) {
        var multDist = this.partida.multDist(this, municipio);
        var multAltura = this.partida.multAltura(municipio);
        var multDefensa = this.especializacion.multDefensa(this.partida);

        return (int) Math.floor(this.cantGauchos * multDist - municipio.getCantGauchos() * multAltura * multDefensa);
    }

    private Integer gauchosDefensoresFinal(Municipio municipio) {
        var multAltura = this.partida.multAltura(municipio);
        var multDefensa = this.especializacion.multDefensa(this.partida);
        var multDist = this.partida.multDist(this, municipio);
        var gauchosDefensores = municipio.getCantGauchos();

        return (int) Math.round(Math.ceil(
                (gauchosDefensores * multAltura * multDefensa) - (this.cantGauchos * multDist))
                / (multAltura * multDefensa));
    }

    public void atacar(Municipio municipio) {
        if (this.partida.usuarioEnTurnoActual() != this.duenio) {
            throw new RuntimeException("No es el turno del dueño del municipio. No puede atacar.");
        }
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
        if (this.partida.usuarioEnTurnoActual() != this.duenio) {
            throw new RuntimeException("No es el turno del dueño del municipio. No puede mover gauchos.");
        }
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
