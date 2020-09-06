package tp.tacs.api.daos;

import tp.tacs.api.dominio.Usuario;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UsuarioDao implements Dao<Usuario> {

    private Map<Long, Usuario> usuarios = new HashMap<>();

    @Override
    public Optional<Usuario> get(Long id) {
        return Optional.ofNullable(this.usuarios.get(id));
    }

    @Override
    public Collection<Usuario> getAll() {
        return this.usuarios.values();
    }

    @Override
    public void save(Usuario element) {
        this.usuarios.put(element.getId(), element);
    }

    @Override
    public void delete(Usuario element) {
        this.usuarios.remove(element.getId());
    }

}
