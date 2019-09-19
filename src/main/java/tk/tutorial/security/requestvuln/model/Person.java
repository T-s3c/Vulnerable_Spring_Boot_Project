package tk.tutorial.security.requestvuln.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String postalcode;

    private Date birthDate;

    private Gender gender;

    private String username;

    private String password;

    private MaritalStatus maritalStatus;

    public Person(String firstName, String lastName, String postalcode, Date birthDate, Gender gender, String username, String password, MaritalStatus maritalStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalcode = postalcode;
        this.birthDate = birthDate;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.maritalStatus = maritalStatus;
    }
}
