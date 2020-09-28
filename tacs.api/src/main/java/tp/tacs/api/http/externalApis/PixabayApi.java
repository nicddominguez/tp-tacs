package tp.tacs.api.http.externalApis;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tp.tacs.api.http.HttpClientConnector;
import tp.tacs.api.http.externalApis.models.ImagenApi;
import tp.tacs.api.http.externalApis.models.ImagenesApi;

import java.util.List;

@Component
public class PixabayApi {

    @Value("${pixabay.key}")
    private String pixabayKey;

    private final String pixabayUrl = "https://pixabay.com/api/?key=%s&per_page=200&image_type=photo&q=";

    @Autowired
    private HttpClientConnector connector;

    @SneakyThrows
    @Cacheable("imagenes")
    public List<ImagenApi> getImagenes(String query){
        String url = String.format(pixabayUrl, pixabayKey);
        String provincia = query.replace(" ", "+");
        return new Gson().fromJson(connector.get(url + provincia), ImagenesApi.class).getHits();
    }
}
