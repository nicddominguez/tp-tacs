package tp.tacs.api.daos;

import tp.tacs.api.dominio.usuario.Usuario;

import java.util.*;

public class UsuarioDao implements Dao<Usuario> {

    private Map<Long, Usuario> usuarios = new HashMap<>();

    @Override
    public Usuario get(Long id) {
        return Optional.ofNullable(this.usuarios.get(id)).orElse(null);
    }

    @Override
    public List<Usuario> getAll() {
        return new ArrayList<>(this.usuarios.values());
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
