package tp.tacs.api.requerimientos.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReqSimularAtacarMunicipioRequest {
    private Long idPartida;
    private Long idMunicipioAtacante;
    private Long idMunicipioObjectivo;
}
