package tp.tacs.api.dominio.partida;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.usuario.Usuario;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

class PartidaTest {

    @Spy
    Municipio municipioA;
    @Spy
    Municipio municipioB;
    @Spy
    Municipio municipioC;
    @Spy
    Municipio municipioD;

    List<Municipio> municipios = new ArrayList<>();

    Usuario usuarioA;
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

        usuarioA = new Usuario(1234L, "hola@gmail.com", "carlos");
        usuarioB = new Usuario(12L, "hola@gmail.com", "tomas");
        fechaPartida = new Date(2020, Calendar.APRIL, 1);
        usuarios.add(usuarioA);
        usuarios.add(usuarioB);
        municipios.add(municipioA);
        municipios.add(municipioB);
        municipios.add(municipioC);
        municipios.add(municipioD);
        partidaBase = new Partida(usuarios, Estado.EN_CURSO, "1234",municipios, modo, fechaPartida);
    }

    private void mockearMunicipio(Municipio municipio, double latitud, double longitud, float altura) {
        doReturn(latitud).when(municipio).getLatitud();
        doReturn(longitud).when(municipio).getLongitud();
        ArrayList<Double> coordenadasA = Lists.newArrayList(municipio.getLatitud(), municipio.getLongitud());
        doReturn(coordenadasA).when(municipio).getCoordenadas();
        doReturn(altura).when(municipio).getAltura();
        doNothing().when(municipio).producir();
    }

    @Test
    void repartirMunicipios() {
        partidaBase.repartirMunicipios();
        var municipiosPorUsuario = municipios.stream()
                .collect(Collectors.groupingBy(Municipio::getDuenio, Collectors.counting()));
        var cantidadMaximaRepartida = Collections
                .max(municipiosPorUsuario.entrySet(), Map.Entry.comparingByValue())
                .getValue();
        assertEquals(cantidadMaximaRepartida, 2);
    }

    @Test
    void pasarTurno() {
        partidaBase.pasarTurno();
        assertFalse(municipios.stream().anyMatch(Municipio::estaBloqueado));
    }

    @Test
    void asignarGanadorAlTerminar() {
        municipioA.setDuenio(usuarioA);
        municipioB.setDuenio(usuarioA);
        municipioC.setDuenio(usuarioA);
        municipioD.setDuenio(usuarioB);
        partidaBase.terminar();
        assertEquals(partidaBase.getGanador(), usuarioA);
    }

    @Test
    void terminar() {
        assertTrue(partidaBase.getJugadores().stream().allMatch(usuario -> usuario.getPartidasJugadas() == 0));
        municipioA.setDuenio(usuarioA);
        municipioB.setDuenio(usuarioA);
        partidaBase.terminar();
        var ganador = partidaBase.getGanador();
        assertTrue(partidaBase.getJugadores().stream().allMatch(usuario -> usuario.getPartidasJugadas() == 1));
        assertEquals(ganador.getRachaActual(), 1);
        partidaBase.getJugadores().stream()
                .filter(usuario -> !usuario.equals(ganador))
                .findAny()
                .ifPresent(perdedor -> assertEquals(perdedor.getRachaActual(), 0));
    }

    @Test
    void usuarioEnTurnoActual() {
        partidaBase.asignarProximoTurnoA(usuarioA);
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