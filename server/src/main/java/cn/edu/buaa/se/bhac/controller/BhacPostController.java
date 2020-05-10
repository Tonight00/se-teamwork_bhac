package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacPost;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.PostCode;
import cn.edu.buaa.se.bhac.code.TagCode;
import cn.edu.buaa.se.bhac.services.BhacPostService;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BhacPostController {

    @Autowired
    private BhacPostService bhacPostService;
    
    
    
    @GetMapping("/untoken/posts/getPostsByAid")
    public String getPostsByAid (Integer aid,Integer pageNum, Integer limit) {
        List<BhacPost> posts =  bhacPostService.GetPostsByAid(aid,pageNum,limit);
        if(posts == null) {
            posts = new ArrayList<>();
        }
        return JSONObject.toJSONString(posts, ControllerUtils.filterFactory(BhacPost.class));
    }
    
    
    
    @PostMapping("/posts")
    public String addPost(HttpServletRequest request, BhacPost post) {
        Claims claims  =  (Claims) request.getAttribute("claims");
        post.setPostedBy((Integer)claims.get("uid"));
        bhacPostService.addPost(post);
        if(post.getType()== 0) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(PostCode.SUCC_POST_ADD_TYPE0));
        }
        else if(post.getType() == 1) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(PostCode.SUCC_POST_ADD_TYPE1));
        }
        else if(post.getType() == 2) {
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(PostCode.SUCC_POST_ADD_TYPE2));
        }
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(PostCode.ERR_POST_ADD));
    }
    
    @GetMapping("/untoken/posts/{id}")
    public String getPost(@PathVariable("id")Integer id) {
        
        BhacPost post = bhacPostService.getPost(id);
        if(post==null) post = new BhacPost();
        return JSONObject.toJSONString(post,ControllerUtils.filterFactory(BhacPost.class));
    }
}
