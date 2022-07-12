package com.nott.cloud.hystrixservice.controller;

import com.nott.cloud.hystrixservice.Result;
import com.nott.cloud.hystrixservice.entity.User;
import com.nott.cloud.hystrixservice.service.UserHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Nott
 * @Date 2022/7/11 20:14
 */

@RestController
public class UserHystrixController {
    @Autowired
    private UserHystrixService hystrixService;

    //测试服务降级
    @GetMapping("/test/{id}")
    public Result testFallback(@PathVariable Long id) {
        return hystrixService.getUser(id);
    }

    //测试HystrixCommand命令
    @GetMapping("/testCommand/{id}")
    public Result testCommand(@PathVariable Long id) {
        return hystrixService.getUserCommand(id);
    }

    //测试使用缓存
    @GetMapping("/testCache/{id}")
    public Result testCache(@PathVariable Long id) {
        hystrixService.getCacheUser(id);
        hystrixService.getCacheUser(id);
        hystrixService.getCacheUser(id);
        return new Result();

    }

    //测试使用移除缓存
    @GetMapping("/testRevCache/{id}")
    public Result testRevCache(@PathVariable Long id) {
        hystrixService.getCacheUser(id);
        hystrixService.getRemoveCacheUser(id);
        hystrixService.getCacheUser(id);
        return new Result();
    }


    @GetMapping("/testCollapser")
    public Result testCollapser() throws ExecutionException, InterruptedException {
        Future<User> future1 = hystrixService.getUserFuture(1L);
        Future<User> future2 = hystrixService.getUserFuture(2L);
        future1.get();
        future2.get();
        Thread.sleep(200); //休眠2秒
        Future<User> future3 = hystrixService.getUserFuture(3L);
        future3.get();
        return new Result();
    }

}
