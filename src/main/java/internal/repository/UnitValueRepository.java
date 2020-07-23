package internal.repository;

import internal.model.unit.UnitValue;

import java.util.List;

public interface UnitValueRepository {

    void addUnitValue(UnitValue value);

    List<UnitValue> findAll();

    List<UnitValue> findAllById(Integer nodeId, Integer unitId);

    UnitValue findFirstById(Integer nodeId, Integer unitId);

    List<UnitValue> findByIdSinceDate(Integer nodeId, Integer unitId, Long millisSince);

    void deleteAll();
}
