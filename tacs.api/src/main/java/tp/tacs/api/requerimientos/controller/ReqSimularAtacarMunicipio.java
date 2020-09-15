package tp.tacs.api.requerimientos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.model.SimularAtacarMunicipioResponse;
import tp.tacs.api.requerimientos.AbstractRequerimiento;
import tp.tacs.api.requerimientos.ReqGauchosDefensoresFinales;
import tp.tacs.api.requerimientos.models.PartidaConMunicipios;
import tp.tacs.api.requerimientos.models.ReqSimularAtacarMunicipioRequest;

@Component
public class ReqSimularAtacarMunicipio extends AbstractRequerimiento<ReqSimularAtacarMunicipioRequest, SimularAtacarMunicipioResponse> {
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private PartidaDao partidaDao;
    @Autowired
    private ReqGauchosDefensoresFinales reqGauchosDefensoresFinales;

    @Override protected SimularAtacarMunicipioResponse execute(ReqSimularAtacarMunicipioRequest request) {
        var municipioAtacante = municipioDao.get(request.getIdMunicipioAtacante());
        var municipioAtacado = municipioDao.get(request.getIdMunicipioObjectivo());
        var partida = partidaDao.get(request.getIdPartida());
        PartidaConMunicipios preRequest = PartidaConMunicipios.builder().municipioAtacante(municipioAtacante).municipioDefensor(municipioAtacado)
                .partida(partida).build();
        Integer gauchosFinales = reqGauchosDefensoresFinales.run(preRequest);
        return new SimularAtacarMunicipioResponse().exitoso(gauchosFinales<=0);
    }
}
