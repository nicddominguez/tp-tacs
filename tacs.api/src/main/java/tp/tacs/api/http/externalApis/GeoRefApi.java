package tp.tacs.api.http.externalApis;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tp.tacs.api.http.HttpClientConnector;
import tp.tacs.api.http.externalApis.models.MunicipiosApi;
import tp.tacs.api.http.externalApis.models.Provincias;
import tp.tacs.api.model.ProvinciaModel;

@Component
public class GeoRefApi {

    @Autowired
    private HttpClientConnector connector;

    private final String geoRefMunicipioBaseUrlEstandar = "https://apis.datos.gob.ar/georef/api/departamentos?aplanar=true";
    private final String geoRefProvinciaNombre = "https://apis.datos.gob.ar/georef/api/provincias?aplanar=true&id=";
    private final String geoRefProvinciabaseUrlEstandar = "https://apis.datos.gob.ar/georef/api/provincias?aplanar=true";

    @Cacheable("municipiosApi")
    public MunicipiosApi getDatosMunicipios(String idProvincia) {
        String url = geoRefMunicipioBaseUrlEstandar + "&provincia=" + idProvincia + "&max=5000"; //Si no especificamos un max, georef no devuelve todos
        return connector.get(url, MunicipiosApi.class);
    }

    @SneakyThrows
    @Cacheable(value = "cantidadMunicipios", key = "#provincia.id")
    public Long agregarCantidadMunicipios(ProvinciaModel provincia) {
        String url = geoRefMunicipioBaseUrlEstandar + "&provincia=" + provincia.getId();
        return new Gson().fromJson(connector.get(url), MunicipiosApi.class).getTotal();
    }

    @Cacheable("nombreProvincias")
    public Provincias getNombreProvinicas(Long idProvincia) {
        var url = geoRefProvinciaNombre + idProvincia.toString();
        return connector.get(url, Provincias.class);
    }

    @Cacheable("provincias")
    public Provincias getProvincias() {
        return connector.get(geoRefProvinciabaseUrlEstandar, Provincias.class);
    }
}
