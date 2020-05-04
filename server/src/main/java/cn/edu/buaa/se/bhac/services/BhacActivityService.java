package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Dao.entity.BhacTag;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacActivityMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
