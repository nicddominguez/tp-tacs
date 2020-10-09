package tp.tacs.api.daos;

import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.PartidaSinInfo;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadoDeJuegoModel;

import java.util.Date;
import java.util.List;

public interface PartidaDao {

    List<Partida> getAll();

    Partida get(String id);

    void save(Partida element);

    void delete(Partida element);

    EstadisticasDeJuegoModel estadisticas(Date fechaInicio, Date fechaFin);

    List<PartidaSinInfo> getPartidasFiltradas(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado);

    List<PartidaSinInfo> getPartidasFiltradasUsuario(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado, Usuario usuario);

}
