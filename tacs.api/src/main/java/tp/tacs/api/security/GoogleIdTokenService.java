package tp.tacs.api.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import tp.tacs.api.exceptions.GoogleIdTokenInvalido;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleIdTokenService {

    private final GoogleIdTokenVerifier verifier;

    public GoogleIdTokenService(String clientId,
                                HttpTransport httpTransport,
                                JsonFactory jsonFactory) {
        this.verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    @Autowired
    public GoogleIdTokenService(@Value("${security.google.clientId}") String clientId) {
        this(
                clientId,
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance()
        );
    }

    public GoogleIdToken verifyToken(String token) {
        try {
            var decodedToken = this.verifier.verify(token);
            Assert.notNull(decodedToken, "Google Id Token inválido");
            return decodedToken;
        } catch (GeneralSecurityException | IOException | AssertionError exc) {
            throw new GoogleIdTokenInvalido("Google Id Token inválido");
        }
    }

    public String extractGoogleId(GoogleIdToken token) {
        return token.getPayload().getSubject();
    }

    public String extractUserName(GoogleIdToken token) {
        return (String) token.getPayload().get("name");
    }

    public String extractEmail(GoogleIdToken token) {
        return token.getPayload().getEmail();
    }

}
