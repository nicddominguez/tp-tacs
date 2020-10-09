package tp.tacs.api.daos;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class UsuarioDaoMongo implements UsuarioDao {

    private final MongoOperations mongoOps;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioDaoMongo(MongoClient mongoClient, UsuarioMapper mapper) {
        this.mongoOps = new MongoTemplate(mongoClient, "tacs");
        this.usuarioMapper = mapper;
    }

    @PostConstruct
    private void postConstruct() {
        Usuario coen = Usuario.builder().rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("Coen").mail("asd@gmail.com").build();
        Usuario juan = Usuario.builder().rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("Juan").mail("asd@gmail.com").build();
        Usuario alejo = Usuario.builder().rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("Alejo").mail("asd@gmail.com").build();
        Usuario pablo = Usuario.builder().rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("Pablo").mail("asd@gmail.com").build();
        Usuario franco = Usuario.builder().rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("Franco").mail("asd@gmail.com").build();
        Usuario nico = Usuario.builder().rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("Nico").mail("asd@gmail.com").build();
        this.save(coen);
        this.save(juan);
        this.save(alejo);
        this.save(pablo);
        this.save(franco);
        this.save(nico);
    }

    @Override
    public Usuario get(String id) {
        return this.mongoOps.findById(id, Usuario.class);
    }

    @Override
    public List<Usuario> getAll() {
        return this.mongoOps.findAll(Usuario.class);
    }

    @Override
    public void save(Usuario element) {
        this.mongoOps.save(element);
    }

    @Override
    public void delete(Usuario element) {
        this.mongoOps.remove(element);
    }

    @Override
    public Usuario getByGoogleId(String googleId) {
        return this.mongoOps.findOne(
                new Query(where("googleId").is(googleId)),
                Usuario.class
        );
    }

    @Override
    public Usuario getByUsername(String username) {
        return this.mongoOps.findOne(
                new Query(where("nombre").is(username)),
                Usuario.class
        );
    }

    @Override
    public List<Usuario> getByIds(List<String> idsUsuarios) {
        return this.mongoOps.find(
                new Query(where("_id").in(idsUsuarios)),
                Usuario.class
        );
    }

    @Override
    public EstadisticasDeUsuarioModel estadisticas(String idUsuario) {
        Usuario usuario = this.get(idUsuario);
        return new EstadisticasDeUsuarioModel()
                .usuario(this.usuarioMapper.wrap(usuario))
                .partidasJugadas(usuario.getPartidasJugadas())
                .partidasGanadas(usuario.getPartidasGanadas())
                .rachaActual(usuario.getRachaActual());
    }

    @Override
    public List<EstadisticasDeUsuarioModel> scoreBoard() {
        Query query = new Query();
        query.fields()
                .include("partidasJugadas")
                .include("partidasGanadas")
                .include("rachaActual")
                .include("nombre")
                .include("_id");
        return this.mongoOps.find(query, Usuario.class)
                .stream().map(usuario -> new EstadisticasDeUsuarioModel()
                                            .usuario(this.usuarioMapper.wrap(usuario))
                                            .partidasJugadas(usuario.getPartidasJugadas())
                                            .partidasGanadas(usuario.getPartidasGanadas())
                                            .rachaActual(usuario.getRachaActual())
                ).collect(Collectors.toList());
    }

}
