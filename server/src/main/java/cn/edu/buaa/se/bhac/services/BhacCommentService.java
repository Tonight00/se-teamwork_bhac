package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.BhacComment;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
