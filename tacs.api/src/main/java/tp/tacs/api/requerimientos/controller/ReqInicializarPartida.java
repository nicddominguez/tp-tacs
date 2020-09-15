package tp.tacs.api.requerimientos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.ModoRapido;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.CrearPartidaBody;
import tp.tacs.api.requerimientos.AbstractRequerimiento;
import tp.tacs.api.requerimientos.ReqCalcularAlturas;
import tp.tacs.api.requerimientos.ReqCalcularDistancias;
import tp.tacs.api.requerimientos.ReqRepartirMunicipios;
import tp.tacs.api.requerimientos.models.ReqRepartirMunicipiosModel;

import java.util.Date;

@Component
public class ReqInicializarPartida extends AbstractRequerimiento<CrearPartidaBody, Partida> {

    @Autowired
    private ReqRepartirMunicipios reqRepartirMunicipios;
    @Autowired
    private ReqCalcularAlturas reqCalcularAlturas;
    @Autowired
    private ReqCalcularDistancias reqCaluclarDistancias;
    @Autowired
    private PartidaDao partidaDao;

    @Override
    protected Partida execute(CrearPartidaBody request) {
        Partida partida = Partida.builder()
                .estado(Estado.EN_CURSO)
                .jugadoresIds(request.getIdJugadores())
                .idProvincia(request.getIdProvincia().toString())
                .modoDeJuego(new ModoRapido())
                .fechaCreacion(new Date())
                .build();
        ReqRepartirMunicipiosModel partidaConMunicipiosRequest = ReqRepartirMunicipiosModel.builder()
                .partida(partida)
                .cantidadMunicipios(request.getCantidadMunicipios())
                .build();

        Partida partidaConMunicipios = reqRepartirMunicipios.run(partidaConMunicipiosRequest);
        Partida partidaConAlturas = reqCalcularAlturas.run(partidaConMunicipios);
        Partida partidaConDistancias = reqCaluclarDistancias.run(partidaConAlturas);
        partidaDao.save(partidaConDistancias);
        return partidaConDistancias;
    }
}
