package tk.tutorial.security.requestvuln.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;

	private String lastName;

	private String postalcode;

	private String username;

	// TODO 2017:A3 Sensitive Date Exposure
	private String password;

	public Person(String firstName, String lastName, String postalcode, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.postalcode = postalcode;
		this.username = username;
		this.password = password;
	}
}
