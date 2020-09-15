package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.requerimientos.models.PartidaConMunicipio;

@Component
public class ReqCalcularMulAlturaMunicipio extends AbstractRequerimiento<PartidaConMunicipio,Float> {
    @Autowired
    private ExternalApis externalApis;

    @Override protected Float execute(PartidaConMunicipio request) {
        Float altura = externalApis.getAltura(request.getMunicipio().getExternalApiId());
        Partida partida = request.getPartida();
        return 1 + (altura - partida.getMinAltura()) / (2 * (partida.getMaxAltura() - partida.getMinAltura()));
    }
}
