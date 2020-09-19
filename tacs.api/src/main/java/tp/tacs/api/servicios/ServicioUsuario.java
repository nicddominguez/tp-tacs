package tp.tacs.api.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.usuario.Usuario;

import java.util.List;

@Service
public class ServicioUsuario {

    @Autowired
    private UsuarioDao usuarioDao;

    public List<Usuario> listarUsuarios() {
        return this.usuarioDao.getAll();
    }
}
