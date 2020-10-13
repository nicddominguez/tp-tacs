package tp.tacs.api.daos;

import java.util.List;

public interface Dao<T> {

    T get(String id);

    List<T> getAll();

    void save(T element);

    void delete(T element);

}
