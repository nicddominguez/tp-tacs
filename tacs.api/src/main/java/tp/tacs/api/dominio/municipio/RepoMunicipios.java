package tp.tacs.api.dominio.municipio;

import tp.tacs.api.model.ProvinciaModel;

import java.util.List;

public interface RepoMunicipios {

    List<Municipio> getMunicipios(String idProvincia, Integer cantidad);

    String getPathImagen(String idMunicipio);

    List<ProvinciaModel> getProvincias();
}

