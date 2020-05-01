package cn.edu.buaa.se.bhac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class webtest {

    @GetMapping("helloworld")
    public String test1() {
        return "helloworld";
    }

    @GetMapping("login")
    public String test2() { return "login"; }

    @PostMapping("validate")
    public String test2() { return "login"; }

    @GetMapping("login2")
    public String test3() {
        return "login2";
    }

    @GetMapping("index_act")
    public String test4() {
        return "index_act";
    }

    @GetMapping("index_sys")
    public String test5() {
        return "index_sys";
    }

    @GetMapping("success")
    public String success(Map<String,Object> map){
        map.put("hello","你好");
        return "success";
    }
}
