package tk.tutorial.security.requestvuln.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.tutorial.security.requestvuln.mapper.PersonRowMapper;
import tk.tutorial.security.requestvuln.model.Person;
import tk.tutorial.security.requestvuln.repository.PersonRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;

@Controller
@Slf4j
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	// Deserialisation
	// Stored XSS
	// only for admins
	@RequestMapping(value = "person/create/json", consumes = ALL_VALUE, method = RequestMethod.POST)
	public String createPersonWithJson(@RequestParam("file") MultipartFile file, Model model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		userDetails.getAuthorities().iterator().forEachRemaining((GrantedAuthority) -> {
			if (GrantedAuthority.getAuthority().contains("ROLE_ADMIN")) {
				ByteArrayInputStream stream = null;
				try {
					stream = new ByteArrayInputStream(file.getBytes());
				} catch (IOException e) {
					log.debug(e.toString());
				}

				ObjectMapper objectMapper = new ObjectMapper();

				Person person = null;
				try {
					person = objectMapper.readValue(stream, Person.class);
				} catch (IOException e) {
					log.debug(e.toString());
				}
				personRepository.save(person);
			} else {
				throw new RuntimeException("Your are not admin");
			}
		});

		return "redirect:/person/all";
	}

	// Stored XSS
	// CSRF
	@RequestMapping(value = "person/create", method = RequestMethod.POST)
	public String createPerson(Person person) throws RuntimeException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		userDetails.getAuthorities().iterator().forEachRemaining((GrantedAuthority) -> {
			if (GrantedAuthority.getAuthority().contains("ROLE_ADMIN")) {
				personRepository.save(person);
			} else {
				throw new RuntimeException("Your are not admin");
			}
		});
		return "redirect:/person/all";
	}

	// SQL Injection
	// http://localhost:8001/person/getSinglePerson/1%27%20or%20%271%27%3D%271
	@RequestMapping(value = "person/getSinglePerson/{id}", method = RequestMethod.GET)
	public String getPersonById(@PathVariable String id, Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		userDetails.getAuthorities().iterator().forEachRemaining((GrantedAuthority) -> {
			if (GrantedAuthority.getAuthority().contains("ROLE_ADMIN")) {
				String sql = "SELECT * FROM Person p where p.id = '" + id + "'";
				List<Person> list = jdbcTemplate.query(sql, new PersonRowMapper());
				model.addAttribute("persons", list);
			} else {
				throw new RuntimeException("Your are not admin");
			}
		});
		return "getsingleperson";
	}

	// A5 Broken Access Control
	// Only Admins should be allowed to delete a person
	// CSRF
	// Force browsing to authenticated pages as an unauthenticated user or to privileged pages as a standard user. Accessing API with missing access controls for POST, PUT and DELETE.
	@RequestMapping(value = "person/delete", method = RequestMethod.POST)
	public String deletePerson(Person person, Model model) {
		personRepository.delete(person);
		return "redirect:/person/all";
	}


	// Path Traversal (file read) -> no Injection -> nope?
	// https://find-sec-bugs.github.io/bugs.htm
	@PostMapping(value = "download/file")
	public @ResponseBody
	byte[] downloadFile(String file) throws IOException {
		InputStream in = getClass().getResourceAsStream("/files/" + file);
		return IOUtils.toByteArray(in);
	}

	// -------------------- Views -------------------

	@RequestMapping(value = "person/delete", method = RequestMethod.GET)
	public String getViewDeletePersonById(Model model) {
		model.addAttribute("person", new Person());
		return "deletePerson";
	}

	@RequestMapping(value = "person/single", method = RequestMethod.GET)
	public String getOnePersonView(Model model) {
		return "getsingleperson";
	}

	@RequestMapping(value = "person/all", method = RequestMethod.GET)
	public String getViewWithAllPersons(Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext()
			.getAuthentication().getDetails();

		System.out.println(userDetails.getUsername());
		System.out.println(details.getSessionId());

		model.addAttribute("persons", personRepository.findAll());
		return "allpersons";
	}

	@RequestMapping(value = "download", method = RequestMethod.GET)
	public String getViewDownloadFile() {
		return "download";
	}

	@RequestMapping(value = "person/view_create", method = RequestMethod.GET)
	public String getViewCreatePerson(Model model) {
		model.addAttribute("person", new Person());
		return "createperson";
	}

	@RequestMapping(value = "person/createwithjson", method = RequestMethod.GET)
	public String getViewCreatePersonWithJson(Model model) {
		return "createpersonjson";
	}
}
