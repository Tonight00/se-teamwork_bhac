package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacRole;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.services.BhacRoleService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BhacRoleController {
    
    @Autowired
    private BhacRoleService roleService;
    
    @GetMapping("/untoken/roles/{id}")
    public String getRole(@PathVariable("id") Integer id) {
        BhacRole role = roleService.getRole(id);
        if (role == null) {
            role = new BhacRole();
        }
        return JSONObject.toJSONString(role);
    }
    
    
}
