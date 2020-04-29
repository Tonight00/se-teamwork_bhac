package cn.edu.buaa.se.bhac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webtest {

    @GetMapping("helloworld")
    public String test() {
        return "helloworld";
    }
}
