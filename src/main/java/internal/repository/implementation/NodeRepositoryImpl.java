package internal.repository.implementation;

import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;
import internal.model.node.Node;
import internal.repository.NodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@AllArgsConstructor
public class NodeRepositoryImpl implements NodeRepository {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    private void init() {
        String COLLECTION_NAME = "nodes";
        //mongoTemplate.getDb().createCollection(COLLECTION_NAME);
    }

    @Override
    public void saveNode(Node node) {
        mongoTemplate.save(node);
    }

    @Override
    public List<Node> findAll() {
        return mongoTemplate.findAll(Node.class);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.remove(Node.class);
    }

    @Override
    public void deleteNodeById(Integer nodeId) {
        Query query = new Query(Criteria.where("nodeId").is(nodeId));
        mongoTemplate.remove(query, Node.class);
    }

    @Override
    public Node findNodeById(Integer nodeId) {
        Query query = new Query(Criteria.where("nodeId").is(nodeId));
        return mongoTemplate.findOne(query, Node.class);
    }

    @Override
    public long updateNode(Node node) {
        Query query = new Query(Criteria.where("nodeId").is(node.getId()));
        Update update = new Update()
                .set("name", node.getName())
                .set("units", node.getUnits());
        UpdateResult result = mongoTemplate.updateFirst(query, update, Node.class);
        return result.getMatchedCount();
    }
}
