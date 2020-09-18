package tp.tacs.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Google Id Token inválido")
public class GoogleIdTokenInvalido extends RuntimeException {

    public GoogleIdTokenInvalido(String message) {
        super(message);
    }

}
