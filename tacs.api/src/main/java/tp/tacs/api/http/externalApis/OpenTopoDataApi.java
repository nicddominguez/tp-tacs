package tp.tacs.api.http.externalApis;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.http.HttpClientConnector;
import tp.tacs.api.http.externalApis.models.TopoData;
import tp.tacs.api.http.externalApis.models.TopoResult;
import tp.tacs.api.servicios.ServicioMunicipio;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OpenTopoDataApi {

    @Autowired
    private HttpClientConnector connector;
    @Autowired
    private ServicioMunicipio servicioMunicipio;

    private final String topoBaseUrl = "https://api.opentopodata.org/v1/srtm90m?locations=";
    private static final Integer LIMITE_REQUESTS = 100;

    @Cacheable(value = "alturas", sync = true, key = "#idProvincia")
    public List<TopoData> getAlturas(String idProvincia, List<Municipio> municipios) {
        var municipiosDivididos = Lists.partition(municipios, LIMITE_REQUESTS);
        return municipiosDivididos.stream().map(this::getTopoData).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @SneakyThrows
    private List<TopoData> getTopoData(List<Municipio> municipios) {
        String coordenadas = municipios.stream().map(municipio -> servicioMunicipio.coordenadasParaTopo(municipio))
                .reduce("", (coord1, coord2) -> new StringBuilder(coord1).append("%7C").append(coord2).toString());
        String url = String.format("%s%s", topoBaseUrl, coordenadas);
        return new Gson().fromJson(connector.get(url), TopoResult.class).getResults();
    }

}
