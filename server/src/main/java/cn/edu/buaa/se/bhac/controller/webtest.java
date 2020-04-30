package cn.edu.buaa.se.bhac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webtest {

    @GetMapping("helloworld")
    public String test1() {
        return "helloworld";
    }

    @GetMapping("login")
    public String test2() {
        return "login";
    }

    @GetMapping("index")
    public String test3() {
        return "index";
    }
}
