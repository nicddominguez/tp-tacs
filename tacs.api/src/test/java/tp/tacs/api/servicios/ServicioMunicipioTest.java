package tp.tacs.api.servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import tp.tacs.api.daos.MunicipioDaoMemoria;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.municipio.Defensa;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.municipio.Produccion;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.mappers.MunicipioEnJuegoMapper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServicioMunicipioTest {

    private ServicioMunicipio servicioMunicipio;

    private ServicioPartida servicioPartida;


    private Usuario usuarioA;
    private Usuario usuarioB;
    private Partida partida;


    Municipio municipioA;
    Municipio municipioB;
    Municipio municipioC;
    Municipio municipioD;

    private MunicipioDaoMemoria municipioDao;
    @Spy
    private ExternalApis externalApis;
    @Spy
    private UsuarioDao usuarioDao;
    @Spy
    private PartidaDao partidaDao;

    private MunicipioEnJuegoMapper municipioEnJuegoMapper = new MunicipioEnJuegoMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        municipioDao = new MunicipioDaoMemoria();
        usuarioA = Usuario.builder().id("0L").build();
        usuarioB = Usuario.builder().id("1L").build();

        partida = Partida.builder().municipios(new ArrayList<>()).build();
        servicioMunicipio = new ServicioMunicipio(municipioDao);

        municipioA = crearMunicipio("0L", 500d, 0d, 00f, usuarioA);
        municipioB = crearMunicipio("1L", 0d, 0d, 10f, usuarioA);
        municipioC = crearMunicipio("2L", 30d, 40d, 30f, usuarioB);
        municipioD = crearMunicipio("3L", 30d, 40d, 30f, usuarioB);

        servicioPartida = new ServicioPartida(externalApis, usuarioDao, municipioDao, partidaDao, servicioMunicipio, municipioEnJuegoMapper);
        servicioPartida.calcularAlturas(partida);
        servicioPartida.calcularDistancias(partida);

    }

    Municipio crearMunicipio(String _id, Double _latitud, Double _longitud, Float _altura, Usuario _usuario) {
        partida.getMunicipios().add(_id);
        var municipio = Municipio.builder().id(_id).latitud(_latitud).longitud(_longitud).altura(_altura).duenio(_usuario).build();
        municipioDao.saveWithId(municipio, _id);
        return municipio;
    }

    @Test
    void produccionDeGauchosEnProduccionAlturaMinima() {
        municipioA.setEspecializacion(new Produccion());
        servicioMunicipio.actualizarNivelProduccion(municipioA, partida);
        assertEquals(15, municipioA.getNivelDeProduccion());
    }

    @Test
    void produccionDeGauchosEnProduccionAlturaMaxima() {
        municipioD.setEspecializacion(new Produccion());
        servicioMunicipio.actualizarNivelProduccion(municipioD, partida);
        assertEquals(8, municipioD.getNivelDeProduccion());
    }

    @Test
    void produccionDeGauchosEnDefensaAlturaMinima() {
        municipioA.setEspecializacion(new Defensa());
        servicioMunicipio.actualizarNivelProduccion(municipioA, partida);
        assertEquals(10, municipioA.getNivelDeProduccion());
    }

    @Test
    void produccionDeGauchosEnDefensaAlturaMaxima() {
        municipioD.setEspecializacion(new Defensa());
        servicioMunicipio.actualizarNivelProduccion(municipioD, partida);
        assertEquals(5, municipioD.getNivelDeProduccion());
    }

    @Test
    void producir() {
        servicioMunicipio.actualizarNivelProduccion(municipioA, partida);
        servicioMunicipio.producir(municipioA);
        assertEquals(30, municipioA.getCantGauchos());
    }

    @Test
    void actualizarMunicipio() {
        servicioMunicipio.actualizarMunicipio(partida, "0L", new Defensa());
        assertEquals(10, municipioA.getNivelDeProduccion());
    }

    @Test
    void agregarGauchos() {
        servicioMunicipio.agregarGauchos(municipioA, 15);
        assertEquals(30, municipioA.getCantGauchos());
    }

    @Test
    void sacarGauchos() {
        servicioMunicipio.sacarGauchos(municipioA, 15);
        assertEquals(0, municipioA.getCantGauchos());
    }

    @Test
    void esDeCorrecto() {
        assertTrue(servicioMunicipio.esDe(municipioA, "0L"));
    }

    @Test
    void esDeInorrecto() {
        assertFalse(servicioMunicipio.esDe(municipioA, "1L"));
    }

    @Test
    void coordenadasParaTopo() {
        assertEquals(servicioMunicipio.coordenadasParaTopo(municipioA), "500.0,0.0");
    }

}