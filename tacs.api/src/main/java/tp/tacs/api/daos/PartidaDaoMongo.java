package tp.tacs.api.daos;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.PartidaSinInfo;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.EstadoDeJuegoMapper;
import tp.tacs.api.mappers.PartidaSinInfoPartidaMapper;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadoDeJuegoModel;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class PartidaDaoMongo implements PartidaDao {

    private final MongoOperations mongoOps;
    private final PartidaSinInfoPartidaMapper partidaSinInfoPartidaMapper;
    private final EstadoDeJuegoMapper estadoDeJuegoMapper;

    @Override
    public Partida get(String id) {
        return this.mongoOps.findById(id, Partida.class);
    }

    @Autowired
    public PartidaDaoMongo(MongoClient mongoClient, PartidaSinInfoPartidaMapper partidaSinInfoPartidaMapper, EstadoDeJuegoMapper estadoDeJuegoMapper) {
        this.mongoOps = new MongoTemplate(mongoClient, "tacs");
        this.partidaSinInfoPartidaMapper = partidaSinInfoPartidaMapper;
        this.estadoDeJuegoMapper = estadoDeJuegoMapper;
    }

    @Override
    public List<Partida> getAll() {
        return this.mongoOps.findAll(Partida.class);
    }

    @Override
    public void save(Partida element) {
        this.mongoOps.save(element);
    }

    @Override
    public void delete(Partida element) {
        this.mongoOps.remove(element);
    }

    private Long cantidadPartidas(Date fechaInicio, Date fechaFin, Estado estado) {
        Query query = new Query(where("fechaCreacion").gte(fechaInicio).lt(fechaFin).and("estado").is(estado));
        return this.mongoOps.count(query, Partida.class);
    }

    @Override
    public EstadisticasDeJuegoModel estadisticas(Date fechaInicio, Date fechaFin) {
        Long partidasEnCurso = this.cantidadPartidas(fechaInicio, fechaFin, Estado.EN_CURSO);
        Long partidasTerminadas = this.cantidadPartidas(fechaInicio, fechaFin, Estado.TERMINADA);
        Long partidasCanceladas = this.cantidadPartidas(fechaInicio, fechaFin, Estado.CANCELADA);
        Long partidasCreadas = partidasEnCurso + partidasTerminadas + partidasCanceladas;
        return new EstadisticasDeJuegoModel()
                .partidasCreadas(partidasCreadas)
                .partidasEnCurso(partidasEnCurso)
                .partidasTerminadas(partidasTerminadas)
                .partidasCanceladas(partidasCanceladas);
    }

    @Override
    public List<PartidaSinInfo> getPartidasFiltradas(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado) {
        Query query;
        if(estado == null) {
            query = new Query(where("fechaCreacion").gte(fechaInicio).lt(fechaFin));
        }
        else {
            var estadoDeJuego = estadoDeJuegoMapper.toEntity(estado);
            query = new Query(where("fechaCreacion").gte(fechaInicio).lt(fechaFin).and("estado").is(estadoDeJuego));
        }
        var partidas = this.mongoOps.find(query, Partida.class);
        return partidas.stream().map(partidaSinInfoPartidaMapper::unwrap).collect(Collectors.toList());
    }

    @Override
    public List<PartidaSinInfo> getPartidasFiltradasUsuario(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado, Usuario usuario) {
        Query query = new Query(where("fechaCreacion").gte(fechaInicio).lt(fechaFin).and("estado").is(estado));
        var partidas = this.mongoOps.find(query, Partida.class);
        //TODO: hacer la operación de búsqueda por usuario en la db
        var partidasDelUsuario = partidas.stream().filter(partida ->
                partida.getIdsJugadoresOriginales().contains(usuario.getId()));
        return partidasDelUsuario.map(partidaSinInfoPartidaMapper::unwrap).collect(Collectors.toList());
    }

}
