package internal.repository;

import internal.model.unit.UnitValue;

import java.util.List;

public interface UnitValueRepository {

    void addUnitValue(UnitValue value);

    List<UnitValue> findAll();

    List<UnitValue> findAllByUnitId(Integer nodeId, Integer unitId);

    UnitValue findFirstByUnitId(Integer nodeId, Integer unitId);

    void deleteAll();
}
