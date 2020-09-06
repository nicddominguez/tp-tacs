package tp.tacs.api.daos;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(Long id);

    Collection<T> getAll();

    void save(T element);

    void delete(T element);

}
