package tp.tacs.api.daos;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository("municipioDaoMongo")
@Primary
@ConditionalOnProperty(prefix="application", name="persistance-implementation", havingValue = "mongo")
public class MunicipioDaoMongo implements MunicipioDao {

    private final MongoOperations mongoOps;

    @Autowired
    public MunicipioDaoMongo(MongoClient mongoClient) {
        this.mongoOps = new MongoTemplate(mongoClient, "tacs");
    }

    @Override
    public Municipio get(String id) {
        return this.mongoOps.findById(id, Municipio.class);
    }

    @Override
    public List<Municipio> getAll() {
        return this.mongoOps.findAll(Municipio.class);
    }

    @Override
    public void save(Municipio element) {
        this.mongoOps.save(element);
    }

    @Override
    public void delete(Municipio element) {
        this.mongoOps.remove(element);
    }


    @Override
    public List<Municipio> getByIds(List<String> idsMunicipios) {
        return this.mongoOps.find(
                new Query(where("_id").in(idsMunicipios)),
                Municipio.class
        );
    }

    @Override
    public Set<Municipio> municipiosConDuenio(Partida partida) {
        var query = new Query(
                where("_id").in(partida.getMunicipios())
                        .and("duenio").exists(true)
        );
        return new HashSet<>(this.mongoOps.find(query, Municipio.class));
    }

}
