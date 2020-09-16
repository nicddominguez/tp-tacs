package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.externalApis.ExternalApis;

import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Component
public class ReqCalcularAlturas extends AbstractRequerimiento<Partida, Partida> {
    @Autowired
    private ExternalApis externalApis;
    @Autowired
    private MunicipioDao municipioDao;

    @Override protected Partida execute(Partida request) {
        Supplier<Stream<Float>> alturas = () -> request.getMunicipios().stream().map(municipio -> {
            Municipio municipio1 = municipioDao.get(municipio);
            return externalApis.getAltura(municipio1.getExternalApiId());
        });
        DoubleStream doubleStreamMax = alturas.get().mapToDouble(value -> value.doubleValue());
        DoubleStream doubleStreamMin = alturas.get().mapToDouble(value -> value.doubleValue());
        request.setMaxAltura((float) doubleStreamMax.max().getAsDouble());
        request.setMinAltura((float) doubleStreamMin.min().getAsDouble());
        return request;
    }
}
