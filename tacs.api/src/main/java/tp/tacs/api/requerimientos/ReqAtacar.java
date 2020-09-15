package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.dominio.municipio.AtaqueMunicipiosResponse;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.requerimientos.Models.ReqAtacarModel;

@Component
public class ReqAtacar extends Requerimiento<ReqAtacarModel, AtaqueMunicipiosResponse> {
    @Autowired
    private PartidaDao partidaDao;

    @Autowired
    private MunicipioDao municipioDao;

    @Override
    protected AtaqueMunicipiosResponse execute(ReqAtacarModel request) {
        Municipio municipioAtacante = municipioDao.get(request.getIdMunicipioAtacante());
        Municipio municipioDefensor = municipioDao.get(request.getIdMunicipioDefensor());
        Partida partida = partidaDao.get(request.getIdPartida());
        AtaqueMunicipiosResponse ataqueMunicipiosResponse = partida.atacar(municipioAtacante, municipioDefensor);

        return ataqueMunicipiosResponse;
    }
}
