package com.nott.cloud.oauth2server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author Nott
 * @Date 2022/7/15
 */

//使用Redis存储token的配置
@Configuration
public class RedisTokenStoreConfig {
    @Autowired
    private RedisConnectionFactory factory;

    @Bean("redisTokenStore")
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(factory);
    }
}
