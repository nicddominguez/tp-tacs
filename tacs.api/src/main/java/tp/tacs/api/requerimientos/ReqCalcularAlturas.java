package tp.tacs.api.requerimientos;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.externalApis.ExternalApis;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Component
public class ReqCalcularAlturas extends Requerimiento<Partida, Partida>{
    @Autowired
    private ExternalApis externalApis;

    @Override protected Partida execute(Partida request) {
        Stream<Float> alturas = request.getMunicipios().stream().map(municipio -> externalApis.getAltura(municipio.toString()));
        DoubleStream doubleStreamMax = alturas.mapToDouble(value -> value.doubleValue());
        DoubleStream doubleStreamMin = alturas.mapToDouble(value -> value.doubleValue());
        request.setMaxAltura((float) doubleStreamMax.max().getAsDouble());
        request.setMinAltura((float) doubleStreamMin.min().getAsDouble());
        return request;
    }
}
