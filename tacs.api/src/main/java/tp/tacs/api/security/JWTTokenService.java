package tp.tacs.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Component
public class JWTTokenService {

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    private final Algorithm jwtAlgorithm;

    @Autowired
    public JWTTokenService(@Value("${security.jwt.secret}") String secretKey) {
        this.jwtAlgorithm = Algorithm.HMAC256(secretKey);
    }

    public String createToken(Long userId, String username, Boolean isAdmin) {
        var calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("username", username)
                .withClaim("isAdmin", isAdmin)
                .withClaim("type", "access")
                .withIssuer(this.jwtIssuer)
                .withExpiresAt(calendar.getTime())
                .sign(this.jwtAlgorithm);
    }

    public String createRefreshToken(Long userId, String username, Boolean isAdmin) {
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("username", username)
                .withClaim("isAdmin", isAdmin)
                .withClaim("type", "refresh")
                .withIssuer(this.jwtIssuer)
                .sign(this.jwtAlgorithm);
    }

    private Authentication buildAuthentication(DecodedJWT decodedJWT) {
        String username = decodedJWT.getClaim("username").asString();
        Boolean isAdmin = decodedJWT.getClaim("isAdmin").asBoolean();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (isAdmin) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new UsernamePasswordAuthenticationToken(
                username, null, grantedAuthorities
        );
    }

    public Optional<Authentication> validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(this.jwtAlgorithm)
                    .withIssuer(this.jwtIssuer)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Authentication auth = this.buildAuthentication(decodedJWT);
            return Optional.of(auth);
        } catch (JWTVerificationException exc) {
            return Optional.empty();
        }
    }

    public Optional<String> extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

}
