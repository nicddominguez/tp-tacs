package tp.tacs.api.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tp.tacs.api.http.exception.HttpErrorException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpErrorException.class)
    protected ResponseEntity<Object> handlerHttpErrorException(HttpErrorException ex, WebRequest request){
        logger.error(ex.getMessage());
        return ResponseEntity.status(500).body("Error de conexion");
    }

}