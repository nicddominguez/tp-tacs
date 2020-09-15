package tp.tacs.api.dominio.municipio;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AtaqueMunicipiosResponse {
    private Municipio municipioAtacante;
    private Municipio municipioDefensor;
}
