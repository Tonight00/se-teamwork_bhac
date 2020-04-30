package Dao.mapper;

import Dao.entity.BhacRole;
import Dao.entity.BhacUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import javax.servlet.annotation.WebServlet;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangqichang
 * @since 2020-04-30
 */
public interface BhacRoleMapper extends BaseMapper<BhacRole> {

    
    @Select("select * from bhac_user where id in (select uid from bhac_actUserRole where rid=#{id})")
    public List<BhacUser> selectActByRid(Integer id);
    
}
