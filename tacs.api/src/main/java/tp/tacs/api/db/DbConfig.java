package tp.tacs.api.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

    public @Bean @ConditionalOnProperty(prefix="application", name="persistance-implementation", havingValue = "mongo") MongoClient mongoClient() {
        return MongoClients.create("mongodb://root:example@34.95.185.134:27017/");
    }

}
