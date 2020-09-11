package tp.tacs.api.dominio.municipio;

import tp.tacs.api.model.ProvinciaModel;

import java.util.List;

public interface RepoMunicipios {

    List<Municipio> getMunicipios(String idProvincia, Integer cantidad);

    String getNombre(String idMunicipio);

    Double getLatitud(String idMunicipio);

    Double getLongitud(String idMunicipio);

    Float getAltura(String idMunicipio);

    String getPathImagen(String idMunicipio);

    String getCoordenadas(String idMunicipio);

    List<ProvinciaModel> getProvincias();
}

