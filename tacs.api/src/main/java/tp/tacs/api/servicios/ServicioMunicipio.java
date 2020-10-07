package tp.tacs.api.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.Especializacion;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.model.ProvinciaModel;

import java.util.List;

@Service
public class ServicioMunicipio {
    @Autowired
    private ExternalApis externalApis;

    @Autowired
    private MunicipioDao municipioDao;

    public Municipio producir(Municipio municipio) {
        this.agregarGauchos(municipio, municipio.getNivelDeProduccion());
        return municipio;
    }

    public void actualizarMunicipio(Partida partida, Long idMunicipio, Especializacion especializacion) {
        var municipio = municipioDao.get(idMunicipio);
        municipio.setEspecializacion(especializacion);
        this.actualizarNivelProduccion(municipio, partida);
    }

    public List<ProvinciaModel> provincias() {
        return this.externalApis.getProvincias();
    }

    public void agregarGauchos(Municipio municipio, Integer cantidad) {
        municipio.setCantGauchos(municipio.getCantGauchos() + cantidad);
    }

    public void sacarGauchos(Municipio municipio, Integer cantidad) {
        municipio.setCantGauchos(Math.max(0, municipio.getCantGauchos() - cantidad));
    }

    public boolean esDe(Municipio municipio, Long userId) {
        return municipio.getDuenio().getId().equals(userId);
    }

    public String coordenadasParaTopo(Municipio municipio) {
        return String.format("%s,%s", municipio.getLatitud().toString(), municipio.getLongitud().toString());
    }

    public void actualizarNivelProduccion(Municipio municipio, Partida partida) {
        var minAltura = partida.getMinAltura();
        var maxAltura = partida.getMaxAltura();
        float multiplicador = 1 - (municipio.getAltura() - minAltura)
                / (2 * (maxAltura - minAltura));
        int cantGauchos = municipio.getEspecializacion().nivelDeProduccion(multiplicador);
        municipio.setNivelDeProduccion(cantGauchos);
    }

}
