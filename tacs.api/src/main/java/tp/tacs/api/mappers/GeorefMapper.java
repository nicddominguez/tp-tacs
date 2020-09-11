package tp.tacs.api.mappers;

import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.externalApis.models.MunicipioApi;

public class GeorefMapper extends AbstractMapper<MunicipioApi, Municipio> {


    @Override
    protected Municipio wrapModel(MunicipioApi model) {
        Municipio municipio = new Municipio();
        municipio.setIdMunicipioReal(model.getId());
        return municipio;
    }

    @Override
    protected MunicipioApi unwrapModel(Municipio model) {
        return null;
    }
}