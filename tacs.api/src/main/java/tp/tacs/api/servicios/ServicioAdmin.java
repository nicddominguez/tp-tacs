package tp.tacs.api.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.PartidaDaoMongo;
import tp.tacs.api.daos.UsuarioDaoMemoria;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.utils.Utils;

import java.util.Date;
import java.util.List;

@Service
public class ServicioAdmin {
    @Autowired
    private Utils utils;
    @Autowired
    private UsuarioDaoMemoria usuarioDaoMemoria;
    @Autowired
    private PartidaDaoMongo partidaDao;

    public EstadisticasDeJuegoModel estadisticasDeJuego(Date fechaInicio, Date fechaFin) {
        return partidaDao.estadisticas(fechaInicio, fechaFin);
    }

    public EstadisticasDeUsuarioModel estadisticasDeUsuario(Long idUsuario) {
        return usuarioDaoMemoria.estadisticas(idUsuario);
    }

    public List<EstadisticasDeUsuarioModel> tablaDePuntos(Long pagina, Long tamanioPagina) {
        return utils.obtenerListaPaginada(pagina, tamanioPagina, usuarioDaoMemoria.scoreBoard());
    }
}
