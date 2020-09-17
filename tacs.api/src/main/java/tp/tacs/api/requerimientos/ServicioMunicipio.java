package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.requerimientos.models.ReqProducirModel;

@Controller
public class ServicioMunicipio {
    @Autowired
    private ExternalApis externalApis;

    public Municipio producir(ReqProducirModel request) {
        var minAltura = request.getPartida().getMinAltura();
        var maxAltura = request.getPartida().getMaxAltura();
        float multiplicador = 1 - (externalApis.getAltura(request.getMunicipio().getExternalApiId()) - minAltura)
                / (2 * (maxAltura - minAltura));
        int cantGauchos = request.getMunicipio().getEspecializacion().nivelDeProduccion(multiplicador);
        request.getMunicipio().setUltimaProduccion(cantGauchos);
        request.getMunicipio().agregarGauchos(cantGauchos);
        return request.getMunicipio();
    }

}
