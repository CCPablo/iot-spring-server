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
    public List<UnitValue> findAllByUnitId(Integer deviceId, Integer unitId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        return mongoTemplate.find(query , UnitValue.class);
    }

    @Override
    public UnitValue findFirstByUnitId(Integer deviceId, Integer unitId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        return mongoTemplate.findOne(query , UnitValue.class);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.remove(UnitValue.class);
    }
}
