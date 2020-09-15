package tp.tacs.api.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.mappers.EstadoDeJuegoMapper;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadoDeJuegoModel;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PartidaDao implements Dao<Partida> {

    @Autowired
    private EstadoDeJuegoMapper estadoDeJuegoMapper;

    private List<Partida> partidas;

    @PostConstruct
    public void postConstruct() {
        partidas = new ArrayList<>();
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

    @Override
    public void save(Partida element) {
        partidas.add(element);
    }

    @Override
    public void delete(Partida element) {
        partidas = partidas.stream().filter(partida -> !partida.getId().equals(element.getId()))
                .collect(Collectors.toList());
    }

    public void update(Partida partida){
        delete(partida);
        save(partida);
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

    public List<Partida> getPartidasFiltradas(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado) {
        if (fechaInicio != null || fechaFin != null) { //TODO
            if (estado != null) {
                var estadoDeJuego = estadoDeJuegoMapper.toEntity(estado);
                return partidasEntre(fechaInicio, fechaFin)
                        .stream()
                        .filter(partida -> partida.getEstado().equals(estadoDeJuego))
                        .collect(Collectors.toList());
            } else {
                return partidasEntre(fechaInicio, fechaFin);
            }
        } else {
            return this.getAll();
        }
    }
}
