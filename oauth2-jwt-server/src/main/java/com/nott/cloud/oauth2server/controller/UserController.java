package com.nott.cloud.oauth2server.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author Nott
 * @Date 2022/7/15
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/get")
    public Object getUser(Authentication authentication, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String jwt = authorization.substring("Bearer".length()+1);
        Claims body = Jwts.parser().setSigningKey("NOTT".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(jwt).getBody();
        Object details = authentication.getPrincipal();

        return body;
    }

    @RequestMapping("/index")
    public String toIndex() {
        return "index";
    }
}
