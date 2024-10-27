package com.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.demo.common.core.ServiceException;
import com.demo.common.utils.AuthUtil;
import com.demo.entity.User;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import com.demo.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public synchronized boolean register(User busUser) {
        User query = new User();
        query.setUsername(busUser.getUsername());
        Example<User> example = Example.of(query);
        if (repository.findAll(example).size() > 0) {
            throw new ServiceException("用户名已存在");
        }
        if (StrUtil.isBlank(busUser.getPassword())) {
            throw new ServiceException("密码不能为空");
        }
        busUser.setPassword(busUser.getPassword());
        repository.save(busUser);
        return true;
    }

    @Override
    public User selectByUserName(String username) {
        List<User> list = repository.findAll();
        for (User user : list) {
            if (AuthUtil.checkPassword(username, user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User login(String username, String password) {
        List<User> list = repository.findAll();
        for (User user : list) {
            if (AuthUtil.checkPassword(username, user.getUsername()) &&
                    AuthUtil.checkPassword(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void updateById(User user) {
        repository.save(user);
    }

    @Override
    public User getById(Long id) {
        return repository.getOne(id);
    }

    @Override
    public List<User> list(User user) {
        return repository.findAll();
    }

    @Override
    public boolean removeByIds(List<Long> ids) {
        for (Long id : ids) {
            repository.deleteById(id);
        }
        return true;
    }
}
