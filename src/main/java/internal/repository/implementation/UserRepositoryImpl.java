package internal.repository.implementation;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import internal.repository.UserRepository;
import internal.repository.model.ApplicationUser;
import internal.service.NodeService;
import internal.util.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final MongoTemplate mongoTemplate;

    private final ApplicationContext applicationContext;

    private final String COLLECTION_NAME = "users";

    private MongoCollection<ApplicationUser> mongoCollection;

    @PostConstruct
    private void init() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        mongoCollection = mongoTemplate.getCollection(COLLECTION_NAME).withDocumentClass(ApplicationUser.class).withCodecRegistry(pojoCodecRegistry);
    }

    @Override
    public List<ApplicationUser> findAll() {
        return mongoTemplate.findAll(ApplicationUser.class, COLLECTION_NAME);
    }

    @Override
    public void deleteAll() {
        mongoCollection.drop();
    }

    @Override
    public void saveUser(ApplicationUser applicationUser) {
        mongoCollection.insertOne(applicationUser);
    }

    @Override
    public ApplicationUser findUserByUserName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, ApplicationUser.class, COLLECTION_NAME);
    }

    @Override
    public long updateUser(ApplicationUser applicationUser) {
        Query query = new Query(Criteria.where("name").is(applicationUser.getName()));
        Update update = new Update().set("name", applicationUser.getName()).set("password", applicationUser.getPassword());
        UpdateResult result = mongoTemplate.updateFirst(query, update, ApplicationUser.class, COLLECTION_NAME);

        return result.getMatchedCount();
    }

    @Override
    public void deleteUserByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        mongoTemplate.remove(query, ApplicationUser.class, COLLECTION_NAME);
    }
}
