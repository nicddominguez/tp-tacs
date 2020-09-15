package tp.tacs.api.requerimientos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.mappers.MunicipioEnJuegoMapper;
import tp.tacs.api.model.MoverGauchosResponse;
import tp.tacs.api.model.MunicipioEnJuegoModel;
import tp.tacs.api.requerimientos.AbstractRequerimiento;
import tp.tacs.api.requerimientos.models.ReqMoverGauchosModel;

@Component
public class ReqMoverGauchos extends AbstractRequerimiento<ReqMoverGauchosModel, MoverGauchosResponse> {
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private MunicipioEnJuegoMapper municipioEnJuegoMapper;

    @Override protected MoverGauchosResponse execute(ReqMoverGauchosModel request) {
        Municipio municipioOrigen = municipioDao.get(request.getIdMunicipioOrigen());
        Municipio municipioDestino = municipioDao.get(request.getIdMunicipioDestino());
        moverGauchos(municipioOrigen,municipioDestino, Math.toIntExact(request.getCantidad()));
        municipioDao.save(municipioDestino);
        municipioDao.save(municipioOrigen);
        MunicipioEnJuegoModel municipioOrigenModel = municipioEnJuegoMapper.wrap(municipioOrigen);
        MunicipioEnJuegoModel municipioDestinoModel = municipioEnJuegoMapper.wrap(municipioDestino);
        return new MoverGauchosResponse()
                .municipioOrigen(municipioOrigenModel)
                .municipioDestino(municipioDestinoModel);
    }

    private void moverGauchos(Municipio municipioOrigen, Municipio municipioDestino, int cantidad) {
        //todo chequear cantidades
        municipioOrigen.sacarGauchos(cantidad);
        municipioDestino.agregarGauchos(cantidad);
    }
}
