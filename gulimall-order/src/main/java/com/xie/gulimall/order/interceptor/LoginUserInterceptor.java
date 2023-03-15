package com.xie.gulimall.order.interceptor;

import cn.hutool.core.text.AntPathMatcher;
import com.xie.common.constant.AuthServerConstant;
import com.xie.common.vo.MemberResponseVo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @title: LoginUserInterceptor
 * @Author Xie
 * @Date: 2023/1/5 21:37
 * @Version 1.0
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {
    public static ThreadLocal<MemberResponseVo> loginUser=new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        boolean match = new AntPathMatcher().match("/order/order/status/**", uri);
        if (match){
            return true;
        }


        MemberResponseVo memberResponseVo = (MemberResponseVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if (memberResponseVo!=null){
            loginUser.set(memberResponseVo);
            return true;
        }else {
            //没登录就去登录
            request.getSession().setAttribute("msg","请先登录");
            response.sendRedirect("http://auth.gulimall.com/login.html");
            return false;
        }

    }
}
