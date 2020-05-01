package cn.edu.buaa.se.bhac.Dao.mapper;

import cn.edu.buaa.se.bhac.Dao.entity.BhacComment;
import cn.edu.buaa.se.bhac.Dao.entity.BhacPost;
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
public interface BhacPostMapper extends BaseMapper<BhacPost> {
    
    @Select("select * from bhac_comment where pid = #{id}")
    public List<BhacComment> selectCommentByPid (Integer id);
    
    
}
