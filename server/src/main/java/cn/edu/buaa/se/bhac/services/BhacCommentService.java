package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.BhacComment;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacCommentMapper;
import cn.edu.buaa.se.bhac.Utils.DaoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BhacCommentService {
    
    @Autowired
    private BhacCommentMapper commentMapper;
    
    public BhacComment getComment (Integer id)
    {
        return commentMapper.selectById(id);
    }
    
    public void addComment (BhacComment comment)
    {
        commentMapper.insert(comment);
    }
    
    public List<BhacComment> getComments (Integer pid,Integer pageNum, Integer limit)
    {
        QueryWrapper q = new QueryWrapper();
        q.eq("pid",pid);
        Page<BhacComment> p = new Page<>(pageNum,limit);
        return DaoUtils.PageSearch(commentMapper,p,q);
    }
}
