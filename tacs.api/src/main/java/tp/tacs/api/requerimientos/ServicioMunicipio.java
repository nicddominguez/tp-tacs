package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.dominio.municipio.Especializacion;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.model.ProvinciaModel;

import java.util.List;

@Service
public class ServicioMunicipio {
    @Autowired
    private ExternalApis externalApis;

    public Municipio producir(Municipio municipio) {
        municipio.agregarGauchos(municipio.getNivelDeProduccion());
        return municipio;
    }

    public void actualizarMunicipio(Municipio municipio, Especializacion especializacion){
        municipio.setEspecializacion(especializacion);
    }

    public List<ProvinciaModel> provincias() {
        return this.externalApis.getProvincias();
    }
}
