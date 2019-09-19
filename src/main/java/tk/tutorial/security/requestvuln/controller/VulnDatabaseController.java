package tk.tutorial.security.requestvuln.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import tk.tutorial.security.requestvuln.model.Person;

import javax.crypto.spec.SecretKeySpec;
import java.sql.ResultSet;

@RestController
@RequestMapping("/vuln")
@CrossOrigin(origins = "http://localhost:4200")
public class VulnDatabaseController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/getPerson/{id}")
    public Person getPersonById(@PathVariable Long id) {
        String sql = "SELECT * FROM person p where p.firstName = " + id;
        return jdbcTemplate.queryForObject(sql,Person.class);
    }

    // https://pmd.github.io/latest/pmd_rules_java_security.html
    // InsecureCryptoIv
    private void secretKey() {
        SecretKeySpec secretKeySpec = new SecretKeySpec("my secret here".getBytes(), "AES");
    }
}
