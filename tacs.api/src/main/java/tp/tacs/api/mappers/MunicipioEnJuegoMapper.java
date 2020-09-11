package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.model.MunicipioEnJuegoModel;

@Component
public class MunicipioEnJuegoMapper extends AbstractMapper<Municipio,MunicipioEnJuegoModel> {
    @Autowired
    UsuarioMapper usuarioMapper;
    @Autowired
    ModoDeMunicipioMapper modoDeMunicipioMapper;
    @Autowired
    CoordenadasMapper coordenadasMapper;

    @Override protected MunicipioEnJuegoModel wrapModel(Municipio model) {
        return new MunicipioEnJuegoModel()
                .altura(model.getAltura().longValue())
                .duenio(usuarioMapper.wrap(model.getDuenio()))
                .estaBloqueado(model.estaBloqueado())
                .gauchos(model.getCantGauchos().longValue())
                .modo(modoDeMunicipioMapper.toModel(model.getEspecializacion()))
                .id(123L) //TODO
                .nombre("TODO") //TODO
                .produccionDeGauchos(model.getEspecializacion().nivelDeProduccion(model).longValue())
                .puntosDeDefensa(model.getEspecializacion().multDefensa(model.getPartida()).longValue())
                .ubicacion(coordenadasMapper.toModel(model.getLatitud(), model.getLongitud()))
                .urlImagen(model.getPathImagen());
    }

    @Override protected Municipio unwrapModel(MunicipioEnJuegoModel model) {
        return null;
    }
}
