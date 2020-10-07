package tp.tacs.api.dominio.usuario;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Usuario {

    private Long id;
    private String mail;
    private String nombre;
    private String googleId;
    @Builder.Default
    private Boolean isAdmin = false;
    @Builder.Default
    private Long partidasJugadas = 0L;
    @Builder.Default
    private Long partidasGanadas = 0L;
    @Builder.Default
    private Long rachaActual = 0L;

    public void aumentarPartidasJugadas() {
        this.partidasJugadas++;
    }

    public void aumentarPartidasGanadas() {
        this.partidasGanadas++;
    }

    public void aumentarRachaActual() {
        this.rachaActual++;
    }

    public void reiniciarRacha() {
        this.rachaActual = 0L;
    }

}
