package tp.tacs.api.servicios;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Service
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

    public void validarAccion(String accion, Partida partida, Usuario duenio) {
        if (!partida.getEstado().equals(Estado.EN_CURSO)) {
            throw new PartidaException("La partida no está en curso. No se pudo " + accion);
        }
        if (!partida.idUsuarioEnTurnoActual().equals(duenio.getId())) {
            throw new PartidaException("No es el turno del dueño del municipio.");
        }
    }

    public void terminarPartida(Partida request) {
        request.setEstado(Estado.TERMINADA);
        Usuario ganador = usuarioConMasMunicipios(request);
        ganador.aumentarPartidasGanadas();
        ganador.aumentarRachaActual();
        request.getJugadoresIds().forEach(jugadorId -> {
            Usuario usuario = usuarioDao.get(jugadorId);
            usuario.aumentarPartidasJugadas();
            if (!usuario.getId().equals(ganador.getId()))
                usuario.reiniciarRacha();
        });
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

    public void repartirMunicipios(Partida partida, List<Municipio> municipios) {
        var idsJugadores = partida.getJugadoresIds();
        var usuarios = idsJugadores.stream().map(id -> usuarioDao.get(id)).collect(Collectors.toList());

        var i = 0;
        for (Municipio municipio : municipios) {
            municipio.setDuenio(usuarios.get(i%usuarios.size()));
            i++;
        }

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
        List<Municipio> municipios = municipioDao.getByIds(request.getMunicipios());
        return request.getJugadoresIds().stream().anyMatch(juagadorId -> esDuenioDeTodo(juagadorId, municipios));
    }

    private boolean esDuenioDeTodo(Long userId, List<Municipio> municipios) {
        return municipios.stream().allMatch(municipio -> municipio.esDe(userId));
    }

    public void desbloquearYProducirMunicipios(Partida partida) {
        List<Municipio> municipios = municipioDao.getByIds(partida.getMunicipios());
        municipios.forEach(municipio -> {
            municipio.desbloquear();
            servicioMunicipio.producir(municipio);
        });
    }

    public Float distanciaEntre(Municipio unMunicipio, Municipio otroMunicipio) {
        return (float) Point2D.distance(
                unMunicipio.getLatitud(), unMunicipio.getLongitud(),
                otroMunicipio.getLatitud(), otroMunicipio.getLongitud());
    }

    public Float multDistancia(Partida partida, Municipio municipioAtacante, Municipio municipioAtacado) {
        Float distancia = this.distanciaEntre(municipioAtacante, municipioAtacado);
        Float maxDist = partida.getMaxDist();
        Float minDist = partida.getMinDist();
        return 1 - (distancia - minDist) / (2 * (maxDist - minDist));
    }

    public Float multAltura(Partida partida, Municipio municipio) {
        Float altura = municipio.getAltura();
        Float minAltura = partida.getMinAltura();
        Float maxAltura = partida.getMaxAltura();
        return 1 + (altura - minAltura) / (2 * (maxAltura - minAltura));
    }

    public Integer gauchosDefensoresFinales(Partida partida, Municipio municipioAtacante, Municipio municipioAtacado) {
        Float multAltura = this.multAltura(partida, municipioAtacado);
        Float multDefensa = municipioAtacado.getEspecializacion().multDefensa();
        Float multDist = this.multDistancia(partida, municipioAtacante, municipioAtacado);
        Integer cantGauchosAtacado = municipioAtacado.getCantGauchos();
        Integer cantGauchosAtacante = municipioAtacante.getCantGauchos();
        return (int) Math.round(Math.ceil((cantGauchosAtacado * multAltura * multDefensa) - (cantGauchosAtacante * multDist))
                / (multAltura * multDefensa));
    }

    public Integer gauchosAtacantesFinales(Partida partida, Municipio municipioAtacante, Municipio municipioAtacado) {
        var multDist = this.multDistancia(partida, municipioAtacante, municipioAtacado);
        var multAltura = this.calcularMultAlturaMunicipio(partida, municipioAtacante);
        var multDefensa = municipioAtacado.getEspecializacion().multDefensa();
        return (int) Math
                .floor(municipioAtacante.getCantGauchos() * multDist - municipioAtacado.getCantGauchos() * multAltura * multDefensa);
    }

    public Float calcularMultAlturaMunicipio(Partida partida, Municipio municipio) {
        Float altura = municipio.getAltura();
        return 1 + (altura - partida.getMinAltura()) / (2 * (partida.getMaxAltura() - partida.getMinAltura()));
    }

    public void distintoDuenio(Municipio unMunicipio, Municipio otroMunucipio) {
        if (unMunicipio.getDuenio().getId().equals(otroMunucipio.getDuenio().getId()))
            throw new PartidaException("El duenio del municipio atacante no puede ser igual al del defensor");
    }

    public void calcularDistancias(Partida partida) {
        HashSet<Float> distancias = distanciasTotales(partida);
        partida.setMaxDist(distancias.stream().max(Float::compareTo).get());
        partida.setMinDist(distancias.stream().min(Float::compareTo).get());
    }

    private HashSet<Float> distanciasTotales(Partida partida) {
        var municipios = partida.getMunicipios().stream().map(id -> municipioDao.get(id));
        var coordenadas = municipios.map(municipio ->
                Lists.newArrayList(municipio.getLatitud(), municipio.getLongitud())).collect(Collectors.toSet());
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

    public void calcularAlturas(Partida partida) {
        Supplier<Stream<Float>> alturas = () -> partida.getMunicipios().stream().map(municipio -> {
            Municipio municipio1 = municipioDao.get(municipio);
            return municipio1.getAltura();
        });
        DoubleStream doubleStreamMax = alturas.get().mapToDouble(Float::doubleValue);
        DoubleStream doubleStreamMin = alturas.get().mapToDouble(Float::doubleValue);
        partida.setMaxAltura((float) doubleStreamMax.max().getAsDouble());
        partida.setMinAltura((float) doubleStreamMin.min().getAsDouble());
    }

    public void actualizarMunicipioPerdedor(Municipio municipioAtacante, Municipio municipioAtacado) {
        municipioAtacado.setCantGauchos(0);
        municipioAtacado.setDuenio(municipioAtacante.getDuenio());
    }

    public SimularAtacarMunicipioResponse simularAtacarMunicipio(Partida partida, Long idMunicipioAtacante, Long idMunicipioAtacado) {
        var municipioAtacante = municipioDao.get(idMunicipioAtacante);
        var municipioAtacado = municipioDao.get(idMunicipioAtacado);
        Integer gauchosFinales = this.gauchosDefensoresFinales(partida, municipioAtacante, municipioAtacado);
        return new SimularAtacarMunicipioResponse().exitoso(gauchosFinales <= 0);
    }

    public Partida obtenerPartidaPorId(Long request) {
        return partidaDao.get(request);
    }

    public MoverGauchosResponse moverGauchos(Long idMunicipioOrigen, Long idMunicipioDestino, Integer cantidad) {
        var municipioOrigen = municipioDao.get(idMunicipioOrigen);
        var municipioDestino = municipioDao.get(idMunicipioDestino);
        if (cantidad > municipioOrigen.getCantGauchos())
            throw new PartidaException("La cantidad de gauchos a mover no puede ser menor a la que posee el municipio origen.");
        municipioOrigen.sacarGauchos(cantidad);
        municipioDestino.agregarGauchos(cantidad);
        MunicipioEnJuegoModel municipioOrigenModel = municipioEnJuegoMapper.wrap(municipioOrigen);
        MunicipioEnJuegoModel municipioDestinoModel = municipioEnJuegoMapper.wrap(municipioDestino);
        return new MoverGauchosResponse()
                .municipioOrigen(municipioOrigenModel)
                .municipioDestino(municipioDestinoModel);
    }

    public Partida inicializar(CrearPartidaBody request) {
        String nombreProvincia;
        try{
            nombreProvincia = externalApis.getNombreProvinicas(request.getIdProvincia()).getNombre();
        }
        catch (IndexOutOfBoundsException e){
            throw new PartidaException("No se pudo crear la partida: No existe provincia con ese id");
        }
        Partida partida = Partida.builder()
                .estado(Estado.EN_CURSO)
                .jugadoresIds(request.getIdJugadores())
                .nombreProvincia(nombreProvincia)
                .idProvincia(request.getIdProvincia().toString())
                .modoDeJuego(new ModoRapido())
                .fechaCreacion(new Date())
                .build();

        var cantidadMunicipiosPartida = Math.toIntExact(request.getCantidadMunicipios());

        var municipiosDeLaPartida = externalApis.getMunicipios(partida.getIdProvincia(), cantidadMunicipiosPartida);
        municipiosDeLaPartida.forEach(municipio -> municipioDao.save(municipio));

        partida.setMunicipios(municipiosDeLaPartida.stream().map(Municipio::getId).collect(Collectors.toList()));
        this.calcularAlturas(partida);
        this.calcularDistancias(partida);

        municipiosDeLaPartida.forEach(municipio -> municipio.actualizarNivelProduccion(partida));

        this.repartirMunicipios(partida, municipiosDeLaPartida);
        partidaDao.save(partida);
        return partida;
    }

    public void atacar(Partida partida, Long idMunicipioAtacante, Long idMunicipioAtacado) {

        var municipioAtacante = municipioDao.get(idMunicipioAtacante);
        var municipioAtacado = municipioDao.get(idMunicipioAtacado);

        this.validarAccion("atacar", partida, municipioAtacante.getDuenio());
        this.distintoDuenio(municipioAtacante, municipioAtacado);

        Integer gauchosAtacantesFinal = this.gauchosAtacantesFinales(partida, municipioAtacante, municipioAtacado);
        municipioAtacante.setCantGauchos(gauchosAtacantesFinal);
        Integer gauchosDefensoresFinal = this.gauchosDefensoresFinales(partida, municipioAtacante, municipioAtacado);
        municipioAtacado.setCantGauchos(gauchosDefensoresFinal);

        if (gauchosDefensoresFinal <= 0) {
            this.actualizarMunicipioPerdedor(municipioAtacante, municipioAtacado);
        }
    }

    public void actualizarEstadoPartida(Partida partida, Estado estado) {
        partida.setEstado(estado);
    }

}
