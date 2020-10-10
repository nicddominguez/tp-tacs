package tp.tacs.api.daos;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import tp.tacs.api.dominio.municipio.Municipio;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository("municipioDaoMemoria")
@ConditionalOnProperty(prefix="application", name="persistance-implementation", havingValue = "memoria")
public class MunicipioDaoMemoria implements MunicipioDao {

    private List<Municipio> municipios;
    private Long municipioId;

    @PostConstruct
    public void postConstruct() {
        municipios = new ArrayList<>();
        municipioId = 0L;
    }

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
        if(!municipios.contains(element)) {
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

    private synchronized void asignarId(Municipio municipio) {
        municipio.setId(municipioId.toString());
        municipioId++;
    }

}
