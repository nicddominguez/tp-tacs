package tp.tacs.api.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

    public @Bean @ConditionalOnProperty(prefix="application", name="persistance-implementation", havingValue = "mongo") MongoClient mongoClient() {
        var dbConnectionString = System.getenv("DB_CONNECTION_STRING");
        return MongoClients.create(dbConnectionString);
    }

}
