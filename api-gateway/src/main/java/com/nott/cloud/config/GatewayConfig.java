package com.nott.cloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nott
 * @Date 2022/7/13
 */

@Configuration
public class GatewayConfig {
    //使用JavaBean配置Gateway
    @Bean
    public RouteLocator CustonRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route2", r -> r.path("/userGet/{id}")
                        .uri("http://localhost:8201/userGet/{id}"))
                .build();
    }
}
