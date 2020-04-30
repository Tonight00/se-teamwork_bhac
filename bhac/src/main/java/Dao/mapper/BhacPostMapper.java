package Dao.mapper;

import Dao.entity.BhacComment;
import Dao.entity.BhacPost;
import com.baomidou.mybatisplus.annotation.TableField;
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
    public List<BhacComment> selectCommentByPid(Integer id);
    
    
}
