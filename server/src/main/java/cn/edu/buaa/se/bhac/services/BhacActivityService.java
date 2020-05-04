package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Dao.entity.BhacTag;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacActivityMapper;
import cn.edu.buaa.se.bhac.Utils.DaoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BhacActivityService {

    @Autowired
    private BhacActivityMapper activityMapper;

    /**
     * @param admin 用户
     * @return 该用户拥有所有权限的所有活动
     * @implNote 只返回拥有所有权限的活动（state = 0）
     */
    public List<BhacActivity> getAuthedActivities(BhacUser admin,Integer pageNum, Integer limit) {
        List<BhacRole> roles = admin.getRolesAct();
        List<Integer> category = new ArrayList<>();
        for (BhacRole role : roles) {
            if (role.getState() == 0 && role.getTag().getState() == 0) {
                category.add(role.getTag().getId());
            }
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
        return activityMapper.updateById(activity) == 1;
    }
}
