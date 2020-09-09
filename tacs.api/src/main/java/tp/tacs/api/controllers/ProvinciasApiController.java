package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.model.ListarProvinciasResponse;
import tp.tacs.api.model.ProvinciaModel;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProvinciasApiController implements ProvinciasApi {

    RepoMunicipios repoMunicipios = RepoMunicipios.instance();

    public void setRepoMunicipios(RepoMunicipios repoMunicipios) {
        this.repoMunicipios = repoMunicipios;
    }

    @Override
    public ResponseEntity<ListarProvinciasResponse> listarProvincias(@Valid Long tamanioPagina, @Valid Long pagina) {
        List<ProvinciaModel> provinciaModels = this.repoMunicipios.getProvincias();

        Utils utils = new Utils();

        List<ProvinciaModel> listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, provinciaModels);

        if (listaPaginada == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new ListarProvinciasResponse().provincias(listaPaginada));
    }
}
