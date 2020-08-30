package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import tp.tacs.api.model.LogIn;
import tp.tacs.api.model.SignUp;

import javax.validation.Valid;

public class AuthApiController implements AuthApi {

    @Override
    public ResponseEntity<Void> logIn(@Valid LogIn body) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> logOut() {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> singUp(@Valid SignUp body) {
        return ResponseEntity.ok().build();
    }
}
