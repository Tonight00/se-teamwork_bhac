package Dao.mapper;

import Dao.entity.BhacActivity;
import Dao.entity.BhacPost;
import Dao.entity.BhacTag;
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
public interface BhacActivityMapper extends BaseMapper<BhacActivity> {
    
    
    
    @Select("select * from bhac_user where id in (select uid from bhac_joinUserActivity where aid = #{id} and state = 0)")
    public List<BhacUser> selectProcessingByAid(Integer id);
    
    @Select("select * from bhac_user where id in (select uid from bhac_joinUserActivity where aid = #{id} and state = 1)")
    public List<BhacUser> selectSucceedByAid(Integer id);
    
    
    @Select("select * from bhac_user where id in (select uid from bhac_manageUserActivity where aid = #{id})")
    public List<BhacUser> selectManageByAid(Integer id);
    
    @Select("select * from bhac_tag where id in (select tid from bhac_belongActivityTag where aid = #{id})")
    public List<BhacTag> selectBelongByAid(Integer id);
    
    @Select("select * from bhac_post where aid = #{id}")
    public List<BhacPost> selectPostByAid(Integer id);
}
