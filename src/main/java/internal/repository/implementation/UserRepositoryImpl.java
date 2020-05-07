package internal.repository.implementation;

import com.mongodb.client.result.UpdateResult;
import internal.repository.UserRepository;
import internal.repository.model.ApplicationUser;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final MongoTemplate mongoTemplate;

    private final String COLLECTION_NAME = "users";

    @PostConstruct
    private void init() {
        //mongoTemplate.createCollection(COLLECTION_NAME);
    }

    @Override
    public List<ApplicationUser> findAll() {
        return mongoTemplate.findAll(ApplicationUser.class, COLLECTION_NAME);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.remove(ApplicationUser.class, COLLECTION_NAME);
    }

    @Override
    public void saveUser(ApplicationUser applicationUser) {
        mongoTemplate.save(applicationUser, COLLECTION_NAME);
    }

    @Override
    public ApplicationUser findUserByUserName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query , ApplicationUser.class, COLLECTION_NAME);
    }

    @Override
    public long updateUser(ApplicationUser applicationUser) {
        Query query = new Query(Criteria.where("name").is(applicationUser.getName()));
        Update update = new Update().set("name", applicationUser.getName()).set("password", applicationUser.getPassword());
        UpdateResult result = mongoTemplate.updateFirst(query,update, ApplicationUser.class, COLLECTION_NAME);

        return result.getMatchedCount();
    }

    @Override
    public void deleteUserByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        mongoTemplate.remove(query, ApplicationUser.class, COLLECTION_NAME);
    }
}
