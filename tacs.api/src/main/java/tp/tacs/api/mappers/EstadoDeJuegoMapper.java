package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.model.EstadoDeJuegoModel;

@Component
public class EstadoDeJuegoMapper {

    public EstadoDeJuegoModel toModel(Estado entity) {
        switch (entity) {
            case EN_CURSO:
                return EstadoDeJuegoModel.fromValue("EnProgreso");
            case CANCELADA:
                return EstadoDeJuegoModel.fromValue("Cancelada");
            case TERMINADA:
                return EstadoDeJuegoModel.fromValue("Terminada");
        }
        return EstadoDeJuegoModel.fromValue("Terminada");
    }

    public Estado toEntity(EstadoDeJuegoModel model){
        return Estado.fromValue(model.toString().toUpperCase());
    }
}
