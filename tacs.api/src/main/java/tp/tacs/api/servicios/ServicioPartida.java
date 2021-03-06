package tp.tacs.api.servicios;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.*;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.handler.MunicipioException;
import tp.tacs.api.handler.PartidaException;
import tp.tacs.api.http.exception.HttpErrorException;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.mappers.MunicipioEnJuegoMapper;
import tp.tacs.api.model.*;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
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

    public void validarTurnoPartida(String accion, Partida partida, Usuario duenio) {
        if (!partida.getEstado().equals(Estado.EN_CURSO)) {
            throw new PartidaException("La partida no está en curso. No se pudo " + accion);
        }
        if (!this.idUsuarioEnTurnoActual(partida).equals(duenio.getId())) {
            throw new PartidaException("No es el turno del dueño del municipio.");
        }
    }

    public void terminarPartida(Partida partida, Estado estadoPartida) {
        partida.setEstado(estadoPartida);
        Usuario ganador = usuarioConMasMunicipios(partida);
        ganador.aumentarPartidasGanadas();
        ganador.aumentarRachaActual();
        usuarioDao.save(ganador);
        partida.getIdsJugadoresOriginales().forEach(jugadorId -> {
            Usuario usuario = usuarioDao.get(jugadorId);
            usuario.aumentarPartidasJugadas();
            if (!usuario.getId().equals(ganador.getId()))
                usuario.reiniciarRacha();
            usuarioDao.save(usuario);
        });
        partida.setGanador(ganador);
        partidaDao.save(partida);
    }

    public Usuario usuarioConMasMunicipios(Partida partida) {
        Set<Municipio> municipiosConDuenio = municipioDao.municipiosConDuenio(partida);

        //Agrupa por usuario sumando la cantidad de municipios que tenga
        var ganadosPorUsuario = municipiosConDuenio.stream()
                .collect(Collectors.groupingBy(Municipio::getDuenio, Collectors.counting()));

        //en caso de 2 con el mismo municipio, gana el "primero", definido por el comparador
        return Collections
                .max(ganadosPorUsuario.entrySet(), Map.Entry.comparingByValue())
                .getKey();
    }

    public void repartirMunicipios(Partida partida, List<Municipio> municipios) {
        var idsJugadores = partida.getIdsJugadoresActuales();
        var usuarios = idsJugadores.stream().map(id -> usuarioDao.get(id)).collect(Collectors.toList());

        var i = 0;
        for (Municipio municipio : municipios) {
            municipio.setDuenio(usuarios.get(i % usuarios.size()));
            municipioDao.save(municipio);
            i++;
        }

    }

    public void eliminarPerdedores(Partida partida) {
        var municipios = municipioDao.getByIds(partida.getMunicipios());
        var duenios = municipios.stream().map(municipio -> municipio.getDuenio().getId()).collect(Collectors.toSet());
        var jugadores = partida.getIdsJugadoresActuales();
        var perdedores = jugadores.stream().filter(idJugador -> !duenios.contains(idJugador)).collect(Collectors.toSet());
        partida.setIdsJugadoresActuales(jugadores.stream().filter(idJugador -> !perdedores.contains(idJugador)).collect(Collectors.toList()));
    }

    public void pasarTurno(Partida request) {

        if (Estado.EN_CURSO.equals(request.getEstado())) {
            if (this.hayGanador(request)) {
                this.terminarPartida(request, Estado.TERMINADA);
            } else {
                this.eliminarPerdedores(request);
                this.asignarProximoTurno(request);
                partidaDao.save(request);
                this.desbloquearYProducirMunicipios(request);
            }
        } else {
            throw new PartidaException("No se puede pasar el turno de una partida que no este en curso");
        }
    }

    public Boolean hayGanador(Partida request) {
        List<Municipio> municipios = municipioDao.getByIds(request.getMunicipios());
        return request.getIdsJugadoresActuales().stream().anyMatch(juagadorId -> esDuenioDeTodo(juagadorId, municipios));
    }

    private boolean esDuenioDeTodo(String userId, List<Municipio> municipios) {
        return municipios.stream().allMatch(municipio -> servicioMunicipio.esDe(municipio, userId));
    }

    public void desbloquearYProducirMunicipios(Partida partida) {
        List<Municipio> municipios = municipioDao.getByIds(partida.getMunicipios());
        municipios.forEach(municipio -> {
            municipio.setBloqueado(false);
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
        return 1f - (distancia - minDist) / (2f * (maxDist - minDist));
    }

    public Float multAltura(Partida partida, Municipio municipio) {
        Float altura = Math.abs(municipio.getAltura());
        Float minAltura = partida.getMinAltura();
        Float maxAltura = partida.getMaxAltura();
        return 1f + (altura - minAltura) / (2f * (maxAltura - minAltura));
    }

    public Integer gauchosDefensoresFinales(Partida partida, Municipio municipioAtacante, Municipio municipioAtacado) {
        Float multAltura = this.multAltura(partida, municipioAtacado);
        Float multDefensa = municipioAtacado.getEspecializacion().multDefensa(partida.getModoDeJuego());
        Float multDist = this.multDistancia(partida, municipioAtacante, municipioAtacado);
        Integer cantGauchosAtacado = municipioAtacado.getCantGauchos();
        Integer cantGauchosAtacante = municipioAtacante.getCantGauchos();
        int gauchosDefensoresFinales = (int) Math.round(Math.ceil((cantGauchosAtacado * multAltura * multDefensa) - (cantGauchosAtacante * multDist))
                / (multAltura * multDefensa));
        return Math.max(gauchosDefensoresFinales, 0);
    }

    public Integer gauchosAtacantesFinales(Partida partida, Municipio municipioAtacante, Municipio municipioAtacado) {
        var multDist = this.multDistancia(partida, municipioAtacante, municipioAtacado);
        var multAltura = this.calcularMultAlturaMunicipio(partida, municipioAtacante);
        var multDefensa = municipioAtacado.getEspecializacion().multDefensa(partida.getModoDeJuego());
        int gauchosAtacantesFinales = (int) Math
                .floor(municipioAtacante.getCantGauchos() * multDist - municipioAtacado.getCantGauchos() * multAltura * multDefensa);
        return Math.max(gauchosAtacantesFinales, 0);
    }

    public Float calcularMultAlturaMunicipio(Partida partida, Municipio municipio) {
        Float altura = Math.abs(municipio.getAltura());
        return 1f + (altura - partida.getMinAltura()) / (2f * (partida.getMaxAltura() - partida.getMinAltura()));
    }

    public void distintoDuenio(Municipio unMunicipio, Municipio otroMunucipio) {
        if (unMunicipio.getDuenio().getId().equals(otroMunucipio.getDuenio().getId()))
            throw new PartidaException("El duenio del municipio atacante no puede ser igual al del defensor");
    }

    public void calcularDistancias(Partida partida) {
        HashSet<Float> distancias = distanciasTotales(partida);
        partida.setMaxDist(distancias.stream().max(Float::compareTo).get());
        partida.setMinDist(distancias.stream().min(Float::compareTo).get());
        partidaDao.save(partida);
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
            return Math.abs(municipio1.getAltura());
        });
        DoubleStream doubleStreamMax = alturas.get().mapToDouble(Float::doubleValue);
        DoubleStream doubleStreamMin = alturas.get().mapToDouble(Float::doubleValue);
        partida.setMaxAltura((float) doubleStreamMax.max().getAsDouble());
        partida.setMinAltura((float) doubleStreamMin.min().getAsDouble());
        partidaDao.save(partida);
    }

    public SimularAtacarMunicipioResponse simularAtacarMunicipio(Partida partida, String idMunicipioAtacante, String idMunicipioAtacado) {
        var municipioAtacante = municipioDao.get(idMunicipioAtacante);
        var municipioAtacado = municipioDao.get(idMunicipioAtacado);
        Integer gauchosFinales = this.gauchosDefensoresFinales(partida, municipioAtacante, municipioAtacado);
        return new SimularAtacarMunicipioResponse().exitoso(gauchosFinales <= 0);
    }

    public Partida obtenerPartidaPorId(String request) {
        return partidaDao.get(request);
    }


    public MoverGauchosResponse moverGauchos(Partida partida, String idMunicipioOrigen, String idMunicipioDestino, Integer cantidad) {
        var municipioOrigen = municipioDao.get(idMunicipioOrigen);
        var municipioDestino = municipioDao.get(idMunicipioDestino);
        this.validarTurnoPartida("mover gauchos", partida, municipioOrigen.getDuenio());
        this.validarBloqueo(municipioOrigen);
        this.validarDueniosAlMoverGauchos(municipioOrigen.getDuenio(), municipioDestino.getDuenio());

        this.moverGauchos(municipioOrigen, municipioDestino, cantidad);

        municipioDao.save(municipioOrigen);
        municipioDao.save(municipioDestino);
        MunicipioEnJuegoModel municipioOrigenModel = municipioEnJuegoMapper.toModel(municipioOrigen, partida.getModoDeJuego());
        MunicipioEnJuegoModel municipioDestinoModel = municipioEnJuegoMapper.toModel(municipioDestino, partida.getModoDeJuego());
        return new MoverGauchosResponse()
                .municipioOrigen(municipioOrigenModel)
                .municipioDestino(municipioDestinoModel);
    }

    private void validarDueniosAlMoverGauchos(Usuario duenioOrigen, Usuario duenioDestino) {
        if (!duenioOrigen.getId().equals(duenioDestino.getId()))
            throw new MunicipioException("No se puede mover gauchos entre municipios de distinto dueño");
    }

    private void validarBloqueo(Municipio municipioOrigen) {
        if (municipioOrigen.isBloqueado()) {
            throw new MunicipioException(
                    new StringBuilder("No se pudo realizar la acción, ")
                            .append(municipioOrigen.getNombre())
                            .append(" está bloqueado.").toString());
        }
    }

    private void moverGauchos(Municipio municipioOrigen, Municipio municipioDestino, Integer cantidad) {
        if (municipioOrigen.getId().equals(municipioDestino.getId()))
            throw new MunicipioException("El municipio de origen de los gauchos no puede ser el de destino.");
        if (cantidad > municipioOrigen.getCantGauchos())
            throw new MunicipioException("La cantidad de gauchos a mover no puede ser menor a la que posee el municipio origen.");
        servicioMunicipio.sacarGauchos(municipioOrigen, cantidad);
        servicioMunicipio.agregarGauchos(municipioDestino, cantidad);
        municipioDestino.setBloqueado(true);
    }

    public Partida inicializar(CrearPartidaBody request) {
        String nombreProvincia;
        if (request.getCantidadMunicipios() <= 1) {
            throw new PartidaException("No se pudo crear la partida: la cantidad de municipios debe ser mayor a 1");
        }
        try {
            nombreProvincia = externalApis.getNombreProvinicas(request.getIdProvincia()).getNombre();
        } catch (HttpErrorException | IndexOutOfBoundsException e) {
            throw new PartidaException("No se pudo crear la partida: No se encontró provincia con ese id");
        }
        Partida partida = Partida.builder()
                .estado(Estado.EN_CURSO)
                .idsJugadoresOriginales(request.getIdJugadores())
                .idsJugadoresActuales(request.getIdJugadores())
                .nombreProvincia(nombreProvincia)
                .idProvincia(request.getIdProvincia().toString())
                .modoDeJuego(new ModoRapido())
                .fechaCreacion(new Date())
                .build();

        var cantidadMunicipiosPartida = Math.toIntExact(request.getCantidadMunicipios());
        List<Municipio> municipios = externalApis.getMunicipios(partida.getIdProvincia(), nombreProvincia);
        var municipiosDeLaPartida = municipios.subList(0, Math.min(municipios.size(), cantidadMunicipiosPartida));

        municipiosDeLaPartida.forEach(municipio -> municipioDao.save(municipio));

        partida.setMunicipios(municipiosDeLaPartida.stream().map(Municipio::getId).collect(Collectors.toList()));
        this.calcularAlturas(partida);
        this.calcularDistancias(partida);

        municipiosDeLaPartida.forEach(municipio -> servicioMunicipio.actualizarNivelProduccion(municipio, partida));

        this.repartirMunicipios(partida, municipiosDeLaPartida);
        municipiosDeLaPartida.forEach(municipio -> municipioDao.save(municipio));
        return partida;
    }

    public AtacarMunicipioResponse atacar(Partida partida, String idMunicipioAtacante, String idMunicipioAtacado) {

        var municipioAtacante = municipioDao.get(idMunicipioAtacante);
        var municipioAtacado = municipioDao.get(idMunicipioAtacado);
        this.validarTurnoPartida("atacar", partida, municipioAtacante.getDuenio());
        this.distintoDuenio(municipioAtacante, municipioAtacado);
        this.validarBloqueo(municipioAtacante);

        realizarAtaque(partida, municipioAtacante, municipioAtacado);

        municipioDao.save(municipioAtacado);
        municipioDao.save(municipioAtacante);
        if (this.hayGanador(partida)) {
            this.terminarPartida(partida, Estado.TERMINADA);
        }

        return new AtacarMunicipioResponse()
                .municipioAtacado(municipioEnJuegoMapper.toModel(municipioAtacado, partida.getModoDeJuego()))
                .municipioAtacante(municipioEnJuegoMapper.toModel(municipioAtacante, partida.getModoDeJuego()));
    }

    private void realizarAtaque(Partida partida, Municipio municipioAtacante, Municipio municipioAtacado) {
        var gauchosAtacantesFinal = this.gauchosAtacantesFinales(partida, municipioAtacante, municipioAtacado);
        var gauchosDefensoresFinal = this.gauchosDefensoresFinales(partida, municipioAtacante, municipioAtacado);
        municipioAtacante.setCantGauchos(gauchosAtacantesFinal);
        municipioAtacado.setCantGauchos(gauchosDefensoresFinal);

        if (gauchosDefensoresFinal <= 0) {
            municipioAtacado.setDuenio(municipioAtacante.getDuenio());
            this.moverGauchos(municipioAtacante, municipioAtacado, gauchosAtacantesFinal);
        }
    }

    public void actualizarEstadoPartida(Partida partida, Estado estado) {
        if (estado.equals(Estado.CANCELADA)) {
            rendirse(partida);
            return;
        }
        if (estado != Estado.EN_CURSO)
            this.terminarPartida(partida, estado);
        else {
            partida.setEstado(estado);
            partidaDao.save(partida);
        }
    }

    public void asignarProximoTurno(Partida partida) {
        if (partida.getUsuarioJugandoIndiceLista() < partida.getIdsJugadoresActuales().size() - 1) {
            partida.setUsuarioJugandoIndiceLista(partida.getUsuarioJugandoIndiceLista() + 1);
        } else {
            partida.setUsuarioJugandoIndiceLista(0);
        }
    }

    public String idUsuarioEnTurnoActual(Partida partida) {
        return partida.getIdsJugadoresActuales().get(partida.getUsuarioJugandoIndiceLista());
    }

    public List<PartidaSinInfo> getPartidasFiltradasUsuario(Date fechaInicio, Date fechaFin, EstadoDeJuegoModel estado, Usuario usuario) {
        return partidaDao.getPartidasFiltradasUsuario(fechaInicio, fechaFin, estado, usuario);
    }

    private void rendirse(Partida partida) {
        var jugadorRendido = idUsuarioEnTurnoActual(partida);
        var nuevosMunicipios = municipioDao.getByIds(partida.getMunicipios()).stream()
                .filter(municipio -> !municipio.getDuenio().getId().equalsIgnoreCase(jugadorRendido)).collect(Collectors.toList());
        partida.setMunicipios(nuevosMunicipios.stream().map(Municipio::getId).collect(Collectors.toList()));
        partida.setIdsJugadoresActuales(
                partida.getIdsJugadoresActuales().stream().filter(id -> !id.equals(jugadorRendido)).collect(Collectors.toList()));
        pasarTurno(partida);
        if (partida.getIdsJugadoresActuales().size() == 1)
            terminarPartida(partida, Estado.TERMINADA);
    }

}
