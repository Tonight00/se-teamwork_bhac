package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Dao.entity.BhacTag;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacActivityMapper;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacUserMapper;
import cn.edu.buaa.se.bhac.code.UserCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BhacUserService {

    @Autowired
    private BhacUserMapper userMapper;
    @Autowired
    private BhacActivityMapper activityMapper;

    /**
     * @param account 需要验证的账号
     * @return 如果是活动管理员则返回true，否则为false
     */
    public static boolean checkAdmin(BhacUser account) {
        return !account.getRolesAct().isEmpty();
    }

    /**
     * @param account 需要验证的账号
     * @return 如果是系统管理员则返回true，否则为false
     */
    public static boolean checkSysAdmin(BhacUser account) {
        return account.getState() == 1;
    }

    /**
     * 验证登录并返回结果编号
     *
     * @param credential 用户输入的用户名和密码
     * @param session    会话域
     * @return 结果编号
     */
    public UserCode adminLogin(BhacUser credential, HttpSession session) {
        QueryWrapper<BhacUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", credential.getUsername());
        BhacUser admin = userMapper.selectOne(wrapper);
        if (admin == null) {
            return UserCode.ERR_USER_NO_UNAME;
        } else if (!admin.getPassword().equals(credential.getPassword())) {
            return UserCode.ERR_USER_VERI_FAILED;
        }
        if (!checkAdmin(admin) && !checkSysAdmin(admin)) {
            return UserCode.ERR_USER_NO_ADMIN;
        }
        session.setAttribute("admin", admin);
        return UserCode.SUCC_USER_LOGIN;
    }

    /**
     * 把登录状态取消
     *
     * @param session 会话域
     * @return 结果编号
     */
    public UserCode adminLogout(HttpSession session) {
        session.removeAttribute("admin");
        return UserCode.SUCC_USER_LOGOUT;
    }


    /**
     * @param admin 用户
     * @return 该用户拥有所有权限的所有活动
     * @implNote 只返回拥有所有权限的活动（state = 0）
     */
    public List<BhacActivity> getAuthedActivities(BhacUser admin) {
        List<BhacActivity> activities = new ArrayList<>();
        for (BhacRole role : admin.getRolesAct()) {
            BhacTag tag = role.getTag();
            if (role.getState() == 0 && role.getTag().getState() == 0) {
                activities.addAll(role.getTag().getActivities());
            }
        }
        return activities;
    }

    public UserCode login(BhacUser credential) {
        QueryWrapper<BhacUser> wrapper = new QueryWrapper<>();
        BhacUser user = null;
        UserCode code = UserCode.SUCC_USER_LOGIN;
        if (credential.getUsername() != null) {
            wrapper.eq("username", credential.getUsername());
            user = userMapper.selectOne(wrapper);
            if (user == null) {
                code = UserCode.ERR_USER_NO_UNAME;
                return code;
            }
        } else if (credential.getPhoneNum() != null) {
            wrapper.eq("phoneNum", credential.getPhoneNum());
            user = userMapper.selectOne(wrapper);
            if (user == null) {
                code = UserCode.ERR_USER_NO_PN;
                return code;
            }
        } else if (credential.getEmail() != null) {
            wrapper.eq("email", credential.getEmail());
            user = userMapper.selectOne(wrapper);
            if (user == null) {
                code = UserCode.ERR_USER_NO_MAIL;
                return code;
            }
        }
        if (!user.getPassword().equals(credential.getPassword())) {
            code = UserCode.ERR_USER_VERI_FAILED;
        }
        else {
            credential.setId(user.getId());
        }
        return code;
    }

    public UserCode register(BhacUser input) {
        if (!userMapper.selectList(new QueryWrapper<BhacUser>()
                .eq("username", input.getUsername())).isEmpty()) {
            return UserCode.ERR_USER_DUP_UNAME;
        }
        if (input.getPhoneNum() != null) {
            if (!userMapper.selectList(new QueryWrapper<BhacUser>()
                    .eq("phoneNum", input.getPhoneNum())).isEmpty()) {
                return UserCode.ERR_USER_DUP_PN;
            }
        }
        if (input.getEmail() != null) {
            if (!userMapper.selectList(new QueryWrapper<BhacUser>()
                    .eq("email", input.getEmail())).isEmpty()) {
                return UserCode.ERR_USER_DUP_MAIL;
            }
        }
        userMapper.insert(input);
        return UserCode.SUCC_USER_REG;
    }
}
