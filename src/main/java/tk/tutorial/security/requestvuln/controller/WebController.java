package tk.tutorial.security.requestvuln.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.tutorial.security.requestvuln.model.Person;
import tk.tutorial.security.requestvuln.repository.PersonRepository;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("controllers")
public class WebController {

    @Autowired
    private PersonRepository personRepository;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void createPerson(@RequestBody Person person) {
        personRepository.save(person);
    }

    @RequestMapping(value = "/password/{id}", method = RequestMethod.GET)
    public Person getUserById(@PathVariable("id") Long id) {
        //Person foundPerson = personRepository.findById(id).orElseThrow();
        //return foundPerson;
        return null;
    }

    @RequestMapping(value = "/delete")
    public Person deletePerson(@RequestBody Person person) {
        personRepository.delete(person);
        return person;
    }

    // TODO https://find-sec-bugs.github.io/bugs.htm
    // Path Traversal (file read)
    @RequestMapping(value = "/image/{image}", produces = "images/*", method = RequestMethod.GET)
    public File getImage(@PathVariable("image") String image) {
        File file = new File("../../../../../../resources/images", image);
        return file;
    }


/*    // TODO https://github.com/WebGoat/WebGoat/blob/develop/webgoat-lessons/insecure-login/src/main/java/org/owasp/webgoat/plugin/InsecureLoginTask.java
    // Insecure Login function
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean loginTask(@RequestParam String username, @RequestParam String password) {
        String tempPassword = personRepository.findPasswordByUsername(username);
        if (tempPassword.equals(password)) {
            return true;
        } else {
            return false;
        }
    }*/

    // TODO COMMAND LINE INJECTION
    @RequestMapping(value = "ping/{command}", method = RequestMethod.GET)
    public void getCommandValue(@PathVariable("command") String command) throws IOException {

        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(command);
    }
}
