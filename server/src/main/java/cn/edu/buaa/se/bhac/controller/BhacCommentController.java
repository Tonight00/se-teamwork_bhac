package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacComment;
import cn.edu.buaa.se.bhac.Dao.entity.BhacPost;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.CommentCode;
import cn.edu.buaa.se.bhac.comparators.CommentsComp;
import cn.edu.buaa.se.bhac.services.BhacCommentService;
import cn.edu.buaa.se.bhac.services.BhacPostService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
public class BhacCommentController {
    
    @Autowired
    private BhacCommentService commentService;
    @Autowired
    private BhacPostService postService;
    
    
    @GetMapping("/untoken/comments/{id}")
    public String GetComment(@PathVariable("id") Integer id) {
        BhacComment comment = commentService.getComment(id);
        if (comment == null ){
            comment = new BhacComment();
        }
        return JSONObject.toJSONString(comment);
    }
    
    @GetMapping("untoken/commentsInPost")
    public String getCommentsInPost(Integer pid, Integer pageNum , Integer limit) {
        List<BhacComment> comments =  commentService.getComments(pid,pageNum,limit);
      //  Collections.sort(comments,new CommentsComp());
        return JSONObject.toJSONString(comments);
    }
    
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @GetMapping("/comments")
    public String addComment(HttpServletRequest request,BhacComment comment ) {
        Claims claims  =  (Claims) request.getAttribute("claims");
        Integer uid = (Integer) claims.get("uid");
        comment.setPostedBy(uid);
        BhacPost post = postService.getPost(comment.getPid());
        Integer num = post.getNumOfComment()+1;
        comment.setSeqNum(num);
        post.setNumOfComment(num);
        return JSONObject.toJSONString(CommentCode.SUCC_COMMENT_ADDED);
    }
    
    
}
