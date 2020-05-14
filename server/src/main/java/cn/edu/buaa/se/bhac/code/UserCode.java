package cn.edu.buaa.se.bhac.code;

import org.apache.catalina.User;

import java.util.HashMap;
import java.util.Map;

public enum UserCode implements BaseCode{
    ERR_USER_DUP_UNAME,
    ERR_USER_DUP_PN,
    ERR_USER_DUP_MAIL,
    ERR_USER_NO_UNAME,
    ERR_USER_NO_PN,
    ERR_USER_NO_MAIL,
    ERR_USER_NO_ACTIVITY,
    ERR_USER_ROLE_NOT_FOUND,
    ERR_USER_VERI_FAILED,
    ERR_USER_NO_ADMIN,
    ERR_USER_PARAM,
    SUCC_USER_UNJOINED,
    ERR_USER_NO_TOKEN,
    ERR_USER_ENROLLED,
    ERR_USER_UNENROLLED,
    ERR_USER_INVALID_TOKEN,
    ERR_USER_ROLE_OWNED,
    ERR_USER_ROLE_DELETED,
    ERR_USER_ENROLL_DDL,
    SUCC_USER_UNENROLL,
    SUCC_USER_LOGIN,
    SUCC_USER_LOGOUT,
    SUCC_USER_EDIT,
    SUCC_USER_AUTHORIZED,
    SUCC_USER_DEAUTHORIZED,
    SUCC_USER_ENROLL,
    SUCC_USER_JOINED,
    
    SUCC_USER_REG;

    private static Map<UserCode, String> message = new HashMap<UserCode, String>() {
        {
            put(UserCode.ERR_USER_DUP_UNAME, "该用户名已注册");
            put(UserCode.ERR_USER_DUP_MAIL, "该邮箱已注册");
            put(UserCode.ERR_USER_DUP_PN, "该手机号已注册");
            put(UserCode.ERR_USER_NO_UNAME, "该用户名不存在");
            put(UserCode.ERR_USER_NO_MAIL, "该邮箱不存在");
            put(UserCode.ERR_USER_NO_PN, "该手机号不存在");
            put(UserCode.ERR_USER_VERI_FAILED, "密码错误");
            put(UserCode.ERR_USER_NO_ADMIN, "该账号没有本系统权限");
            put(UserCode.SUCC_USER_EDIT, "修改成功");
            put(UserCode.ERR_USER_NO_ACTIVITY,"该账户没有可管理的活动");
            put(UserCode.SUCC_USER_LOGIN, "登录成功");
            put(UserCode.SUCC_USER_LOGOUT, "登出成功");
            put(UserCode.SUCC_USER_REG, "注册成功");
            put(UserCode.SUCC_USER_AUTHORIZED,"权限授予成功");
            put(UserCode.ERR_USER_ROLE_NOT_FOUND,"标签不含该权限");
            put(UserCode.SUCC_USER_DEAUTHORIZED,"权限撤销成功");
            put(UserCode.ERR_USER_PARAM, "参数错误");
            put(UserCode.ERR_USER_NO_TOKEN, "尚未登陆");
            put(UserCode.ERR_USER_INVALID_TOKEN, "登录失效");
            put(UserCode.SUCC_USER_ENROLL,"成功申请加入活动");
            put(UserCode.SUCC_USER_UNENROLL,"成功取消申请");
            put(UserCode.ERR_USER_ROLE_OWNED,"不能重复授权");
            put(UserCode.ERR_USER_ROLE_DELETED,"不能重复撤销授权");
            put(UserCode.ERR_USER_ENROLLED,"不能重复加入活动");
            put(UserCode.ERR_USER_UNENROLLED,"不能重复退出活动");
            put(UserCode.SUCC_USER_JOINED,"成功加入活动");
            put(UserCode.SUCC_USER_UNJOINED,"成功退出活动");
            put(UserCode.ERR_USER_ENROLL_DDL,"活动已截止");
        }
    };

    @Override
    public String getMessage() {
        return message.get(this);
    }

    @Override
    public boolean isSuccessful() {
        return this.toString().startsWith("SUCC");
    }

}

