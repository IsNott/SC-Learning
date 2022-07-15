package com.nott.cloud.oauth2client.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nott
 * @Date 2022/7/15
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/get")
    public Object getUser(Authentication authentication) {
        return authentication;
    }

    @RequestMapping("/index")
    public String toIndex() {
        return "index";
    }
}
