package com.nott.cloud;

import lombok.Data;

/**
 * @author Nott
 * @Date 2022/7/12 17:30
 */


@Data
public class User {
    private Long id;
    private String username;
    private String password;
}
