package tp.tacs.api.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.PartidaSinInfo;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.EstadoDeJuegoMapper;
import tp.tacs.api.mappers.PartidaSinInfoPartidaMapper;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadoDeJuegoModel;
import tp.tacs.api.servicios.ServicioPartida;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository("partidaDaoMemoria")
@ConditionalOnProperty(prefix="application", name="persistance-implementation", havingValue = "memoria")
public class PartidaDaoMemoria implements PartidaDao {

    @Autowired
    private ServicioPartida servicioPartida;
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

    @Override
    public Partida get(String id) {
        return partidas.stream()
                .filter(partida -> partida.getId().equals(id))
                .findFirst().orElse(null);
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
        if(!partidas.contains(element)) {
            asignarId(element);
            partidas.add(element);
        }
    }

    private synchronized void asignarId(Partida partida) {
        partida.setId(partidaId.toString());
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

    private Stream<PartidaSinInfo> getPartidasFiltradasStream(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado) {
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
        return partidasFiltradasStream;
    }

    public List<PartidaSinInfo> getPartidasFiltradas(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado) {
        Stream<PartidaSinInfo> partidasFiltradasStream = getPartidasFiltradasStream(fechaInicio, fechaFin, estado);
        return partidasFiltradasStream.collect(Collectors.toList());
    }

    public List<PartidaSinInfo> getPartidasFiltradasUsuario(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado, Usuario usuario) {
        Stream<PartidaSinInfo> partidasFiltradasStream = getPartidasFiltradasStream(fechaInicio, fechaFin, estado);
        partidasFiltradasStream = partidasFiltradasStream.filter(partida -> servicioPartida.perteneceALaPartida(partida, usuario));
        return partidasFiltradasStream.collect(Collectors.toList());
    }
}
