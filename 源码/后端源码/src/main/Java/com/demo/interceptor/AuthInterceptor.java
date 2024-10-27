package com.demo.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.core.R;
import com.demo.common.utils.AuthUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class AuthInterceptor  implements HandlerInterceptor {

    private List<String> excludePath = Arrays.asList("/auth", "/register", "/paper/topic/list", "/submit/save");

    @Override
    //原始方法调用前执行的内容
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html; charset=UTF-8");


        if (excludePath.contains(request.getRequestURI())) {
            return true;
        }
        if (!AuthUtil.isLogin()) {
            PrintWriter writer = response.getWriter();
            writer.println(JSONObject.toJSONString(R.fail("用户未登录")));
            return false;
        }
        return true;
    }

    @Override
    //原始方法调用后执行的内容
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    //原始方法调用完成后执行的内容
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
