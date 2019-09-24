package tk.tutorial.security.requestvuln.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import tk.tutorial.security.requestvuln.mapper.PersonRowMapper;
import tk.tutorial.security.requestvuln.model.Person;

import javax.crypto.spec.SecretKeySpec;
import java.sql.SQLException;

@RestController
@RequestMapping("/vuln")
// Overly permissive CORS policy
@CrossOrigin
public class DatabaseController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/getPerson/{id}", method = RequestMethod.GET)
    public Person getPersonById(@PathVariable String id) throws SQLException {
        String sql = "SELECT * FROM Person p where p.id = '" + id + "'";
        Person list = jdbcTemplate.queryForObject(sql, new Object[]{}, new PersonRowMapper());
        return list;
    }


    // https://pmd.github.io/latest/pmd_rules_java_security.html
    // InsecureCryptoIv
    private void secretKey() {
        SecretKeySpec secretKeySpec = new SecretKeySpec("my secret here".getBytes(), "AES");
    }
}
