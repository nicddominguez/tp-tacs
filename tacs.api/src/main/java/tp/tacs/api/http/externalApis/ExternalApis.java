package tp.tacs.api.http.externalApis;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.externalApis.models.Provincias;
import tp.tacs.api.http.externalApis.models.TopoData;
import tp.tacs.api.mappers.GeorefMapper;
import tp.tacs.api.mappers.ProvinciaMapper;
import tp.tacs.api.model.ProvinciaModel;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExternalApis {

    @Autowired
    private GeorefMapper geoRefWrapper;
    @Autowired
    private ProvinciaMapper provinciaWrapper;
    @Autowired
    private GeoRefApi geoRefApi;
    @Autowired
    private OpenTopoDataApi openTopoDataApi;
    @Autowired
    PixabayApi pixabayApi;

    public List<Municipio> getMunicipios(String idProvincia, String nombreProvincia) {
        var municipiosApi = geoRefApi.getDatosMunicipios(idProvincia);
        var municipiosBase = geoRefWrapper.wrapList(municipiosApi.getDepartamentos());
        var municipiosConImagenes = getImagenes(municipiosBase, nombreProvincia);
        return getAlturas(idProvincia, municipiosConImagenes);
    }

    private List<Municipio> getAlturas(String idProvincia, List<Municipio> municipios) {
        List<TopoData> results = openTopoDataApi.getAlturas(idProvincia, municipios);
        for (int i = 0; i < results.size(); i++) {
            municipios.get(i).setAltura(results.get(i).getElevation().floatValue());
        }
        return municipios;
    }

    @SneakyThrows
    private List<Municipio> getImagenes(List<Municipio> municipios, String nombreProvincia) {
        var imagenes = pixabayApi.getImagenes("argentina+" + nombreProvincia);
        var cantidadImagenes = imagenes.size();
        if (cantidadImagenes == 0) {
            imagenes = pixabayApi.getImagenes("argentina");
        }
        for (int i = 0; i < municipios.size(); i++) {
            double numeroConComa = (double) i / cantidadImagenes;
            double parteDecimal = numeroConComa - Math.floor(numeroConComa);
            double indiceImagen = (parteDecimal) * cantidadImagenes;
            municipios.get(i).setUrlImagen(imagenes.get((int) indiceImagen).getLargeImageURL());
        }
        return municipios;
    }

    private ProvinciaModel agregarCantidadMunicipios(ProvinciaModel provincia) {
        var cantidadMunicipios = geoRefApi.agregarCantidadMunicipios(provincia);
        return provincia.cantidadMunicipios(cantidadMunicipios);
    }

    public List<ProvinciaModel> getProvincias() {
        Provincias provincias = geoRefApi.getProvincias();
        List<ProvinciaModel> provinciasSinCantidadMunicipios = provinciaWrapper.wrapList(provincias.getProvincias());

        return provinciasSinCantidadMunicipios.stream()
                .map(this::agregarCantidadMunicipios)
                .collect(Collectors.toList());
    }

    public ProvinciaModel getNombreProvinicas(Long idProvincia) {
        Provincias provincias = geoRefApi.getNombreProvinicas(idProvincia);
        return provinciaWrapper.wrap(provincias.getProvincias().get(0));
    }
}