package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.*;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacActivityMapper;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacJoinuseractivityMapper;
import cn.edu.buaa.se.bhac.Utils.DaoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BhacActivityService {

    @Autowired
    private BhacActivityMapper activityMapper;
    @Autowired
    BhacJoinuseractivityMapper joinuseractivityMapper;

    /**
     * @param admin 用户
     * @return 该用户拥有所有权限的所有活动
     * @implNote 只返回拥有所有权限的活动（state = 0）
     */
    public List<BhacActivity> getAuthedActivities(BhacUser admin, @Param("page") Integer pageNum, Integer limit) {
        List<BhacRole> roles = admin.getRolesAct();
        if(roles.isEmpty()) {
            return null;
        }
        List<Integer> category = new ArrayList<>();
        for (BhacRole role : roles) {
            if (role.getState() == 0 && role.getTag().getState() == 0) {
                category.add(role.getTag().getId());
            }
        }
        if(category.isEmpty()) {
            return null;
        }
        QueryWrapper q = new QueryWrapper();

        q.in("category",category);
        Page<BhacActivity> page = new Page<>(pageNum,limit);
        return  DaoUtils.PageSearch(activityMapper,page,q);
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
        activityMapper.updateById(activity);
        return true;
    }
    
    /**
     *
     * @param title
     * @param tid
     * @return 返回表中字段title是(%title%),并且category = tid 的活动列表
     */
    public List<BhacActivity> getActivities (String title ,Integer tid,Integer pageNum,Integer limit) {
        
        QueryWrapper q = new QueryWrapper();
        q.like("title",title);
        q.eq("category",tid);
        
        Page<BhacActivity> page = new Page<BhacActivity>(pageNum,limit);
        return DaoUtils.PageSearch(activityMapper,page,q);
      
    }
    
    public  Integer enroll(Integer aid, Integer uid) {
        QueryWrapper q = new QueryWrapper();
        q.eq("aid",aid);
        q.eq("uid",uid);
        if(joinuseractivityMapper.selectCount(q) > 0 ) {
            return -1; // 已经加入
        }
        BhacJoinuseractivity join = new BhacJoinuseractivity();
        join.setAid(aid);
        join.setUid(uid);
        joinuseractivityMapper.insert(join);
        return 1;
    }
    
    public Integer unenroll(Integer aid,Integer uid) {
        QueryWrapper q = new QueryWrapper();
        q.eq("aid",aid);
        q.eq("uid",uid);
        if(joinuseractivityMapper.selectCount(q) == 0 ) {
            return -1; // 已经退出
        }
        joinuseractivityMapper.delete(q);
        return 1;
    }
}
