package tp.tacs.api.daos;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.PartidaSinInfo;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.EstadoDeJuegoMapper;
import tp.tacs.api.mappers.PartidaSinInfoPartidaMapper;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadoDeJuegoModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository("partidaDaoMongo")
@Primary
@ConditionalOnProperty(prefix = "application", name = "persistance-implementation", havingValue = "mongo")
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

    private Criteria generarCriteriaFechas(Date fechaInicio, Date fechaFin) {
        if (fechaInicio != null) {
            if (fechaFin != null)
                return where("fechaCreacion").gte(fechaInicio).lt(fechaFin);
            return where("fechaCreacion").gte(fechaInicio);
        }
        if (fechaFin != null)
            return where("fechaCreacion").lt(fechaFin);
        return new Criteria();
    }

    private ArrayList<Criteria> generarCriteria(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado) {
        var criteria = new ArrayList<Criteria>();
        criteria.add(generarCriteriaFechas(fechaInicio, fechaFin));
        if (estado != null)
            criteria.add(where("estado").is(estado));
        return criteria;
    }

    private Query queryPartidasFiltradas(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado) {
        var criteria = this.generarCriteria(fechaInicio, fechaFin, estado);
        Query query = new Query();
        criteria.forEach(query::addCriteria);
        return query;
    }

    @Override
    public List<PartidaSinInfo> getPartidasFiltradas(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado) {
        var query = queryPartidasFiltradas(fechaInicio, fechaFin, estado);
        var partidas = this.mongoOps.find(query, Partida.class);
        return partidas.stream().map(partidaSinInfoPartidaMapper::unwrap).collect(Collectors.toList());
    }


    @Override
    public List<PartidaSinInfo> getPartidasFiltradasUsuario(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado, Usuario usuario) {
        var query = this.queryPartidasFiltradas(fechaInicio, fechaFin, estado);
        query.addCriteria(where(usuario.getId()).in("idsJugadoresOriginales"));
        var partidas = this.mongoOps.find(query, Partida.class);
        return partidas.stream().map(partidaSinInfoPartidaMapper::unwrap).collect(Collectors.toList());
    }

}
