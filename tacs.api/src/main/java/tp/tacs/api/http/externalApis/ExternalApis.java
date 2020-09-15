package tp.tacs.api.http.externalApis;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.http.HttpClientConnector;
import tp.tacs.api.http.exception.HttpErrorException;
import tp.tacs.api.http.externalApis.models.MunicipiosApi;
import tp.tacs.api.http.externalApis.models.Provincias;
import tp.tacs.api.http.externalApis.models.TopoResult;
import tp.tacs.api.mappers.GeorefMapper;
import tp.tacs.api.mappers.ProvinciaMapper;
import tp.tacs.api.model.ProvinciaModel;

import java.util.ArrayList;
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
        try {
            sleep(200);
            String url = geoRefMunicipioBaseUrlBasico + "&provincia=" + idProvincia + "&max=" + cantidad;
            MunicipiosApi municipiosApi = connector.get(url, MunicipiosApi.class);
            return geoRefWrapper.wrapList(municipiosApi.getMunicipios());
        } catch (Exception e) {
            throw new HttpErrorException();
        }
    }

    @Override
    public String getNombre(String idMunicipio) {
        try {
            sleep(200);
            String url = geoRefMunicipioBaseUrlBasico + "&id=" + idMunicipio;
            MunicipiosApi municipiosApi = connector.get(url, MunicipiosApi.class);
            return municipiosApi.getMunicipios().get(0).getNombre();
        } catch (InterruptedException e) {
            throw new HttpErrorException(e.getMessage());
        }

    }

    @Override
    public Double getLatitud(String idMunicipio) {
        try {
            sleep(200);
            String url = String.format("%s&campos=centroide.lat&id=%s", geoRefMunicipioBaseUrlEstandar, idMunicipio);
            return connector.get(url, MunicipiosApi.class).getMunicipios().get(0).getCentroide_lat().doubleValue();
        } catch (Exception e) {
            throw new HttpErrorException(e.getMessage());
        }
    }

    @Override
    public Double getLongitud(String idMunicipio) {
        try {
            sleep(200);
            String url = String.format("%s&campos=centroide.lon&id=%s", geoRefMunicipioBaseUrlEstandar, idMunicipio);
            return connector.get(url, MunicipiosApi.class).getMunicipios().get(0).getCentroide_lon().doubleValue();
        } catch (Exception e) {
            throw new HttpErrorException(e.getMessage());
        }
    }

    @Override
    public Float getAltura(String idMunicipio) {
        try {
            sleep(200);
            String coordenadas = getCoordenadas(idMunicipio);
            String url = String.format("%s%s", topoBaseUrl, coordenadas);
            return connector.get(url, TopoResult.class).getResults().get(0).getElevation().floatValue();
        } catch (Exception e) {
            throw new HttpErrorException(e.getMessage());
        }
    }

    @Override
    public String getPathImagen(String idMunicipio) {
        return null;
    }

    @Override
    public String getCoordenadas(String idMunicipio) {
        try {
            sleep(200);
            String url = String.format("%s&campos=centroide.lat,centroide.lon&id=%s", geoRefMunicipioBaseUrlEstandar, idMunicipio);
            return connector.get(url, MunicipiosApi.class).getMunicipios().get(0).coordenadasParaTopo();
        } catch (Exception e) {
            throw new HttpErrorException(e.getMessage());
        }
    }

    public ArrayList<Double> getCoordenadasArray(String idMunicipio) {
        return Lists.newArrayList(getLatitud(idMunicipio), this.getLongitud(idMunicipio));
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