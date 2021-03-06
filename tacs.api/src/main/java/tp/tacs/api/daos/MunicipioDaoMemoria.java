package tp.tacs.api.daos;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Partida;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository("municipioDaoMemoria")
@ConditionalOnProperty(prefix = "application", name = "persistance-implementation", havingValue = "memoria")
public class MunicipioDaoMemoria implements MunicipioDao {

    public List<Municipio> municipios = new ArrayList<>();
    private Long municipioId = 0L;

    @Override
    public Municipio get(String id) {
        return municipios.stream().filter(municipio -> municipio.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    @Override
    public List<Municipio> getAll() {
        return municipios;
    }

    @Override
    public void save(Municipio element) {
        if (!municipios.contains(element)) {
            this.asignarId(element);
            municipios.add(element);
        }
    }

    @Override
    public void delete(Municipio element) {
        municipios = municipios.stream().filter(municipio -> !municipio.getId().equals(element.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Municipio> getByIds(List<String> ids) {
        return municipios.stream().filter(municipio -> ids.contains(municipio.getId())).collect(Collectors.toList());
    }

    @Override
    public Set<Municipio> municipiosConDuenio(Partida partida) {
        return this.getByIds(partida.getMunicipios()).stream()
                .filter(municipio -> municipio.getDuenio() != null).collect(Collectors.toSet());

    }

    private synchronized void asignarId(Municipio municipio) {
        municipio.setId(municipioId.toString());
        municipioId++;
    }

    public void saveWithId(Municipio municipio, String idMunicipio) {
        this.municipios.add(municipio);
    }

}
