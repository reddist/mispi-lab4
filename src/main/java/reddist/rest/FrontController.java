package reddist.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@Controller
public class FrontController {
    @GetMapping("/mainpage")
    public String auth(Map<String, Object> model) {
        return "/index.html";
    }

    @GetMapping("/welcome")
    public String main(Map<String, Object> model) {
        return "/index.html";
    }
}