package com.nott.cloud.userservice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nott.cloud.userservice.entity.User;
import com.nott.cloud.userservice.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zouwenlong
 * @Date 2022/7/11 16:50
 */

@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private UserMapper userMapper;


    //模拟Crud，测试Ribbon调用

    public void create() {
        log.info("User create...");
        User user = new User();
        user.setUsername("nott");
        user.setPassword("123");
        userMapper.insert(user);
    }

    public void remove(Long id) {
        log.info("User remove...");
        userMapper.deleteById(id);
    }

    public void update(User user) {
        log.info("User update...");
        userMapper.updateById(user);
    }

    public User get(Long id) {
        log.info("User get...");
        return userMapper.selectById(id);
    }

    public User getByname(String name) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, name);
        return userMapper.selectList(wrapper).get(0);
    }
}
