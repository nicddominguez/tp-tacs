package tp.tacs.api.requerimientos.Models;

import lombok.Builder;
import lombok.Getter;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;

@Builder
@Getter
public class ReqProducirModel {
    private Municipio municipio;
    private Partida partida;
}
