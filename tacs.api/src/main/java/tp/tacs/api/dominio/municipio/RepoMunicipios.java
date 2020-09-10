package tp.tacs.api.dominio.municipio;

import tp.tacs.api.dominio.partida.RepoPartidas;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.model.ProvinciaModel;

import java.util.ArrayList;
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
        municipio1.setIdMunicipioReal("1234");
        municipio1.setDuenio(new Usuario(123L, "@gmaol", "nd"));
        municipio2.setIdMunicipioReal("5432");
        municipio2.setDuenio(new Usuario(1L, "@gml", "asdf"));
        return Arrays.asList(municipio1, municipio2);
    }

    public Municipio getMunicipio(Long idPartida, Long idMunicipio) {
        var partida = RepoPartidas.instance().getPartida(idPartida);
        var municipios = partida.getMunicipios();
        return municipios
                .stream()
                .filter(municipio -> municipio.getIdMunicipioReal().equals(idMunicipio.toString()))
                .findFirst()
                .orElse(null); //TODO
    }

    String getNombre(String idProvinca) {
        return "ola";
    }

    Double getLatitud(String idProvinca) {
        Random rand = new Random();
        return (double) rand.nextInt(100);
    }

    Double getLongitud(String idProvinca) {
        Random rand = new Random();
        return (double) rand.nextInt(100);
    }

    Float getAltura(String idProvinca) {
        Random rand = new Random();
        return (float) rand.nextInt(200);
    }

    public String getPathImagen(String idMunicipioReal) {
        return "google.com";
    }

    public List<ProvinciaModel> getProvincias() {
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
        return provinciaModels;
    }
}
