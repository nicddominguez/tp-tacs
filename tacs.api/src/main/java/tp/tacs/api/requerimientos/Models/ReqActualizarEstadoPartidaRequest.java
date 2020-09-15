package tp.tacs.api.requerimientos.models;

import lombok.Builder;
import lombok.Getter;
import tp.tacs.api.dominio.partida.Estado;

@Builder
@Getter
public class ReqActualizarEstadoPartidaRequest {
    private Long idPartida;
    private Estado estado;
}
