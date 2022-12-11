package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    JdbcTemplate jdbc;

    @PostMapping(path = "")
    public void createPerson(@RequestBody Map<String, Object> person) {
        String query = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        jdbc.update(query, person.get("first_name"), person.get("last_name"));
    }

    // BEGIN
    @GetMapping(path = "")
    public List<Map<String, Object>> getPerson() {
        String query = "SELECT * FROM person";

        return jdbc.queryForList(query);
        // END
    }

    // BEGIN
    @GetMapping(path = "/{id}")
    public List<Map<String, Object>> getPerson(@PathVariable int id) {
        String query = "SELECT * FROM person WHERE id = ?";

        return jdbc.queryForList(query, new Object[]{id});
        // END
    }
}
