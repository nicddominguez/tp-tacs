package tp.tacs.api.dominio.municipio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tp.tacs.api.dominio.partida.ModoRapido;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.http.externalApis.ExternalApis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class MunicipioTest {

    @Mock
    ExternalApis repoMunicipios;

    Municipio municipioAtacante = new Municipio();
    Municipio municipioDefensor = new Municipio();

    @Mock
    Partida partida;

    ModoRapido modoFacil = new ModoRapido();

    Usuario usuario0 = new Usuario(0l, "mail0@mock.com", "mock0");
    Usuario usuario1 = new Usuario(1l, "mail1@mock.com", "mock1");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockearRepoMunicipios("ProvinciaMock", 100f, 10d, 0d);
        municipioAtacante.setRepoMunicipios(repoMunicipios);
        municipioDefensor.setRepoMunicipios(repoMunicipios);

        doReturn(modoFacil).when(partida).getModoDeJuego();
        doReturn(1.25f).when(partida).multAltura(municipioDefensor);
        doReturn(0.5f).when(partida).multDist(municipioAtacante, municipioDefensor);
        municipioAtacante.setPartida(partida);
        municipioDefensor.setPartida(partida);

        municipioAtacante.setDuenio(usuario0);
        municipioDefensor.setDuenio(usuario1);

    }

    private void mockearRepoMunicipios(String idProvincia, Float altura, Double latitud, Double longitud) {
        when(repoMunicipios.getAltura(idProvincia)).thenReturn(altura);
        when(repoMunicipios.getLatitud(idProvincia)).thenReturn(latitud);
        when(repoMunicipios.getLongitud(idProvincia)).thenReturn(longitud);
    }

    @Test
    void atacar() {
        municipioAtacante.setCantGauchos(100);
        municipioDefensor.setCantGauchos(1);
        when(partida.usuarioEnTurnoActual()).thenReturn(usuario0);
        municipioAtacante.atacar(municipioDefensor);
        assertEquals(49, municipioAtacante.getCantGauchos());
        assertEquals(0, municipioDefensor.getCantGauchos());
    }

    @Test
    void noSePuedeAtacarATusPropiosMunicipios() {
        municipioAtacante.setDuenio(usuario0);
        municipioDefensor.setDuenio(usuario0);
        assertThrows(RuntimeException.class, () -> municipioAtacante.atacar(municipioDefensor));
    }

    @Test
    void ataqueExitoso() {
        municipioAtacante.setCantGauchos(1000);
        assertTrue(municipioAtacante.ataqueExitoso(municipioDefensor));
    }

    @Test
    void ataqueSinExito() {
        municipioDefensor.setCantGauchos(1000);
        assertFalse(municipioAtacante.ataqueExitoso(municipioDefensor));
    }

    @Test
    void noSePuede() {
        municipioDefensor.setCantGauchos(1000);
        assertFalse(municipioAtacante.ataqueExitoso(municipioDefensor));
    }

    @Test
    void moverGauchos() {
        municipioAtacante.setCantGauchos(10);
        municipioAtacante.setDuenio(usuario0);
        municipioDefensor.setDuenio(usuario0);
        when(partida.usuarioEnTurnoActual()).thenReturn(usuario0);
        municipioAtacante.moverGauchos(municipioDefensor, 5);
        assertEquals(5, municipioAtacante.getCantGauchos());
        assertEquals(20, municipioDefensor.getCantGauchos());
    }

    @Test
    void noSePuedeMoverGauchosPorBloqueo() {
        municipioAtacante.setCantGauchos(10);
        municipioAtacante.setDuenio(usuario0);
        municipioDefensor.setDuenio(usuario0);
        when(partida.usuarioEnTurnoActual()).thenReturn(usuario0);
        municipioAtacante.moverGauchos(municipioDefensor, 5);
        assertThrows(RuntimeException.class, () -> municipioDefensor.moverGauchos(municipioAtacante, 1));
    }

    @Test
    void noSePuedeMoverGauchosPorqueNoLeAlcanza() {
        municipioAtacante.setCantGauchos(10);
        municipioAtacante.setDuenio(usuario0);
        municipioDefensor.setDuenio(usuario0);
        assertThrows(RuntimeException.class, () -> municipioAtacante.moverGauchos(municipioDefensor, 100));
    }

    @Test
    void noSePuedeMoverGauchosPorqueNoEsElMismoDuenio() {
        municipioAtacante.setCantGauchos(10);
        municipioAtacante.setDuenio(usuario0);
        municipioDefensor.setDuenio(usuario1);
        assertThrows(RuntimeException.class, () -> municipioAtacante.moverGauchos(municipioDefensor, 5));
    }

    @Test
    void esDe() {
        municipioAtacante.setDuenio(usuario0);
        assertTrue(municipioAtacante.esDe(usuario0));
    }

    @Test
    void noEsDe() {
        municipioAtacante.setDuenio(usuario1);
        assertFalse(municipioAtacante.esDe(usuario0));
    }
}