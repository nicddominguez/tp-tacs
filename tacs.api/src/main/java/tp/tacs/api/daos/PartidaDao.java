package tp.tacs.api.daos;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.mappers.EstadoDeJuegoMapper;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadoDeJuegoModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class PartidaDao implements Dao<Partida> {

    private final EstadoDeJuegoMapper estadoDeJuegoMapper = new EstadoDeJuegoMapper();

    private static HashMap<Long, Partida> partidas = new HashMap<>();

    private static Long i = 0L;

    public synchronized void limpiar() {
        PartidaDao.partidas = new HashMap<>();
        PartidaDao.i = 0L;
    }

    @Override
    public Partida get(Long id) {
        return PartidaDao.partidas.get(id);
    }

    @Override
    public synchronized List<Partida> getAll() {
        return new ArrayList<>(PartidaDao.partidas.values());
    }

    @Override
    public synchronized void save(Partida element) {
        element.setId(PartidaDao.i);
        PartidaDao.partidas.put(element.getId(), element);
        PartidaDao.i++;
    }

    @Override
    public synchronized void delete(Partida element) {
        PartidaDao.partidas.remove(element.getId());
    }

    public EstadisticasDeJuegoModel estadisticas(Date fechaInicio, Date fechaFin) {
        List<Partida> partidas = partidasEntre(fechaInicio, fechaFin);

        Long partidasCreadas = (long) partidas.size();

        Long partidasEnCurso = partidas.stream().filter(partida -> partida.getEstado().equals(Estado.EN_CURSO)).count();

        Long partidasTerminadas = partidas.stream().filter(partida -> partida.getEstado().equals(Estado.TERMINADA)).count();

        Long partidasCanceladas = partidas.stream().filter(partida -> partida.getEstado().equals(Estado.CANCELADA)).count();

        return new EstadisticasDeJuegoModel()
                .partidasCreadas(partidasCreadas)
                .partidasEnCurso(partidasEnCurso)
                .partidasTerminadas(partidasTerminadas)
                .partidasCanceladas(partidasCanceladas);
    }

    private List<Partida> partidasEntre(Date fechaInicio, Date fechaFin) {
        return PartidaDao.partidas
                .values()
                .stream()
                .filter(partida ->
                        partida.getFechaCreacion().after(fechaInicio) && partida.getFechaCreacion().before(fechaFin))
                .collect(Collectors.toList());
    }

    public List<Partida> getPartidasFiltradas(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado) {
        if (fechaInicio != null || fechaFin != null) { //TODO
            if (estado != null) {
                var estadoDeJuego = estadoDeJuegoMapper.toEntity(estado);
                return partidasEntre(fechaInicio, fechaFin)
                        .stream()
                        .filter(partida -> partida.getEstado().equals(estadoDeJuego))
                        .collect(Collectors.toList());
            } else {
                return new ArrayList<>(partidasEntre(fechaInicio, fechaFin));
            }
        } else {
            return this.getAll();
        }
    }
}
