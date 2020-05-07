package internal.repository;

import internal.model.unit.UnitValue;

import java.util.List;

public interface UnitValueRepository {

    void addUnitValue(UnitValue value);

    List<UnitValue> findAll();

    List<UnitValue> findAllByUnitId(Integer deviceId, Integer unitId);

    UnitValue findFirstByUnitId(Integer deviceId, Integer unitId);

    void deleteAll();
}
