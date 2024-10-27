package com.demo.controller;

import com.demo.common.core.R;
import com.demo.common.core.ServiceException;
import com.demo.common.utils.Func;
import com.demo.entity.User;
import com.demo.vo.UserVO;
import com.demo.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.demo.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<UserVO> list(User user) {
        return UserWrapper.build().listVO(userService.list(user));
    }

    @PostMapping("/save")
    public R save(@RequestBody User user) {
        user.setCreateTime(new Date());

        if (Objects.equals(user.getRole(), "用户")) {
            user.setStatus(1);
        }else {
            user.setStatus(0);
        }
        return R.status(userService.register(user));
    }

    @GetMapping("/detail")
    public R detail(@RequestParam("id") Long id) {
        return R.data(UserWrapper.build().entityVO(userService.getById(id)));
    }

    @GetMapping("/delete")
    public R save(@RequestParam("ids") String ids) {
        return R.status(userService.removeByIds(Func.toLongList(ids)));
    }

    @PostMapping("/update/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody User paper) {
        paper.setId(id);

        User user = userService.selectByUserName(paper.getUsername());
        if (user != null && !Objects.equals(id, user.getId())) {
            throw new ServiceException("用户名重复");
        }
        if (Objects.equals(paper.getPassword(), "")) {
            paper.setPassword(null);
        }

        userService.updateById(paper);
        return R.status(true);
    }
}
