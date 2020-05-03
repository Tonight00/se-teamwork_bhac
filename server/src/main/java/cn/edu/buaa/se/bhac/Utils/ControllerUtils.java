package cn.edu.buaa.se.bhac.Utils;

import cn.edu.buaa.se.bhac.code.BaseCode;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.ui.Model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ControllerUtils {

    public static void putCodeAndMessage(BaseCode code, Model model) {
        model.addAttribute("code", code.toString());
        model.addAttribute("message", code.getMessage());
    }

    // 不打印那些TableField注解exist为false的域
    public static PropertyFilter filterFactory(Class clazz) {
        PropertyFilter filter = new PropertyFilter() {
            @Override
            public boolean apply(Object o, String s, Object o1) {
                boolean exist = true;
                try {
                    Field field = clazz.getDeclaredField(s);
                    TableField ann = field.getAnnotation(TableField.class);
                    if (ann != null) {
                        exist = ann.exist();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                return exist;
            }
        };
        return filter;
    }

}
