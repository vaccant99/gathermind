package woongjin.gatherMind.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/login", "/signup", "/mypage", "/mypage/*", "/editprofile", "/goodbye", "/serious"})
    public String index() {
        return "forward:/index.html";
    }
}
