package tp.tacs.api.daos;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.usuario.Usuario;

import java.util.*;

@Component
public class UsuarioDao implements Dao<Usuario> {

    private static final Map<Long, Usuario> usuarios = new HashMap<>();

    @Override
    public synchronized Usuario get(Long id) {
        return Optional.ofNullable(UsuarioDao.usuarios.get(id)).orElse(null);
    }

    public synchronized Usuario getByGoogleId(String googleId) {
        return UsuarioDao.usuarios
                .values()
                .stream()
                .filter(usuario -> usuario.getGoogleId().equals(googleId))
                .findFirst()
                .orElse(null);
    }

    public synchronized Usuario getByUsername(String username) {
        return UsuarioDao.usuarios
                .values()
                .stream()
                .filter(usuario -> usuario.getNombre().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<Usuario> getAll() {
        return new ArrayList<>(UsuarioDao.usuarios.values());
    }

    @Override
    public synchronized void save(Usuario element) {
        UsuarioDao.usuarios.put(element.getId(), element);
    }

    @Override
    public synchronized void delete(Usuario element) {
        UsuarioDao.usuarios.remove(element.getId());
    }

}
