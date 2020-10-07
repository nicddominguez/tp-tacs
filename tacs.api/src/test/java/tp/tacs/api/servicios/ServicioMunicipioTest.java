package tp.tacs.api.servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.utils.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.Defensa;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.municipio.Produccion;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class ServicioMunicipioTest {

    private ServicioMunicipio servicioMunicipio;

    @Spy
    private MunicipioDao municipioDao;

    private Municipio municipio;
    private Usuario usuario;
    private Partida partida;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuario = Usuario.builder().id(0L).rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("nombre0").mail("mail0").build();
        municipio = Municipio.builder().id(0L).nombre("nombre0").externalApiId("0").duenio(usuario).nivelDeProduccion(1).latitud(1d).longitud(1d).altura(1f).urlImagen("urlImageb").build();
        partida = Partida.builder().minAltura(1f).maxAltura(2f).build();
        doReturn(municipio).when(municipioDao).get(0L);
        servicioMunicipio = new ServicioMunicipio(municipioDao);
    }

    @Test
    void producir() {
        servicioMunicipio.producir(municipio);
        assertEquals(municipio.getCantGauchos(), 16);
    }

    @Test
    void actualizarMunicipio() {
        servicioMunicipio.actualizarMunicipio(partida, 0L, new Defensa());
        assertEquals(municipio.getNivelDeProduccion(), 10);
    }

    @Test
    void agregarGauchos() {
        servicioMunicipio.agregarGauchos(municipio, 15);
        assertEquals(municipio.getCantGauchos(), 30);
    }

    @Test
    void sacarGauchos() {
        servicioMunicipio.sacarGauchos(municipio, 15);
        assertEquals(municipio.getCantGauchos(), 0);
    }

    @Test
    void esDeCorrecto() {
        assertTrue(servicioMunicipio.esDe(municipio, 0L));
    }

    @Test
    void esDeInorrecto() {
        assertFalse(servicioMunicipio.esDe(municipio, 1L));
    }

    @Test
    void coordenadasParaTopo() {
        assertEquals(servicioMunicipio.coordenadasParaTopo(municipio), "1.0,1.0");
    }

    @Test
    void actualizarNivelProduccion() {
        servicioMunicipio.actualizarNivelProduccion(municipio, partida);
        assertEquals(municipio.getNivelDeProduccion(), 15);
    }
}