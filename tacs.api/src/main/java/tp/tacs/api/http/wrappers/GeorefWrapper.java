package tp.tacs.api.http.wrappers;

import tp.tacs.api.http.externalApis.models.MunicipioApi;
import tp.tacs.api.model.MunicipioEnJuegoModel;

public class GeorefWrapper extends AbstractWrapper <MunicipioApi, MunicipioEnJuegoModel> {


    @Override
    protected MunicipioEnJuegoModel wrapModel(MunicipioApi model) {
        MunicipioEnJuegoModel municipio = new MunicipioEnJuegoModel();
        municipio.setId(Long.valueOf(model.getId()));
        return municipio;
    }

    @Override
    protected MunicipioApi unwrapModel(MunicipioEnJuegoModel model) {
        return null;
    }
}