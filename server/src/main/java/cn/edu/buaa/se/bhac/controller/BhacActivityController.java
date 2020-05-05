package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.ActivityCode;
import cn.edu.buaa.se.bhac.services.BhacActivityService;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BhacActivityController {
    @Autowired
    private BhacActivityService activityService;

    @GetMapping("/admin/activities/authed")
    public String getAuthedActivities(HttpSession session,Integer pageNum, Integer limit) {
        BhacUser admin = (BhacUser) session.getAttribute("admin");
        List<BhacActivity> authedActivities = activityService.getAuthedActivities(admin,pageNum,limit);
        if(authedActivities == null) authedActivities = new ArrayList<>();
        return JSONObject.toJSONString(authedActivities,
                /*exist=false属性的filter，不打印这部分属性*/ControllerUtils.filterFactory(BhacActivity.class));
    }

    /**
     * @param id 批准的活动的aid
     * @return 返回code和message
     * @implNote 返回时请使用ControllerUtils.JsonCodeAndMessage方法
     */
    @PutMapping("/admin/activities/permit")
    public String permitActivity(@Param("activityId") Integer id) {
        System.out.println(id);
        BhacActivity activity = activityService.getActivity(id);
        if (activity == null) {
            return JSONObject.toJSONString(ControllerUtils
                  .JsonCodeAndMessage(ActivityCode.ERR_ACTIVITY_NOT_EXISTED));
        }
        if (activity.getState() != 1) {
            if (!activityService.permitActivity(activity.getId(), 1))
                return JSONObject.toJSONString(ControllerUtils
                        .JsonCodeAndMessage(ActivityCode.ERR_ACTIVITY_INNER_ERROR));
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(ActivityCode.SUCC_ACTIVITY_AUDIT_SUCC));
    }

}
