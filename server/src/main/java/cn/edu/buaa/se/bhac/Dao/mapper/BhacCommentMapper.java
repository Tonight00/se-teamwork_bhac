package cn.edu.buaa.se.bhac.Dao.mapper;

import cn.edu.buaa.se.bhac.Dao.entity.BhacComment;
import cn.edu.buaa.se.bhac.Dao.entity.BhacPost;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangqichang
 * @since 2020-04-30
 */
public interface BhacCommentMapper extends BaseMapper<BhacComment> {
    
    
   // @Select("select * from bhac_post where id = #{id}")
    public BhacPost selectPostByPid(Integer id);
    
    //@Select("select * from bhac_user where id = #{id}")
    public BhacUser selectPosterByPostedBy(Integer id);
}
