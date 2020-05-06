package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacRoleMapper;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacTagMapper;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.Utils.JWTUtils;
import cn.edu.buaa.se.bhac.code.UserCode;
import cn.edu.buaa.se.bhac.services.BhacRoleService;
import cn.edu.buaa.se.bhac.services.BhacTagService;
import cn.edu.buaa.se.bhac.services.BhacUserService;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
     * 根据username模糊查询(% x %)查出对应的用户
     * @param username 输入的用户名
     * @param page  第几页
     * @param limit    页容量
     * @return BhacUser对象集合
     * @implNote 返回JSON格式的做法请参考getAuthedActivities方法
     */
    @GetMapping("/sysadmin/users")
    public String getUsersByUsername(@Param("username") String username,@Param("page") Integer page, @Param("limit") Integer limit) {
        Integer pageNum = page;
        List<BhacUser> users = userService.getUsersByUsername(username, pageNum, limit);
        if (users == null ) users = new ArrayList<>();
        return JSONObject.toJSONString(users, ControllerUtils.filterFactory(BhacUser.class));
    }

    /**
     * 赋予用户uid 标签tid的角色state
     * @param uid   用户id
     * @param tid   标签id
     * @param state 赋予的权限(目前都是0)
     * @return 返回code和message
     */
    @PutMapping("/sysadmin/users/auth")
    public String authorize(@Param("uid") Integer uid, @Param("tid") Integer tid, @Param("state") Integer state) {
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
     * 撤销用户uid 标签tid的角色state
     * @param uid   用户id
     * @param tid   标签id
     * @param state 赋予的权限(目前都是0)
     * @return 返回code和message
     */
    @PutMapping("/sysadmin/users/deauth")
    public String deauthorize(@Param("uid") Integer uid, @Param("tid") Integer tid, @Param("state") Integer state) {
        BhacRole role = roleService.getRoleByTid(tid,state);
        if(role == null ) {
             return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_ROLE_NOT_FOUND));
        }
        if (userService.deleteRoleOfUser(role.getId(), uid)==-1) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.ERR_USER_ROLE_DELETED));
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(UserCode.SUCC_USER_DEAUTHORIZED));
    }
    
    /**
     * 登录user
     * @param user 用户对象
     * @return  返回code和message,如果成功登录则还返回token。
     */
    @PostMapping("/users/login")
    public String login(BhacUser user) {
        UserCode code = userService.login(user);
        JSONObject json = ControllerUtils.JsonCodeAndMessage(code);
        if (code == UserCode.SUCC_USER_LOGIN) {
            json.put("token", JWTUtils.createToken(user));
        }
        return json.toJSONString();
    }
    
    /**
     * 注册user
     * @param input 用户对象
     * @return 返回code 和 message
     */
    @PostMapping("/users")
    public String register(BhacUser input) {
        UserCode code = userService.register(input);
        JSONObject json = ControllerUtils.JsonCodeAndMessage(code);
        if (code == UserCode.SUCC_USER_REG) {
            json.put("token", JWTUtils.createToken(input));
        }
        return json.toJSONString();
    }
    
    /**
     * 查询当前用户对象
     * @param request
     * @return BhacUser对象
     */
    @GetMapping("/users/self")
    public String getCurUsers(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        return JSONObject.toJSONString(userService.getUserById((Integer) claims.get("uid")),
                ControllerUtils.filterFactory(BhacUser.class));
    }
    
    /**
     * 根据type更改信息当前用户信息;type==0是修改基本信息,type==1是修改密码
     * @param type
     * @param oldPassword
     * @param newPassword
     * @param modified
     * @param request
     * @return , 返回code 和 message
     */
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
