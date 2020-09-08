package tp.tacs.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Google Id Token inválido")
public class InvalidGoogleIdToken extends RuntimeException {

    public InvalidGoogleIdToken(String message) {
        super(message);
    }

}
