package exercise;

import exercise.daytimes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

// BEGIN
@RestController
public class WelcomeController {

    @Autowired
    Daytime daytime;

    @Autowired
    Meal meal;
    @GetMapping("/daytime")
    public String root() {
        String time = daytime.getName();

        return "It is " + time + " now. Enjoy your " + meal.getMealForDaytime(daytime.getName());
    }
}
// END
