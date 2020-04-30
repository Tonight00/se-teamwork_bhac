package Dao.mapper;

import Dao.entity.BhacActivity;
import Dao.entity.BhacRole;
import Dao.entity.BhacUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangqichang
 * @since 2020-04-30
 */
public interface BhacUserMapper extends BaseMapper<BhacUser> {
    
    @Select("select * from bhac_activity where uid = #{id}")
    List<BhacActivity> selectReleaseByUid (Integer id);
    
    @Select("select * from bhac_activity where id in (select aid from bhac_joinUserActivity where uid=#{id} and state=0 )")
    List<BhacActivity> selectProcessingByUid (Integer id);
    
    @Select("select * from bhac_activity where id in (select aid from bhac_joinUserActivity where uid=#{id} and state=1)")
    List<BhacActivity> selectSucceedByUid (Integer id);
    
    @Select("select * from bhac_activity where id in (select aid from bhac_manageUserActivity where uid=#{id})")
    List<BhacActivity> selectManageByUid (Integer id);
    
    @Select("select * from bhac_role where id in(select rid from bhac_actUserRole where uid = #{id} )")
    List<BhacRole> selectRoleByUid(Integer id);
    
}
