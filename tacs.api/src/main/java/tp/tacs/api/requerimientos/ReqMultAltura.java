package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.requerimientos.models.PartidaConMunicipio;

@Component
public class ReqMultAltura extends AbstractRequerimiento<PartidaConMunicipio, Float> {
    @Autowired
    private ExternalApis externalApis;

    @Override protected Float execute(PartidaConMunicipio request) {
        Float altura = externalApis.getAltura(request.getMunicipio().getExternalApiId());
        Float minAltura = request.getPartida().getMinAltura();
        Float maxAltura = request.getPartida().getMaxAltura();
        return 1 + (altura - minAltura) / (2 * (maxAltura - minAltura));
    }
}