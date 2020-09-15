package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.handler.PartidaException;

@Component
public class ReqPasarTurno extends AbstractRequerimiento<Partida, Void> {
    @Autowired
    private ReqHayGanador reqHayGanador;
    @Autowired
    private ReqTerminarPartida reqTerminarPartida;
    @Autowired
    private ReqDesbloquearYProducirMunicipios reqDesbloquearYProducirMunicipios;

    @Override protected Void execute(Partida request) {
        if (Estado.EN_CURSO.equals(request.getEstado())) {
            if (reqHayGanador.run(request)) {
                reqTerminarPartida.run(request);
            } else {
                request.asignarProximoTurno();
                reqDesbloquearYProducirMunicipios.run(request);
            }
            return null;
        } else {
            throw new PartidaException("No se puede pasar el turno de una partida que no este en curso");
        }
    }
}