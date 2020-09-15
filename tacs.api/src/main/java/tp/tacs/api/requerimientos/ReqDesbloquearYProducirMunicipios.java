package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.requerimientos.Models.ReqProducirModel;

import java.util.List;

@Component
public class ReqDesbloquearYProducirMunicipios extends AbstractRequerimiento<Partida,Void> {
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private ReqProducir reqProducir;

    @Override protected Void execute(Partida request) {
        List<Municipio> municipios = municipioDao.getByIds(request.getMunicipios());
        municipios.forEach(municipio -> {
            municipio.desbloquear();
            Municipio municipioProducido = reqProducir.run(ReqProducirModel.builder().municipio(municipio).partida(request).build());
            municipioDao.save(municipioProducido);
        });
        return null;
    }
}
