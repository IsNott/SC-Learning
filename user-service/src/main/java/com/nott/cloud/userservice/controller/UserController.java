package com.nott.cloud.userservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nott.cloud.userservice.Result;
import com.nott.cloud.userservice.entity.User;
import com.nott.cloud.userservice.mapper.UserMapper;
import com.nott.cloud.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nott
 * @Date 2022/7/11
 */

@RestController
@RequestMapping("")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Resource
    private UserMapper userMapper;

    @RequestMapping("userCreate")
    public Result userCreate() {
        userService.create();
        return new Result();
    }

    @RequestMapping("userUpdate")
    public Result userUpdate(User user) {
        userService.update(user);
        return new Result(user);
    }

    @RequestMapping("userGet/{id}")
    public Result userGet(@PathVariable Long id) {
        return Result.data(userService.get(id));
    }

    @RequestMapping("get/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.get(id);
    }

    @RequestMapping("userRev")
    public Result userRev(Long id) {
        userService.remove(id);
        return new Result();
    }

    @RequestMapping("userGetByname")
    public Result userGet(String name) {
        return Result.data(userService.getByname(name));
    }

    @GetMapping("userGetByIds/{str}")
    public List<User> userGetByIds(@PathVariable String str) {
        String[] split = null;
        List<String> list = new ArrayList<>();
        if (str.indexOf(",") > 0) {
            split = str.split(",");
            list = Arrays.asList(split);
        } else {
            list.add(str);
        }
        List<User> ids = userService.listByIds(list);
        log.info(ids + "");
        return ids;
    }
}
