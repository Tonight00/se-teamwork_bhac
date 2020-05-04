package cn.edu.buaa.se.bhac.code;

import java.util.HashMap;
import java.util.Map;

public enum UserCode implements BaseCode{
    ERR_USER_DUP_UNAME,
    ERR_USER_DUP_PN,
    ERR_USER_DUP_MAIL,
    ERR_USER_NO_UNAME,
    ERR_USER_NO_PN,
    ERR_USER_NO_MAIL,
    ERR_USER_ROLE_NOT_FOUND,
    ERR_USER_VERI_FAILED,
    ERR_USER_NO_ADMIN,
    ERR_USER_INNER_ERROR,
    ERR_USER_PARAM,
    ERR_USER_NO_TOKEN,
    ERR_USER_INVALID_TOKEN,
    SUCC_USER_LOGIN,
    SUCC_USER_LOGOUT,
    SUCC_USER_EDIT,
    SUCC_USER_AUTHORIZED,
    SUCC_USER_DEAUTHORIZED,
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
            put(UserCode.SUCC_USER_LOGIN, "登录成功");
            put(UserCode.SUCC_USER_LOGOUT, "登出成功");
            put(UserCode.SUCC_USER_REG, "注册成功");
            put(UserCode.SUCC_USER_AUTHORIZED,"权限授予成功");
            put(UserCode.ERR_USER_ROLE_NOT_FOUND,"角色权限不存在");
            put(UserCode.SUCC_USER_DEAUTHORIZED,"权限撤销成功");
            put(UserCode.ERR_USER_INNER_ERROR,"服务器内部错误");
            put(UserCode.ERR_USER_PARAM, "参数错误");
            put(UserCode.ERR_USER_NO_TOKEN, "尚未登陆");
            put(UserCode.ERR_USER_INVALID_TOKEN, "登录失效");
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

