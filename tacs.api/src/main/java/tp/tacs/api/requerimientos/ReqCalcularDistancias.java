package tp.tacs.api.requerimientos;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.externalApis.ExternalApis;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Component
public class ReqCalcularDistancias extends Requerimiento<Partida,Partida>{
    @Autowired
    private ExternalApis externalApis;

    @Override protected Partida execute(Partida request) {
        HashSet<Float> distancias = distanciasTotales(request);
        request.setMaxDist(distancias.stream().max(Float::compareTo).get());
        request.setMinDist(distancias.stream().min(Float::compareTo).get());
        return request;
    }

    private HashSet<Float> distanciasTotales(Partida partida) {
        var coordenadas = partida.getMunicipios()
                .stream()
                .map(id -> externalApis.getCoordenadas(id.toString()))
                .collect(Collectors.toSet());

        var combinaciones = Sets.combinations(coordenadas, 2);

        var distancias = new HashSet<Float>();
        combinaciones.forEach(combinacion -> {
            var list = new ArrayList<>(combinacion);
            var list0 = new ArrayList<>(Collections.singleton(Double.valueOf(list.get(0))));
            var list1 = new ArrayList<>(Collections.singleton(Double.valueOf(list.get(0))));
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
