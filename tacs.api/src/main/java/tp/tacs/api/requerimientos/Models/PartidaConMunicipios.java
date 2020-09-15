package tp.tacs.api.requerimientos.models;

import lombok.Builder;
import lombok.Getter;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;

@Builder
@Getter
public class PartidaConMunicipios {
    private Partida partida;
    private Municipio municipioAtacante;
    private Municipio municipioDefensor;
}
