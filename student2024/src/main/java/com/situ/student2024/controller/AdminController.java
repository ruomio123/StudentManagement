package com.situ.student2024.controller;

import com.situ.student2024.interceptor.LoginInterceptor;
import com.situ.student2024.model.User;
import com.wf.captcha.SpecCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Map;


@Controller
public class AdminController {
    /**
     * 跳转到后台首页
     */
    @GetMapping("/admin")
    public String admin(HttpSession session, Map<String, Object> map) {
        User user = (User) session.getAttribute(LoginInterceptor.LOGIN_USER_KEY);
        map.put("user", user);
        return "admin/admin";
    }

    /**
     * 欢迎页面
     */
    @GetMapping("/admin/welcome")
    public String welcome() {
        return "admin/welcome";
    }

    /**
     * 跳转到登录页
     */
    @GetMapping("/admin/login")
    public String login() {
        return "admin/login";
    }

    /**
     * 注销
     */
    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        //从会话域中将认证对象移除
        session.removeAttribute(LoginInterceptor.LOGIN_USER_KEY);
        //重定向到首页
        return "redirect:/admin";
    }

    /**
     * 响应一个验证码
     */
    @GetMapping("/admin/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SpecCaptcha captcha = new SpecCaptcha(130, 48, 5);

        //清空响应对象的缓存
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);

        request.getSession().setAttribute("captcha", captcha.text().toLowerCase());
        captcha.out(response.getOutputStream());
    }
}
