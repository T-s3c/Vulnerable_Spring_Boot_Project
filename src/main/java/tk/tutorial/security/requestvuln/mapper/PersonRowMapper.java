package tk.tutorial.security.requestvuln.mapper;

import org.springframework.jdbc.core.RowMapper;
import tk.tutorial.security.requestvuln.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

        Person person = new Person();
        person.setId(rs.getLong("ID"));
        person.setUsername(rs.getString("username"));
        person.setLastName(rs.getString("last_Name"));
        person.setFirstName(rs.getString("first_Name"));
        person.setPassword(rs.getString("password"));
        person.setPostalcode(rs.getString("postalcode"));
        return person;
    }
}
