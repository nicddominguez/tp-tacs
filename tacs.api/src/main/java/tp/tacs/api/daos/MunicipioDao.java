package tp.tacs.api.daos;

import tp.tacs.api.dominio.municipio.Municipio;

import java.util.List;

public interface MunicipioDao extends Dao<Municipio> {


    Municipio get (Long id);

    List<Municipio> getAll();

    void save(Municipio element);

    void delete(Municipio element);

    List<Municipio> getByIds(List<Long> ids);
}
