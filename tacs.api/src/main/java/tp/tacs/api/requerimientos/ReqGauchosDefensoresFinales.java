package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.requerimientos.models.PartidaConMunicipio;
import tp.tacs.api.requerimientos.models.PartidaConMunicipios;

@Component
public class ReqGauchosDefensoresFinales extends AbstractRequerimiento<PartidaConMunicipios, Integer> {
    @Autowired
    private ReqMultAltura reqMultAltura;
    @Autowired
    private ReqMultDistancia reqMultDistancia;

    @Override protected Integer execute(PartidaConMunicipios request) {
        Float multAltura = reqMultAltura.run(PartidaConMunicipio.builder().partida(request.getPartida()).municipio(request.getMunicipioDefensor()).build());
        Float multDefensa = request.getMunicipioDefensor().getEspecializacion().multDefensa(request.getPartida());
        Float multDist = reqMultDistancia.run(request);
        Integer cantGauchosDefensor = request.getMunicipioDefensor().getCantGauchos();
        Integer cantGauchosAtacante = request.getMunicipioAtacante().getCantGauchos();
        return (int) Math.round(Math.ceil((cantGauchosDefensor * multAltura * multDefensa) - (cantGauchosAtacante * multDist))
                / (multAltura * multDefensa));
    }
}