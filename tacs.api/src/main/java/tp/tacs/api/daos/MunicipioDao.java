package tp.tacs.api.daos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Builder
@Getter
@Setter
public class MunicipioDao implements Dao<Municipio> {

    @Builder.Default
    public List<Municipio> municipios = new ArrayList<>();
    @Builder.Default
    private Long municipioId = 0L;;

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
        this.asignarId(element);
        municipios.add(element);
    }

    @Override
    public void delete(Municipio element) {
        municipios = municipios.stream().filter(municipio -> !municipio.getId().equals(element.getId()))
                .collect(Collectors.toList());
    }

    public List<Municipio> getByIds(List<Long> ids) {
        return municipios.stream().filter(municipio -> ids.contains(municipio.getId())).collect(Collectors.toList());
    }

    private synchronized void asignarId(Municipio municipio) {
        municipio.setId(municipioId);
        municipioId++;
    }

    public void saveWithId(Municipio municipio, Long idMunicipio) {
        this.municipios.add(municipio);
    }

}
