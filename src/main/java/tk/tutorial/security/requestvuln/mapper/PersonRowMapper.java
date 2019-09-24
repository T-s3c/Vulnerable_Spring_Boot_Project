package tk.tutorial.security.requestvuln.mapper;

import org.springframework.jdbc.core.RowMapper;
import tk.tutorial.security.requestvuln.model.Gender;
import tk.tutorial.security.requestvuln.model.MaritalStatus;
import tk.tutorial.security.requestvuln.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

        Person person = new Person();
        person.setId(rs.getLong("ID"));
        person.setBirthDate(rs.getDate("birth_Date"));
        person.setUsername(rs.getString("username"));
        person.setLastName(rs.getString("last_Name"));
        person.setFirstName(rs.getString("first_Name"));
        if (rs.getInt("gender") == 0) {
            person.setGender(Gender.MALE);
        } else if (rs.getInt("gender") == 1) {
            person.setGender(Gender.FEMALE);
        } else if (rs.getInt("gender") == 2){
            person.setGender(Gender.DIVERSE);
        }

        person.setPassword(rs.getString("password"));
        person.setPostalcode(rs.getString("postalcode"));
        if (rs.getInt("marital_Status") == 0) {
            person.setMaritalStatus(MaritalStatus.SINGLE);
        } else if (rs.getInt("marital_Status") == 1) {
            person.setMaritalStatus(MaritalStatus.MARRIED);
        } else if (rs.getInt("marital_Status") == 2) {
            person.setMaritalStatus(MaritalStatus.WIDOWD);
        }
        return person;
    }
}
