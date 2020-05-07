package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.ActivityCode;
import cn.edu.buaa.se.bhac.code.UserCode;
import cn.edu.buaa.se.bhac.services.BhacActivityService;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BhacActivityController {
    @Autowired
    private BhacActivityService activityService;
    
    /**
     * 返回该用户可以管理的活动集
     * @param session
     * @param pageNum
     * @param limit
     * @return BhacActivities集合
     */
    @GetMapping("/admin/activities/authed")
    public String getAuthedActivities(HttpSession session, Integer pageNum, Integer limit) {
        BhacUser admin = (BhacUser) session.getAttribute("admin");
        List<BhacActivity> authedActivities = activityService.getAuthedActivities(admin,pageNum,limit);
        if(authedActivities == null) authedActivities = new ArrayList<>();
        return JSONObject.toJSONString(authedActivities,
                /*exist=false属性的filter，不打印这部分属性*/ControllerUtils.filterFactory(BhacActivity.class));
    }

    /**
     * 更改活动审核状态state
     * @param id 批准的活动的aid
     * @return 返回code和message
     * @implNote 返回时请使用ControllerUtils.JsonCodeAndMessage方法
     */
    @PutMapping("/admin/activities/permit")
    public String permitActivity(@Param("id") Integer id) {
        System.out.println(id);
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
     * 返回(%title%)且category = tid 的活动列表
     * @param title 活动标题
     * @param tid 活动分类
     * @return  List<BhacActivities>
     * @implNote  BhacActivity对象集合
     */
    @GetMapping("/untoken/activities")
    public String getActivities(@Param("title")String title, @Param("tid") Integer tid,
                                    @Param("pageNum")Integer pageNum , @Param("limit") Integer limit) {
        return JSONObject.toJSONString(
                activityService.getActivities(title,tid,pageNum,limit) ,
                ControllerUtils.filterFactory(BhacActivity.class));
    }
    
    /**
     * 返回id对应的BhacActivity
     * @param id 活动id
     * @return BhacActivity对象
     */
    @GetMapping("/untoken/activities/{id}")
    public String getActivity(@PathVariable("id") Integer id) {
        BhacActivity activity = activityService.getActivity(id);
        if (activityService.getActivity(id) == null) {
            activity = new BhacActivity();
        }
        return JSONObject.toJSONString(activity,
                ControllerUtils.filterFactory(BhacActivity.class));
    }
    
    /**
     * 用户id加入活动aid
     * @param aid 活动id
     * @param request Http 请求
     * @return 返回code 和 message
    */
    
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
    
    
    /**
     * 用户id退出活动aid
     * @param aid 活动id
     * @param request Http请求
     * @return 返回code和message
     */
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
    
    /**
     * 根据用户id和活动id返回JoinUserActivity对象
     * @param aid
     * @param request
     * @return
     */
    @GetMapping("/join/info")
    public String getJoinInfo(Integer aid,HttpServletRequest request) {
        Claims claims  =  (Claims) request.getAttribute("claims");
        if(activityService.getActivity(aid) == null ) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(ActivityCode.ERR_ACTIVITY_NOT_EXISTED));
        }
        return JSONObject.toJSONString(activityService.getJoinUserActivity(aid,(Integer)claims.get("uid")));
    }
    
    @PostMapping("/activities")
    public String addActivities(BhacActivity activity,HttpServletRequest request) {
        Claims claims  =  (Claims) request.getAttribute("claims");
        activity.setUid((Integer) claims.get("uid"));
        activityService.addActivity(activity);
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(ActivityCode.SUCC_ACTIVITY_ADD));
    }
}
