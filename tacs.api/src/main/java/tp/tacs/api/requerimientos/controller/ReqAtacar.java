package tp.tacs.api.requerimientos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.requerimientos.models.AtaqueMunicipiosResponse;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.requerimientos.*;
import tp.tacs.api.requerimientos.models.DosMunicipiosModel;
import tp.tacs.api.requerimientos.models.PartidaConMunicipios;
import tp.tacs.api.requerimientos.models.ReqAtacarModel;
import tp.tacs.api.requerimientos.models.ReqValidarAccionModel;

@Component
public class ReqAtacar extends AbstractRequerimiento<ReqAtacarModel, AtaqueMunicipiosResponse> {
    @Autowired
    private PartidaDao partidaDao;
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private ReqValidarAccion reqValidarAccion;
    @Autowired
    private ReqDistintoDuenio distintoDuenio;
    @Autowired
    private ReqGauchosAtacantesFinales reqGauchosAtacantesFinales;
    @Autowired
    private ReqGauchosDefensoresFinales reqGauchosDefensoresFinales;
    @Autowired
    private ReqActualizarMunicipioPerdedor actualizarMunicipioPerdedor;
    @Autowired
    private ReqPasarTurno reqPasarTurno;

    @Override
    protected AtaqueMunicipiosResponse execute(ReqAtacarModel request) {
        Municipio municipioAtacante = municipioDao.get(request.getIdMunicipioAtacante());
        Municipio municipioDefensor = municipioDao.get(request.getIdMunicipioDefensor());
        Partida partida = partidaDao.get(request.getIdPartida());
        ReqValidarAccionModel validarRequest = ReqValidarAccionModel.builder().accion("atacar").duenio(municipioAtacante.getDuenio()).partida(partida)
                .build();
        reqValidarAccion.run(validarRequest);
        DosMunicipiosModel distintoDuenioRequest = DosMunicipiosModel.builder().municipioAtacante(municipioAtacante)
                .municipioDefensor(municipioDefensor).build();
        distintoDuenio.run(distintoDuenioRequest);

        PartidaConMunicipios partidaConMunicipios = PartidaConMunicipios.builder().municipioAtacante(municipioAtacante)
                .municipioDefensor(municipioDefensor).partida(partida).build();

        Integer gauchosAtacantesFinal = reqGauchosAtacantesFinales.run(partidaConMunicipios);
        municipioAtacante.setCantGauchos(gauchosAtacantesFinal);
        Integer gauchosDefensoresFinal = reqGauchosDefensoresFinales.run(partidaConMunicipios);
        municipioDefensor.setCantGauchos(gauchosDefensoresFinal);

        if (gauchosDefensoresFinal <= 0) {
            actualizarMunicipioPerdedor.run(partidaConMunicipios);
        }
        reqPasarTurno.run(partidaConMunicipios.getPartida());
        return AtaqueMunicipiosResponse.builder().municipioAtacante(partidaConMunicipios.getMunicipioAtacante())
                .municipioDefensor(partidaConMunicipios.getMunicipioDefensor()).build();
    }

}