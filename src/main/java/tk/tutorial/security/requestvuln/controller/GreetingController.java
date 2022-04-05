package tk.tutorial.security.requestvuln.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    // A7:2017 Cross Site Scripting (Reflective XSS)
    // http://localhost:8001/greeting?name=%3Cscript%3Ealert(%27XSS%27);%3C/script%3E
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name",name);
        return "greeting";
    }

    // http://localhost:8001/greeting/stage1?name=%3C%3Cscript%3E%3Ealert%28%27XSS%27%29%3B%3C%2Fscript%3E
    @GetMapping("/greeting/stage1")
    public String greetingStage1(@RequestParam(name="name", required = false, defaultValue = "WorldStage1") String name, Model model) {
        model.addAttribute("name", filterStage1(name));
        return "greetingstage1";
    }

    // http://localhost:8001/greeting/stage2?name=%3CSCRIPT%3Ealert%28%27XSS%27%29%3B%3C%2FSCRIPT%3E
    @GetMapping("greeting/stage2")
    public String greetingStage2(@RequestParam(name="name", required = false, defaultValue = "WorldStage2") String name, Model model) {
        model.addAttribute("name", filterStage2(name));
        return "greetingstage2";
    }

    private String filterStage1(String name) {
        String openTag = "<";
        String closeTag = ">";
        if(name.contains(openTag)) {
            name = name.replaceFirst(openTag, "");
        }
        if(name.contains(closeTag)) {
            name = name.replaceFirst(closeTag, "");
        }
        return name;
    }

    private String filterStage2(String name) {
        String scriptOpenTag = "<script>";
        String scriptCloseTag = "</script>";
        if(name.contains(scriptOpenTag)) {
            name = name.replace(scriptOpenTag, "-");
        }
        if(name.contains(scriptCloseTag)) {
            name = name.replace(scriptCloseTag, "-");
        }
        return name;
    }
}
