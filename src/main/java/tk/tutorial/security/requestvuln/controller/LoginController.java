package tk.tutorial.security.requestvuln.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import tk.tutorial.security.requestvuln.model.Person;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Boolean loginTask(@RequestParam String username, @RequestParam String password){
        Person person = jdbcTemplate.queryForObject("SELECT * FROM PERSON p WHERE p.username = " + username + " AND p.password = " + password, Person.class);
        if (person.getId() != null) {
            return true;
        } else {
            return false;
        }
    }
}
