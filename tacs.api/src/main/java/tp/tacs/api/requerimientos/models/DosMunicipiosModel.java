package tp.tacs.api.requerimientos.models;

import lombok.Builder;
import lombok.Getter;
import tp.tacs.api.dominio.municipio.Municipio;

@Builder
@Getter
public class DosMunicipiosModel {
    private Municipio municipioAtacante;
    private Municipio municipioDefensor;
}
