package com.nott.cloud.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 在微服务架构中往往会有一个注册中心，每个微服务都会向注册中心去注册自己的地址及端口信息，
 * 注册中心维护着服务名称与服务实例的对应关系。每个微服务都会定时从注册中心获取服务列表，
 * 同时汇报自己的运行情况，这样当有的服务需要调用其他服务时，就可以从自己获取到的服务列表中获取实例地址进行调用，
 * Eureka实现了这套服务注册与发现机制。
 */

@SpringBootApplication
@EnableEurekaServer //启用Euerka注册中心功能
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
