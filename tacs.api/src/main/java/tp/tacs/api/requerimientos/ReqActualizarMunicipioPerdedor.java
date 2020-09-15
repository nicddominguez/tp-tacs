package tp.tacs.api.requerimientos;

import org.springframework.stereotype.Component;
import tp.tacs.api.requerimientos.models.PartidaConMunicipios;

@Component
public class ReqActualizarMunicipioPerdedor extends AbstractRequerimiento<PartidaConMunicipios, Void> {

    @Override protected Void execute(PartidaConMunicipios request) {
        request.getMunicipioDefensor().setCantGauchos(0);
        request.getMunicipioDefensor().setDuenio(request.getMunicipioAtacante().getDuenio());
        return null;
    }
}
