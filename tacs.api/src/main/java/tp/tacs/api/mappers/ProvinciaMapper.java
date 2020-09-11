package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.model.ProvinciaModel;

@Component
public class ProvinciaMapper {

    public ProvinciaModel toModel(String entity){
        return new ProvinciaModel().nombre(entity).id(123L);
    }
}
