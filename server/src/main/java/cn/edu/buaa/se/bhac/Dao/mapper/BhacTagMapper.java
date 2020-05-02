package cn.edu.buaa.se.bhac.Dao.mapper;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacPost;
import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Dao.entity.BhacTag;
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
public interface BhacTagMapper extends BaseMapper<BhacTag> {
 
    //@Select("select * from bhac_post where tid = #{id}")
    public List<BhacPost> selectPostByTid (Integer id);
    //@Select("select * from bhac_role where tid = #{id}")
    public List<BhacRole> selectRolesByTid (Integer id);
    //@Select("select * from bhac_activity where id in (select aid from bhac_belongActivityTag where tid = #{id})")
    public List<BhacActivity> selectBelongByTid (Integer id);
}
