package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.requerimientos.models.ReqRepartirMunicipiosModel;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReqRepartirMunicipios extends Requerimiento<ReqRepartirMunicipiosModel, Partida> {
    @Autowired
    private ExternalApis externalApis;
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private UsuarioDao usuarioDao;

    private Long municipioId = 0L;

    @Override protected Partida execute(ReqRepartirMunicipiosModel request) {
        var cantidad = Math.floor(request.getCantidadMunicipios() / request.getPartida().getJugadoresIds().size());
        List<Municipio> municipios = externalApis.getMunicipios(request.getPartida().getIdProvincia(), request.getCantidadMunicipios().intValue());
        for (var municipio : municipios) {
            asignarId(municipio);
        }

        request.getPartida().getJugadoresIds().forEach(id -> {
            Usuario jugador =  usuarioDao.get(id);
            for (int i = 0; i < cantidad; i++) {
                municipios.stream()
                        .filter(Municipio::estaBacante)
                        .findAny()
                        .ifPresent(municipio -> {
                            municipio.setDuenio(jugador);
                            municipioDao.save(municipio);
                        });
            }
        });
        request.getPartida().setMunicipios(municipios.stream().map(Municipio::getId).collect(Collectors.toList()));
        return request.getPartida();
    }

    private synchronized void asignarId(Municipio municipio) {
        municipio.setId(municipioId);
        municipioId++;
    }
}
