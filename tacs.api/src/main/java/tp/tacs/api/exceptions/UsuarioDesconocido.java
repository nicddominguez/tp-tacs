package tp.tacs.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Intento de logueo de un usuario desconocido")
public class UsuarioDesconocido extends RuntimeException {

    public UsuarioDesconocido(String message) {
        super(message);
    }

}
