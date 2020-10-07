package tp.tacs.api.servicios;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.utils.Utils;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ServicioAdmin {

    @Autowired
    private Utils utils;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private PartidaDao partidaDao;

    public EstadisticasDeJuegoModel estadisticasDeJuego(Date fechaInicio, Date fechaFin) {
        return partidaDao.estadisticas(fechaInicio, fechaFin);
    }

    public EstadisticasDeUsuarioModel estadisticasDeUsuario(Long idUsuario) {
        return usuarioDao.estadisticas(idUsuario);
    }

    public List<EstadisticasDeUsuarioModel> tablaDePuntos(Long tamanioPagina, Long pagina) {
        return utils.obtenerListaPaginada(pagina, tamanioPagina, usuarioDao.scoreBoard());
    }
}
