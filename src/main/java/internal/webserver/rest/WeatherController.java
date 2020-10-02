package internal.webserver.rest;

import internal.client.WeatherClient;
import internal.client.model.ValueUnitsModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherClient weatherClient;

    public WeatherController(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    @GetMapping(value = "/realtime")
    public Map<String, ValueUnitsModel> getCurrentWeatherValues() {
        return weatherClient.getCachedRealtimeWeatherValues();
    }
}
