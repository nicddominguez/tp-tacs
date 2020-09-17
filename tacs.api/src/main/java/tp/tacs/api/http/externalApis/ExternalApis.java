package tp.tacs.api.http.externalApis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.http.HttpClientConnector;
import tp.tacs.api.http.exception.HttpErrorException;
import tp.tacs.api.http.externalApis.models.MunicipiosApi;
import tp.tacs.api.http.externalApis.models.Provincias;
import tp.tacs.api.http.externalApis.models.TopoData;
import tp.tacs.api.http.externalApis.models.TopoResult;
import tp.tacs.api.mappers.GeorefMapper;
import tp.tacs.api.mappers.ProvinciaMapper;
import tp.tacs.api.model.ProvinciaModel;

import java.util.List;

import static java.lang.Thread.sleep;

@Component
public class ExternalApis implements RepoMunicipios {

    @Autowired
    private HttpClientConnector connector;
    @Autowired
    private GeorefMapper geoRefWrapper;
    @Autowired
    private ProvinciaMapper provinciaWrapper;

    private String geoRefMunicipioBaseUrlBasico = "https://apis.datos.gob.ar/georef/api/municipios?campos=basico&aplanar=true";
    private String geoRefMunicipioBaseUrlEstandar = "https://apis.datos.gob.ar/georef/api/municipios?aplanar=true";
    private String geoRefProvinciabaseUrlEstandar = "https://apis.datos.gob.ar/georef/api/provincias?aplanar=true";
    private String topoBaseUrl = "https://api.opentopodata.org/v1/srtm90m?locations=";

    @Override
    public List<Municipio> getMunicipios(String idProvincia, Integer cantidad) {
        String url = geoRefMunicipioBaseUrlEstandar + "&provincia=" + idProvincia + "&max=" + cantidad;
        var municipiosApi = connector.get(url, MunicipiosApi.class);
        var municipiosBase = geoRefWrapper.wrapList(municipiosApi.getMunicipios());
        municipiosBase.forEach(
                municipio -> municipio.setAltura(getAltura(municipio))); //TODO cambiar el ForEach para pedirle todo de una
        return municipiosBase;
    }

    private Float getAltura(Municipio municipio) {
        try {
            sleep(1000);
            String coordenadas = municipio.coordenadasParaTopo();
            String url = String.format("%s%s", topoBaseUrl, coordenadas);
            List<TopoData> results = connector.get(url, TopoResult.class).getResults();
            return results.get(0).getElevation().floatValue();
        } catch (Exception e) {
            throw new HttpErrorException(e.getMessage());
        }
    }

    @Override
    public String getPathImagen(String idMunicipio) {
        return null;
    }

    @Override
    public List<ProvinciaModel> getProvincias() {
        try {
            sleep(200);
            Provincias provincias = connector.get(geoRefProvinciabaseUrlEstandar, Provincias.class);
            return provinciaWrapper.wrapList(provincias.getProvincias());
        } catch (Exception e) {
            throw new HttpErrorException(e.getMessage());
        }
    }
}