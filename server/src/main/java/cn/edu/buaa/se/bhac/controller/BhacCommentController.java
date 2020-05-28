package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacComment;
import cn.edu.buaa.se.bhac.Dao.entity.BhacPost;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.CommentCode;
import cn.edu.buaa.se.bhac.comparators.CommentsComp;
import cn.edu.buaa.se.bhac.services.BhacCommentService;
import cn.edu.buaa.se.bhac.services.BhacPostService;
import cn.edu.buaa.se.bhac.services.BhacUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Autowired
    private BhacUserService userService;
    
    
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
    @PostMapping("/comments")
    public String addComment(HttpServletRequest request,BhacComment comment ) {
        Claims claims  =  (Claims) request.getAttribute("claims");
        
        BhacPost post = postService.getPost(comment.getPid());
        BhacUser user = userService.getUserById((Integer)claims.get("uid"));
        if (post.getType() == 0 || post.getType() == 2) {
            if(!BhacUserService.checkAdmin(user) && ! BhacUserService.checkSysAdmin(user) &&  user.getId() != post.getPostedBy()) {
                return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(CommentCode.ERR_COMMENT_PERMIT));
            }
        }
        Integer uid = (Integer) claims.get("uid");
        comment.setPostedBy(uid);
        Integer num = post.getNumOfComment()+1;
        comment.setSeqNum(num);
        post.setNumOfComment(num);
        commentService.addComment(comment);
        postService.updateById(post);
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(CommentCode.SUCC_COMMENT_ADDED));
    }
    
    
}
