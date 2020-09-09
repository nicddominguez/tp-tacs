package tp.tacs.api.dominio.partida;

import org.springframework.beans.factory.annotation.Autowired;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.PartidaMapper;
import tp.tacs.api.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class RepoPartidas {

    @Autowired
    private PartidaMapper partidaMapper;

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
                municipios, new ModoFacil(), new Date());
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
        List<Partida> partidas = this.partidas
                .stream()
                .filter(partida ->
                        partida.getFechaCreacion().after(fechaInicio) && partida.getFechaCreacion().before(fechaFin))
                .collect(Collectors.toList());

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
}
