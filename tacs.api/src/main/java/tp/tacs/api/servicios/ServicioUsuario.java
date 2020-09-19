package tp.tacs.api.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.usuario.Usuario;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioUsuario {

    @Autowired
    private UsuarioDao usuarioDao;

    public List<Usuario> listarUsuarios(String filter) {
        List<Usuario> usuarios = this.usuarioDao.getAll();
        if (filter != null) {
            usuarios = usuarios.stream().filter(usuario -> usuario.mismoNombre(filter)).collect(Collectors.toList());
        }
        return usuarios;
    }
}
