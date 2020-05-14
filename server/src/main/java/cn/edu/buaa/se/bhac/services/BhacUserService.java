package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActuserrole;
import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacActivityMapper;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacActuserroleMapper;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacUserMapper;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.UserCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

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
        for (BhacRole role : account.getRolesAct()) {
            if (role.getTag().getState()==0) {
                return true;
            }
        }
        return false;
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
     * 通过模糊查询(% name %)来返回满足条件的用户
     * @param name 用户名
     * @return BhacUser对象集合
     */
    public String getUsersByUsername(String name, Integer pageNum, Integer limit) {
        QueryWrapper q = new QueryWrapper();
        q.like("username", name);
        Page<BhacUser> page = new Page<>(pageNum, limit);
        IPage<BhacUser> iPage = userMapper.selectPage(page,q);
        return ControllerUtils.putCountAndData(iPage,BhacUser.class);
    }

    /**
     *  将角色rid加入到用户uid的角色集合中, 并判断是否重复添加(-1)
     * @param rid 角色id
     * @param uid 用户id
     * @return 1 或者 -1
     */

    public int addRole2User(Integer rid, Integer uid) {
        BhacActuserrole actuserrole = new BhacActuserrole();
        actuserrole.setRid(rid);
        actuserrole.setUid(uid);
        QueryWrapper q = new QueryWrapper();
        q.eq("rid",rid);
        q.eq("uid",uid);
        if(actuserroleMapper.selectCount(q) > 0) {
            return -1 ; // 已经存在
        }
        actuserroleMapper.insert(actuserrole);
        return 1;
    }

    /**
     *  将角色rid从用户id的角色集中移除, 并判断是否重复移除(-1)
     * @param rid 角色id
     * @param uid 用户id
     * @return 1 或者 -1
     */
    public int deleteRoleOfUser(Integer rid, Integer uid) {
        QueryWrapper q = new QueryWrapper();
        q.eq("uid", uid);
        q.eq("rid", rid);
        if(actuserroleMapper.selectCount(q) == 0) {
            return  -1;
        }
        actuserroleMapper.delete(q);
        return 1;
    }
    
    /**
     * 登录
     * @param credential 用户对象
     * @return 根据用户对象返回相应的登录状态码
     */
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
        } else {
            credential.setId(user.getId());
        }
        return code;
    }
    
    /**
     * 判断对象是否重复，是否需要注册。
     * @param input 用户对象
     * @return 状态码
     */
    public UserCode register(BhacUser input) {
        UserCode code = checkDuplicate(input);
        if (code != null) {
            return code;
        }
        userMapper.insert(input);
        return UserCode.SUCC_USER_REG;
    }
    
    /**
     * 根据用户id返回用户
     * @param uid 用户id
     * @return BhacUser对象
     */
    public BhacUser getUserById(Integer uid) {
        return userMapper.selectById(uid);
    }
    
    /**
     * 用modified对象去更新之前的old对象,当old.id == modified.id
     * @param modified 用户对象
     * @return 状态码
     */
    public UserCode editBasic(BhacUser modified) {
        UserCode code = checkDuplicate(modified);
        if (code != null) {
            return code;
        }
        userMapper.updateById(modified);
        return UserCode.SUCC_USER_EDIT;
    }
    
    /**
     * 用户查重
     * @param input 用户对象
     * @return 状态码
     */
    private UserCode checkDuplicate(BhacUser input) {
        UserCode code = null;
        if (!userMapper.selectList(new QueryWrapper<BhacUser>()
                .eq("username", input.getUsername())).isEmpty()) {
            code = UserCode.ERR_USER_DUP_UNAME;
        }
        if (input.getPhoneNum() != null && !input.getPhoneNum().isEmpty()) {
            if (!userMapper.selectList(new QueryWrapper<BhacUser>()
                    .eq("phoneNum", input.getPhoneNum())).isEmpty()) {
                code = UserCode.ERR_USER_DUP_PN;
            }
        }
        if (input.getEmail() != null && !input.getEmail().isEmpty()) {
            if (!userMapper.selectList(new QueryWrapper<BhacUser>()
                    .eq("email", input.getEmail())).isEmpty()) {
                code = UserCode.ERR_USER_DUP_MAIL;
            }
        }
        return code;
    }
    
    /**
     * 当uid对应的user.password ==oldPassword时,令user.password = newPassword
     * @param oldPassword
     * @param newPassword
     * @param uid
     * @return 状态码
     */
    public UserCode editPWD(String oldPassword, String newPassword, Integer uid) {
        BhacUser user = userMapper.selectById(uid);
        if (!user.getPassword().equals(oldPassword)) {
            return UserCode.ERR_USER_VERI_FAILED;
        }
        BhacUser modified = new BhacUser();
        modified.setId(uid);
        modified.setPassword(newPassword);
        userMapper.updateById(modified);
        return UserCode.SUCC_USER_EDIT;
    }
    
    public int getUsersCount ()
    {
        return userMapper.selectCount(null);
    }
}
