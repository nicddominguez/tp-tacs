package tp.tacs.api.dominio.municipio;

import java.util.List;

public class RepoMunicipios {

    private static RepoMunicipios instanciaRepoMunicipios = null;

    public static RepoMunicipios instance() {
        if (instanciaRepoMunicipios == null) {
            instanciaRepoMunicipios = new RepoMunicipios();
        }
        return instanciaRepoMunicipios;
    }

    public List<Municipio> getMunicipios(String idProvincia, Integer cantidad) {
        return null;
    }

    String getNombre(String idProvinca) {
        return null;
    }

    Double getLatitud(String idProvinca) {
        return null;
    }

    Double getLongitud(String idProvinca) {
        return null;
    }

    Float getAltura(String idProvinca) {
        return null;
    }

    public String getPathImagen(String idMunicipioReal) {
        return null;
    }
}
