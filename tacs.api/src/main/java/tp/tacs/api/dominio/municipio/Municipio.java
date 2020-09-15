package tp.tacs.api.dominio.municipio;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.handler.MunicipioException;

import java.util.ArrayList;

@Builder
@Getter
@Setter
public class Municipio {
    private Long id;
    private String nombre;
    private String externalApiId;
    @Builder.Default
    private Integer cantGauchos = 15;
    @Builder.Default
    private boolean bloqueado = false;
    @Builder.Default
    private Especializacion especializacion = new Produccion();
    private Usuario duenio;

    public Municipio() {
        new MunicipioDao().save(this);
    }

    public String getNombre() {
        return this.repoMunicipios.getNombre(this.externalApiId);
    }

    public Double getLatitud() {
        //        sleep(1000);
        return this.repoMunicipios.getLatitud(this.externalApiId);
    }

    public Double getLongitud() {
        //        sleep(1000);
        return this.repoMunicipios.getLongitud(this.externalApiId);
    }

    public void setCantGauchos(Integer cantGauchos) {
        this.cantGauchos = Math.max(cantGauchos, 0);
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

    public Boolean estaBacante() {
        return duenio == null;
    }

    public ArrayList<Double> getCoordenadas() {
        return Lists.newArrayList(this.getLatitud(), this.getLongitud());
    }

    public Float getAltura() {
        return this.repoMunicipios.getAltura(this.externalApiId);
    }

    public String getPathImagen() {
        return this.repoMunicipios.getPathImagen(this.externalApiId);
    }

    public void producir() {
        this.especializacion.producir(this);
    }

    private boolean mismoDuenio(Municipio municipio) {
        return this.duenio == municipio.getDuenio();
    }

    public void moverGauchos(Municipio municipio, Integer cantidad) {
        this.validarAccion("mover");
        if (!this.mismoDuenio(municipio)) {
            throw new MunicipioException("Debe ser el duenio del municipio para poder mover gauchos");
        }
        if (this.estaBloqueado()) {
            throw new MunicipioException("Un municipio bloqueado no puede mover gauchos");
        }
        if (cantidad > this.cantGauchos || cantidad < 0) {
            throw new MunicipioException("Cantidad Erronea");
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
