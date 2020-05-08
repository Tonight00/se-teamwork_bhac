package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.*;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacActivityMapper;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacJoinuseractivityMapper;
import cn.edu.buaa.se.bhac.Utils.DaoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    private BhacJoinuseractivityMapper joinuseractivityMapper;

    /**
     * 该用户拥有所有权限的所有活动
     * @param admin 用户
     * @return BhacActivity对象集合
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
     *  查询id 对应的活动
     * @param id 活动id
     * @return BhacActivity对象
     */
    public BhacActivity getActivity(Integer id) {
        return activityMapper.selectById(id);
    }

    /**
     * 更改活动审核状态state
     * @param id 被审核活动id
     * @param state 活动状态
     * @return true
     */
    public Boolean permitActivity(Integer id,Integer state) {
        BhacActivity activity = new BhacActivity();
        activity.setId(id);
        activity.setState(state);
        activityMapper.updateById(activity);
        return true;
    }
    
    /**
     * 返回表中字段title是(%title%),并且category = tid 的活动列表
     * @param title
     * @param tid tid = 0,则返回所有activities
     * @return  BhacActivity对象集合
     */
    public List<BhacActivity> getActivities (String title ,Integer tid,Integer pageNum,Integer limit) {
        
        QueryWrapper q = new QueryWrapper();
        if(title != null && tid!=null && tid!=0)
            q.like("title",title);
        if(tid != null && tid !=0)
            q.eq("category",tid);
        
        Page<BhacActivity> page = new Page<BhacActivity>(pageNum,limit);
        return DaoUtils.PageSearch(activityMapper,page,q);
      
    }
    
    /**
     * 让用户uid加入活动aid,并判断是否重复退出(-1)
     * @param aid
     * @param uid
     * @return 1 或者 -1
     */
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
    
    /**
     * 让用户uid退出活动aid,并判断是否重复退出(-1)
     * @param aid
     * @param uid
     * @return 1 或者 -1
     */
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
    
    /**
     * 查看用户id加入活动aid的信息。
     * @param aid
     * @param id
     * @return
     */
    public Object getJoinUserActivity(Integer aid,Integer id) {
        QueryWrapper q = new QueryWrapper();
        q.eq("aid",aid );
        q.eq("uid",id);
        if(joinuseractivityMapper.selectCount(q) == 0) {
            return "null";
        }
        else{
            return joinuseractivityMapper.selectOne(q);
        }
    }
    
    /**
     * org: 添加活动activity
     * @param activity
     */
    public void addActivity (BhacActivity activity)
    {
        activityMapper.insert(activity);
    }
}
