package internal.client.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClimacellRealtime {
    Float lat;

    Float lon;

    ValueModel observation_time;

    ValueUnitsModel temp;

    ValueUnitsModel feels_like;

    ValueUnitsModel dewpoint;

    ValueUnitsModel wind_speed;

    ValueUnitsModel wind_gust;

    ValueUnitsModel visibility;

    ValueUnitsModel wind_direction;

    ValueUnitsModel precipitation;

    ValueUnitsModel cloud_cover;

    ValueUnitsModel cloud_ceiling;

    ValueUnitsModel cloud_base;

    ValueModel moon_phase;

    ValueModel sunrise;

    ValueModel sunset;

    ValueModel road_risk_confidence;
}
