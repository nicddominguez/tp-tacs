package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.http.externalApis.models.Provincia;
import tp.tacs.api.model.ProvinciaModel;

@Component
public class ProvinciaMapper extends AbstractMapper<Provincia,ProvinciaModel>{

    @Override protected ProvinciaModel wrapModel(Provincia model) {
        return new ProvinciaModel().nombre(model.getNombre()).id(Long.valueOf(model.getId()));
    }

    @Override protected Provincia unwrapModel(ProvinciaModel model) {
        return null;
    }
}
