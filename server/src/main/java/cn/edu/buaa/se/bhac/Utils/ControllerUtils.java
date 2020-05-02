package cn.edu.buaa.se.bhac.Utils;

import cn.edu.buaa.se.bhac.code.BaseCode;
import org.springframework.ui.Model;

public class ControllerUtils {

    public static void putCodeAndMessage(BaseCode code, Model model) {
        model.addAttribute("code", code.toString());
        model.addAttribute("message", code.getMessage());
    }

}
