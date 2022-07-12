package com.nott.cloud.feignservice.service;

import com.nott.cloud.Result;
import com.nott.cloud.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nott
 * @Date 2022/7/12 19:04
 */

@Component //实现FeignService对每个方法实现服务降级
public class FeignFallbackService implements FeignService {
    @Override
    public Result create() {
        return Result.data("default...");
    }

    @Override
    public Result update(User user) {
        return Result.data("default...");
    }

    @Override
    public User getUser(Long id) {
        return new User();
    }

    @Override
    public Result userRev(Long id) {
        return Result.data("default...");
    }

    @Override
    public Result userGet(String name) {
        return Result.data("default...");
    }

    @Override
    public List<User> userGetByIds(String str) {
        return new ArrayList<>();
    }
}
