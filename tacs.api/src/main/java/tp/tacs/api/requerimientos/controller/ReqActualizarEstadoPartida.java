package tp.tacs.api.requerimientos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.requerimientos.AbstractRequerimiento;
import tp.tacs.api.requerimientos.models.ReqActualizarEstadoPartidaRequest;

@Component
public class ReqActualizarEstadoPartida extends AbstractRequerimiento<ReqActualizarEstadoPartidaRequest,Void> {
    @Autowired
    private PartidaDao partidaDao;

    @Override protected Void execute(ReqActualizarEstadoPartidaRequest request) {
        Partida partida = partidaDao.get(request.getIdPartida());
        partida.setEstado(request.getEstado());
        partidaDao.update(partida);
        return null;
    }
}
