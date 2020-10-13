package tp.tacs.api.servicios;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.municipio.Especializacion;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;

@Service
@AllArgsConstructor
@Setter
public class ServicioMunicipio {

    @Autowired
    private MunicipioDao municipioDao;

    public void producir(Municipio municipio) {
        this.agregarGauchos(municipio, municipio.getNivelDeProduccion());
        municipioDao.save(municipio);
    }

    public void actualizarMunicipio(Partida partida, String idMunicipio, Especializacion especializacion) {
        var municipio = municipioDao.get(idMunicipio);
        municipio.setEspecializacion(especializacion);
        this.actualizarNivelProduccion(municipio, partida);
        municipioDao.save(municipio);
    }

    public void agregarGauchos(Municipio municipio, Integer cantidad) {
        municipio.setCantGauchos(municipio.getCantGauchos() + cantidad);
    }

    public void sacarGauchos(Municipio municipio, Integer cantidad) {
        municipio.setCantGauchos(Math.max(0, municipio.getCantGauchos() - cantidad));
    }

    public boolean esDe(Municipio municipio, String userId) {
        return municipio.getDuenio().getId().equals(userId);
    }

    public String coordenadasParaTopo(Municipio municipio) {
        return String.format("%s,%s", municipio.getLatitud().toString(), municipio.getLongitud().toString());
    }

    public void actualizarNivelProduccion(Municipio municipio, Partida partida) {
        var minAltura = partida.getMinAltura();
        var maxAltura = partida.getMaxAltura();
        float multiplicador = 1f - (municipio.getAltura() - minAltura)
                / (2f * (maxAltura - minAltura));
        int cantGauchos = municipio.getEspecializacion().nivelDeProduccion(multiplicador);
        municipio.setNivelDeProduccion(cantGauchos);
    }

}
