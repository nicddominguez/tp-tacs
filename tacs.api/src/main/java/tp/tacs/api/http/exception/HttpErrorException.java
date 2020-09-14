package tp.tacs.api.http.exception;

public class HttpErrorException extends RuntimeException {

    public HttpErrorException() {
    }

    public HttpErrorException(String responseMsg) {
        super(responseMsg);
    }
}