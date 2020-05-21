package cn.edu.buaa.se.bhac.Utils;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;
import cn.edu.buaa.se.bhac.code.BaseCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.ui.Model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ControllerUtils {

    public static void putCodeAndMessage(BaseCode code, Model model) {
        model.addAttribute("code", code.toString());
        model.addAttribute("message", code.getMessage());
    }

    public static JSONObject JsonCodeAndMessage(BaseCode code) {
        JSONObject json = new JSONObject();
        json.put("code", code.toString());
        json.put("message", code.getMessage());
        return json;
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
    
    public static JSONObject JsonMap (HashMap<String, Object> mp)
    {
        JSONObject json = new JSONObject();
        for(Object key : mp.keySet()) {
            Object val = mp.get(key);
            json.put((String) key,val);
        }
        return json;
    }
    
    public static <T> String putCountAndData (IPage<T> iPage, Class clazz)
    {
        List<T> activities =  iPage.getRecords();
        Long count = iPage.getTotal();
        JSONObject json = new JSONObject();
        json.put("count",count);
        json.put("data",activities);
        return json.toJSONString();
    }
    
}
