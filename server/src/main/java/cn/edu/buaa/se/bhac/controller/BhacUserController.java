package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.*;
import cn.edu.buaa.se.bhac.Dao.mapper.*;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.Utils.JWTUtils;
import cn.edu.buaa.se.bhac.code.ActivityCode;
import cn.edu.buaa.se.bhac.code.BaseCode;
import cn.edu.buaa.se.bhac.code.TagCode;
import cn.edu.buaa.se.bhac.code.UserCode;
import cn.edu.buaa.se.bhac.config.JWTConfig;
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
import io.jsonwebtoken.Claims;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.lang.reflect.Field;
import java.rmi.activation.Activatable;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * @param username 输入的用户名
     * @param pageNum  第几页
     * @param limit    页容量
     * @return 根据username模糊查询(% x %)查出对应的用户，以Json格式返回
     * @implNote 返回JSON格式的做法请参考getAuthedActivities方法
     */
    @GetMapping("/sysadmin/users")
    public String getUsersByUsername(@Param("username") String username, @Param("page") Integer pageNum, @Param("limit") Integer limit) {
        List<BhacUser> users = userService.getUsersByUsername(username, pageNum, limit);
        if (users == null ) users = new ArrayList<>();
        return JSONObject.toJSONString(users, ControllerUtils.filterFactory(BhacUser.class));
    }

    /**
     * @param uid   用户id
     * @param tid   标签id
     * @param state 赋予的权限(目前都是0)
     * @return 赋予用户uid 标签tid的角色state，返回code和message
     */
    @PutMapping("sysadmin/users/auth")
    public String authorize(@Param("userId") Integer uid, @Param("tagId") Integer tid, @Param("state") Integer state) {
        BhacRole role = roleService.getRoleByTid(tid,state);
        if(role == null ) {
             return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_ROLE_NOT_FOUND));
        }
        if(userService.addRole2User(role.getId(),uid)==-1) {
             return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_ROLE_OWNED));
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.SUCC_USER_AUTHORIZED));
    }

    /**
     * @param uid   用户id
     * @param tid   标签id
     * @param state 赋予的权限(目前都是0)
     * @return 撤销用户uid 标签tid的角色state，返回code和message
     */
    @PutMapping("sysadmin/users/deauth")
    public String deauthorize(@Param("userId") Integer uid, @Param("tagId") Integer tid, @Param("state") Integer state) {
        BhacRole role = roleService.getRoleByTid(tid,state);
        if(role == null ) {
             return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_ROLE_NOT_FOUND));
        }
        if (userService.deleteRoleOfUser(role.getId(), uid)==-1) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_ROLE_DELETED));
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.SUCC_USER_DEAUTHORIZED));
    }


    @PostMapping("/users/login")
    public String login(BhacUser user) {
        UserCode code = userService.login(user);
        JSONObject json = ControllerUtils.JsonCodeAndMessage(code);
        if (code == UserCode.SUCC_USER_LOGIN) {
            json.put("token", JWTUtils.createToken(user));
        }
        return json.toJSONString();
    }

    @PostMapping("/users")
    public String register(BhacUser input) {
        UserCode code = userService.register(input);
        JSONObject json = ControllerUtils.JsonCodeAndMessage(code);
        if (code == UserCode.SUCC_USER_REG) {
            json.put("token", JWTUtils.createToken(input));
        }
        return json.toJSONString();
    }

    @GetMapping("/users/self")
    public String getCurUsers(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        return JSONObject.toJSONString(userService.getUserById((Integer) claims.get("uid")),
                ControllerUtils.filterFactory(BhacUser.class));
    }

    @PutMapping("/users/self")
    public String editUserInfo(@Param("type") Integer type,
                               @Param("oldPassword") String oldPassword,
                               @Param("newPassword") String newPassword,
                               BhacUser modified, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        UserCode code = null;
        modified.setId((Integer) claims.get("uid"));
        if (type == 0) {
            code = userService.editBasic(modified);
        } else if (type == 1) {
            code = userService.editPWD(oldPassword, newPassword, (Integer) claims.get("uid"));
        } else {
            code = UserCode.ERR_USER_PARAM;
        }
        return ControllerUtils.JsonCodeAndMessage(code).toJSONString();
    }

}
