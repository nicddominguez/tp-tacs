package tp.tacs.api.requerimientos.models;

import lombok.Builder;
import lombok.Getter;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;

@Builder
@Getter
public class PartidaConMunicipio {
    private Partida partida;
    private Municipio municipio;
}
