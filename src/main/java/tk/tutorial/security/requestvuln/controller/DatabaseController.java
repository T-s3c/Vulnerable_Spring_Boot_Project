package tk.tutorial.security.requestvuln.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import tk.tutorial.security.requestvuln.mapper.PersonRowMapper;
import tk.tutorial.security.requestvuln.model.Person;

import javax.crypto.spec.SecretKeySpec;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/vuln")
// Overly permissive CORS policy
@CrossOrigin
public class DatabaseController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // SQL Injection
    // http://localhost:8001/vuln/getPerson/1%27%20or%20%271%27%3D%271
    @RequestMapping(value = "/getPerson/{id}", method = RequestMethod.GET)
    public List<Person> getPersonById(@PathVariable String id) throws SQLException {
        String sql = "SELECT * FROM Person p where p.id = '" + id + "'";
        List<Person> list = jdbcTemplate.query(sql, new PersonRowMapper());
        return list;
    }


    // https://pmd.github.io/latest/pmd_rules_java_security.html
    // InsecureCryptoIv
    private void secretKey() {
        SecretKeySpec secretKeySpec = new SecretKeySpec("my secret here".getBytes(), "AES");
    }
}
