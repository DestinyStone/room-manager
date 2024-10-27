package com.demo.common.utils;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.DigestUtil;
import com.demo.common.core.ServiceException;
import com.demo.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;


public class AuthUtil {
    // session 的 key 值
    private static final String TOKEN_KEY = "token";

    // 获取当前登录的用户id
    public static Long getUserId() {
        return getUser().getId();
    }

    // 设置登录权限
    public static void setToken(User auth) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(TOKEN_KEY, auth);
    }

    // 清空权限
    public static void clear() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(TOKEN_KEY, null);
    }

    // 是否为管理员
    public static boolean isAdmin() {
        User user = getUser();
        return Objects.equals(user.getRole(), "管理员");
    }

    // 获取当前登录用户
    public static User getUser() {
        // 获取全局request
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(TOKEN_KEY);

        // 判断是否登录
        if(attribute == null) {
            throw new ServiceException("用户未登录");
        }
        return (User) attribute;
    }

    /**
     * 密码加密
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    /**
     * 验证密码是否匹配
     * @param rawPassword 原始密码
     * @param hashedPassword 加密后的密码
     * @return 密码是否匹配
     */
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

    public static boolean isLogin() {
        // 获取全局request
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(TOKEN_KEY);

        if(attribute == null) {
            return false;
        }
        return true;
    }
}
