package cn.edu.buaa.se.bhac.services;

import cn.edu.buaa.se.bhac.Dao.entity.BhacPost;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacPostMapper;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.Utils.DaoUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class BhacPostService {
    
    @Autowired
    private BhacPostMapper bhacPostMapper;
    
    
    public List<BhacPost> GetPostsByAid (Integer aid ,Integer pageNum, Integer limit){
        QueryWrapper q = new QueryWrapper();
        q.eq("aid",aid);
        Page<BhacPost> page = new Page<>(pageNum,limit);
        return DaoUtils.PageSearch(bhacPostMapper,page,q);
        
    }
    
    public boolean addPost (BhacPost post)
    {
        bhacPostMapper.insert(post);
        return true;
    }
    
    public BhacPost getPost (Integer id)
    {
        return bhacPostMapper.selectById(id);
    }
    
    public Integer getId (Integer uid)
    {
        QueryWrapper q = new QueryWrapper();
        q.select("max(id) max_id").eq("postedBy",uid);
        List<Integer> ids = bhacPostMapper.selectObjs(q);
        return ids.get(0);
    }
    
    public void updateById (BhacPost post)
    {
        bhacPostMapper.updateById(post);
    }
}
