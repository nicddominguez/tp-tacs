package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.model.MunicipioEnJuegoModel;

@Component
public class MunicipioEnJuegoMapper extends AbstractMapper<Municipio, MunicipioEnJuegoModel> {
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private ModoDeMunicipioMapper modoDeMunicipioMapper;
    @Autowired
    private CoordenadasMapper coordenadasMapper;
    @Autowired
    private ExternalApis externalApis;

    @Override
    protected MunicipioEnJuegoModel wrapModel(Municipio model) {
        return new MunicipioEnJuegoModel()
                .altura(model.getAltura().longValue())
                .duenio(usuarioMapper.wrap(model.getDuenio()))
                .estaBloqueado(model.isBloqueado())
                .gauchos(model.getCantGauchos().longValue())
                .modo(modoDeMunicipioMapper.toModel(model.getEspecializacion()))
                .id(model.getId())
                .nombre(model.getNombre())
                .produccionDeGauchos(model.getNivelDeProduccion().longValue())
                .puntosDeDefensa(model.getEspecializacion().multDefensa().floatValue())
                .ubicacion(coordenadasMapper.toModel(model.getLatitud(), model.getLongitud()))
                .urlImagen(model.getUrlImagen());
    }

    @Override
    protected Municipio unwrapModel(MunicipioEnJuegoModel model) {
        return null;
    }
}
