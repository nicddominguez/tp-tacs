package tp.tacs.api.dominio.municipio;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tp.tacs.api.dominio.usuario.Usuario;

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


    public void setCantGauchos(Integer cantGauchos) {
        this.cantGauchos = Math.max(cantGauchos, 0);
    }

    public void agregarGauchos(Integer cantidad){
        cantGauchos =+ cantidad;
    }
    public void sacarGauchos(Integer cantidad){
        cantGauchos =- cantidad;
    }

    public boolean estaBloqueado() {
        return bloqueado;
    }

    public void desbloquear() {
        this.bloqueado = false;
    }

    public Boolean estaBacante() {
        return duenio == null;
    }

    public void producir() {
        this.especializacion.producir(this);
    }

    public boolean esDe(Long userId) {
        return duenio.getId().equals(userId);
    }
}
