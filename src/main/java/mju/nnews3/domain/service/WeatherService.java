package mju.nnews3.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getWeatherText(double latitude, double longitude, boolean isLocationAgreed) {
        String city = "Seoul";
        double lat = latitude;
        double lon = longitude;

        if (!isLocationAgreed) {
            lat = 37.5665;
            lon = 126.978;
        }

        String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();

        String weatherResponse = restTemplate.getForObject(url, String.class);

        return extractWeatherDescription(weatherResponse);
    }

    private String extractWeatherDescription(String response) {
        try {
            org.json.JSONObject jsonResponse = new org.json.JSONObject(response);
            org.json.JSONArray weatherArray = jsonResponse.getJSONArray("weather");
            String mainWeather = weatherArray.getJSONObject(0).getString("main").toLowerCase();

            if (mainWeather.equals("scattered clouds") || mainWeather.equals("broken clouds")) {
                return "clouds";
            } else if (mainWeather.equals("shower rain") || mainWeather.equals("rain") || mainWeather.equals("mist")) {
                return "rain";
            } else {
                return mainWeather;
            }

        } catch (Exception e) {
            return "Unable to retrieve weather information.";
        }
    }

}

