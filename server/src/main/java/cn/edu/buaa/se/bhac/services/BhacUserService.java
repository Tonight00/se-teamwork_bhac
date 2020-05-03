package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.*;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacActivityMapper;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacActuserroleMapper;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacUserMapper;
import cn.edu.buaa.se.bhac.Utils.DaoUtils;
import cn.edu.buaa.se.bhac.code.UserCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.catalina.User;
import org.omg.PortableInterceptor.INACTIVE;
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
    @Autowired
    private BhacActuserroleMapper actuserroleMapper;

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
    
    /**
     * @param id 活动id
     * @return 该id 对应的活动
     */
    public BhacActivity getActivity(Integer id) {
        return activityMapper.selectById(id);
    }
    
    /**
     *
     * @param id 被审核活动id
     * @param state 活动状态
     * @return 是否成功更改活动状态,通过判断更新条数是否为1条,来判断是否成功更改活动状态
     */
    public Boolean permitActivity(Integer id,Integer state) {
        BhacActivity activity = new BhacActivity();
        activity.setId(id);
        activity.setState(state);
        return activityMapper.updateById(activity) == 1;
    }
    
    /**
     *
     * @param name 用户名
     * @return 通过模糊查询(%name%)来返回满足条件的用户
     */
    public List<BhacUser> getUsersByUsername(String name, Integer pageNum , Integer limit) {
        QueryWrapper q = new QueryWrapper();
        q.like("username",name);
    
        Page<BhacUser> page = new Page<>(pageNum,limit,false);
        return DaoUtils.PageSearch(userMapper,page,q);
    }
    
    /**
     *
     * @param rid 角色id
     * @param uid 用户id
     * @return 将角色rid加入到用户uid的角色集合中,通过判断更新条数是否为1,来判断是否正常修改
     */
    
    public Boolean addRole2User(Integer rid, Integer uid) {
        BhacActuserrole actuserrole  = new BhacActuserrole();
        actuserrole.setRid(rid);
        actuserrole.setUid(uid);
        return actuserroleMapper.insert(actuserrole) == 1;
    }
    
    /**
     *
     * @param rid 角色id
     * @param uid 用户id
     * @return 将角色rid从用户id的角色集中移除,通过判断更新条数是否为1,来判断是否正常修改
     */
    public Boolean deleteRoleOfUser(Integer rid, Integer uid) {
        QueryWrapper q = new QueryWrapper();
        q.eq("uid",uid);
        q.eq("rid",rid);
        return actuserroleMapper.delete(q) == 1;
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
