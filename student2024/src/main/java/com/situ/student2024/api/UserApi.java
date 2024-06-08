package com.situ.student2024.api;

import com.situ.student2024.interceptor.LoginInterceptor;
import com.situ.student2024.model.User;
import com.situ.student2024.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/json;charset=utf-8")
public class UserApi {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 对用户信息进行认证
     */
    @GetMapping("/login")
    public Map<String, Object> login(User user, String captcha, HttpSession session) {
        if (captcha == null || captcha.equals(" ")) {
            return Map.of("success", false, "error", "验证码不可为空");
        }

        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (!captcha.equalsIgnoreCase(sessionCaptcha)) {
            return Map.of("success", false, "error", "验证码不正确");
        }

        User dbUser = userService.findByUsername(user.getUsername());
        if (dbUser == null) {
            return Map.of("success", false, "error", "无此用户");
        }

        //对用户中和密码组合字符串进行md5加密
        String password = user.getPassword() + "{" + user.getUsername() + "}";
        String encrypted = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        if (dbUser.getPassword().equals(encrypted)) {//密码匹配
            session.setAttribute(LoginInterceptor.LOGIN_USER_KEY, dbUser);
            return Map.of("success", true);
        } else {
            return Map.of("success", false, "error", "密码不正确");
        }
    }
}
