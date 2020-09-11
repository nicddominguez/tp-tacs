package tp.tacs.api.http.externalApis;

import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.http.HttpClientConnector;
import tp.tacs.api.http.externalApis.models.MunicipiosApi;
import tp.tacs.api.http.externalApis.models.TopoResult;
import tp.tacs.api.http.wrappers.GeorefWrapper;

import java.util.List;

public class ExternalApis implements RepoMunicipios {

    private static ExternalApis instancia;
    private HttpClientConnector connector;
    private GeorefWrapper wrapper;

    private String geoRefBaseUrlBasico      = "https://apis.datos.gob.ar/georef/api/municipios?campos=basico&aplanar=true";
    private String geoRefbaseUrlEstandar    = "https://apis.datos.gob.ar/georef/api/municipios?aplanar=true";
    private String topoBaseUrl              = "https://api.opentopodata.org/v1/srtm90m?locations=";

    public static ExternalApis instance() {
        if (instancia == null) {
            instancia = new ExternalApis();
            instancia.connector = new HttpClientConnector();
            instancia.wrapper = new GeorefWrapper();
        }
        return instancia;
    }

    @Override
    public List<Municipio> getMunicipios(String idProvincia, Integer cantidad) {
        String url = geoRefBaseUrlBasico + "&provincia=" + idProvincia;
        MunicipiosApi municipiosApi = connector.get(url, MunicipiosApi.class);
        return wrapper.wrapList(municipiosApi.getMunicipios());
    }

    @Override
    public String getNombre(String idMunicipio) {
        String url = geoRefBaseUrlBasico + "&id=" + idMunicipio;
        MunicipiosApi municipiosApi = connector.get(url, MunicipiosApi.class);
        return municipiosApi.getMunicipios().get(0).getNombre();
    }

    @Override
    public Double getLatitud(String idMunicipio) {
        String url = String.format("%s&campos=centroide.lat&id=%s",geoRefBaseUrlBasico,idMunicipio);
        return connector.get(url, MunicipiosApi.class).getMunicipios().get(0).getCentroide_lat().doubleValue();
    }

    @Override
    public Double getLongitud(String idMunicipio) {
        String url = String.format("%s&campos=centroide.lon&id=%s",geoRefBaseUrlBasico,idMunicipio);
        return connector.get(url, MunicipiosApi.class).getMunicipios().get(0).getCentroide_lon().doubleValue();
    }

    @Override
    public Float getAltura(String idMunicipio) {
        String coordenadas = getCoordenadas(idMunicipio);
        String url = String.format("%s%s",topoBaseUrl,coordenadas);
        return connector.get(url, TopoResult.class).getResults().get(0).getElevation().floatValue();
    }

    @Override
    public String getPathImagen(String idMunicipio) {
        return null;
    }

    @Override
    public String getCoordenadas(String idMunicipio) {
        String url = String.format("%s&campos=centroide.lat,centroide.lon&id=%s",geoRefbaseUrlEstandar,idMunicipio);
        return connector.get(url, MunicipiosApi.class).getMunicipios().get(0).coordenadasParaTopo();
    }
}