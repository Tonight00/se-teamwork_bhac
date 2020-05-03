package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.services.BhacUserService;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BhacUserController {
    @Autowired
    BhacUserService userService;

    @GetMapping("/admin/activities/authed")
    public String getAuthedActivities(HttpSession session) {
        BhacUser admin = (BhacUser) session.getAttribute("admin");
        List<BhacActivity> authedActivities = userService.getAuthedActivities(admin);
        return JSONObject.toJSONString(authedActivities,
                /*exist=false属性的filter，不打印这部分属性*/ControllerUtils.filterFactory(BhacActivity.class));
    }

}
