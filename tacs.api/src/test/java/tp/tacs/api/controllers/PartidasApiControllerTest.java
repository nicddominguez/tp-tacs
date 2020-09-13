package tp.tacs.api.controllers;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.ModoRapido;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.PartidaBuilder;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.model.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
class PartidasApiControllerTest {

    @Spy
    Municipio municipioA;
    @Spy
    Municipio municipioB;
    @Spy
    Municipio municipioC;
    @Spy
    Municipio municipioD;

    @Mock
    ExternalApis municipioRepo;

    @Spy
    PartidaBuilder partidaBuilder;

    @Spy
    Usuario usuarioA;
    @Spy
    Usuario usuarioB;

    private Partida partidaBase;

    @Spy
    ModoRapido modoRapido = new ModoRapido();

    @Mock
    UsuarioDao usuarioDao = new UsuarioDao();

    List<Usuario> jugadores = new ArrayList<>();

    List<Municipio> municipios = new ArrayList<>();

    Date fechaPartida;

    private PartidasApiController partidasApiController = new PartidasApiController();

    private PartidaDao partidaDao = new PartidaDao();
    private Long idPartida = 0L;
    private String idProvincia = "22";

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockearMunicipio(municipioA, 500d, 0d, 10f, "1");
        mockearMunicipio(municipioB, 0d, 0d, 110f, "2");
        mockearMunicipio(municipioC, 30d, 40d, 210f, "3");
        mockearMunicipio(municipioD, 30d, 40d, 210f, "4");
        this.mockearUsuario(usuarioA, 1L, "usuarioA@gmail.com", "usuarioA");
        this.mockearUsuario(usuarioB, 2L, "usuarioB@gmail.com", "usuarioB");
        fechaPartida = new Date(2020, Calendar.APRIL, 1);
        partidaBase = new Partida(partidaDao, jugadores, Estado.EN_CURSO, idProvincia, municipios, modoRapido, fechaPartida);
        partidasApiController.setUsuarioDao(this.usuarioDao);
        partidaBuilder.setRepoMunicipios(this.municipioRepo);
        doReturn(municipios).when(municipioRepo).getMunicipios(anyString(), anyInt());
        partidasApiController.setPartidaBuilder(this.partidaBuilder);
    }

    @AfterEach
    public void tearup() {
        partidaDao.limpiar();
    }

    private void mockearUsuario(Usuario usuario, long id, String mail, String nombre) {
        doReturn(id).when(usuario).getId();
        doReturn(mail).when(usuario).getMail();
        doReturn(nombre).when(usuario).getNombre();
        usuarioDao.save(usuario);
        doReturn(usuario).when(usuarioDao).get(id);
        jugadores.add(usuario);
    }

    private void mockearMunicipio(Municipio municipio, double latitud, double longitud, float altura
            , String idMunicipioReal) {
        doReturn(latitud).when(municipio).getLatitud();
        doReturn(longitud).when(municipio).getLongitud();
        ArrayList<Double> coordenadasA = Lists.newArrayList(municipio.getLatitud(), municipio.getLongitud());
        doReturn(coordenadasA).when(municipio).getCoordenadas();
        doReturn(altura).when(municipio).getAltura();
        doReturn("testname").when(municipio).getNombre();
        doReturn("google.com").when(municipio).getPathImagen();

        doReturn(latitud).when(municipioRepo).getLatitud(idMunicipioReal);
        doReturn(longitud).when(municipioRepo).getLongitud(idMunicipioReal);
        doReturn(altura).when(municipioRepo).getAltura(idMunicipioReal);
        municipios.add(municipio);
        municipio.setRepoMunicipios(municipioRepo);
    }

    @Test
    void actualizarEstadoPartida() {
        var partidaModel = new PartidaModel().estado(EstadoDeJuegoModel.CANCELADA);
        var response = partidasApiController.actualizarEstadoPartida(idPartida, partidaModel);
        assertEquals(Estado.CANCELADA, partidaBase.getEstado());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void atacarMunicipio() {
        var atacarMunicipioBody = new AtacarMunicipioBody()
                .idMunicipioAtacante(municipioA.getId())
                .idMunicipioObjetivo(municipioD.getId());
        ResponseEntity<AtacarMunicipioResponse> response = partidasApiController
                .atacarMunicipio(idPartida, atacarMunicipioBody);
        var municipioAtacanteModel = response.getBody().getMunicipioAtacante();
        var municipioAtacadoModel = response.getBody().getMunicipioAtacado();
        assertEquals(1L, municipioAtacanteModel.getDuenio().getId());
        assertEquals(2L, municipioAtacadoModel.getDuenio().getId());
        assertEquals(usuarioA, municipioA.getDuenio());
        assertEquals(usuarioB, municipioD.getDuenio());

        assertEquals(11L, response.getBody().getMunicipioAtacante().getGauchos());
        assertEquals(13L, municipioAtacadoModel.getGauchos());
        assertNotNull(municipioAtacadoModel.getModo());
        assertNotNull(municipioAtacadoModel.getAltura());
        assertNotNull(municipioAtacadoModel.getUbicacion());
        assertNotNull(municipioAtacadoModel.getNombre());
        assertNotNull(municipioAtacadoModel.getProduccionDeGauchos());
        assertNotNull(municipioAtacadoModel.getPuntosDeDefensa());
        assertNotNull(municipioAtacadoModel.getUrlImagen());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void crearPartida() {
        var crearPartidaBody = new CrearPartidaBody()
                .idJugadores(this.jugadores.stream().map(Usuario::getId).collect(Collectors.toList()))
                .cantidadMunicipios(2L)
                .idProvincia(Long.valueOf(this.idProvincia))
                .modoDeJuego(ModoDeJuegoModel.RAPIDO);

        ResponseEntity<Void> response = partidasApiController.crearPartida(crearPartidaBody);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getPartida() {
        ResponseEntity<PartidaModel> response = partidasApiController.getPartida(idPartida);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(municipios.size(), response.getBody().getCantidadMunicipios());
        assertEquals(idPartida, response.getBody().getId());
        assertEquals(EstadoDeJuegoModel.ENPROGRESO, response.getBody().getEstado());
        assertNotNull(response.getBody().getInformacionDeJuego());
//        assertEquals(idProvincia, response.getBody().getProvincia().getId().toString()); TODO agregar cuando esté listo
        assertEquals(
                Arrays.asList(usuarioA.getId(), usuarioB.getId()),
                response.getBody()
                        .getJugadores()
                        .stream()
                        .map(UsuarioModel::getId)
                        .collect(Collectors.toList()));
        assertNull(response.getBody().getIdGanador());
        assertEquals(ModoDeJuegoModel.RAPIDO, response.getBody().getModoDeJuego());
    }

    @Test
    void listarPartidas() {
        ResponseEntity<ListarPartidasResponse> response = partidasApiController
                .listarPartidas(
                        null, null,
                        EstadoDeJuegoModel.ENPROGRESO,
                        "", 10L, 0L);
        assertEquals(idPartida, response.getBody().getPartidas().get(0).getId());
        assertNull(response.getBody().getPartidas().get(0).getInformacionDeJuego()); //Al front no le sirve
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void moverGauchos() {
        var moverGauchosBody = new MoverGauchosBody()
                .cantidad(1L)
                .idMunicipioOrigen(municipioA.getId())
                .idMunicipioDestino(municipioB.getId());
        ResponseEntity<MoverGauchosResponse> response = partidasApiController.moverGauchos(idPartida, moverGauchosBody);
        var municipioOrigenModel =  response.getBody().getMunicipioOrigen();
        var municipioDestinoModel =  response.getBody().getMunicipioDestino();
        assertEquals(16, municipioB.getCantGauchos());
        assertEquals(14, municipioA.getCantGauchos());
        assertEquals(14, municipioOrigenModel.getGauchos());
        assertEquals(16, municipioDestinoModel.getGauchos());

        assertNotNull(municipioOrigenModel.getModo());
        assertNotNull(municipioOrigenModel.getAltura());
        assertNotNull(municipioOrigenModel.getUbicacion());
        assertNotNull(municipioOrigenModel.getNombre());
        assertNotNull(municipioOrigenModel.getProduccionDeGauchos());
        assertNotNull(municipioOrigenModel.getPuntosDeDefensa());
        assertNotNull(municipioOrigenModel.getUrlImagen());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}