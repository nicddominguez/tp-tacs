package tp.tacs.api.requerimientos;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.handler.PartidaException;
import tp.tacs.api.requerimientos.models.ReqValidarAccionModel;

@Component
public class ReqValidarAccion extends AbstractRequerimiento<ReqValidarAccionModel,Void> {

    @Override protected Void execute(ReqValidarAccionModel request) {
        if (!request.getPartida().getEstado().equals(Estado.EN_CURSO)) {
            throw new PartidaException("La partida no está en curso. No se pudo " + request.getAccion());
        }
        if (request.getPartida().idUsuarioEnTurnoActual() != request.getDuenio().getId()) {
            throw new PartidaException("No es el turno del dueño del municipio.");
        }
        return null;
    }
}
