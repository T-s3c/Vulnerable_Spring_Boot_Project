package tk.tutorial.security.requestvuln;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.tutorial.security.requestvuln.model.Commentary;
import tk.tutorial.security.requestvuln.model.Person;
import tk.tutorial.security.requestvuln.repository.CommentaryRepository;
import tk.tutorial.security.requestvuln.repository.PersonRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	CommentaryRepository commentaryRepository;

	// TODO OWASP Top 10 2017 Category A1 - Injection?
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		// Test data for Person
		personRepository.save(new Person("tobias1", "kolb", "95512", "tobi1", "passwd"));
		personRepository.save(new Person("tobias2", "kolb", "95512", "tobi2", "test"));
		personRepository.save(new Person("tobias3", "kolb", "95512", "tobi3", "password"));
		personRepository.save(new Person("tobias4", "kolb", "95512", "tobi4", "testtest"));

		// Test data for Commentary
		commentaryRepository.save(new Commentary("Das ist der erste Kommentar"));
		commentaryRepository.save(new Commentary("Das ist der zweite Kommentar"));
		commentaryRepository.save(new Commentary("Das ist der dritte Kommentar"));
		commentaryRepository.save(new Commentary("Das ist der vierte Kommentar"));
	}

	/* If set http-only:false -> XSS attack can read the session id
	servlet:
    session:
      cookie:
        http-only: false*/
}
