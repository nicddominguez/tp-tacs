package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.requerimientos.models.DosMunicipiosModel;

import java.awt.geom.Point2D;
import java.util.ArrayList;

@Component
public class ReqDistanciaEntre extends AbstractRequerimiento<DosMunicipiosModel,Float> {
    @Autowired
    private ExternalApis externalApis;

    @Override protected Float execute(DosMunicipiosModel request) {
        ArrayList<Double> coordenadas1 = externalApis.getCoordenadasArray(request.getMunicipioAtacante().getExternalApiId());
        ArrayList<Double> coordenadas2 = externalApis.getCoordenadasArray(request.getMunicipioDefensor().getExternalApiId());
        return (float) Point2D.distance(coordenadas1.get(0), coordenadas1.get(1),coordenadas2.get(0), coordenadas2.get(1));
    }
}
