package internal.repository.implementation;

import internal.repository.UnitValueRepository;
import internal.model.unit.UnitValue;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UnitValueRepositoryImpl implements UnitValueRepository {

    private final MongoTemplate mongoTemplate;

    private final String COLLECTION_NAME = "unitValues";

    @Override
    public void addUnitValue(UnitValue value) {
        mongoTemplate.save(value, COLLECTION_NAME);
    }

    @Override
    public List<UnitValue> findAll() {
        return mongoTemplate.findAll(UnitValue.class);
    }

    @Override
    public List<UnitValue> findAllByUnitId(Integer nodeId, Integer unitId) {
        Query query = new Query(Criteria.where("nodeId").is(nodeId));
        return mongoTemplate.find(query, UnitValue.class);
    }

    @Override
    public UnitValue findFirstByUnitId(Integer nodeId, Integer unitId) {
        Query query = new Query(Criteria.where("nodeId").is(nodeId));
        return mongoTemplate.findOne(query, UnitValue.class);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.remove(UnitValue.class);
    }
}
