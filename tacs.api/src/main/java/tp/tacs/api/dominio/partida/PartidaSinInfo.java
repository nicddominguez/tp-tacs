package tp.tacs.api.dominio.partida;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tp.tacs.api.dominio.usuario.Usuario;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class PartidaSinInfo {

    private String id;
    private List<String> jugadoresIds;
    @Builder.Default
    private Integer usuarioJugandoIndiceLista = 0;
    private Estado estado;
    private String nombreProvincia;
    private String idProvincia;
    private List<String> municipios;
    private ModoDeJuego modoDeJuego;
    private Date fechaCreacion;
    private Usuario ganador;
    private Float minAltura;
    private Float maxAltura;
    private Float maxDist;
    private Float minDist;

}
