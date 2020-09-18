package tp.tacs.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Google Id Token requerido")
public class GoogleIdTokenFaltante extends RuntimeException {

    public GoogleIdTokenFaltante(String message) {
        super(message);
    }

}
