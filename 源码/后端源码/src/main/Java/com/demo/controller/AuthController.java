package com.demo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.bean.BeanUtil;
import com.demo.common.core.R;
import com.demo.common.core.ServiceException;
import com.demo.common.utils.AuthUtil;
import com.demo.dto.LoginDTO;
import com.demo.entity.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 获取验证码图片
     * @return
     */
    @RequestMapping("/auth/getAuthCodeImg")
    public void  getAuthCodeImg(HttpSession session, HttpServletResponse response, Integer count){

        //定义图形验证码的长和宽  码值个数  干扰圈数
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(90, 40, 4, 10);
        if (null != count && count > 0){
            //重新生成验证码
            circleCaptcha.createCode();
        }
        BufferedImage codeImg = circleCaptcha.getImage();
        String authCode = circleCaptcha.getCode();
        if(session.getAttribute("validateCode")!=null){
            session.removeAttribute("validateCode");
            session.setAttribute("validateCode", authCode);
        }else{
            session.setAttribute("validateCode", "请输入验证码");
        }
        ServletOutputStream sos;
        try {
            sos = response.getOutputStream();
            ImageIO.write(codeImg, "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/info/update")
    @ResponseBody
    public R infoPassword(@RequestBody User user) {

        user.setId(AuthUtil.getUserId());
        userService.updateById(user);
        return R.success("修改密码成功");
    }

    @RequestMapping("/register")
    @ResponseBody
    public R register(@RequestBody @Validated User busUserDTO) {
        User busUser = BeanUtil.copyProperties(busUserDTO, User.class);
        busUser.setCreateTime(new Date());
        busUser.setStatus(1);
        busUser.setUsername(AuthUtil.hashPassword(busUser.getUsername()));
        busUser.setPassword(AuthUtil.hashPassword(busUser.getPassword()));
        userService.register(busUser);
        return R.success("注册成功");
    }


    @GetMapping("/current/user")
    public R current() {
        try {
            User user = AuthUtil.getUser();
            return R.data(userService.getById(user.getId()));
        }catch (Exception e) {
            return R.data(null);
        }
    }

    @GetMapping("/loginOut")
    public R loginOut() {
        AuthUtil.clear();
        return R.success("操作成功");
    }

    @PostMapping("/auth")
    public R login(@RequestBody LoginDTO formBean, HttpSession session) {
        Object code = session.getAttribute("validateCode");
        if (code == null || !Objects.equals(code.toString().toLowerCase(Locale.ROOT), formBean.getCode().toLowerCase(Locale.ROOT))) {
            throw new ServiceException("验证码错误");
        }

        AuthUtil.clear();
        // 登录操作
        boolean isLogin = userLogin(formBean);
        return isLogin ? R.success("登录成功") : R.fail("用户名或密码错误");
    }


    /**
     * 用户登录
     */
    private boolean userLogin(User formBean) {
        User user = userService.login(formBean.getUsername(), formBean.getPassword());

        if (user == null) {
            return false;
        }

        if (!Objects.equals(user.getRole(), formBean.getRole())) {
            throw new ServiceException("用户名或密码错误");
        }

        AuthUtil.setToken(user);
        return true;
    }

}
