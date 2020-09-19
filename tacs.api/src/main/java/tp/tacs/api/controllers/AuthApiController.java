package tp.tacs.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.exceptions.GoogleIdTokenFaltante;
import tp.tacs.api.exceptions.UsuarioDesconocido;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.GoogleAuthModel;
import tp.tacs.api.model.NuevoJWTModel;
import tp.tacs.api.model.RefreshAccessTokenBody;
import tp.tacs.api.security.GoogleIdTokenService;
import tp.tacs.api.security.JWTTokenService;
import tp.tacs.api.servicios.ServicioUsuario;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AuthApiController implements AuthApi {

    private final JWTTokenService jwtTokenService;
    private final GoogleIdTokenService googleIdTokenService;
    private final UsuarioMapper usuarioMapper;
    private final ServicioUsuario servicioUsuario;

    @Autowired
    public AuthApiController(JWTTokenService jwtTokenService, GoogleIdTokenService googleIdTokenService, UsuarioMapper usuarioMapper, ServicioUsuario servicioUsuario) {
        this.jwtTokenService = jwtTokenService;
        this.googleIdTokenService = googleIdTokenService;
        this.usuarioMapper = usuarioMapper;
        this.servicioUsuario = servicioUsuario;
    }

    private NuevoJWTModel generarJwtParaUsuario(Usuario usuario) {
        return new NuevoJWTModel()
                .token(this.servicioUsuario.generarJwtParaUsuario(usuario))
                .usuario(this.usuarioMapper.wrap(usuario));
    }

    private NuevoJWTModel agregarRefreshToken(Usuario usuario, NuevoJWTModel model) {
        return model.refreshToken(this.servicioUsuario.generarRefreshToken(usuario));
    }

    @Override
    public ResponseEntity<NuevoJWTModel> logIn(@Valid GoogleAuthModel body) {
        var idTokenString =
                Optional.ofNullable(body.getIdToken())
                        .orElseThrow(() -> new GoogleIdTokenFaltante("Google Id token necesario para loguearse"));

        var idToken = this.googleIdTokenService.verifyToken(idTokenString);
        var googleId = this.googleIdTokenService.extractGoogleId(idToken);

        var usuario = this.servicioUsuario.getByGoogleId(googleId)
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

        var usuario = this.servicioUsuario.crearUsuario(idTokenString);

        var response = this.generarJwtParaUsuario(usuario);
        response = this.agregarRefreshToken(usuario, response);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<NuevoJWTModel> refreshAccessToken(@Valid RefreshAccessTokenBody body) {
        var refreshToken = body.getRefreshToken();
        var maybeAuthorization = Optional.ofNullable(refreshToken)
                .flatMap(this.jwtTokenService::validateToken);

        if (maybeAuthorization.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var username = (String) maybeAuthorization.get().getPrincipal();
        var usuario = this.servicioUsuario.getByUsername(username)
                .orElseThrow(() -> new UsuarioDesconocido("Usuario desconocido"));

        var response = this.generarJwtParaUsuario(usuario);

        return ResponseEntity.ok(response);
    }

}
