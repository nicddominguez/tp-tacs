package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;

import java.util.List;

@Component
public class ReqDesbloquearYProducirMunicipios extends AbstractRequerimiento<Partida,Void> {
    @Autowired
    private MunicipioDao municipioDao;

    @Override protected Void execute(Partida request) {
        List<Municipio> municipios = municipioDao.getByIds(request.getMunicipios());
        municipios.forEach(municipio -> {
            municipio.desbloquear();
            municipio.producir();
            municipioDao.save(municipio);
        });
        return null;
    }
}
