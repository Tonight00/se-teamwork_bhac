package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.Dao.entity.BhacComment;
import cn.edu.buaa.se.bhac.Dao.entity.BhacPost;
import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacActivityMapper;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacUserMapper;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.PostCode;
import cn.edu.buaa.se.bhac.services.BhacCommentService;
import cn.edu.buaa.se.bhac.services.BhacPostService;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BhacPostController {

    @Autowired
    private BhacPostService bhacPostService;

    @Autowired
    private BhacCommentService commentService;
    @Autowired
    private BhacActivityMapper activityMapper;
    @Autowired
    private BhacUserMapper userMapper;

    @GetMapping("/untoken/posts/getPostsByAid")
    public String getPostsByAid (Integer aid,Integer pageNum, Integer limit) {
        List<BhacPost> posts =  bhacPostService.GetPostsByAid(aid,pageNum,limit);
        if(posts == null) {
            posts = new ArrayList<>();
        }
        return JSONObject.toJSONString(posts);
    }



    @PostMapping("/posts")
    public String addPost(HttpServletRequest request, BhacPost post,String content,Integer rate) {
        Claims claims  =  (Claims) request.getAttribute("claims");
        post.setPostedBy((Integer)claims.get("uid"));
        Integer type = post.getType();
        String s = post.getTitle();
        if (type == 0 ) {
            post.setTitle("[评分"+rate+"/5] "+s);
        } else if(type == 2) {
            post.setTitle("[通知] "+s);
        }

        BhacActivity activity = activityMapper.selectById(post.getAid());
        LocalDateTime end =  activity.getEnd();
        LocalDateTime today = LocalDateTime.now();
        BhacUser u =  userMapper.selectById((Integer)claims.get("uid"));
        List<BhacActivity>activities =  u.getActivitiesSucceed();
        Boolean flag = false;
        for(BhacActivity act : activities) {
            if(act.getId().equals(activity.getId())) {
                flag = true;break;
            }
        }
        if(type == 0)
        {
            if (!flag || today.compareTo(end) < 0 )
            {
                return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(PostCode.ERR_POST_PUT));
            }
        }

        bhacPostService.addPost(post);
        Integer pid = bhacPostService.getId((Integer)claims.get("uid"));

        // 配置一楼
        BhacComment comment = new BhacComment();
        comment.setContent(content);
        comment.setPostedBy((Integer)claims.get("uid"));
        comment.setPid(pid);
        comment.setSeqNum(1);
        commentService.addComment(comment);

        //更新帖子的numOfComment

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
        return JSONObject.toJSONString(post);
    }
}
