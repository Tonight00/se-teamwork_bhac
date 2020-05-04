package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.*;
import cn.edu.buaa.se.bhac.Dao.mapper.*;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.ActivityCode;
import cn.edu.buaa.se.bhac.code.BaseCode;
import cn.edu.buaa.se.bhac.code.TagCode;
import cn.edu.buaa.se.bhac.code.UserCode;
import cn.edu.buaa.se.bhac.services.BhacRoleService;
import cn.edu.buaa.se.bhac.services.BhacTagService;
import cn.edu.buaa.se.bhac.services.BhacUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.lang.reflect.Field;
import java.rmi.activation.Activatable;
import java.util.ArrayList;
import java.util.List;

// todo: 1. 完成下面有javadoc的接口
// todo: 注意事项1: 要添加code请参考BaseCode、UserCode的实现和命名规则
// todo: 注意事项2: 因为属性的延迟加载只会生效一次，数据库变动后已经加载出来的值就是过期的，实现的时候请注意这点


@RestController
public class BhacUserController {
    @Autowired
    BhacUserService userService;
    @Autowired
    BhacTagService tagService;
    @Autowired
    BhacRoleService roleService;
    
    @Autowired
    BhacTagMapper bhacTagMapper;
    @Autowired
    BhacRoleMapper bhacRoleMapper;
    
   
    // todo : 这里的分页查询暂时没办法物理分页，想到的思路是逻辑分页。因为这里的实现是间接查的。
    @GetMapping("/admin/activities/authed")
    public String getAuthedActivities(HttpSession session,Integer pageNum, Integer limit) {
        BhacUser admin = (BhacUser) session.getAttribute("admin");
        System.out.println(admin);
        List<BhacActivity> authedActivities = userService.getAuthedActivities(admin,pageNum,limit);
        if(authedActivities == null) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_NO_ACTIVITY));
        }
        
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
        BhacActivity  activity = userService.getActivity(id);
        if(activity == null) {
            return JSONObject.toJSONString(ControllerUtils
                    .JsonCodeAndMessage(ActivityCode.ERR_ACTIVITY_NOT_EXISTED));
        }
        if (activity.getState() != 1) {
             if (! userService.permitActivity(activity.getId(),1))
                 return JSONObject.toJSONString(ControllerUtils
                         .JsonCodeAndMessage(ActivityCode.ERR_ACTIVITY_INNER_ERROR));
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(ActivityCode.SUCC_ACTIVITY_AUDIT_SUCC));
    }

    /**
     * @param username 输入的用户名
     * @param pageNum 第几页
     * @param limit 页容量
     * @return 根据username模糊查询(%x%)查出对应的用户，以Json格式返回
     * @implNote 返回JSON格式的做法请参考getAuthedActivities方法
     *
     */
    @GetMapping("/sysadmin/users")
    public String getUsersByUsername(@Param("username") String username,@Param("page") Integer pageNum , @Param("limit") Integer limit) {
        
        List<BhacUser> users =  userService.getUsersByUsername(username,pageNum,limit);
        if(users == null || users.size() == 0) {
            return  JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_NO_UNAME));
        }
        return JSONObject.toJSONString(users,ControllerUtils.filterFactory(BhacUser.class));
    }

    /**
     * @param name 输入的标签名
     * @return 根据name模糊查询(%x%)查出对应的标签，以Json格式返回
     * @implNote 返回JSON格式的做法请参考getAuthedActivities方法
     *
     */
    @GetMapping("/sysadmin/tags")
    public String getTagsByName(@Param("name") String name,@Param("page") Integer pageNum , @Param("limit")Integer limit) {
        List<BhacTag> tags = tagService.getTagsByTagname(name,pageNum,limit);
        if(tags == null || tags.size() == 0) {
            return  JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(TagCode.ERR_TAG_NO_NAME));
        }
        return JSONObject.toJSONString(tags,
                /*exist=false属性的filter，不打印这部分属性*/ControllerUtils.filterFactory(BhacTag.class));
    }

    /**
     *
     * @param uid 用户id
     * @param tid 标签id
     * @param state 赋予的权限(目前都是0)
     * @return 赋予用户uid 标签tid的角色state，返回code和message
     */
    @PutMapping("sysadmin/users/auth")
    public String authorize(@Param("userId") Integer uid, @Param("tagId") Integer tid, @Param("state") Integer state) {
        BhacRole role = roleService.getRoleByTid(tid,state);
        if(role == null ) {
              return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_ROLE_NOT_FOUND));
        }
        else {
            if(!userService.addRole2User(role.getId(),uid)) {
                return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_INNER_ERROR));
            }
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.SUCC_USER_AUTHORIZED));
    }

    /**
     * @param uid 用户id
     * @param tid 标签id
     * @param state 赋予的权限(目前都是0)
     * @return 撤销用户uid 标签tid的角色state，返回code和message
     */
    @PutMapping("sysadmin/users/deauth")
    public String deauthorize(@Param("userId") Integer uid, @Param("tagId") Integer tid, @Param("state") Integer state) {
        BhacRole role = roleService.getRoleByTid(tid,state);
        if(role == null ) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_ROLE_NOT_FOUND));
        }
        if(!userService.deleteRoleOfUser(role.getId(),uid)) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_INNER_ERROR));
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.SUCC_USER_DEAUTHORIZED));
    }

    /**
     * @param input 用户输入的标签属性
     * @return 添加该标签，返回code和message
     * @implNote 添加标签的同时要添加对应的role(State=0)，建议这里使用事务
     */
    @PostMapping("sysadmin/tags")
    @Transactional(rollbackFor = Exception.class)
    public String addTag(BhacTag input) {
        
        bhacTagMapper.insert(input);
        BhacRole role = new BhacRole();
        role.setTid(input.getId());
        role.setState(0);
        bhacRoleMapper.insert(role);
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(TagCode.SUCC_TAG_NAME_EXISTED));
    }

    /**
     * @param id 要删除的标签的id
     * @return 删除该标签，返回code和message
     * @implNote 软删除，把标签的state置为-1即可，不需要真正从数据库中删除
     */
    @DeleteMapping("sysadmin/tag{id}")
    public String delTag(@PathParam("id") Integer id) {
        if(!tagService.delTag(id))
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(TagCode.ERR_TAG_INNER_ERROR));
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(TagCode.SUCC_TAG_DELETED));
    }

}
