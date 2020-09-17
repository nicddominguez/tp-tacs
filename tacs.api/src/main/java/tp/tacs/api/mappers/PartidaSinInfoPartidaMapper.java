package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.PartidaSinInfo;

import javax.servlet.http.Part;

@Component
public class PartidaSinInfoPartidaMapper extends AbstractMapper<PartidaSinInfo, Partida> {
    @Override
    protected Partida wrapModel(PartidaSinInfo partida) {
        return Partida.builder()
                .estado(partida.getEstado())
                .fechaCreacion(partida.getFechaCreacion())
                .id(partida.getId())
                .jugadoresIds(partida.getJugadoresIds())
                .municipios(partida.getMunicipios())
                .ganador(partida.getGanador())
                .idProvincia(partida.getIdProvincia())
                .nombreProvincia(partida.getNombreProvincia())
                .jugadoresIds(partida.getJugadoresIds())
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
        return PartidaSinInfo.builder()
                .estado(partida.getEstado())
                .fechaCreacion(partida.getFechaCreacion())
                .id(partida.getId())
                .jugadoresIds(partida.getJugadoresIds())
                .municipios(partida.getMunicipios())
                .ganador(partida.getGanador())
                .idProvincia(partida.getIdProvincia())
                .nombreProvincia(partida.getNombreProvincia())
                .jugadoresIds(partida.getJugadoresIds())
                .modoDeJuego(partida.getModoDeJuego())
                .usuarioJugandoIndiceLista(partida.getUsuarioJugandoIndiceLista())
                .minAltura(partida.getMinAltura())
                .minDist(partida.getMinDist())
                .maxAltura(partida.getMaxAltura())
                .maxDist(partida.getMaxDist())
                .build();
    }

}
