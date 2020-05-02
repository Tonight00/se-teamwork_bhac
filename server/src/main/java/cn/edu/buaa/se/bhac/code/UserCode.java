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
    ERR_USER_VERI_FAILED,
    ERR_USER_NO_ADMIN,
    SUCC_USER_LOGIN,
    SUCC_USER_LOGOUT,
    SUCC_USER_EDIT,
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
            put(UserCode.SUCC_USER_EDIT, "编辑成功");
            put(UserCode.SUCC_USER_LOGIN, "登录成功");
            put(UserCode.SUCC_USER_LOGOUT, "登出成功");
            put(UserCode.SUCC_USER_REG, "注册成功");
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

