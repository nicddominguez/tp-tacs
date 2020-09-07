package tp.tacs.api.dominio;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.ModoFacil;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PartidaTest {

    @Mock
    Municipio municipioA;
    @Mock
    Municipio municipioB;
    @Mock
    Municipio municipioC;
    @Mock
    Municipio municipioD;

    List<Municipio> municipios = new ArrayList<>();

    @Mock
    Usuario usuarioA;
    @Mock
    Usuario usuarioB;

    List<Usuario> usuarios = new ArrayList<>();


    ModoFacil modo = new ModoFacil();
    Partida partidaBase;
    Date fechaPartida;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockearMunicipio(municipioA, 500d, 0d, 10f);
        mockearMunicipio(municipioB, 0d, 0d, 110f);
        mockearMunicipio(municipioC, 30d, 40d, 210f);
        mockearMunicipio(municipioD, 30d, 40d, 210f);

        fechaPartida = new Date(2020, Calendar.APRIL, 1);
        usuarios.add(usuarioA);
        usuarios.add(usuarioB);
        municipios.add(municipioA);
        municipios.add(municipioB);
        municipios.add(municipioC);
        municipios.add(municipioD);
        partidaBase = new Partida(usuarios, Estado.EN_CURSO, "1234", municipios, modo, fechaPartida);
    }

    private void mockearMunicipio(Municipio municipioA, double latitud, double longitud, float altura) {
        when(municipioA.getLatitud()).thenReturn(latitud);
        when(municipioA.getLongitud()).thenReturn(longitud);
        ArrayList<Double> coordenadasA = Lists.newArrayList(municipioA.getLatitud(), municipioA.getLongitud());
        when(municipioA.getCoordenadas()).thenReturn(coordenadasA);
        when(municipioA.getAltura()).thenReturn(altura);
    }

    @Test
    void pasarTurno() {
        partidaBase.pasarTurno();
        assertFalse(municipios.stream().anyMatch(Municipio::estaBloqueado));
    }

    @Test
    void terminar() {
        assertTrue(usuarios.stream().allMatch(usuario -> usuario.getPartidasGanadas() == 0));
        partidaBase.terminar();
        var ganador = partidaBase.getGanador();
        assertTrue(usuarios.stream().allMatch(usuario -> usuario.getPartidasGanadas() == 1));
        assertEquals(ganador.getRachaActual(), 1);
        usuarios.stream()
                .filter(usuario -> !usuario.equals(ganador))
                .findAny()
                .ifPresent(perdedor -> assertEquals(perdedor.getRachaActual(), 0));
    }

    @Test
    void usuarioEnTurnoActual() {
        partidaBase.pasarTurno();
        partidaBase.pasarTurno();
        partidaBase.pasarTurno();
        assertEquals(partidaBase.usuarioEnTurnoActual(), usuarioB);
    }

    @Test
    void minAltura() {
        assertEquals(partidaBase.minAltura(), 10f);
    }

    @Test
    void maxAltura() {
        assertEquals(partidaBase.maxAltura(), 210f);
    }

    @Test
    void maxDist() {
        assertEquals(partidaBase.maxDist(), 500f);
    }

    @Test
    void minDist() {
        assertEquals(partidaBase.minDist(), 50f);
    }

    @Test
    void multDist() {
        assertEquals(partidaBase.multDist(municipioA, municipioB), 0.5f);
    }

    @Test
    void multAltura() {
        assertEquals(partidaBase.multAltura(municipioB), 1.25f);
    }
}