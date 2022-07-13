package com.nott.cloud.feignservice.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nott
 * @Date 2022/7/12
 */

@Configuration
public class FeignConfig {

    @Bean //设置feign的日志打印级别
    Logger.Level feilogLevel(){
        return Logger.Level.FULL;
    }
}
