package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;

import java.util.List;

@Component
public class ReqHayGanador extends AbstractRequerimiento<Partida,Boolean> {
    @Autowired
    private MunicipioDao municipioDao;

    @Override protected Boolean execute(Partida request) {
        List<Municipio> municipios = municipioDao.getByIds(request.getJugadoresIds());
        return request.getJugadoresIds().stream().anyMatch(juagadorId -> esDuenioDeTodo(juagadorId, municipios));
    }

    private boolean esDuenioDeTodo(Long userId, List<Municipio> municipios) {
        return municipios.stream().allMatch(municipio -> municipio.esDe(userId));
    }
}
