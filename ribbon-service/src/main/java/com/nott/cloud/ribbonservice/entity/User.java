package com.nott.cloud.ribbonservice.entity;

import lombok.Data;

/**
 * @author zouwenlong
 * @Date 2022/7/11 17:31
 */


@Data
public class User {
    private Long id;
    private String username;
    private String password;
}

