package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.requerimientos.models.PartidaConMunicipio;
import tp.tacs.api.requerimientos.models.PartidaConMunicipios;

@Component
public class ReqGauchosAtacantesFinales extends AbstractRequerimiento<PartidaConMunicipios, Integer> {
    @Autowired
    private ReqMultDistancia reqMultDistancia;
    @Autowired
    private ReqCalcularMulAlturaMunicipio reqCalcularMulAlturaMunicipio;

    @Override protected Integer execute(PartidaConMunicipios request) {
        var multDist = reqMultDistancia.run(request);
        var multAltura = reqCalcularMulAlturaMunicipio
                .run(PartidaConMunicipio.builder().municipio(request.getMunicipioAtacante()).partida(request.getPartida()).build());
        var multDefensa = request.getMunicipioDefensor().getEspecializacion().multDefensa(request.getPartida());
        return (int) Math
                .floor(request.getMunicipioAtacante().getCantGauchos() * multDist - request.getMunicipioDefensor().getCantGauchos() * multAltura * multDefensa);
    }
}