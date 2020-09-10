package tp.tacs.api.dominio.partida;

import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.EstadoDeJuegoMapper;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadoDeJuegoModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RepoPartidas {

    private EstadoDeJuegoMapper estadoDeJuegoMapper = new EstadoDeJuegoMapper();

    private List<Partida> partidas = new ArrayList<>();

    private static RepoPartidas instancia = null;

    public static RepoPartidas instance() {
        if (instancia == null) {
            instancia = new RepoPartidas();
        }
        return instancia;
    }

    public Partida getPartida(Long idPartida){
        var jugadores = Arrays.asList(new Usuario(1L, "jasdfo@gmail.com", "juan"),
                new Usuario(2L, "aa@gmail.com", "carlos"));
        List<Municipio> municipios = RepoMunicipios.instance().getMunicipios("buenos aires", 5);
        var partida = new Partida(jugadores, Estado.EN_CURSO, "123",
                municipios, new ModoRapido(), new Date());
        return partida;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public void agregarPartida(Partida partida) {
        this.partidas.add(partida);
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
        return this.partidas
                .stream()
                .filter(partida ->
                        partida.getFechaCreacion().after(fechaInicio) && partida.getFechaCreacion().before(fechaFin))
                .collect(Collectors.toList());
    }

    public List<Partida> getPartidasFiltradas(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado) {
        var estadoDeJuego = estadoDeJuegoMapper.toEntity(estado);
        return partidasEntre(fechaInicio, fechaFin)
                .stream()
                .filter(partida -> partida.getEstado().equals(estadoDeJuego))
                .collect(Collectors.toList());
    }
}
