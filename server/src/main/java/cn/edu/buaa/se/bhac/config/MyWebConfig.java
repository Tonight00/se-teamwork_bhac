package cn.edu.buaa.se.bhac.config;


import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.Utils.ControllerUtils;
import cn.edu.buaa.se.bhac.code.UserCode;
import cn.edu.buaa.se.bhac.services.BhacUserService;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
        registry.addViewController("/sysadmin/index_sys").setViewName("index_sys");
        registry.addViewController("/sysadmin/index_act").setViewName("index_act");
        registry.addViewController("/sysadmin/index_tag").setViewName("index_tag");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ArrayList<String> loginRelated = new ArrayList<String>() {
            {
                add("/login");
                add("/");
                add("/u/login");
                add("/users");
                add("/users/login");
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

        ArrayList<String> appAPIs = new ArrayList<String>() {
            {
                add("/users/**");
                add("/activities/**");
                add("/tags/**");
                add("/roles/**");
                add("/posts/**");
                add("/comments/**");
                add("/join/**");
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

        HandlerInterceptor tokenInter = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                Logger logger = LoggerFactory.getLogger(this.getClass());
                final String auth = request.getHeader("authorization");
                UserCode err = null;
                Claims claims = null;
                if (auth == null || !auth.startsWith("Bearer")) {
                    err = UserCode.ERR_USER_NO_TOKEN;
                } else {
                    final String token = auth.substring(7);
                    try {
                        claims = Jwts.parser().setSigningKey(JWTConfig.secretKey).parseClaimsJws(token).getBody();
                    } catch (Exception e) {
                        err = UserCode.ERR_USER_INVALID_TOKEN;
                    }
                }
                if (err != null) {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter writer = null;
                    try {
                        writer = response.getWriter();
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    } finally {
                        writer.print(ControllerUtils.JsonCodeAndMessage(err).toJSONString());
                        writer.flush();
                        writer.close();
                    }
                    return false;
                }
                request.setAttribute("claims", claims);
                return true;
            }
        };
        registry.addInterceptor(loginInter)
                .addPathPatterns(adminPages)
                .addPathPatterns(sysAdminPages);

        registry.addInterceptor(sysAdminInter).addPathPatterns(sysAdminPages);

        registry.addInterceptor(tokenInter)
                .addPathPatterns(appAPIs)
                .excludePathPatterns(loginRelated);
    
    }

}
