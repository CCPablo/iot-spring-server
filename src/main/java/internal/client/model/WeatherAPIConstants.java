package internal.client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherAPIConstants {

    public final static String TEMPERATURE_FIELD = "temp";
    public final static String FEELS_LIKE_FIELD = "feels_like";
    public final static String PRECIPITATION_FIELD = "precipitation";
    public final static String CLOUD_COVER_FIELD = "cloud_cover";
    public final static String WIND_SPEED_FIELD = "wind_speed";
    public final static String WIND_GUST_FIELD = "wind_gust";
    public final static String WIND_DIRECTION = "wind_direction";
    public final static String VISIBILITY_FIELD = "visibility";
    public final static String SUNRISE_FIELD = "sunrise";
    public final static String SUNSET_FIELD = "sunset";
    public final static String MOON_PHASE_FIELD = "moon_phase";

    public final static String URL = "api.climacell.co";
    public final static String HOST = "https";

    public final static String API_KEY = "zs9OtkZGsp3PI0X6CnLxaecZK25uRnfO";

    public final static List<String> REALTIME_FOLDERS = List.of("v3", "weather", "realtime");

    public final static List<String> FIELDS = List.of(
            TEMPERATURE_FIELD,
            FEELS_LIKE_FIELD,
            PRECIPITATION_FIELD,
            CLOUD_COVER_FIELD,
            WIND_SPEED_FIELD,
            WIND_GUST_FIELD,
            WIND_DIRECTION,
            VISIBILITY_FIELD,
            SUNRISE_FIELD,
            SUNSET_FIELD,
            MOON_PHASE_FIELD);

    public final static String LATITUDE = "37.378683";
    public final static String LONGITUDE = "-6.001435";
    public final static String UNIT_SYSTEM = "si";

    public static final Map<String, Object> PARAMETERS = new HashMap<>(Map.of(
            "lat", LATITUDE,
            "lon", LONGITUDE,
            "unit_system", UNIT_SYSTEM,
            "fields", FIELDS
        )
    );
}
