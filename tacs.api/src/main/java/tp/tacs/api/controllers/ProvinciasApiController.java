package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import tp.tacs.api.model.Provincia;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProvinciasApiController implements ProvinciasApi{

    @Override
    public ResponseEntity<List<Provincia>> listarProvincias(@NotNull @Valid Integer page, @Valid Integer pageSize) {
        List<Provincia> provincias = new ArrayList<>();
        provincias.add(new Provincia().id(1).nombre("Buenos Aires"));
        provincias.add(new Provincia().id(2).nombre("Catamarca"));
        provincias.add(new Provincia().id(3).nombre("Chaco"));
        provincias.add(new Provincia().id(4).nombre("Chubut"));
        provincias.add(new Provincia().id(5).nombre("Córdoba"));
        provincias.add(new Provincia().id(6).nombre("Corrientes"));
        provincias.add(new Provincia().id(7).nombre("Entre Ríos"));
        provincias.add(new Provincia().id(8).nombre("Formosa"));
        provincias.add(new Provincia().id(9).nombre("Jujuy"));
        provincias.add(new Provincia().id(10).nombre("La Pampa"));
        provincias.add(new Provincia().id(11).nombre("La Rioja"));
        provincias.add(new Provincia().id(12).nombre("Mendoza"));
        provincias.add(new Provincia().id(13).nombre("Misiones"));
        provincias.add(new Provincia().id(14).nombre("Neuquén"));
        provincias.add(new Provincia().id(15).nombre("Río Negro"));
        provincias.add(new Provincia().id(16).nombre("Salta"));
        provincias.add(new Provincia().id(17).nombre("San Juan"));
        provincias.add(new Provincia().id(18).nombre("San Luis"));
        provincias.add(new Provincia().id(19).nombre("Santa Cruz"));
        provincias.add(new Provincia().id(20).nombre("Santa Fe"));
        provincias.add(new Provincia().id(21).nombre("Santiago del Estero"));
        provincias.add(new Provincia().id(22).nombre("Tierra del Fuego, Antártida e Isla del Atlántico Sur"));
        provincias.add(new Provincia().id(23).nombre("Tucumán"));
        int start = page * pageSize;
        int end = start + pageSize;
        if(start > provincias.size() || end > provincias.size() || start < 0 || end <= 0)
            return ResponseEntity.notFound().build();
        List<Provincia> provinciasPaginadas = provincias.subList(start, end);
        return ResponseEntity.ok(provinciasPaginadas);
    }
}
