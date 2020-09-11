package tp.tacs.api.http.wrappers;

import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.externalApis.models.MunicipioApi;

public class GeorefWrapper extends AbstractWrapper <MunicipioApi, Municipio> {


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