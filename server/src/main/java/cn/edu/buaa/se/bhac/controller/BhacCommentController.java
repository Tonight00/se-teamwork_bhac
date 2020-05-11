package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacComment;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.services.BhacCommentService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BhacCommentController {
    
    @Autowired
    private BhacCommentService commentService;
    
    @GetMapping("/untoken/comments/{id}")
    public String GetComment(@PathVariable("id") Integer id) {
        BhacComment comment = commentService.getComment(id);
        if (comment == null ){
            comment = new BhacComment();
        }
        return JSONObject.toJSONString(comment);
    }
    
    
    
}
