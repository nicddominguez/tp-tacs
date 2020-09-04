package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import tp.tacs.api.model.ProvinciaModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProvinciasApiController implements ProvinciasApi{

    @Override
    public ResponseEntity<List<ProvinciaModel>> listarProvincias(@NotNull @Valid Integer page, @Valid Integer pageSize) {
        List<ProvinciaModel> provinciaModels = new ArrayList<>();
        provinciaModels.add(new ProvinciaModel().id(1).nombre("Buenos Aires"));
        provinciaModels.add(new ProvinciaModel().id(2).nombre("Catamarca"));
        provinciaModels.add(new ProvinciaModel().id(3).nombre("Chaco"));
        provinciaModels.add(new ProvinciaModel().id(4).nombre("Chubut"));
        provinciaModels.add(new ProvinciaModel().id(5).nombre("Córdoba"));
        provinciaModels.add(new ProvinciaModel().id(6).nombre("Corrientes"));
        provinciaModels.add(new ProvinciaModel().id(7).nombre("Entre Ríos"));
        provinciaModels.add(new ProvinciaModel().id(8).nombre("Formosa"));
        provinciaModels.add(new ProvinciaModel().id(9).nombre("Jujuy"));
        provinciaModels.add(new ProvinciaModel().id(10).nombre("La Pampa"));
        provinciaModels.add(new ProvinciaModel().id(11).nombre("La Rioja"));
        provinciaModels.add(new ProvinciaModel().id(12).nombre("Mendoza"));
        provinciaModels.add(new ProvinciaModel().id(13).nombre("Misiones"));
        provinciaModels.add(new ProvinciaModel().id(14).nombre("Neuquén"));
        provinciaModels.add(new ProvinciaModel().id(15).nombre("Río Negro"));
        provinciaModels.add(new ProvinciaModel().id(16).nombre("Salta"));
        provinciaModels.add(new ProvinciaModel().id(17).nombre("San Juan"));
        provinciaModels.add(new ProvinciaModel().id(18).nombre("San Luis"));
        provinciaModels.add(new ProvinciaModel().id(19).nombre("Santa Cruz"));
        provinciaModels.add(new ProvinciaModel().id(20).nombre("Santa Fe"));
        provinciaModels.add(new ProvinciaModel().id(21).nombre("Santiago del Estero"));
        provinciaModels.add(new ProvinciaModel().id(22).nombre("Tierra del Fuego, Antártida e Isla del Atlántico Sur"));
        provinciaModels.add(new ProvinciaModel().id(23).nombre("Tucumán"));
        int start = page * pageSize;
        int end = start + pageSize;
        if(start > provinciaModels.size() || end > provinciaModels.size() || start < 0 || end <= 0)
            return ResponseEntity.notFound().build();
        List<ProvinciaModel> provinciasPaginadas = provinciaModels.subList(start, end);
        return ResponseEntity.ok(provinciasPaginadas);
    }
}
