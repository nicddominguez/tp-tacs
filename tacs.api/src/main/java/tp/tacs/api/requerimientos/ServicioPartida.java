package tp.tacs.api.requerimientos;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.ModoRapido;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.handler.PartidaException;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.mappers.MunicipioEnJuegoMapper;
import tp.tacs.api.model.CrearPartidaBody;
import tp.tacs.api.model.MoverGauchosResponse;
import tp.tacs.api.model.MunicipioEnJuegoModel;
import tp.tacs.api.model.SimularAtacarMunicipioResponse;
import tp.tacs.api.requerimientos.models.ReqProducirModel;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Component
public class ServicioPartida {
    @Autowired
    private ExternalApis externalApis;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private PartidaDao partidaDao;
    @Autowired
    private ServicioMunicipio servicioMunicipio;
    @Autowired
    private MunicipioEnJuegoMapper municipioEnJuegoMapper;

    public void validarAccion(tp.tacs.api.requerimientos.models.ReqValidarAccionModel request) {
        if (!request.getPartida().getEstado().equals(Estado.EN_CURSO)) {
            throw new PartidaException("La partida no está en curso. No se pudo " + request.getAccion());
        }
        if (request.getPartida().idUsuarioEnTurnoActual() != request.getDuenio().getId()) {
            throw new PartidaException("No es el turno del dueño del municipio.");
        }
    }

    public void terminarPartida(Partida request) {
        request.setEstado(Estado.TERMINADA);
        Usuario ganador = usuarioConMasMunicipios(request);
        ganador.aumentarPartidasGanadas();
        ganador.aumentarRachaActual();
        usuarioDao.save(ganador);
        request.getJugadoresIds().forEach(jugadorId -> {
            Usuario usuario = usuarioDao.get(jugadorId);
            usuario.aumentarPartidasJugadas();
            if (!usuario.getId().equals(ganador.getId()))
                usuario.reiniciarRacha();
            usuarioDao.save(usuario);
        });
        partidaDao.save(request);
    }

    public Usuario usuarioConMasMunicipios(Partida partida) {
        var municipiosConDuenio = municipioDao.getByIds(partida.getMunicipios()).stream()
                .filter(municipio -> municipio.getDuenio() != null).collect(Collectors.toSet());

        //Agrupa por usuario sumando la cantidad de municipios que tenga
        var ganadosPorUsuario = municipiosConDuenio.stream()
                .collect(Collectors.groupingBy(Municipio::getDuenio, Collectors.counting()));

        return Collections
                .max(ganadosPorUsuario.entrySet(), Map.Entry.comparingByValue())
                .getKey();
    }

    public Partida repartirMunicipios(tp.tacs.api.requerimientos.models.ReqRepartirMunicipiosModel request) {
        var cantidad = Math.floor(request.getCantidadMunicipios() / request.getPartida().getJugadoresIds().size());
        List<Municipio> municipios = externalApis.getMunicipios(request.getPartida().getIdProvincia(), request.getCantidadMunicipios().intValue());

        request.getPartida().getJugadoresIds().forEach(id -> {
            Usuario jugador = usuarioDao.get(id);
            for (int i = 0; i < cantidad; i++) {
                municipios.stream()
                        .filter(Municipio::estaBacante)
                        .findAny()
                        .ifPresent(municipio -> {
                            municipio.setDuenio(jugador);
                            municipioDao.save(municipio);
                        });
            }
        });
        request.getPartida().setMunicipios(municipios.stream().map(Municipio::getId).collect(Collectors.toList()));
        return request.getPartida();
    }

    public void pasarTurno(Partida request) {
        if (Estado.EN_CURSO.equals(request.getEstado())) {
            if (this.hayGanador(request)) {
                this.terminarPartida(request);
            } else {
                request.asignarProximoTurno();
                this.desbloquearYProducirMunicipios(request);
            }
        } else {
            throw new PartidaException("No se puede pasar el turno de una partida que no este en curso");
        }
    }

    public Boolean hayGanador(Partida request) {
        List<Municipio> municipios = municipioDao.getByIds(request.getJugadoresIds());
        return request.getJugadoresIds().stream().anyMatch(juagadorId -> esDuenioDeTodo(juagadorId, municipios));
    }

    private boolean esDuenioDeTodo(Long userId, List<Municipio> municipios) {
        return municipios.stream().allMatch(municipio -> municipio.esDe(userId));
    }

    public void desbloquearYProducirMunicipios(Partida request) {
        List<Municipio> municipios = municipioDao.getByIds(request.getMunicipios());
        municipios.forEach(municipio -> {
            municipio.desbloquear();
            Municipio municipioProducido = servicioMunicipio.producir(ReqProducirModel.builder().municipio(municipio).partida(request).build());
            municipioDao.save(municipioProducido);
        });
    }

    public Float distanciaEntre(tp.tacs.api.requerimientos.models.DosMunicipiosModel request) {
        ArrayList<Double> coordenadas1 = externalApis.getCoordenadasArray(request.getMunicipioAtacante().getExternalApiId());
        ArrayList<Double> coordenadas2 = externalApis.getCoordenadasArray(request.getMunicipioDefensor().getExternalApiId());
        return (float) Point2D.distance(coordenadas1.get(0), coordenadas1.get(1), coordenadas2.get(0), coordenadas2.get(1));
    }

    public Float multDistancia(tp.tacs.api.requerimientos.models.PartidaConMunicipios request) {
        Float distancia = this.distanciaEntre(
                tp.tacs.api.requerimientos.models.DosMunicipiosModel.builder()
                        .municipioDefensor(request.getMunicipioDefensor())
                        .municipioAtacante(request.getMunicipioAtacante())
                        .build());
        Float maxDist = request.getPartida().getMaxDist();
        Float minDist = request.getPartida().getMinDist();
        return 1 - (distancia - minDist) / (2 * (maxDist - minDist));
    }

    public Float multAltura(tp.tacs.api.requerimientos.models.PartidaConMunicipio request) {
        Float altura = externalApis.getAltura(request.getMunicipio().getExternalApiId());
        Float minAltura = request.getPartida().getMinAltura();
        Float maxAltura = request.getPartida().getMaxAltura();
        return 1 + (altura - minAltura) / (2 * (maxAltura - minAltura));
    }

    public Integer gauchosDefensoresFinales(tp.tacs.api.requerimientos.models.PartidaConMunicipios request) {
        Float multAltura = this.multAltura(tp.tacs.api.requerimientos.models.PartidaConMunicipio.builder().partida(request.getPartida()).municipio(request.getMunicipioDefensor()).build());
        Float multDefensa = request.getMunicipioDefensor().getEspecializacion().multDefensa(request.getPartida());
        Float multDist = this.multDistancia(request);
        Integer cantGauchosDefensor = request.getMunicipioDefensor().getCantGauchos();
        Integer cantGauchosAtacante = request.getMunicipioAtacante().getCantGauchos();
        return (int) Math.round(Math.ceil((cantGauchosDefensor * multAltura * multDefensa) - (cantGauchosAtacante * multDist))
                / (multAltura * multDefensa));
    }

    public Integer gauchosAtacantesFinales(tp.tacs.api.requerimientos.models.PartidaConMunicipios request) {
        var multDist = this.multDistancia(request);
        var multAltura = this.calcularMultAlturaMunicipio
                (tp.tacs.api.requerimientos.models.PartidaConMunicipio.builder().municipio(request.getMunicipioAtacante()).partida(request.getPartida()).build());
        var multDefensa = request.getMunicipioDefensor().getEspecializacion().multDefensa(request.getPartida());
        return (int) Math
                .floor(request.getMunicipioAtacante().getCantGauchos() * multDist - request.getMunicipioDefensor().getCantGauchos() * multAltura * multDefensa);
    }

    public Float calcularMultAlturaMunicipio(tp.tacs.api.requerimientos.models.PartidaConMunicipio request) {
        Float altura = externalApis.getAltura(request.getMunicipio().getExternalApiId());
        Partida partida = request.getPartida();
        return 1 + (altura - partida.getMinAltura()) / (2 * (partida.getMaxAltura() - partida.getMinAltura()));
    }

    public void distintoDuenio(tp.tacs.api.requerimientos.models.DosMunicipiosModel request) {
        if (request.getMunicipioAtacante().getDuenio().getId().equals(request.getMunicipioDefensor().getId()))
            throw new PartidaException("El duenio del municipio atacante no puede ser igual al del defensor");
    }

    public Partida calcularDistancias(Partida request) {
        HashSet<Float> distancias = distanciasTotales(request);
        request.setMaxDist(distancias.stream().max(Float::compareTo).get());
        request.setMinDist(distancias.stream().min(Float::compareTo).get());
        return request;
    }

    private HashSet<Float> distanciasTotales(Partida partida) {
        var coordenadas = partida.getMunicipios()
                .stream()
                .map(id -> {
                    Municipio municipio = municipioDao.get(id);
                    var a = Arrays
                            .asList(externalApis.getCoordenadas(municipio.getExternalApiId()).split(","))
                            .stream().map(v -> Double.valueOf(v))
                            .collect(Collectors.toList());
                    var coordenadasString = new ArrayList<>(a);
                    return coordenadasString;
                }).collect(Collectors.toSet());
        var combinaciones = Sets.combinations(coordenadas, 2);

        var distancias = new HashSet<Float>();
        combinaciones.forEach(combinacion -> {
            var list = new ArrayList<>(combinacion);
            var distancia = distanciaEntre(list.get(0), list.get(1));
            distancias.add(distancia);
        });
        return distancias;
    }

    private Float distanciaEntre(ArrayList<Double> coordenadasDesde, ArrayList<Double> coordenadasHasta) {
        return (float) Point2D.distance(
                coordenadasDesde.get(0), coordenadasDesde.get(1),
                coordenadasHasta.get(0), coordenadasHasta.get(1));
    }

    public Partida calcularAlturas(Partida request) {
        Supplier<Stream<Float>> alturas = () -> request.getMunicipios().stream().map(municipio -> {
            Municipio municipio1 = municipioDao.get(municipio);
            return externalApis.getAltura(municipio1.getExternalApiId());
        });
        DoubleStream doubleStreamMax = alturas.get().mapToDouble(value -> value.doubleValue());
        DoubleStream doubleStreamMin = alturas.get().mapToDouble(value -> value.doubleValue());
        request.setMaxAltura((float) doubleStreamMax.max().getAsDouble());
        request.setMinAltura((float) doubleStreamMin.min().getAsDouble());
        return request;
    }

    public void actualizarMunicipioPerdedor(tp.tacs.api.requerimientos.models.PartidaConMunicipios request) {
        request.getMunicipioDefensor().setCantGauchos(0);
        request.getMunicipioDefensor().setDuenio(request.getMunicipioAtacante().getDuenio());
    }

    public SimularAtacarMunicipioResponse simularAtacarMunicipio(tp.tacs.api.requerimientos.models.ReqSimularAtacarMunicipioRequest request) {
        var municipioAtacante = municipioDao.get(request.getIdMunicipioAtacante());
        var municipioAtacado = municipioDao.get(request.getIdMunicipioObjectivo());
        var partida = partidaDao.get(request.getIdPartida());
        tp.tacs.api.requerimientos.models.PartidaConMunicipios preRequest = tp.tacs.api.requerimientos.models.PartidaConMunicipios.builder().municipioAtacante(municipioAtacante).municipioDefensor(municipioAtacado)
                .partida(partida).build();
        Integer gauchosFinales = this.gauchosDefensoresFinales(preRequest);
        return new SimularAtacarMunicipioResponse().exitoso(gauchosFinales <= 0);
    }

    public Partida obtenerPartidaPorId(Long request) {
        return partidaDao.get(request);
    }

    public MoverGauchosResponse moverGauchos(tp.tacs.api.requerimientos.models.ReqMoverGauchosModel request) {
        Municipio municipioOrigen = municipioDao.get(request.getIdMunicipioOrigen());
        Municipio municipioDestino = municipioDao.get(request.getIdMunicipioDestino());
        var cantidad = Math.toIntExact(request.getCantidad());
        municipioOrigen.sacarGauchos(cantidad);
        municipioDestino.agregarGauchos(cantidad);
        municipioDao.save(municipioDestino);
        municipioDao.save(municipioOrigen);
        MunicipioEnJuegoModel municipioOrigenModel = municipioEnJuegoMapper.wrap(municipioOrigen);
        MunicipioEnJuegoModel municipioDestinoModel = municipioEnJuegoMapper.wrap(municipioDestino);
        return new MoverGauchosResponse()
                .municipioOrigen(municipioOrigenModel)
                .municipioDestino(municipioDestinoModel);
    }

    public Partida inicializar(CrearPartidaBody request) {
        Partida partida = Partida.builder()
                .estado(Estado.EN_CURSO)
                .jugadoresIds(request.getIdJugadores())
                .idProvincia(request.getIdProvincia().toString())
                .modoDeJuego(new ModoRapido())
                .fechaCreacion(new Date())
                .build();
        tp.tacs.api.requerimientos.models.ReqRepartirMunicipiosModel partidaConMunicipiosRequest = tp.tacs.api.requerimientos.models.ReqRepartirMunicipiosModel.builder()
                .partida(partida)
                .cantidadMunicipios(request.getCantidadMunicipios())
                .build();

        Partida partidaConMunicipios = this.repartirMunicipios(partidaConMunicipiosRequest);
        Partida partidaConAlturas = this.calcularAlturas(partidaConMunicipios);
        Partida partidaConDistancias = this.calcularDistancias(partidaConAlturas);
        partidaDao.save(partidaConDistancias);
        return partidaConDistancias;
    }

    public tp.tacs.api.requerimientos.models.AtaqueMunicipiosResponse atacar(tp.tacs.api.requerimientos.models.ReqAtacarModel request) {
        Municipio municipioAtacante = municipioDao.get(request.getIdMunicipioAtacante());
        Municipio municipioDefensor = municipioDao.get(request.getIdMunicipioDefensor());
        Partida partida = partidaDao.get(request.getIdPartida());
        tp.tacs.api.requerimientos.models.ReqValidarAccionModel validarRequest = tp.tacs.api.requerimientos.models.ReqValidarAccionModel.builder().accion("atacar").duenio(municipioAtacante.getDuenio()).partida(partida)
                .build();
        this.validarAccion(validarRequest);
        tp.tacs.api.requerimientos.models.DosMunicipiosModel distintoDuenioRequest = tp.tacs.api.requerimientos.models.DosMunicipiosModel.builder().municipioAtacante(municipioAtacante)
                .municipioDefensor(municipioDefensor).build();
        this.distintoDuenio(distintoDuenioRequest);

        tp.tacs.api.requerimientos.models.PartidaConMunicipios partidaConMunicipios = tp.tacs.api.requerimientos.models.PartidaConMunicipios.builder().municipioAtacante(municipioAtacante)
                .municipioDefensor(municipioDefensor).partida(partida).build();

        Integer gauchosAtacantesFinal = this.gauchosAtacantesFinales(partidaConMunicipios);
        municipioAtacante.setCantGauchos(gauchosAtacantesFinal);
        Integer gauchosDefensoresFinal = this.gauchosDefensoresFinales(partidaConMunicipios);
        municipioDefensor.setCantGauchos(gauchosDefensoresFinal);

        if (gauchosDefensoresFinal <= 0) {
            this.actualizarMunicipioPerdedor(partidaConMunicipios);
        }
        this.pasarTurno(partidaConMunicipios.getPartida());
        return tp.tacs.api.requerimientos.models.AtaqueMunicipiosResponse.builder().municipioAtacante(partidaConMunicipios.getMunicipioAtacante())
                .municipioDefensor(partidaConMunicipios.getMunicipioDefensor()).build();
    }

    public void actualizarEstadoPartida(tp.tacs.api.requerimientos.models.ReqActualizarEstadoPartidaRequest request) {
        Partida partida = partidaDao.get(request.getIdPartida());
        partida.setEstado(request.getEstado());
        partidaDao.update(partida);
    }

}
