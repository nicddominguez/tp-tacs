package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.requerimientos.Models.ReqProducirModel;

@Component
public class ReqProducir extends AbstractRequerimiento<ReqProducirModel, Municipio> {

    @Autowired
    private ExternalApis externalApis;

    @Override
    protected Municipio execute(ReqProducirModel request) {
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