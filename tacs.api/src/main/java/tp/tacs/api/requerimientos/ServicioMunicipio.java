package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Municipio producir(Partida partida, Municipio municipio) {
        this.actualizarNivelProduccion(municipio, partida);
        municipio.agregarGauchos(municipio.getNivelDeProduccion());
        return municipio;
    }

    public void actualizarNivelProduccion(Municipio municipio, Partida partida) { //TODO el multiplicador podría calcualrse una sola vez al iniciar la partida
        var minAltura = partida.getMinAltura();
        var maxAltura = partida.getMaxAltura();
        float multiplicador = 1 - (municipio.getAltura() - minAltura)
                / (2 * (maxAltura - minAltura));
        int cantGauchos = municipio.getEspecializacion().nivelDeProduccion(multiplicador);
        municipio.setNivelDeProduccion(cantGauchos);
    }

    public void actualizarMunicipio(Municipio municipio, Especializacion especializacion){
        municipio.setEspecializacion(especializacion);
    }

    public List<ProvinciaModel> provincias() {
        return this.externalApis.getProvincias();
    }
}
