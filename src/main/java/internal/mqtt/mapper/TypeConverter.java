package internal.mqtt.mapper;

public class TypeConverter {


    /*
    public static Map<Integer, Set<Measure>> getMeasuresFromMsg(SensorMeasuresMsg msg) {
        Map<Integer, Set<Measure>> measures = new HashMap<>();
        Map<Integer, Set<MeasureMsg>> measuresInMsg = msg.getMeasures();

        measuresInMsg.forEach((key, value) -> measures.putIfAbsent(key, mapMeasures(value)));
        return measures;
    }

    private static Set<Measure> mapMeasures (Set<MeasureMsg> measureMsg) {
        return measureMsg.stream().map(TypeConverter::mapMeasure).collect(Collectors.toSet());
    }

    private static Measure mapMeasure (MeasureMsg measureMsg) {
        return Measure.builder()
                .value(measureMsg.getValue())
                .timestamp(measureMsg.getTimestamp())
                .build();
    }
     */


}
