package tp.tacs.api.dominio.municipio;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tp.tacs.api.dominio.partida.Partida;
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
    private Integer nivelDeProduccion;
    private Double latitud;
    private Double longitud;
    private Float altura;

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

    public boolean esDe(Long userId) {
        return duenio.getId().equals(userId);
    }

    public String coordenadasParaTopo(){
        return String.format("%s,%s",this.getLatitud().toString(),this.getLongitud().toString());
    }

    public void actualizarNivelProduccion(Partida partida) {
        var minAltura = partida.getMinAltura();
        var maxAltura = partida.getMaxAltura();
        float multiplicador = 1 - (this.getAltura() - minAltura)
                / (2 * (maxAltura - minAltura));
        int cantGauchos = this.getEspecializacion().nivelDeProduccion(multiplicador);
        this.setNivelDeProduccion(cantGauchos);
    }

}
