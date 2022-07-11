package com.nott.cloud.userservice.controller;

import com.nott.cloud.userservice.Result;
import com.nott.cloud.userservice.entity.User;
import com.nott.cloud.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouwenlong
 * @Date 2022/7/11 16:46
 */

@RestController
@RequestMapping("")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

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
    @RequestMapping("userRev")
    public Result userRev(Long id) {
        userService.remove(id);
        return new Result();
    }

    @RequestMapping("userGetByname")
    public Result userGet(String name) {
        return Result.data(userService.getByname(name));
    }
}
