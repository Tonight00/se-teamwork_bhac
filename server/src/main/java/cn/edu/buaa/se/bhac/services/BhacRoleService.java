package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BhacRoleService {
 
    @Autowired
    private BhacRoleMapper roleMapper;
    
    
    /**
     * 返回满足tagId = tid的角色中的一个
     * @param state 权限级别
     * @param tid 标签id
     * @return BhacRole对象
     */
    public BhacRole getRoleByTid(Integer tid,Integer state) {
        QueryWrapper q = new QueryWrapper();
        q.eq("tid",tid);
        q.eq("state",state);
        List<BhacRole> roles = roleMapper.selectList(q);
        if(roles == null || roles.size() == 0) return null;
        return  roles.get(0);
    }

}
