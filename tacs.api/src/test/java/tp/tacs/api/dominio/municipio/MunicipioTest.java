package tp.tacs.api.dominio.municipio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.DoubleConsumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class MunicipioTest {

    @Mock
    RepoMunicipios repoMunicipios;

    Municipio municipioA;
    Municipio municipioB;
    Municipio municipioD;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockearRepoMunicipios("A", 100f, 10d, 0d);
        mockearRepoMunicipios("B", 200f, 0d, 0d);
        mockearRepoMunicipios("C", 0f, 30d, 40d);

    }

    private void mockearRepoMunicipios(String idProvincia, Float altura, Double latitud, Double longitud) {
        when(repoMunicipios.getAltura(idProvincia)).thenReturn(altura);
        when(repoMunicipios.getLatitud(idProvincia)).thenReturn(latitud);
        when(repoMunicipios.getLongitud(idProvincia)).thenReturn(longitud);
    }

    @Test
    void jugarPartida() {


    }

    @Test
    void producir() {
    }

    @Test
    void atacar() {
    }

    @Test
    void ataqueExitoso() {
    }

    @Test
    void moverGauchos() {
    }
}