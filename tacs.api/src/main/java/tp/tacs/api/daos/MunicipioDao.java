package tp.tacs.api.daos;

import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;

import java.util.List;
import java.util.Set;

public interface MunicipioDao extends Dao<Municipio> {

    Municipio get (String id);

    List<Municipio> getAll();

    void save(Municipio element);

    void delete(Municipio element);

    List<Municipio> getByIds(List<String> ids);

    Set<Municipio> municipiosConDuenio(Partida partida);
}
