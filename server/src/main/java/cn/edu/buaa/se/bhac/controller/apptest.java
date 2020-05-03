package cn.edu.buaa.se.bhac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class apptest {

    @GetMapping("hello_world")
    public String test() {
        return "Hello, World!";
    }

}
