package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import tp.tacs.api.model.ListarProvinciasResponse;
import tp.tacs.api.model.ProvinciaModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProvinciasApiController implements ProvinciasApi{

    @Override
    public ResponseEntity<ListarProvinciasResponse> listarProvincias(@Valid Long tamanioPagina, @Valid Long pagina) {
        List<ProvinciaModel> provinciaModels = new ArrayList<>();
        provinciaModels.add(new ProvinciaModel().id(1L).nombre("Buenos Aires"));
        provinciaModels.add(new ProvinciaModel().id(2L).nombre("Catamarca"));
        provinciaModels.add(new ProvinciaModel().id(3L).nombre("Chaco"));
        provinciaModels.add(new ProvinciaModel().id(4L).nombre("Chubut"));
        provinciaModels.add(new ProvinciaModel().id(5L).nombre("Córdoba"));
        provinciaModels.add(new ProvinciaModel().id(6L).nombre("Corrientes"));
        provinciaModels.add(new ProvinciaModel().id(7L).nombre("Entre Ríos"));
        provinciaModels.add(new ProvinciaModel().id(8L).nombre("Formosa"));
        provinciaModels.add(new ProvinciaModel().id(9L).nombre("Jujuy"));
        provinciaModels.add(new ProvinciaModel().id(10L).nombre("La Pampa"));
        provinciaModels.add(new ProvinciaModel().id(11L).nombre("La Rioja"));
        provinciaModels.add(new ProvinciaModel().id(12L).nombre("Mendoza"));
        provinciaModels.add(new ProvinciaModel().id(13L).nombre("Misiones"));
        provinciaModels.add(new ProvinciaModel().id(14L).nombre("Neuquén"));
        provinciaModels.add(new ProvinciaModel().id(15L).nombre("Río Negro"));
        provinciaModels.add(new ProvinciaModel().id(16L).nombre("Salta"));
        provinciaModels.add(new ProvinciaModel().id(17L).nombre("San Juan"));
        provinciaModels.add(new ProvinciaModel().id(18L).nombre("San Luis"));
        provinciaModels.add(new ProvinciaModel().id(19L).nombre("Santa Cruz"));
        provinciaModels.add(new ProvinciaModel().id(20L).nombre("Santa Fe"));
        provinciaModels.add(new ProvinciaModel().id(21L).nombre("Santiago del Estero"));
        provinciaModels.add(new ProvinciaModel().id(22L).nombre("Tierra del Fuego, Antártida e Isla del Atlántico Sur"));
        provinciaModels.add(new ProvinciaModel().id(23L).nombre("Tucumán"));

        Long start = pagina * tamanioPagina;
        Long end = start + tamanioPagina;

        if(start > provinciaModels.size() || end > provinciaModels.size() || start < 0 || end <= 0)
            return ResponseEntity.notFound().build();

        List<ProvinciaModel> provinciasPaginadas = provinciaModels.subList(start.intValue(), end.intValue());
        return ResponseEntity.ok(new ListarProvinciasResponse().provincias(provinciasPaginadas));
    }
}
