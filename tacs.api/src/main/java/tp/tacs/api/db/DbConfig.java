package tp.tacs.api.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

    public @Bean MongoClient mongoClient() {
        return MongoClients.create("mongodb://root:example@34.72.171.144:27017/");
    }

}
