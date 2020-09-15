package tp.tacs.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FrontendController {

    @RequestMapping(value = {"/", "/app", "/login"})
    public String getIndex(HttpServletRequest request) {
        return "/index.html";
    }

}

