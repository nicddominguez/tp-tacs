package tp.tacs.api.dominio.usuario;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

class UsuarioTest {

    Usuario usuario = new Usuario(0l, "mail@mock.com", "mock");
    Usuario usuario2 = new Usuario(0l, "mail@mock.com", "mock");
    List<Usuario> usuarios = new ArrayList<>();


    Partida partida0;

    Partida partida1;

    Partida partida2;

    @Spy
    Municipio municipioA;
    @Spy
    Municipio municipioB;
    List<Municipio> municipios = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        usuarios.add(usuario);
        usuarios.add(usuario2);
        mockearMunicipio(municipioA, 500d, 0d, 10f, usuario);
        mockearMunicipio(municipioB, 0d, 0d, 110f, usuario);
        municipios.add(municipioA);
        municipios.add(municipioB);
        partida0 = new Partida(usuarios, null, null, municipios, null, null);
        partida1 = new Partida(usuarios, null, null, municipios, null, null);
    }

    private void mockearMunicipio(Municipio municipio, double latitud, double longitud, float altura, Usuario duenio) {
        doReturn(latitud).when(municipio).getLatitud();
        doReturn(longitud).when(municipio).getLongitud();
        ArrayList<Double> coordenadasA = Lists.newArrayList(municipio.getLatitud(), municipio.getLongitud());
        doReturn(coordenadasA).when(municipio).getCoordenadas();
        doReturn(altura).when(municipio).getAltura();
        doNothing().when(municipio).producir();
        doReturn(duenio).when(municipio).getDuenio();
    }

    @Test
    void partidasJugadas() {
        partida0.terminar();
        partida1.terminar();
        assertEquals(2, usuario.getPartidasJugadas());
    }

    @Test
    void partidasGanadas() {
        partida0.terminar();
        partida1.terminar();
        assertEquals(2, usuario.getPartidasGanadas());
    }

    @Test
    void rachaActual() {
        partida0.terminar();
        partida1.terminar();
        assertEquals(2, usuario.getRachaActual());
    }

    @Test
    void seReiniciaLaRachaSiSePierde() {
        mockearMunicipio(municipioA, 500d, 0d, 10f, usuario2);
        mockearMunicipio(municipioB, 0d, 0d, 110f, usuario2);
        partida2 = new Partida(usuarios, null, null, municipios, null, null);
        assertEquals(0, usuario.getRachaActual());
    }
}