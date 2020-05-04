//package cn.edu.buaa.se.bhac.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.nio.charset.StandardCharsets;
//
//@RestController
//public class webtest2 {
//
//    @GetMapping("/admin/activities/authed")
//    public String testMap() throws Exception{
//        File file = new File("D:\\Study\\OneDrive\\ThirdTwo\\Software Engineering\\Homework\\Group\\se-teamwork_bhac\\server\\src\\main\\resources\\static\\json\\tabletest.json");
//        if(!file.exists()){
//            return null;
//        }
//        FileInputStream inputStream = new FileInputStream(file);
//        int length = inputStream.available();
//        byte bytes[] = new byte[length];
//        inputStream.read(bytes);
//        inputStream.close();
//        String str =new String(bytes, StandardCharsets.UTF_8);
//        return str ;
//    }
//}
