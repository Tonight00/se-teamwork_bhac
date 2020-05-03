package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BhacRoleService {
 
    @Autowired
    private BhacRoleMapper roleMapper;
    
    
    /**
     *
     * @param tid 标签id
     * @return 返回满足tagId = tid的角色中的一个
     */
    public BhacRole getRoleByTid(Integer tid) {
        QueryWrapper q = new QueryWrapper();
        q.eq("tid",tid);
        return  roleMapper.selectOne(q);
    }

}
