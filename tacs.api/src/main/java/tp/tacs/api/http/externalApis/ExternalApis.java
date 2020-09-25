package tp.tacs.api.http.externalApis;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.http.HttpClientConnector;
import tp.tacs.api.http.externalApis.models.MunicipiosApi;
import tp.tacs.api.http.externalApis.models.Provincias;
import tp.tacs.api.http.externalApis.models.TopoData;
import tp.tacs.api.http.externalApis.models.TopoResult;
import tp.tacs.api.mappers.GeorefMapper;
import tp.tacs.api.mappers.ProvinciaMapper;
import tp.tacs.api.model.ProvinciaModel;
import java.util.stream.Collectors;

import java.util.List;

@Component
public class ExternalApis implements RepoMunicipios {

    @Autowired
    private HttpClientConnector connector;
    @Autowired
    private GeorefMapper geoRefWrapper;
    @Autowired
    private ProvinciaMapper provinciaWrapper;

    private String geoRefMunicipioBaseUrlEstandar = "https://apis.datos.gob.ar/georef/api/departamentos?aplanar=true";
    private String geoRefProvinciabaseUrlEstandar = "https://apis.datos.gob.ar/georef/api/provincias?aplanar=true";
    private String geoRefProvinciaNombre = "https://apis.datos.gob.ar/georef/api/provincias?aplanar=true&id=";
    private String topoBaseUrl = "https://api.opentopodata.org/v1/srtm90m?locations=";

    @Override
    public List<Municipio> getMunicipios(String idProvincia, Integer cantidad) {
        String url = geoRefMunicipioBaseUrlEstandar + "&provincia=" + idProvincia + "&max=" + cantidad;
        var municipiosApi = connector.get(url, MunicipiosApi.class);
        var municipiosBase = geoRefWrapper.wrapList(municipiosApi.getDepartamentos());
        return getAlturas(municipiosBase);
    }

    @SneakyThrows private List<Municipio> getAlturas(List<Municipio> municipios) {
        String coordenadas = municipios.stream().map(municipio -> municipio.coordenadasParaTopo())
                .reduce("", (coord1, coord2) -> coord1 + "%7C" + coord2);
        String url = String.format("%s%s", topoBaseUrl, coordenadas);
        List<TopoData> results = new Gson().fromJson(connector.get(url), TopoResult.class).getResults();
        for (int i = 0; i < results.size(); i++) {
            municipios.get(i).setAltura(results.get(i).getElevation().floatValue());
        }
        return municipios;
    }

    @Override
    public String getPathImagen(String idMunicipio) {
        return "https://cdn.pixabay.com/photo/2020/02/16/19/41/horses-4854601_960_720.jpg";
    }

    @SneakyThrows private ProvinciaModel agregarCantidadMunicipios(ProvinciaModel provincia) {
        String url = geoRefMunicipioBaseUrlEstandar + "&provincia=" + provincia.getId();
        Long cantidadMunicipios = new Gson().fromJson(connector.get(url), MunicipiosApi.class).getTotal();
        return provincia.cantidadMunicipios(cantidadMunicipios);
    }

    @Override
    public List<ProvinciaModel> getProvincias() {
        Provincias provincias = connector.get(geoRefProvinciabaseUrlEstandar, Provincias.class);
        List<ProvinciaModel> provinciasSinCantidadMunicipios = provinciaWrapper.wrapList(provincias.getProvincias());

        List<ProvinciaModel> provinciasModel = provinciasSinCantidadMunicipios.stream()
            .map( provincia -> agregarCantidadMunicipios(provincia))
            .collect(Collectors.toList());
        
        return provinciasModel;
    }

    public ProvinciaModel getNombreProvinicas(Long idProvincia) {
        var url = geoRefProvinciaNombre + idProvincia.toString();
        Provincias provincias = connector.get(url, Provincias.class);
        return provinciaWrapper.wrap(provincias.getProvincias().get(0));
    }
}