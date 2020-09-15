package internal.repository;

import internal.model.unit.UnitMeasure;

import java.util.List;

public interface UnitValueRepository {

    void addUnitValue(UnitMeasure value);

    List<UnitMeasure> findAll();

    List<UnitMeasure> findAllById(Integer nodeId, Integer unitId);

    UnitMeasure findFirstById(Integer nodeId, Integer unitId);

    List<UnitMeasure> findByIdSinceDate(Integer nodeId, Integer unitId, Long millisSince);

    void deleteAll();
}
