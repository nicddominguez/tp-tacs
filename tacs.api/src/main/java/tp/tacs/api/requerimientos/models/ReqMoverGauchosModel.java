package tp.tacs.api.requerimientos.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReqMoverGauchosModel {
    private Long idPartida;
    private Long idMunicipioOrigen;
    private Long idMunicipioDestino;
    private Long cantidad;
}
