package tp.tacs.api.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.Especializacion;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.model.ProvinciaModel;

import java.util.List;

@Service
public class ServicioMunicipio {
    @Autowired
    private ExternalApis externalApis;

    @Autowired
    private MunicipioDao municipioDao;

    public Municipio producir(Municipio municipio) {
        municipio.agregarGauchos(municipio.getNivelDeProduccion());
        return municipio;
    }

    public void actualizarMunicipio(Partida partida, Long idMunicipio, Especializacion especializacion) {
        var municipio = municipioDao.get(idMunicipio);
        municipio.setEspecializacion(especializacion);
        municipio.actualizarNivelProduccion(partida);
    }

    public List<ProvinciaModel> provincias() {
        return this.externalApis.getProvincias();
    }
}
