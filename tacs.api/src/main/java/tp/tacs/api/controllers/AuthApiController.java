package tp.tacs.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.exceptions.UsuarioDesconocido;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.GoogleAuthModel;
import tp.tacs.api.model.NuevoJWTModel;
import tp.tacs.api.security.GoogleIdTokenService;
import tp.tacs.api.security.JWTTokenService;
import tp.tacs.api.exceptions.GoogleIdTokenFaltante;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AuthApiController implements AuthApi {

    private final JWTTokenService jwtTokenService;
    private final GoogleIdTokenService googleIdTokenService;
    private final UsuarioDao usuarioDao;
    private final UsuarioMapper usuarioMapper;

    // TODO: El id del usuario debería generarlo UsuarioDao cuando se almacena en la DB.
    //  Implementarlo así ahora rompería el branch de dominio. Will do por ahora.
    private final AtomicLong nextUserId = new AtomicLong(0L);

    @Autowired
    public AuthApiController(JWTTokenService jwtTokenService,
                             GoogleIdTokenService googleIdTokenService,
                             UsuarioDao usuarioDao,
                             UsuarioMapper usuarioMapper) {
        this.jwtTokenService = jwtTokenService;
        this.googleIdTokenService = googleIdTokenService;
        this.usuarioDao = usuarioDao;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public ResponseEntity<NuevoJWTModel> logIn(@Valid GoogleAuthModel body) {
        var idTokenString =
                Optional.ofNullable(body.getIdToken())
                        .orElseThrow(() -> new GoogleIdTokenFaltante("Google Id token necesario para loguearse"));

        var idToken = this.googleIdTokenService.verifyToken(idTokenString);
        var googleId = this.googleIdTokenService.extractGoogleId(idToken);

        var usuario =
                this.usuarioDao
                        .getByGoogleId(googleId)
                        .orElseThrow(() -> new UsuarioDesconocido("Intentó iniciar sesión un usuario desconocido"));

        var nuevoJwt = this.jwtTokenService.createToken(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getAdmin()
        );
        var response = new NuevoJWTModel()
                .token(nuevoJwt)
                .usuario(this.usuarioMapper.toModel(usuario));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<NuevoJWTModel> singUp(@Valid GoogleAuthModel body) {
        var idTokenString =
                Optional.ofNullable(body.getIdToken())
                        .orElseThrow(() -> new GoogleIdTokenFaltante("Google Id token necesario para loguearse"));

        var idToken = this.googleIdTokenService.verifyToken(idTokenString);

        var name = this.googleIdTokenService.extractUserName(idToken);
        var email = this.googleIdTokenService.extractEmail(idToken);
        var googleId = this.googleIdTokenService.extractGoogleId(idToken);

        var usuario = this.usuarioDao
                .getByGoogleId(googleId)
                .orElseGet(() -> {
                    // El usuario no existe, lo creamos
                    var userId = this.nextUserId.getAndIncrement();
                    var nuevoUsuario = new Usuario(userId, email, name);
                    nuevoUsuario.setGoogleId(googleId);
                    nuevoUsuario.setAdmin(true);
                    this.usuarioDao.save(nuevoUsuario);
                    return nuevoUsuario;
                });

        var nuevoJwt = this.jwtTokenService.createToken(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getAdmin()
        );

        var response = new NuevoJWTModel()
                .token(nuevoJwt)
                .usuario(this.usuarioMapper.toModel(usuario));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<NuevoJWTModel> refreshToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var username = (String) principal;
        var usuario = this.usuarioDao
                .getByUsername(username)
                .orElseThrow(() -> new UsuarioDesconocido("Usuario desconocido"));

        var nuevoJwt = this.jwtTokenService.createToken(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getAdmin()
        );
        var usuarioModel = this.usuarioMapper.toModel(usuario);

        return ResponseEntity.ok(
                new NuevoJWTModel().token(nuevoJwt).usuario(usuarioModel)
        );
    }

}
