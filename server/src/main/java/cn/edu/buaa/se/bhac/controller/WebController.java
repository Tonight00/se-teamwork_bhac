package cn.edu.buaa.se.bhac.controller;

import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.UserCode;
import cn.edu.buaa.se.bhac.services.BhacUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class WebController {

    @Autowired
    BhacUserService userService;

    @PostMapping("/u/login")
    public String validate(BhacUser user, Model model, HttpSession session) {
        UserCode code = userService.adminLogin(user, session);
        ControllerUtils.putCodeAndMessage(code, model);
        if (!code.isSuccessful()) {
            return "redirect:/login";
        }
        return "helloworld";
    }

    @GetMapping("/u/logout")
    public String logout(HttpSession session, Model model) {
        UserCode code = userService.adminLogout(session);
        ControllerUtils.putCodeAndMessage(code, model);
        return "redirect:/login";
    }

}
