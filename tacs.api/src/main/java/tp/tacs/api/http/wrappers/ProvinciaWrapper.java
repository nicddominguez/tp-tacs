package tp.tacs.api.http.wrappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.http.externalApis.models.Provincia;
import tp.tacs.api.model.ProvinciaModel;

@Component
public class ProvinciaWrapper extends AbstractWrapper<Provincia, ProvinciaModel>{

    @Override
    protected ProvinciaModel wrapModel(Provincia model) {
        return new ProvinciaModel().id(Long.valueOf(model.getId())).nombre(model.getNombre());
    }

    @Override
    protected Provincia unwrapModel(ProvinciaModel model) {
        return null;
    }
}
