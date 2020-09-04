package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import tp.tacs.api.model.GoogleAuthModel;
import tp.tacs.api.model.NuevoJWTModel;

import javax.validation.Valid;

public class AuthApiController implements AuthApi {

    @Override
    public ResponseEntity<NuevoJWTModel> logIn(@Valid GoogleAuthModel body) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<NuevoJWTModel> singUp(@Valid GoogleAuthModel body) {
        return ResponseEntity.ok().build();
    }

}
