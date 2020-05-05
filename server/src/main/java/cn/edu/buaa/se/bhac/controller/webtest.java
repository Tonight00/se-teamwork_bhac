//package cn.edu.buaa.se.bhac.controller;
//
//import org.apache.ibatis.annotations.Param;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.Map;
//
//@Controller
//public class webtest {
//
//    @GetMapping("helloworld")
//    public String test1() {
//        return "helloworld";
//    }
//
//    @GetMapping("/login")
//    public String test2() { return "login"; }
//
//    @GetMapping("/u/logout")
//    public String logout() {
//        return "redirect:login";
//    }
//}

//@PostMapping("/u/login")
//    public String login(@Param("username") String username, @Param("password") String password , Model model) {
//        if(username.equals("admin") && password.equals("123456")){
//            model.addAttribute("id", "admin");
//            return "index_sys";
//        }else if(username.equals("manager") && password.equals("123456")){
//            model.addAttribute("id", "manager");
//            return "index_act";
//        }
//        model.addAttribute("message", "用户名或密码错误！");
//        return "login";
//    }
//
//    @GetMapping("index_act")
//    public String test4() {
//        return "index_act";
//    }
//
//    @GetMapping("index_sys")
//    public String test5() {
//        return "index_sys";
//    }
//
//    @GetMapping("index_tag")
//    public String getIndexTag() {
//        return "index_tag";
//    }
//
//    @GetMapping("success")
//    public String success(Map<String,Object> map){
//        map.put("hello","你好");
//        return "success";
//    }
//
//    @GetMapping("test")
//    public String test() {
//        return "test";
//    }
//}
