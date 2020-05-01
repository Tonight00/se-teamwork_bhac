package cn.edu.buaa.se.bhac.Dao.mapper;

import cn.edu.buaa.se.bhac.Dao.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangqichang
 * @since 2020-04-30
 */
public interface BhacPostMapper extends BaseMapper<BhacPost> {
    
    @Select("select * from bhac_comment where pid = #{id}")
    public List<BhacComment> selectCommentByPid (Integer id);
    
    @Select("select * from bhac_tag where id = #{id}")
    public BhacTag selectTagByTid(Integer id);
    
    @Select("select * from bhac_activity where id =#{id}")
    public BhacActivity selectActivityByAid(Integer id);
    
    @Select("select * from bhac_user where id = #{id}")
    public BhacUser selectPosterByPostedBy(Integer id);
    
}
