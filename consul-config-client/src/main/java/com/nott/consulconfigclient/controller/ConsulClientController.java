package com.nott.consulconfigclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nott
 * @Date 2022/7/13
 */

@RefreshScope
@RestController
public class ConsulClientController {

    @Value("${config.info}")
    private String value;

    @RequestMapping("config")
    public String consulConfig() {
        return value;
    }
}
