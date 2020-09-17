package tp.tacs.api.requerimientos.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReqAtacarModel {
    private Long idPartida;
    private Long idMunicipioAtacante;
    private Long idMunicipioDefensor;
}
