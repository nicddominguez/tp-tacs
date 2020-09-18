package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.externalApis.models.MunicipioApi;

@Component
public class GeorefMapper extends AbstractMapper<MunicipioApi, Municipio> {

    @Override
    protected Municipio wrapModel(MunicipioApi model) {
        return Municipio.builder()
                .nombre(model.getNombre())
                .externalApiId(model.getId())
                .latitud(model.getCentroide_lat() != null ? model.getCentroide_lat().doubleValue() : null)
                .longitud(model.getCentroide_lon() != null ? model.getCentroide_lon().doubleValue() : null)
                .build();
    }

    @Override
    protected MunicipioApi unwrapModel(Municipio model) {
        return null;
    }
}