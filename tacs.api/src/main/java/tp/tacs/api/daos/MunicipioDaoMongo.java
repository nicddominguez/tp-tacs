package tp.tacs.api.daos;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class MunicipioDaoMongo implements MunicipioDao {

    private final MongoOperations mongoOps;

    @Autowired
    public MunicipioDaoMongo(MongoClient mongoClient) {
        this.mongoOps = new MongoTemplate(mongoClient, "tacs");
    }

    @Override
    public Municipio get(Long id) {
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
    public List<Municipio> getByIds(List<Long> idsMunicipios) {
        return this.mongoOps.find(
                new Query(where("_id").in(idsMunicipios)),
                Municipio.class
        );
    }

}
