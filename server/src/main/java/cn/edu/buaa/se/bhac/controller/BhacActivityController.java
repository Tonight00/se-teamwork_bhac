package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.ActivityCode;
import cn.edu.buaa.se.bhac.code.UserCode;
import cn.edu.buaa.se.bhac.services.BhacActivityService;
import cn.edu.buaa.se.bhac.services.BhacUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
        BhacActivity activity = activityService.getActivity(id);
        if (activity == null) {
            return JSONObject.toJSONString(ControllerUtils
                  .JsonCodeAndMessage(ActivityCode.ERR_ACTIVITY_NOT_EXISTED));
        }
        if (activity.getState() != 1) {
            activityService.permitActivity(activity.getId(), 1);
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(ActivityCode.SUCC_ACTIVITY_AUDIT_SUCC));
    }
    
    /**
     *
     * @param title
     * @param tid
     * @return  List<BhacActivities>
     * @implNote  返回(%title%)且category = tid 的活动列表
     */
    @GetMapping("untoken/activities")
    public String getActivities(@Param("title")String title, @Param("tid") Integer tid,
                                    @Param("pageNum")Integer pageNum , @Param("limit") Integer limit) {
        return JSONObject.toJSONString(
                activityService.getActivities(title,tid,pageNum,limit) ,
                ControllerUtils.filterFactory(BhacActivity.class));
    }
    
    /**
     *
     * @param id
     * @return BhacActivity
     */
    
    @GetMapping("untoken/activities/{id}")
    public String getActivity(@PathVariable("id") Integer id) {
        BhacActivity activity = activityService.getActivity(id);
        if (activityService.getActivity(id) == null) {
            activity = new BhacActivity();
        }
        return JSONObject.toJSONString(activity,
                ControllerUtils.filterFactory(BhacActivity.class));
    }

    
    @PutMapping("/activities/enroll")
    public String enroll(Integer aid, HttpServletRequest request) {
        Claims claims  =  (Claims) request.getAttribute("claims");
        if(activityService.getActivity(aid) == null ) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(ActivityCode.ERR_ACTIVITY_NOT_EXISTED));
        }
        Integer state = activityService.enroll(aid,(Integer)claims.get("uid"));
        if(state == -1 ) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_ENROLLED));
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.SUCC_USER_ENROLL));
    }
    
    @PutMapping("/activities/unenroll")
    public String unenroll (Integer aid,HttpServletRequest request) {
        Claims claims  =  (Claims) request.getAttribute("claims");
        if(activityService.getActivity(aid) == null ) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(ActivityCode.ERR_ACTIVITY_NOT_EXISTED));
        }
        Integer state = activityService.unenroll(aid,(Integer)claims.get("uid"));
        if(state == -1) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_UNENROLLED));
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.SUCC_USER_UNENROLL));
    }
    
    
}
