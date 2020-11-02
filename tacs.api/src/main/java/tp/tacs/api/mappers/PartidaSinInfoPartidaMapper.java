package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.PartidaSinInfo;

@Component
public class PartidaSinInfoPartidaMapper extends AbstractMapper<PartidaSinInfo, Partida> {
    @Override
    protected Partida wrapModel(PartidaSinInfo partida) {
        return Partida.builder()
                .estado(partida.getEstado())
                .fechaCreacion(partida.getFechaCreacion())
                .id(partida.getId())
                .idsJugadoresActuales(partida.getJugadoresIds())
                .municipios(partida.getMunicipios())
                .ganador(partida.getGanador())
                .idProvincia(partida.getIdProvincia())
                .nombreProvincia(partida.getNombreProvincia())
                .idsJugadoresActuales(partida.getJugadoresIds())
                .modoDeJuego(partida.getModoDeJuego())
                .usuarioJugandoIndiceLista(partida.getUsuarioJugandoIndiceLista())
                .minAltura(partida.getMinAltura())
                .minDist(partida.getMinDist())
                .maxAltura(partida.getMaxAltura())
                .maxDist(partida.getMaxDist())
                .build();
    }

    @Override
    protected PartidaSinInfo unwrapModel(Partida partida) {
        return new PartidaSinInfo(partida.getId(),partida.getIdsJugadoresActuales(), partida.getUsuarioJugandoIndiceLista(), partida.getEstado(),
                partida.getNombreProvincia(), partida.getIdProvincia(), partida.getMunicipios(), partida.getModoDeJuego(), partida.getFechaCreacion(),
                partida.getGanador(), partida.getMinAltura(), partida.getMaxAltura(), partida.getMaxDist(), partida.getMinDist());
    }

}
