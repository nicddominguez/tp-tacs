package tp.tacs.api.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.MunicipioDaoMemoria;
import tp.tacs.api.dominio.municipio.Especializacion;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.model.ProvinciaModel;

import java.util.List;

@Service
public class ServicioMunicipio {
    @Autowired
    private ExternalApis externalApis;

    @Autowired
    private MunicipioDaoMemoria municipioDao;

    public Municipio producir(Municipio municipio) {
        municipio.agregarGauchos(municipio.getNivelDeProduccion());
        return municipio;
    }

    public void actualizarMunicipio(Long idMunicipio, Especializacion especializacion, boolean bloqueado) {
        var municipio = municipioDao.get(idMunicipio);
        municipio.setEspecializacion(especializacion);
        municipio.setBloqueado(bloqueado);
    }

    public List<ProvinciaModel> provincias() {
        return this.externalApis.getProvincias();
    }
}
