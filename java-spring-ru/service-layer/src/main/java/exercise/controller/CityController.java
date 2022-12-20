package exercise.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.CityNotFoundException;
import exercise.dto.CityDTO;
import exercise.dto.CityWeather;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;

    // BEGIN
    @GetMapping(path = "/cities/{id}")
    public CityWeather getCityWeather(@PathVariable Long id) throws JsonProcessingException {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City not found"));

        return weatherService.getCityWeather(city.getName());
    }

    @GetMapping(path = "/search")
    public List<CityDTO> getCityWeather(
            @RequestParam(required = false) String name
    ) throws JsonProcessingException {
        List<CityDTO> result = new ArrayList<>();

        Iterable<City> cities;
        if (null != name) {
            cities = cityRepository.findCitiesByNameStartingWithIgnoreCase(name);
        } else {
            cities = cityRepository.findAllByOrderByNameAsc();
        }

        for (City city : cities) {
            CityWeather cityWeather = weatherService.getCityWeather(city.getName());
            CityDTO cityDTO = new CityDTO();
            cityDTO.name = cityWeather.name;
            cityDTO.temperature = cityWeather.temperature;

            result.add(cityDTO);
        }

        return result;
    }
    // END
}

