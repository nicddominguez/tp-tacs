package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReqTerminarPartida extends AbstractRequerimiento<Partida, Void> {
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private PartidaDao partidaDao;

    @Override protected Void execute(Partida request) {
        request.setEstado(Estado.TERMINADA);
        Usuario ganador = usuarioConMasMunicipios(request);
        ganador.aumentarPartidasGanadas();
        ganador.aumentarRachaActual();
        usuarioDao.save(ganador);
        request.getJugadoresIds().forEach(jugadorId -> {
            Usuario usuario = usuarioDao.get(jugadorId);
            usuario.aumentarPartidasJugadas();
            if (!usuario.getId().equals(ganador.getId()))
                usuario.reiniciarRacha();
            usuarioDao.save(usuario);
        });
        partidaDao.save(request);
        return null;
    }
    public Usuario usuarioConMasMunicipios(Partida partida) {
        var municipiosConDuenio = municipioDao.getByIds(partida.getMunicipios()).stream()
                .filter(municipio -> municipio.getDuenio() != null).collect(Collectors.toSet());

        //Agrupa por usuario sumando la cantidad de municipios que tenga
        var ganadosPorUsuario = municipiosConDuenio.stream()
                .collect(Collectors.groupingBy(Municipio::getDuenio, Collectors.counting()));

        return Collections
                .max(ganadosPorUsuario.entrySet(), Map.Entry.comparingByValue())
                .getKey();
    }
}
