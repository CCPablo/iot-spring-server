package internal.repository.implementation;

import com.mongodb.client.result.UpdateResult;
import internal.model.device.Device;
import internal.repository.DeviceRepository;
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
public class DeviceRepositoryImpl implements DeviceRepository {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    private void init() {
        String COLLECTION_NAME = "devices";
        //mongoTemplate.getDb().createCollection(COLLECTION_NAME);
    }

    @Override
    public void saveDevice(Device device) {
        mongoTemplate.save(device);
    }

    @Override
    public List<Device> findAll() {
        return mongoTemplate.findAll(Device.class);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.remove(Device.class);
    }

    @Override
    public void deleteDeviceById(Integer deviceId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        mongoTemplate.remove(query, Device.class);
    }

    @Override
    public Device findDeviceById(Integer deviceId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        return mongoTemplate.findOne(query, Device.class);
    }

    @Override
    public long updateDevice(Device device) {
        Query query = new Query(Criteria.where("deviceId").is(device.getId()));
        Update update = new Update()
                .set("name", device.getName())
                .set("units", device.getUnits());
        UpdateResult result = mongoTemplate.updateFirst(query, update, Device.class);
        return result.getMatchedCount();
    }
}
