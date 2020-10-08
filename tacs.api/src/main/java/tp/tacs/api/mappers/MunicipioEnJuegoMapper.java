package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.ModoDeJuego;
import tp.tacs.api.model.MunicipioEnJuegoModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MunicipioEnJuegoMapper {
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private ModoDeMunicipioMapper modoDeMunicipioMapper;
    @Autowired
    private CoordenadasMapper coordenadasMapper;

    public MunicipioEnJuegoModel toModel(Municipio municipio, ModoDeJuego modoDeJuego) {
        return new MunicipioEnJuegoModel()
                .altura(municipio.getAltura().longValue())
                .duenio(usuarioMapper.wrap(municipio.getDuenio()))
                .estaBloqueado(municipio.isBloqueado())
                .gauchos(municipio.getCantGauchos().longValue())
                .modo(modoDeMunicipioMapper.toModel(municipio.getEspecializacion()))
                .id(municipio.getId())
                .nombre(municipio.getNombre())
                .produccionDeGauchos(municipio.getNivelDeProduccion().longValue())
                .puntosDeDefensa(municipio.getEspecializacion().multDefensa(modoDeJuego))
                .ubicacion(coordenadasMapper.toModel(municipio.getLatitud(), municipio.getLongitud()))
                .urlImagen(municipio.getUrlImagen());
    }

    public List<MunicipioEnJuegoModel> toModelList(List<Municipio> municipios, ModoDeJuego modoDeJuego) {
        return Optional.ofNullable(municipios)
                .orElse(Collections.emptyList())
                .stream()
                .map(municipio -> this.toModel(municipio, modoDeJuego))
                .collect(Collectors.toList());
    }
}
