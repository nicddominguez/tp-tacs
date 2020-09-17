package tp.tacs.api.requerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tp.tacs.api.dominio.municipio.Especializacion;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.externalApis.ExternalApis;

@Controller
public class ServicioMunicipio {
    @Autowired
    private ExternalApis externalApis;

    public Municipio producir(Partida partida, Municipio municipio) {
        var minAltura = partida.getMinAltura();
        var maxAltura = partida.getMaxAltura();
        float multiplicador = 1 - (externalApis.getAltura(municipio.getExternalApiId()) - minAltura)
                / (2 * (maxAltura - minAltura));
        int cantGauchos = municipio.getEspecializacion().nivelDeProduccion(multiplicador);
        municipio.setUltimaProduccion(cantGauchos);
        municipio.agregarGauchos(cantGauchos);
        return municipio;
    }

    public void actualizarMunicipio(Municipio municipio, Especializacion especializacion){
        municipio.setEspecializacion(especializacion);
    }

}
