package com.nott.cloud.hystrixservice.entity;

import lombok.Data;

/**
 * @author zouwenlong
 * @Date 2022/7/11 20:25
 */


@Data
public class User {
    private Long id;
    private String username;
    private String password;
}
