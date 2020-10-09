package tp.tacs.api.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.MunicipioDaoMongo;
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
    private MunicipioDaoMongo municipioDao;

    public Municipio producir(Municipio municipio) {
        municipio.agregarGauchos(municipio.getNivelDeProduccion());
        municipioDao.save(municipio);
        return municipio;
    }

    public void actualizarMunicipio(String idMunicipio, Especializacion especializacion, boolean bloqueado) {
        var municipio = municipioDao.get(idMunicipio);
        municipio.setEspecializacion(especializacion);
        municipio.setBloqueado(bloqueado);
        municipioDao.save(municipio);
    }

    public List<ProvinciaModel> provincias() {
        return this.externalApis.getProvincias();
    }
}
