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
                .altura(Long.valueOf(externalApis.getAltura(model.getExternalApiId()).toString()))
                .duenio(usuarioMapper.wrap(model.getDuenio()))
                .estaBloqueado(model.estaBloqueado())
                .gauchos(model.getCantGauchos().longValue())
                .modo(modoDeMunicipioMapper.toModel(model.getEspecializacion()))
                .id(model.getId())
                .nombre(model.getNombre())
                .produccionDeGauchos(model.getUltimaProduccion().longValue())
                //.puntosDeDefensa(model.getEspecializacion().multDefensa(model.getPartida()).longValue())
                .ubicacion(coordenadasMapper.toModel(externalApis.getLatitud(model.getExternalApiId()), externalApis.getLongitud(model.getExternalApiId())))
                .urlImagen(externalApis.getPathImagen(model.getExternalApiId()));
    }

    @Override
    protected Municipio unwrapModel(MunicipioEnJuegoModel model) {
        return null;
    }
}
