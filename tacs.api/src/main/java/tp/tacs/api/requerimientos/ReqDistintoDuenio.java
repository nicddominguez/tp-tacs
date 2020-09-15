package tp.tacs.api.requerimientos;

import org.springframework.stereotype.Component;
import tp.tacs.api.handler.PartidaException;
import tp.tacs.api.requerimientos.models.DosMunicipiosModel;

@Component
public class ReqDistintoDuenio extends AbstractRequerimiento<DosMunicipiosModel, Void> {

    @Override protected Void execute(DosMunicipiosModel request) {
        if (request.getMunicipioAtacante().getDuenio().getId().equals(request.getMunicipioDefensor().getId()))
            throw new PartidaException("El duenio del municipio atacante no puede ser igual al del defensor");
        return null;
    }
}
