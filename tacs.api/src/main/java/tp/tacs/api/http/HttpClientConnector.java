package tp.tacs.api.http;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tp.tacs.api.http.exception.HttpErrorException;

import java.util.Map;

@Component
public class HttpClientConnector {

    private RestTemplate restTemplate;

    @Bean
    private void restTemplate(){
        restTemplate = new RestTemplate();
    }

    public <T> T get(String url, Class<T> clazz) {
        Map<String, String> headers = Map.of("accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity(headers);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, clazz).getBody();
        } catch (Exception ex) {
            throw new HttpErrorException(ex.getMessage());
        }
    }

    public <T> T get(String url, Class<T> clazz, Map<String, String> moreHeaders) {
        Map<String, String> headers = new java.util.HashMap<>(Map.of("accept", MediaType.APPLICATION_JSON_VALUE));
        try {
            headers.putAll(moreHeaders);
            HttpEntity<String> entity = new HttpEntity(headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, clazz).getBody();
        } catch (Exception ex) {
            throw new HttpErrorException(ex.getMessage());
        }
    }
}