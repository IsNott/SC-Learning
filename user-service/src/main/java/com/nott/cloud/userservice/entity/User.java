package com.nott.cloud.userservice.entity;

import lombok.Data;

/**
 * @author zouwenlong
 * @Date 2022/7/11 17:04
 */

@Data
public class User {
    private Long id;
    private String username;
    private String password;
}
