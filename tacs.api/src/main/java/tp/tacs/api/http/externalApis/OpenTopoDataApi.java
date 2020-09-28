package tp.tacs.api.http.externalApis;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.HttpClientConnector;
import tp.tacs.api.http.externalApis.models.TopoData;
import tp.tacs.api.http.externalApis.models.TopoResult;

import java.util.List;

@Component
public class OpenTopoDataApi {

    @Autowired
    private HttpClientConnector connector;

    private final String topoBaseUrl = "https://api.opentopodata.org/v1/srtm90m?locations=";

    @SneakyThrows
    @Cacheable(value = "alturas", sync = true, key = "#idProvincia")
    public List<TopoData> getAlturas(String idProvincia, List<Municipio> municipios) {
        String coordenadas = municipios.stream().map(Municipio::coordenadasParaTopo)
                .reduce("", (coord1, coord2) -> coord1 + "%7C" + coord2);
        String url = String.format("%s%s", topoBaseUrl, coordenadas);
        return new Gson().fromJson(connector.get(url), TopoResult.class).getResults();
    }

}
