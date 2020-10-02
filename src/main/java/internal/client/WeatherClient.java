package internal.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import internal.client.model.ClimacellRealtime;
import internal.client.model.ValueModel;
import internal.client.model.ValueUnitsModel;
import internal.client.model.WeatherAPIConstants;
import internal.client.util.URLBuilder;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class WeatherClient {

    private final Map<String, ValueUnitsModel> cachedRealtimeWeatherValues = new HashMap<>();

    private int realtimeSecondsRate = 0;

    @SneakyThrows
    public void getCurrentConditions() {
        String content = currentConditionsResponseBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ClimacellRealtime response = objectMapper.readValue(content, ClimacellRealtime.class);

        Date sunriseDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
                .parse(response.getSunrise().getValue().replaceAll("Z$", "+0000"));
        Date sunsetDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
                .parse(response.getSunrise().getValue().replaceAll("Z$", "+0000"));

        cachedRealtimeWeatherValues.putAll(Map.of(
                "temp", response.getTemp(),
                "feels_like", response.getFeels_like(),
                "precipitation", response.getPrecipitation(),
                "cloud_cover", response.getCloud_cover(),
                "wind_speed", response.getWind_speed(),
                "wind_gust", response.getWind_gust(),
                "wind_direction", response.getWind_direction(),
                "moon_phase", getValueUnitsModelFromValueModel(response.getMoon_phase()),
                "sunrise", getValueUnitsModelFromValueModel(response.getSunrise()),
                "sunset", getValueUnitsModelFromValueModel(response.getSunset())
        ));
    }

    private String currentConditionsResponseBody() throws IOException, URISyntaxException {
        URLBuilder urlBuilder = new URLBuilder(WeatherAPIConstants.HOST, WeatherAPIConstants.URL);

        urlBuilder.addSubfolders(WeatherAPIConstants.REALTIME_FOLDERS);
        urlBuilder.addParameters(WeatherAPIConstants.PARAMETERS);

        HttpURLConnection con = (HttpURLConnection) urlBuilder.getURL().openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("apiKey", WeatherAPIConstants.API_KEY);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

    private ValueUnitsModel getValueUnitsModelFromValueModel(ValueModel model) {
        return new ValueUnitsModel(model.getValue(), null);
    }
}
