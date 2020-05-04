package cn.edu.buaa.se.bhac.Dao.mapper;

import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Dao.entity.BhacTag;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangqichang
 * @since 2020-04-30
 */
@Mapper
@Repository
public interface BhacRoleMapper extends BaseMapper<BhacRole> {

    
    //@Select("select * from bhac_user where id in (select uid from bhac_actUserRole where rid=#{id})")
    public List<BhacUser> selectActByRid (Integer id);
    
    //@Select("select * from bhac_tag where id = #{id}")
    public BhacTag selectTagByTid(Integer id);
}
