package tp.tacs.api.daos;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.usuario.Usuario;

import java.util.*;

@Component
public class UsuarioDao implements Dao<Usuario> {

    private final Map<Long, Usuario> usuarios = new HashMap<>();

    @Override
    public Usuario get(Long id) {
        return Optional.ofNullable(this.usuarios.get(id)).orElse(null);
    }

    public Optional<Usuario> getByGoogleId(String googleId) {
        return this.usuarios
                .values()
                .stream()
                .filter(usuario -> usuario.getGoogleId().equals(googleId))
                .findFirst();
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
