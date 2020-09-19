package tp.tacs.api.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.PartidaSinInfo;
import tp.tacs.api.mappers.EstadoDeJuegoMapper;
import tp.tacs.api.mappers.PartidaSinInfoPartidaMapper;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadoDeJuegoModel;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PartidaDao implements Dao<Partida> {

    @Autowired
    private EstadoDeJuegoMapper estadoDeJuegoMapper;
    @Autowired
    private PartidaSinInfoPartidaMapper partidaSinInfoPartidaMapper;

    private List<Partida> partidas;
    private Long partidaId;

    @PostConstruct
    public void postConstruct() {
        partidas = new ArrayList<>();
        partidaId = 0L;
    }

    public void limpiar() {
        partidas = new ArrayList<>();
    }

    @Override
    public Partida get(Long id) {
        return partidas.stream().filter(partida -> partida.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    @Override
    public List<Partida> getAll() {
        return partidas;
    }

    public List<PartidaSinInfo> getAllSinInfo() {
        return partidas.stream().map(partida -> partidaSinInfoPartidaMapper.unwrap(partida)).collect(Collectors.toList());
    }

    @Override
    public void save(Partida element) {
        asignarId(element);
        partidas.add(element);
    }

    private synchronized void asignarId(Partida municipio) {
        municipio.setId(partidaId);
        partidaId++;
    }

    @Override
    public void delete(Partida element) {
        partidas = partidas.stream().filter(partida -> !partida.getId().equals(element.getId()))
                .collect(Collectors.toList());
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
        return partidas.stream().filter(partida -> partida.getFechaCreacion().after(fechaInicio) && partida.getFechaCreacion().before(fechaFin))
                .collect(Collectors.toList());
    }

    private List<PartidaSinInfo> partidasSinInfoEntre(Date fechaInicio, Date fechaFin) {
        return partidas
                .stream()
                .filter(partida -> partida.getFechaCreacion().after(fechaInicio) && partida.getFechaCreacion().before(fechaFin))
                .map(partida -> partidaSinInfoPartidaMapper.unwrap(partida))
                .collect(Collectors.toList());
    }

    public List<PartidaSinInfo> getPartidasFiltradas(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado) {
        Stream<PartidaSinInfo> partidasFiltradasStream = partidas
            .stream()
            .map(partida -> partidaSinInfoPartidaMapper.unwrap(partida));

        if (fechaInicio != null) {
            partidasFiltradasStream = partidasFiltradasStream
                .filter(partida -> partida.getFechaCreacion().after(fechaInicio));
        }

        if (fechaFin != null) {
            partidasFiltradasStream = partidasFiltradasStream
                .filter(partida -> partida.getFechaCreacion().before(fechaFin));
        }

        if (estado != null) {
            var estadoDeJuego = estadoDeJuegoMapper.toEntity(estado);
            partidasFiltradasStream = partidasFiltradasStream
                .filter(partida -> partida.getEstado().equals(estadoDeJuego));
        }

        return partidasFiltradasStream.collect(Collectors.toList());
    }
}
