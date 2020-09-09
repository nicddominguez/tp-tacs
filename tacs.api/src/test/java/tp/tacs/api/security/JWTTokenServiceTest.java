package tp.tacs.api.security;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JWTTokenServiceTest {

    private final JWTTokenService jwtTokenService = new JWTTokenService("my-super-secret-key");

    @Test
    public void crearYValidarToken() {
        var username = "Grupo 3";
        var jwtString = this.jwtTokenService.createToken(0L, username, false);
        var authentication = this.jwtTokenService.validateToken(jwtString).get();
        assertEquals(username, authentication.getPrincipal());
    }

    @Test
    public void extraerToken() {
        var request = new MockHttpServletRequest();
        var tokenOriginal = "supongamos_que_esto_es_un_jwt";
        request.addHeader("Authorization", "Bearer " + tokenOriginal);
        var tokenExtraido = this.jwtTokenService.extractToken(request);
        assertEquals(Optional.of(tokenOriginal), tokenExtraido);
    }

}
