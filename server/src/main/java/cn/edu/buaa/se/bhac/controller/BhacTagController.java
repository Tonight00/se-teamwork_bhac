package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Dao.entity.BhacTag;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacRoleMapper;
import cn.edu.buaa.se.bhac.Dao.mapper.BhacTagMapper;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.TagCode;
import cn.edu.buaa.se.bhac.services.BhacTagService;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BhacTagController {
    @Autowired
    private BhacTagMapper bhacTagMapper;
    @Autowired
    private BhacRoleMapper bhacRoleMapper;

    @Autowired
    private BhacTagService tagService;

    /**
     * @param input 用户输入的标签属性
     * @return 添加该标签，返回code和message
     * @implNote 添加标签的同时要添加对应的role(State = 0)，建议这里使用事务
     */
    @PostMapping("/sysadmin/tags")
    @Transactional(rollbackFor = Exception.class)
    public String addTag(BhacTag input) {
        bhacTagMapper.insert(input);
        BhacRole role = new BhacRole();
        role.setTid(input.getId());
        role.setState(0);
        bhacRoleMapper.insert(role);
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(TagCode.SUCC_TAG_NAME_EXISTED));
    }

    /**
     * @param id 要删除的标签的id
     * @return 删除该标签，返回code和message
     * @implNote 软删除，把标签的state置为-1即可，不需要真正从数据库中删除
     */
    @DeleteMapping("/sysadmin/tag/{id}")
    public String delTag(@PathVariable("id") Integer id) {
        System.out.println(id);
        if (!tagService.delTag(id))
            return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(TagCode.ERR_TAG_INNER_ERROR));
        return JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(TagCode.SUCC_TAG_DELETED));
    }

    /**
     * @param name 输入的标签名
     * @return 根据name模糊查询(% x %)查出对应的标签，以Json格式返回
     * @implNote 返回JSON格式的做法请参考getAuthedActivities方法
     */
    @GetMapping("/sysadmin/tags")
    public String getTagsByName(@Param("name") String name, @Param("page") Integer page , @Param("limit")Integer limit) {
        Integer pageNum = page;
        List<BhacTag> tags = tagService.getTagsByTagname(name,pageNum,limit);
        if(tags == null ) {
            tags = new ArrayList<>();
            // return  JSONObject.toJSONString(ControllerUtils.JsonCodeAndMessage(TagCode.ERR_TAG_NO_NAME));
        }
        return JSONObject.toJSONString(tags,
                /*exist=false属性的filter，不打印这部分属性*/ControllerUtils.filterFactory(BhacTag.class));
    }
}
