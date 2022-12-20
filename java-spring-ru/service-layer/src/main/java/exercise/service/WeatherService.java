package exercise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.HttpClient;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.dto.CityWeather;
import org.springframework.stereotype.Service;
import exercise.CityNotFoundException;
import exercise.repository.CityRepository;
import exercise.model.City;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class WeatherService {

    @Autowired
    CityRepository cityRepository;

    // Клиент
    HttpClient client;

    // При создании класса сервиса клиент передаётся снаружи
    // В теории это позволит заменить клиент без изменения самого сервиса
    WeatherService(HttpClient client) {
        this.client = client;
    }

    // BEGIN
    public CityWeather getCityWeather(String city) throws JsonProcessingException {
        String data = this.client.get("http://weather/api/v2/cities/" + city);

        ObjectMapper mapper = new ObjectMapper();
        CityWeather cityWeather = mapper.readValue(data, CityWeather.class);

        return cityWeather;
    }
    // END
}
