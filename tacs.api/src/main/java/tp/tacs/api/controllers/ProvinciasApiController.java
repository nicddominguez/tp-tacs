package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.model.ListarProvinciasResponse;
import tp.tacs.api.model.ProvinciaModel;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProvinciasApiController implements ProvinciasApi {

    ExternalApis repoMunicipios = ExternalApis.instance();

    public void setRepoMunicipios(ExternalApis repoMunicipios) {
        this.repoMunicipios = repoMunicipios;
    }

    @Override
    public ResponseEntity<ListarProvinciasResponse> listarProvincias(@Valid Long tamanioPagina, @Valid Long pagina) {

        Utils utils = new Utils();

        //List<ProvinciaModel> provinciaModels = this.repoMunicipios.getProvincias();
        //List<ProvinciaModel> listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, provinciaModels);
        //return ResponseEntity.ok(new ListarProvinciasResponse().provincias(listaPaginada));
        return ResponseEntity.ok().build();
    }
}
