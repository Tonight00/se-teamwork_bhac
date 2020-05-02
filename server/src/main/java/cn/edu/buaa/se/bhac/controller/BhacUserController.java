package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.services.BhacUserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class BhacUserController {
    @Autowired
    BhacUserService userService;

    @GetMapping("/admin/activities/authed")
    public String getAuthedActivities(HttpSession session) {
        BhacUser admin = (BhacUser) session.getAttribute("admin");
        List<BhacActivity> authedActivities = userService.getAuthedActivities(admin);
        JSONObject json = new JSONObject(authedActivities);
        return json.toString();
    }

}
