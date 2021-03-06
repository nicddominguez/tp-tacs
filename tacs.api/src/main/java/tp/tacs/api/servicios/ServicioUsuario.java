package tp.tacs.api.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.security.GoogleIdTokenService;
import tp.tacs.api.security.JWTTokenService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ServicioUsuario {

    @Value("${application.admin-emails}")
    private ArrayList<String> adminEmails;

    private final JWTTokenService jwtTokenService;
    private final GoogleIdTokenService googleIdTokenService;

    @Autowired
    UsuarioDao usuarioDao;

    @Autowired
    public ServicioUsuario(JWTTokenService jwtTokenService, GoogleIdTokenService googleIdTokenService) {
        this.jwtTokenService = jwtTokenService;
        this.googleIdTokenService = googleIdTokenService;
    }

    public List<Usuario> listarUsuarios(String filter) {
        List<Usuario> usuarios = this.usuarioDao.getAll();
        if (filter != null) {
            usuarios = usuarios.stream().filter(usuario -> this.nombreContiene(usuario.getNombre(), filter)).collect(Collectors.toList());
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

    public String generarJwtParaUsuarioPorId(String id) {
        Usuario usuario = this.usuarioDao.get(id);
        return this.generarJwtParaUsuario(usuario);
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
            var nuevoUsuario = Usuario.builder()
                    .mail(email)
                    .nombre(name)
                    .googleId(googleId)
                    .isAdmin(adminEmails.contains(email))
                    .build();
            this.usuarioDao.save(nuevoUsuario);
            return nuevoUsuario;
        });
    }

    public boolean nombreContiene(String nombre, String letras) {
        return nombre.toUpperCase().contains(letras.toUpperCase());
    }
}
