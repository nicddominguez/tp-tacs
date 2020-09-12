package tp.tacs.api.daos;

import tp.tacs.api.dominio.municipio.Municipio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MunicipioDao implements Dao<Municipio> {

    private static final Map<Long, Municipio> municipios = new HashMap<>();

    private Long i = 0L;

    @Override
    public synchronized Municipio get(Long id) {
        return MunicipioDao.municipios.get(id);
    }

    @Override
    public synchronized List<Municipio> getAll() {
        return new ArrayList<>(MunicipioDao.municipios.values());
    }

    @Override
    public synchronized void save(Municipio element) {
        element.setId(this.i);
        MunicipioDao.municipios.put(element.getId(), element);
        i++;
    }

    @Override
    public synchronized void delete(Municipio element) {
        MunicipioDao.municipios.remove(element.getId());
    }
}
