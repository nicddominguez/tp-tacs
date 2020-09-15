package tp.tacs.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.model.GoogleAuthModel;
import tp.tacs.api.model.NuevoJWTModel;

import static org.junit.jupiter.api.Assertions.*;

class AuthApiControllerTest {

    @InjectMocks
    private AuthApiController authApiController;

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
    
}