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

    @Test
    void logIn() {
        ResponseEntity<NuevoJWTModel> response = authApiController.logIn(new GoogleAuthModel().idToken("miidtokenpiola"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void singUp() {
        ResponseEntity<NuevoJWTModel> response = authApiController.singUp(new GoogleAuthModel().idToken("miidtokenpiola"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}