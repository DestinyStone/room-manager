package com.demo.service;

import com.demo.entity.User;

import java.util.List;

public interface UserService{
    boolean register(User busUser);

    User selectByUserName(String username);

    User login(String username, String password);

    void updateById(User user);

    User getById(Long id);

    List<User> list(User user);

    boolean removeByIds(List<Long> toLongList);
}
