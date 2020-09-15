package tp.tacs.api.daos;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MunicipioDao implements Dao<Municipio> {

    private List<Municipio> municipios;

    @PostConstruct
    public void postConstruct() {
        municipios = new ArrayList<>();
    }

    @Override
    public Municipio get(Long id) {
        return municipios.stream().filter(municipio -> municipio.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    @Override
    public List<Municipio> getAll() {
        return municipios;
    }

    @Override
    public void save(Municipio element) {
        municipios.add(element);
    }

    @Override
    public void delete(Municipio element) {
        municipios = municipios.stream().filter(municipio -> !municipio.getId().equals(element.getId()))
                .collect(Collectors.toList());
    }
}
