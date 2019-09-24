package tk.tutorial.security.requestvuln.controller;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import tk.tutorial.security.requestvuln.mapper.CommentaryRowMapper;
import tk.tutorial.security.requestvuln.model.Commentary;
import tk.tutorial.security.requestvuln.repository.CommentaryRepository;

import java.io.StringWriter;
import java.util.List;

@RestController
@RequestMapping("commentary")
public class CommentaryController {

    @Autowired
    CommentaryRepository commentaryRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static String SQLSCRIPTPATH = "/sql/getAllCommentary.sql";

    // Potential XSS
    @RequestMapping(method = RequestMethod.POST)
    public Commentary createCommentary(@RequestBody Commentary commentary) {
        return commentaryRepository.save(commentary);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Commentary> getAll() {
        return commentaryRepository.findAll();
    }

    // Potential template injection with Velocity
    @RequestMapping(value = "template/{id}" , method = RequestMethod.GET)
    public Commentary getAllCommentaryByTemplate(@PathVariable Long id) throws Exception {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        VelocityContext velocityContext = new VelocityContext();

        Template template = velocityEngine.getTemplate(SQLSCRIPTPATH);
        StringWriter stringWriter = new StringWriter();

        velocityContext.put("templateId", id);

        template.merge(velocityContext, stringWriter);

        return jdbcTemplate.queryForObject(stringWriter.toString(), new Object[]{}, new CommentaryRowMapper());
    }
}
