package tp.tacs.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.exceptions.UsuarioDesconocido;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.GoogleAuthModel;
import tp.tacs.api.model.NuevoJWTModel;
import tp.tacs.api.model.RefreshAccessTokenBody;
import tp.tacs.api.security.GoogleIdTokenService;
import tp.tacs.api.security.JWTTokenService;
import tp.tacs.api.exceptions.GoogleIdTokenFaltante;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;
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

    private NuevoJWTModel generarJwtParaUsuario(Usuario usuario) {
        var nuevoJwt = this.jwtTokenService.createToken(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getIsAdmin()
        );
        return new NuevoJWTModel()
                .token(nuevoJwt)
                .usuario(this.usuarioMapper.wrap(usuario));
    }

    private NuevoJWTModel agregarRefreshToken(Usuario usuario, NuevoJWTModel model) {
        var nuevoRefreshToken = this.jwtTokenService.createRefreshToken(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getIsAdmin()
        );

        return model.refreshToken(nuevoRefreshToken);
    }

    @Override
    public ResponseEntity<NuevoJWTModel> logIn(@Valid GoogleAuthModel body) {
        var idTokenString =
                Optional.ofNullable(body.getIdToken())
                        .orElseThrow(() -> new GoogleIdTokenFaltante("Google Id token necesario para loguearse"));

        var idToken = this.googleIdTokenService.verifyToken(idTokenString);
        var googleId = this.googleIdTokenService.extractGoogleId(idToken);

        var usuario =
                Optional.ofNullable(this.usuarioDao.getByGoogleId(googleId))
                        .orElseThrow(() -> new UsuarioDesconocido("Intentó iniciar sesión un usuario desconocido"));

        var response = this.generarJwtParaUsuario(usuario);
        response = this.agregarRefreshToken(usuario, response);

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

        var usuario = Optional.ofNullable(this.usuarioDao.getByGoogleId(googleId))
                .orElseGet(() -> {
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

        var response = this.generarJwtParaUsuario(usuario);
        response = this.agregarRefreshToken(usuario, response);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<NuevoJWTModel> refreshAccessToken(@Valid RefreshAccessTokenBody body) {
        var refreshToken = body.getRefreshToken();
        var maybeAuthorization = Optional.ofNullable(refreshToken)
                .flatMap(this.jwtTokenService::validateToken);

        if(maybeAuthorization.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var username = (String) maybeAuthorization.get().getPrincipal();
        var usuario = Optional.ofNullable(this.usuarioDao.getByUsername(username))
                .orElseThrow(() -> new UsuarioDesconocido("Usuario desconocido"));

        var response = this.generarJwtParaUsuario(usuario);

        return ResponseEntity.ok(response);
    }

}
