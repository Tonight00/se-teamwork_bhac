package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacTag;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.services.BhacUserService;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.baomidou.mybatisplus.annotation.TableField;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// todo: 1. 完成下面有javadoc的接口
// todo: 注意事项1: 要添加code请参考BaseCode、UserCode的实现和命名规则
// todo: 注意事项2: 因为属性的延迟加载只会生效一次，数据库变动后已经加载出来的值就是过期的，实现的时候请注意这点
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

    /**
     * @param id 批准的活动的aid
     * @return 返回code和message
     * @implNote 返回时请使用ControllerUtils.JsonCodeAndMessage方法
     */
    @PutMapping("/admin/activities/permit")
    public String permitActivity(@Param("activityId") Integer id) {
        return null;
    }

    /**
     * @param username 输入的用户名
     * @return 根据username模糊查询(%x%)查出对应的用户，以Json格式返回
     * @implNote 返回JSON格式的做法请参考getAuthedActivities方法
     *
     */
    @GetMapping("/sysadmin/users")
    public String getUsersByUsername(@Param("username") String username) {
        return null;
    }

    /**
     * @param name 输入的标签名
     * @return 根据name模糊查询(%x%)查出对应的标签，以Json格式返回
     * @implNote 返回JSON格式的做法请参考getAuthedActivities方法
     *
     */
    @GetMapping("/sysadmin/tags")
    public String getTagsByName(@Param("name") String name) {
        return null;
    }

    /**
     * @param uid 用户id
     * @param tid 标签id
     * @param state 赋予的权限(目前都是0)
     * @return 赋予用户uid 标签tid的角色state，返回code和message
     */
    @PutMapping("sysadmin/users/auth")
    public String authorize(@Param("userId") Integer uid, @Param("tagId") Integer tid, @Param("state") Integer state) {
        return null;
    }

    /**
     * @param uid 用户id
     * @param tid 标签id
     * @param state 赋予的权限(目前都是0)
     * @return 撤销用户uid 标签tid的角色state，返回code和message
     */
    @PutMapping("sysadmin/users/deauth")
    public String deauthorize(@Param("userId") Integer uid, @Param("tagId") Integer tid, @Param("state") Integer state) {
        return null;
    }

    /**
     * @param input 用户输入的标签属性
     * @return 添加该标签，返回code和message
     */
    @PostMapping("sysadmin/tags")
    public String addTag(BhacTag input) {
        return null;
    }

    /**
     * @param id 要删除的标签的id
     * @return 删除该标签，返回code和message
     */
    @DeleteMapping("sysadmin/tag{id}")
    public String delTag(@PathParam("id") Integer id) {
        return null;
    }

}
