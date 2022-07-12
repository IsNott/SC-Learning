package com.nott.cloud.feignservice.controller;

import com.nott.cloud.Result;
import com.nott.cloud.User;
import com.nott.cloud.feignservice.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nott
 * @Date 2022/7/12 17:32
 */

@RestController
public class FeignController {
    @Autowired
    private FeignService feignService;

    @GetMapping("/{id}")
    public Result getUser(@PathVariable Long id) {
        return Result.data(feignService.getUser(id));
    }

    @GetMapping("/getByUsername")
    public Result getByUsername(@RequestParam String username) {
        return feignService.userGet(username);
    }

    @PostMapping("/create")
    public Result create(@RequestBody User user) {
        return feignService.create();
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return feignService.update(user);
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return feignService.userRev(id);
    }
}
