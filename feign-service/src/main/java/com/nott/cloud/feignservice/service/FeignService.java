package com.nott.cloud.feignservice.service;

import com.nott.cloud.Result;
import com.nott.cloud.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Nott
 * @Date 2022/7/12 17:23
 */
//绑定user-service，通过@FeignClient注解实现了一个Feign客户端，其中的value为user-service表示这是对user-service服务的接口调用客户端
@FeignClient(value = "user-service", fallback = FeignFallbackService.class)
public interface FeignService {

    @RequestMapping("userCreate")
    Result create();

    @RequestMapping("userUpdate")
    Result update(User user);

    @RequestMapping("get/{id}")
    User getUser(@PathVariable Long id);

    @RequestMapping("userRev")
    Result userRev(Long id);

    @RequestMapping("userGetByname")
    Result userGet(String name);

    @GetMapping("userGetByIds/{str}")
    List<User> userGetByIds(@PathVariable String str);
}
