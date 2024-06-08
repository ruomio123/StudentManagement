package com.situ.student2024.interceptor;

import com.situ.student2024.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;


public class LoginInterceptor implements HandlerInterceptor {
    public static final String LOGIN_USER_KEY = "@#current_login_user";

    /**
     * w
     * 重写前置拦截
     *
     * @param request  请求对象
     * @param response 响应对应
     * @param handler  被拦截的目标，通常是controller
     * @return 返回是否通过的boolean值
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //会话域
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(LOGIN_USER_KEY);
        if (user == null) {
            //重定向到登录页
            response.sendRedirect("/admin/login");
            return false;//拦截
        } else {
            return true;//放行
        }
    }
}
