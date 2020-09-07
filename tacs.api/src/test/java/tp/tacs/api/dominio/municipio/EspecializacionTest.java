package tp.tacs.api.dominio.municipio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import tp.tacs.api.dominio.partida.ModoFacil;
import tp.tacs.api.dominio.partida.Partida;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

class EspecializacionTest {

    Defensa defensa = new Defensa();

    Produccion produccion = new Produccion();

    @Spy
    Municipio municipio = new Municipio();

    ModoFacil modoFacil = new ModoFacil();

    @Mock
    Partida partida;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        doReturn(20f).when(partida).maxAltura();
        doReturn(10f).when(partida).minAltura();
        doReturn(5f).when(municipio).getAltura();
        doReturn(modoFacil).when(partida).getModoDeJuego();
        municipio.setPartida(partida);
    }


    @Test
    void producirEnDefensa() {
        municipio.setEspecializacion(defensa);
        municipio.producir();
        assertEquals(13, municipio.getCantGauchos());
    }

    @Test
    void multDefDeDefensa() {
        assertEquals(1.25f, defensa.multDefensa(partida));
    }

    @Test
    void nivelDeProduccionDeDefensa() {
        assertEquals(13, defensa.nivelDeProduccion(municipio));
    }

    @Test
    void producirEnProduccion() {
        municipio.setEspecializacion(produccion);
        municipio.producir();
        assertEquals(19, municipio.getCantGauchos());
    }

    @Test
    void multDefDeProduccion() {
        assertEquals(1f, produccion.multDefensa(partida));
    }

    @Test
    void nivelDeProduccionDeProduccion() {
        assertEquals(19, produccion.nivelDeProduccion(municipio));
    }
}