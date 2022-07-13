package com.nott.cloud.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nott
 * @Date 2022/7/13 9:44
 */

@RestController
@RefreshScope //开启配置刷新
public class ConfigClientController {
    @Value("${config.info}")
    private String info;

    @RequestMapping("/config")
    public String testConfig() {
        return "this is " + info + ",thank you !";
    }
}
