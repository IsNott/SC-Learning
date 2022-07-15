package com.nott.cloud.oauth2server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author Nott
 * @Date 2022/7/14
 */
@Configuration
@EnableResourceServer //资源服务器配置
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //ResourceServerConfigurerAdapter （资源服务器配置）
    //内部关联了ResourceServerSecurityConfigurer 和 HttpSecurity。前者与资源安全配置相关，后者与http安全配置相关

    /**
     * HttpSecurity配置这个与Spring Security类似：
     * 请求匹配器，用来设置需要进行保护的资源路径，默认的情况下是保护资源服务的全部路径。
     *
     * @param http
     * @throws Exception
     */
    public void configure(HttpSecurity http) throws Exception {
        //((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().anyRequest()).authenticated();
        http //需要保护的路径
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .requestMatchers()
                .antMatchers("/user/**"); //需要保护的路径
    }

}
