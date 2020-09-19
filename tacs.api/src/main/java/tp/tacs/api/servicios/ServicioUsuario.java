package tp.tacs.api.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.security.GoogleIdTokenService;
import tp.tacs.api.security.JWTTokenService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ServicioUsuario {

    private final UsuarioDao usuarioDao;
    private final JWTTokenService jwtTokenService;
    private final GoogleIdTokenService googleIdTokenService;

    // TODO: El id del usuario debería generarlo UsuarioDao cuando se almacena en la DB.
    //  Implementarlo así ahora rompería el branch de dominio. Will do por ahora.
    private final AtomicLong nextUserId = new AtomicLong(0L);

    @Autowired
    public ServicioUsuario(UsuarioDao usuarioDao, JWTTokenService jwtTokenService, GoogleIdTokenService googleIdTokenService) {
        this.usuarioDao = usuarioDao;
        this.jwtTokenService = jwtTokenService;
        this.googleIdTokenService = googleIdTokenService;
    }

    public List<Usuario> listarUsuarios(String filter) {
        List<Usuario> usuarios = this.usuarioDao.getAll();
        if (filter != null) {
            usuarios = usuarios.stream().filter(usuario -> usuario.nombreContiene(filter)).collect(Collectors.toList());
        }
        return usuarios;
    }

    public String generarJwtParaUsuario(Usuario usuario) {
        return this.jwtTokenService.createToken(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getIsAdmin()
        );
    }

    public String generarRefreshToken(Usuario usuario) {
        return this.jwtTokenService.createRefreshToken(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getIsAdmin()
        );
    }

    public Optional<Usuario> getByUsername(String username) {
        return Optional.ofNullable(this.usuarioDao.getByUsername(username));
    }

    public Optional<Usuario> getByGoogleId(String googleid) {
        return Optional.ofNullable(this.usuarioDao.getByGoogleId(googleid));
    }

    public Usuario crearUsuario(String idTokenString) {
        var idToken = this.googleIdTokenService.verifyToken(idTokenString);

        var name = this.googleIdTokenService.extractUserName(idToken);
        var email = this.googleIdTokenService.extractEmail(idToken);
        var googleId = this.googleIdTokenService.extractGoogleId(idToken);

        return this.getByGoogleId(googleId).orElseGet(() -> {
            // El usuario no existe, lo creamos
            var userId = this.nextUserId.getAndIncrement();
            var nuevoUsuario = Usuario.builder()
                    .id(userId)
                    .mail(email)
                    .nombre(name)
                    .googleId(googleId)
                    .isAdmin(true)
                    .build();
            this.usuarioDao.save(nuevoUsuario);
            return nuevoUsuario;
        });
    }
}
