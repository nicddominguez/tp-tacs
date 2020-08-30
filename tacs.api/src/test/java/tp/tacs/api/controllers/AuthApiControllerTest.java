package tp.tacs.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.model.LogIn;
import tp.tacs.api.model.SignUp;

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
        ResponseEntity<Void> response = authApiController.logIn(new LogIn().username("ncoen").password("asd123"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void logOut() {
        ResponseEntity<Void> response = authApiController.logOut();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void singUp() {
        ResponseEntity<Void> response = authApiController.singUp(new SignUp().username("ncoen").password("asd123").email("ncoen@dblandit.com"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}