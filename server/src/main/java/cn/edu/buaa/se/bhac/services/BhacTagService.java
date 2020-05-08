package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.BhacTag;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacTagMapper;
import cn.edu.buaa.se.bhac.Utils.DaoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.imageio.plugins.common.I18N;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BhacTagService {
    
    @Autowired
    private BhacTagMapper tagMapper;
    
    /**
     * 根据标签名字name，进行模糊查询(%name%)，返回标签集合
     * @param name 标签名字
     * @return BhacTag对象集合
     */
    public List<BhacTag> getTagsByTagname(String name, Integer pageNum, Integer limit) {
        QueryWrapper q = new QueryWrapper();
        q.like("name",name);
        q.ne("state",-1);
        Page<BhacTag> page =  new Page<>(pageNum,limit,false);
        return DaoUtils.PageSearch(tagMapper,page,q);
    }
    
    /**
     * 删除标签id，软删除（state = -1),并判断是否重复删除(-1)
     * @param id 标签id
     * @return  1 或者 -1
     */
    public int delTag(Integer id) {
        QueryWrapper q = new QueryWrapper();
        q.eq("id",id);
        q.eq("state",-1);
        if(tagMapper.selectCount(q)> 0 )  {
            return -1;
        }
        BhacTag tag = new BhacTag();
        tag.setState(-1);
        tag.setId(id);
        tagMapper.updateById(tag);
        return 1;
    }
    
    /**
     * 返回所有tags的第pageNum页的tags
     * @param pageNum 第几页
     * @param limit 页容量
     * @return  BhacTag对象集合
     */
    public List<BhacTag> showTags(Integer pageNum,Integer limit) {
        Page<BhacTag> page = new Page<>(pageNum,limit);
        return DaoUtils.PageSearch(tagMapper,page,null);
    }
}
