package tp.tacs.api.requerimientos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.requerimientos.AbstractRequerimiento;

@Component
public class ReqObtenerPartidaPorId extends AbstractRequerimiento<Long, Partida> {
    @Autowired
    private PartidaDao partidaDao;

    @Override protected Partida execute(Long request) {
        return partidaDao.get(request);
    }
}
