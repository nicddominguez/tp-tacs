package tp.tacs.api.servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.ModoRapido;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.mappers.MunicipioEnJuegoMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServicioPartidaTest {

    private ServicioPartida servicioPartida;

    private Partida partida;
    private Usuario usuario;
    private Usuario usuario1;
    private Municipio municipio;
    private Municipio municipioAtacante;
    private Municipio municipioDefensor;

    @Spy
    private ExternalApis externalApis;
    @Spy
    private UsuarioDao usuarioDao;
    @Spy
    private MunicipioDao municipioDao;
    @Spy
    private PartidaDao partidaDao;
    @Mock
    private ServicioMunicipio servicioMunicipio;
    private MunicipioEnJuegoMapper municipioEnJuegoMapper = new MunicipioEnJuegoMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuario = Usuario.builder().id(0L).rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("nombre0").mail("mail0").build();
        usuario1 = Usuario.builder().id(1L).rachaActual(1L).partidasJugadas(1L).partidasGanadas(1L).isAdmin(false).googleId("").nombre("nombre1").mail("mail1").build();
        municipio = Municipio.builder().id(0L).nombre("nombre0").externalApiId("0").duenio(usuario).nivelDeProduccion(1).latitud(1d).longitud(1d).altura(1f).urlImagen("urlImageb").build();
        municipioAtacante = Municipio.builder().id(0L).nombre("nombre0").externalApiId("0").duenio(usuario).nivelDeProduccion(1).latitud(1d).longitud(1d).altura(1f).urlImagen("urlImageb").build();
        municipioDefensor = Municipio.builder().id(0L).nombre("nombre0").externalApiId("0").duenio(usuario1).nivelDeProduccion(1).latitud(1d).longitud(1d).altura(1f).urlImagen("urlImageb").build();
        partida = Partida.builder().minAltura(1f).maxAltura(2f).maxDist(2f).minDist(1f).modoDeJuego(new ModoRapido()).build();

        servicioPartida = new ServicioPartida(externalApis, usuarioDao, municipioDao, partidaDao, servicioMunicipio, municipioEnJuegoMapper);
    }

    @Test
    void validarAccion() {
    }

    @Test
    void terminarPartida() {
    }

    @Test
    void usuarioConMasMunicipios() {
    }

    @Test
    void repartirMunicipios() {
    }

    @Test
    void eliminarPerdedores() {
    }

    @Test
    void pasarTurno() {
    }

    @Test
    void hayGanador() {
    }

    @Test
    void desbloquearYProducirMunicipios() {
    }

    @Test
    void distanciaEntre() {
        assertEquals(servicioPartida.distanciaEntre(municipioAtacante, municipioDefensor), 0.0f);
    }

    @Test
    void multDistancia() {
        Float multDistancia = servicioPartida.multDistancia(partida, municipioAtacante, municipioDefensor);
        assertEquals(multDistancia, 1.5f);
    }

    @Test
    void multAltura() {
        assertEquals(servicioPartida.multAltura(partida, municipio), 1f);
    }

    @Test
    void gauchosDefensoresFinales() {
        assertEquals(servicioPartida.gauchosDefensoresFinales(partida, municipioAtacante, municipioDefensor), 0);
    }

    @Test
    void gauchosAtacantesFinales() {
        assertEquals(servicioPartida.gauchosAtacantesFinales(partida, municipioAtacante, municipioDefensor), 4);
    }

    @Test
    void calcularMultAlturaMunicipio() {
        assertEquals(servicioPartida.calcularMultAlturaMunicipio(partida, municipio), 1f);
    }

    @Test
    void distintoDuenio() {
    }

    @Test
    void calcularDistancias() {
    }

    @Test
    void calcularAlturas() {
    }

    @Test
    void simularAtacarMunicipio() {
    }

    @Test
    void obtenerPartidaPorId() {
    }

    @Test
    void moverGauchos() {
    }

    @Test
    void inicializar() {
    }

    @Test
    void atacar() {
    }

    @Test
    void actualizarEstadoPartida() {
    }

    @Test
    void asignarProximoTurno() {
    }

    @Test
    void idUsuarioEnTurnoActual() {
    }

    @Test
    void perteneceALaPartida() {
    }
}