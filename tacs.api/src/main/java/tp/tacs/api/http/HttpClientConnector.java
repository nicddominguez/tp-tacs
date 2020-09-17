package tp.tacs.api.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tp.tacs.api.http.exception.HttpErrorException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

@Component
public class HttpClientConnector {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public <T> T get(String url, Class<T> clazz) {
        Map<String, String> headers = Map.of("accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity(headers);
        try {
            return new RestTemplate().exchange(url, HttpMethod.GET, entity, clazz).getBody();
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            throw new HttpErrorException(ex.getMessage());
        }
    }

    public <T> T get(String url, Class<T> clazz, Map<String, String> moreHeaders) {
        Map<String, String> headers = new java.util.HashMap<>(Map.of("accept", MediaType.APPLICATION_JSON_VALUE));
        try {
            headers.putAll(moreHeaders);
            HttpEntity<String> entity = new HttpEntity(headers);
            return new RestTemplate().exchange(url, HttpMethod.GET, entity, clazz).getBody();
        } catch (Exception ex) {
            throw new HttpErrorException(ex.getMessage());
        }
    }

    public String GGet(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}