package tp.tacs.api.requerimientos.models;

import lombok.Builder;
import lombok.Getter;
import tp.tacs.api.dominio.partida.Partida;

@Builder
@Getter
public class ReqRepartirMunicipiosModel {
    private Partida partida;
    private Long cantidadMunicipios;
}
