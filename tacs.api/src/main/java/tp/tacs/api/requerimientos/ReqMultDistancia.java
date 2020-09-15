package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.requerimientos.models.DosMunicipiosModel;
import tp.tacs.api.requerimientos.models.PartidaConMunicipios;

@Component
public class ReqMultDistancia extends AbstractRequerimiento<PartidaConMunicipios, Float> {

    @Autowired
    private ReqDistanciaEntre reqDistanciaEntre;

    @Override protected Float execute(PartidaConMunicipios request) {
        Float distancia = reqDistanciaEntre
                .run(DosMunicipiosModel.builder().municipioDefensor(request.getMunicipioDefensor()).municipioAtacante(request.getMunicipioAtacante())
                        .build());
        Float maxDist = request.getPartida().getMaxDist();
        Float minDist = request.getPartida().getMinDist();
        return 1 - (distancia - minDist) / (2 * (maxDist - minDist));
    }
}
