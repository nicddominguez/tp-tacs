package tp.tacs.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.model.ListarProvinciasResponse;
import tp.tacs.api.model.ProvinciaModel;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProvinciasApiController implements ProvinciasApi {

    @Autowired
    private ExternalApis externalApis;

    @Autowired
    private Utils utils;

    @Override
    public ResponseEntity<ListarProvinciasResponse> listarProvincias(@Valid Long tamanioPagina, @Valid Long pagina) {
        List<ProvinciaModel> listaPaginada = this.utils.obtenerListaPaginada(pagina, tamanioPagina, this.externalApis.getProvincias());
        return ResponseEntity.ok(new ListarProvinciasResponse().provincias(listaPaginada));
    }
}
