package tp.tacs.api.dominio.municipio;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RepoMunicipios {

    private static RepoMunicipios instanciaRepoMunicipios = null;

    public static RepoMunicipios instance() {
        if (instanciaRepoMunicipios == null) {
            instanciaRepoMunicipios = new RepoMunicipios();
        }
        return instanciaRepoMunicipios;
    }

    public List<Municipio> getMunicipios(String idProvincia, Integer cantidad) {
        Municipio municipio1 = new Municipio();
        Municipio municipio2 = new Municipio();
        return Arrays.asList(municipio1, municipio2);
    }

    public Municipio getMunicipio(Long idPartida, Long idMunicipio) {
        return new Municipio();
    }
    String getNombre(String idProvinca) {
        return "ola";
    }

    Double getLatitud(String idProvinca) {
        Random rand = new Random();
        return (double)rand.nextInt(100);
    }

    Double getLongitud(String idProvinca) {
        Random rand = new Random();
        return (double)rand.nextInt(100);
    }

    Float getAltura(String idProvinca) {
        Random rand = new Random();
        return (float) rand.nextInt(200);
    }

    public String getPathImagen(String idMunicipioReal) {
        return "google.com";
    }
}
