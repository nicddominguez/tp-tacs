package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.model.MunicipioEnJuegoModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class MunicipioEnJuegoMapper {

    UsuarioMapper usuarioMapper = new UsuarioMapper();

    ModoDeMunicipioMapper modoDeMunicipioMapper = new ModoDeMunicipioMapper();

    CoordenadasMapper coordenadasMapper = new CoordenadasMapper();

    public MunicipioEnJuegoModel toModel(Municipio entity) {
        return new MunicipioEnJuegoModel()
                .altura(entity.getAltura().longValue())
                .duenio(usuarioMapper.toModel(entity.getDuenio()))
                .estaBloqueado(entity.estaBloqueado())
                .gauchos(entity.getCantGauchos().longValue())
                .modo(modoDeMunicipioMapper.toModel(entity.getEspecializacion()))
                .id(123L) //TODO
                .nombre("TODO") //TODO
                .produccionDeGauchos(entity.getEspecializacion().nivelDeProduccion(entity).longValue())
                .puntosDeDefensa(entity.getEspecializacion().multDefensa(entity.getPartida()).longValue())
                .ubicacion(coordenadasMapper.toModel(entity.getLatitud(), entity.getLongitud()))
                .urlImagen(entity.getPathImagen());
    }

    public List<MunicipioEnJuegoModel> municipiosToModel(List<Municipio> municipios) {
        List<MunicipioEnJuegoModel> municipioModels = new ArrayList<>();
        municipios.forEach(municipio -> municipioModels.add(this.toModel(municipio)));
        return municipioModels;
    }

}
