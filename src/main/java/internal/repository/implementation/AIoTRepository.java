package internal.repository.implementation;

import com.mongodb.client.MongoCollection;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AIoTRepository<T>  {

    private final MongoTemplate mongoTemplate;

    protected MongoCollection<T> mongoCollection;

    protected AIoTRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    void postConstruct() {
    }

}
