package cn.edu.buaa.se.bhac.config;


import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.code.UserCode;
import cn.edu.buaa.se.bhac.services.BhacUserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ArrayList<String> loginRelated = new ArrayList<String>() {
            {
                add("/login");
                add("/");
                add("/u/login");
            }
        };
        ArrayList<String> static_files = new ArrayList<String>() {
            {
                add("/img/**");
                add("/css/**");
                add("/js/**");
                add("/webjars/**");
            }
        };
        ArrayList<String> adminPages = new ArrayList<String>() {
            {
                add("/admin/**");
            }
        };
        ArrayList<String> sysAdminPages = new ArrayList<String>() {
            {
                add("/sysadmin/**");
            }
        };

        HandlerInterceptor loginInter = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                HttpSession session = request.getSession();
                BhacUser admin = (BhacUser) session.getAttribute("admin");
                if (admin == null) {
                    response.sendRedirect("/login");
                    return false;
                }
                return true;
            }
        };
        HandlerInterceptor sysAdminInter = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                Logger logger = LoggerFactory.getLogger(this.getClass());
                HttpSession session = request.getSession();
                BhacUser admin = (BhacUser) session.getAttribute("admin");
                if (!BhacUserService.checkSysAdmin(admin)) {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter writer = null;
                    JSONObject json = new JSONObject();
                    try {
                        writer = response.getWriter();
                        json.put("code", UserCode.ERR_USER_NO_ADMIN.toString());
                        json.put("message", UserCode.ERR_USER_NO_ADMIN.getMessage());
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    } finally {
                        writer.print(json.toString());
                        writer.flush();
                        writer.close();
                    }
                    return false;
                }
                return true;
            }
        };
        registry.addInterceptor(loginInter)
                .addPathPatterns(adminPages)
                .addPathPatterns(sysAdminPages);
        registry.addInterceptor(sysAdminInter).addPathPatterns(sysAdminPages);
    }

}
