package com.nott.cloud.oauth2server.config;

import com.nott.cloud.oauth2server.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author Nott
 * @Date 2022/7/14
 */

/**
 * AuthorizationServerConfigurerAdapter中:
 * <p>
 * ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService），
 * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
 * AuthorizationServerSecurityConfigurer：用来配置令牌端点(Token Endpoint)的安全约束.
 * AuthorizationServerEndpointsConfigurer：用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
 */
@Configuration
@EnableAuthorizationServer //认证服务器配置
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager; //认证管理器
    @Autowired
    private UserDetailsService userDetailsService;

    @Resource
    @Qualifier("myJwtTokenStore")
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;


    /**
     * 配置客户端详情信息（Client Details),Spring Security OAuth2的配置方法是编写@Configuration类继承AuthorizationServerConfigurerAdapter，
     * 然后重写void configure(ClientDetailsServiceConfigurer clients)方法，如：
     *
     * @param clients
     * @throws Exception
     */
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("admin") //clientID
                .secret(passwordEncoder.encode("abc")) // client_secret
                .scopes("all") //配置申请的权限范围
                .accessTokenValiditySeconds(7200) //配置访问token的有效期
                .refreshTokenValiditySeconds(7200) //刷新token的有效期
                .redirectUris("http://localhost:9901/login") //授权成功的跳转，单点登录时用
                .authorizedGrantTypes("authorization_code", "password","refresh_token"); //配置GrantTypes，允许的授权类型
    }

    /**
     * AuthorizationServerEndpointsConfigurer端点配置
     * AuthorizationServerEndpointsConfigurer其实是一个装载类，
     * 装载Endpoints所有相关的类配置（AuthorizationServer、TokenServices、TokenStore、ClientDetailsService、UserDetailsService）。
     * <p>
     * 注入相关配置：
     * 1. 密码模式下配置认证管理器 AuthenticationManager
     * 2. 设置 AccessToken的存储介质tokenStore， 默认使用内存当做存储介质。
     * 3. userDetailsService注入
     */
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain chain = new TokenEnhancerChain();
        ArrayList<TokenEnhancer> list = new ArrayList<>();
        list.add(jwtTokenEnhancer); //配置Jwt内容增强器
        list.add(jwtAccessTokenConverter);
        chain.setTokenEnhancers(list);
        endpoints.authenticationManager(authenticationManager) //MUST：密码模式下需设置一个AuthenticationManager对象,获取 UserDetails信息
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore)  //设置令牌存储
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(chain);
    }

    ////解决访问oauth/token出现401的问题
    //@Override
    //public void configure(AuthorizationServerSecurityConfigurer security) {
    //    security
    //            .tokenKeyAccess("permitAll()")                    //oauth/token_key是公开
    //            .checkTokenAccess("permitAll()")                  //oauth/check_token公开
    //            .allowFormAuthenticationForClients()                //表单认证（申请令牌）
    //    ;
    //}

    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("isAuthenticated()"); // 获取密钥需要身份认证，使用单点登录时必须配置
    }

}
