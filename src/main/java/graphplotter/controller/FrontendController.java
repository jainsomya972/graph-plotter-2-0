package graphplotter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String forward() {
        // Forward to index.html for non-API routes
        return "forward:/index.html";
    }
}
