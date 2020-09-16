package tp.tacs.api.requerimientos;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.externalApis.ExternalApis;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class ReqCalcularDistancias extends AbstractRequerimiento<Partida,Partida> {
    @Autowired
    private ExternalApis externalApis;
    @Autowired
    private MunicipioDao municipioDao;
    @Override protected Partida execute(Partida request) {
        HashSet<Float> distancias = distanciasTotales(request);
        request.setMaxDist(distancias.stream().max(Float::compareTo).get());
        request.setMinDist(distancias.stream().min(Float::compareTo).get());
        return request;
    }

    private HashSet<Float> distanciasTotales(Partida partida) {
        var coordenadas = partida.getMunicipios()
                .stream()
                .map(id -> {
                    Municipio municipio = municipioDao.get(id);
                    return externalApis.getCoordenadas(municipio.getExternalApiId());
                }).collect(Collectors.toSet());

        var combinaciones = Sets.combinations(coordenadas, 2);

        var distancias = new HashSet<Float>();
        combinaciones.forEach(combinacion -> { // todo preguntar que onda esto
            var list = new ArrayList<>(combinacion);
            var cooredenada = list.get(0).split(",");
            var list0 = new ArrayList<>(Collections.singleton(Double.valueOf(cooredenada[0])));
            var list1 = new ArrayList<>(Collections.singleton(Double.valueOf(cooredenada[1])));
            var distancia = distanciaEntre(list0, list1);
            distancias.add(distancia);
        });
        return distancias;
    }

    private Float distanciaEntre(ArrayList<Double> coordenadasDesde, ArrayList<Double> coordenadasHasta) {
        return (float) Point2D.distance(
                coordenadasDesde.get(0), coordenadasDesde.get(1),
                coordenadasHasta.get(0), coordenadasHasta.get(1));
    }
}
