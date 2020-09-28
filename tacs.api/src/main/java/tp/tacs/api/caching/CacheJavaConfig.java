package tp.tacs.api.caching;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheJavaConfig {

    //Hay que crear uno de estos Beans por cada configuración de cache distinta (por ejemplo para distintos TTL)
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("municipiosApi", "cantidadMunicipios",
                "nombreProvincias", "provincias", "alturas", "imagenes"); //Todos los que tengan la misma config se pueden poner acá
        cacheManager.setAllowNullValues(false); // puede pasar si tenes un valor de @Cacheable que retorna null
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(40, TimeUnit.SECONDS)
                .recordStats();
    }

}