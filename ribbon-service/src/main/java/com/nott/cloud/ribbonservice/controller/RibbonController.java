package com.nott.cloud.ribbonservice.controller;

import com.nott.cloud.ribbonservice.Result;
import com.nott.cloud.ribbonservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Nott
 * @Date 2022/7/11
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class RibbonController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @RequestMapping("/{id}")
    public Result getUser(@PathVariable Long id) {
        log.info("get obj from" + userServiceUrl);
        return Result.data(restTemplate.getForObject(userServiceUrl+"/userGet/{id}", Result.class, id));
    }

    @RequestMapping("/create")
    public Result addUser() {
        log.info("create obj from" + userServiceUrl);
        return Result.data(restTemplate.getForObject(userServiceUrl+"/userCreate", Result.class));
    }

    @RequestMapping("/del/{id}")
    public Result delUser(@PathVariable Long id) {
        log.info("del obj from" + userServiceUrl);
        return Result.data(restTemplate.getForObject(userServiceUrl+"/userRev", Result.class, id));
    }

    @RequestMapping("/update")
    public Result updateUser() {
        User user = new User();
        user.setId(2L);
        user.setPassword("123");
        user.setUsername("123");
        log.info("update obj from" + userServiceUrl);
        return Result.data(restTemplate.postForObject(userServiceUrl+"/userUpdate",user,Result.class));
    }

    @RequestMapping("/get/{name}")
    public Result getUser(@PathVariable String name) {
        log.info("get obj from" + userServiceUrl);
        ResponseEntity<Result> entity = restTemplate.getForEntity(userServiceUrl + "/userGetByname", Result.class, name);
        if(entity.getStatusCode().is2xxSuccessful()){
            return Result.data(entity.getBody().getObj());
        }
        return null;
    }


}
