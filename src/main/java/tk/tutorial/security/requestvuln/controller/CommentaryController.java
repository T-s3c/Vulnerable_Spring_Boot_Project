package tk.tutorial.security.requestvuln.controller;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tk.tutorial.security.requestvuln.mapper.CommentaryRowMapper;
import tk.tutorial.security.requestvuln.model.Commentary;
import tk.tutorial.security.requestvuln.repository.CommentaryRepository;
import tk.tutorial.security.requestvuln.service.XmlParser;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;

@Controller
public class CommentaryController {

	@Autowired
	CommentaryRepository commentaryRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static String SQLSCRIPTPATH = "/sql/getAllCommentary.sql";

	// CSRF
	// Stored XSS
	@RequestMapping(value = "commentary/create", method = RequestMethod.POST)
	public String createCommentary(Commentary commentary, Model model) {
		commentaryRepository.save(commentary);
		return "redirect:/commentary";
	}

	// SQLinjection Union attack
	// erst%' UNION SELECT id, password FROM PERSON --
	@RequestMapping(value = "commentary/search", method = RequestMethod.POST)
	public String getCommentaryByText(String searchString, Model model) {
		String sql = "SELECT * FROM COMMENTARY c WHERE c.text like '%" + searchString + "%'";
		List<Commentary> list = jdbcTemplate.query(sql, new CommentaryRowMapper());
		model.addAttribute("commentaries", list);
		model.addAttribute("searchString", searchString);
		model.addAttribute("results", list.size());
		return "search";
	}

	// Potential template injection with Velocity
	// http://localhost:8001/template/1%20or%20%271%27%20%3D%20%271%27
	@RequestMapping(value = "commentary/template", method = RequestMethod.POST)
	public String getCommentaryByIdWithTemplate(String id, Model model) throws Exception {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();

		VelocityContext velocityContext = new VelocityContext();

		Template template = velocityEngine.getTemplate(SQLSCRIPTPATH);
		StringWriter stringWriter = new StringWriter();

		velocityContext.put("templateId", id);

		template.merge(velocityContext, stringWriter);

		List<Commentary> list = jdbcTemplate.query(stringWriter.toString(), new CommentaryRowMapper());

		model.addAttribute("output", list);
		return "searchWithXml";
	}


	// XXE
	// https://github.com/WebGoat/WebGoat/blob/develop/webgoat-lessons/xxe/src/main/java/org/owasp/webgoat/xxe/Comments.java
	@RequestMapping(value = "create/xxe", consumes = ALL_VALUE, method = RequestMethod.POST)
	public String createNewCommentWithXmlParser(@RequestParam("file") MultipartFile file, Model model) throws
		Exception {
		String newFile = "post.xml";
		Files.write(Paths.get(newFile), file.getBytes());

		XmlParser xmlParser = new XmlParser();
		String output = xmlParser.evalXmlParserImpl(Paths.get(newFile).toFile());
		model.addAttribute("output", output);
		return "fileUpload";
	}


	// https://pmd.github.io/latest/pmd_rules_java_security.html
	// OWASP Top 10 - 2007 : A8 - Insecure cryptographic Storage
	// HardCodedCryptoKey
	private SecretKeySpec secretKey() {
		return new SecretKeySpec("my secret here".getBytes(), "AES");
	}

	// ------------------- Views ----------------------

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String getSearchView(Model model) {
		model.addAttribute("searchString", "test");
		return "search";
	}

	@RequestMapping(value = "file", method = RequestMethod.GET)
	public String getViewFileUpload(Model model) {
		model.addAttribute("message", "File upload function");
		return "fileUpload";
	}

	@RequestMapping(value = "commentary", method = RequestMethod.GET)
	public String getViewAllCommentaries(Model model) {
		model.addAttribute("commentaries", commentaryRepository.findAll());
		return "commentary";
	}

	// A3 Sensitive Data Exposure - really -> nope
	// A6:2017 - Security Misconfiguration - Cookie misconfigurated - really?
	// Cookie is not HttpOnly
	// https://sonar.indibit.eu/coding_rules?languages=java&open=squid%3AS3330&types=VULNERABILITY
	// https://sonar.indibit.eu/coding_rules?open=squid%3AS3331&q=cross+site+scripting&types=SECURITY_HOTSPOT
	@RequestMapping(value = "createcommentary", method = RequestMethod.GET)
	public String getViewCreateCommentary(Model model) {
		Cookie cookie = new Cookie("testCookie", "testCookie"); // by default cookie.isHttpOnly() is returning false
		cookie.setDomain(".com");
		//UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext()
			.getAuthentication().getDetails();
		cookie.setValue(details.getSessionId());
		cookie.setComment(secretKey().toString());
		model.addAttribute("commentary", new Commentary());
		return "createcommentary";
	}

	@RequestMapping(value = "template", method = RequestMethod.GET)
	public String getViewSearchCommentaryWithTemplate(Model model) {
		return "searchWithXml";
	}
}
