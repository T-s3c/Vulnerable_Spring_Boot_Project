package tk.tutorial.security.requestvuln;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.tutorial.security.requestvuln.model.Gender;
import tk.tutorial.security.requestvuln.model.MaritalStatus;
import tk.tutorial.security.requestvuln.model.Person;
import tk.tutorial.security.requestvuln.repository.PersonRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class RequestvulnApplication implements CommandLineRunner {

    @Autowired
    PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(RequestvulnApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String birthDateString = "15.10.1995";
        DateFormat format = new SimpleDateFormat("DD.MM.YYYY");
        Date date = format.parse(birthDateString);


        personRepository.save(new Person("tobias1", "kolb", "95512", date, Gender.MALE, "tobi1", "passwd", MaritalStatus.SINGLE));
        personRepository.save(new Person("tobias2", "kolb", "95512", date, Gender.MALE, "tobi2", "test", MaritalStatus.SINGLE));
        personRepository.save(new Person("tobias3", "kolb", "95512", date, Gender.MALE, "tobi3", "password", MaritalStatus.SINGLE));
        personRepository.save(new Person("tobias4", "kolb", "95512", date, Gender.MALE, "tobi4", "testtest", MaritalStatus.SINGLE));
    }
}
