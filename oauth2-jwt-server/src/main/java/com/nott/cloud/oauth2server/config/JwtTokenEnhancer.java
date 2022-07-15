package com.nott.cloud.oauth2server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author Nott
 * @Date 2022/7/15
 */

//Token内容增强
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("enhancer", "enhancer test"); //给token新增一个enhancer属性
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(map);
        return oAuth2AccessToken;
    }
}
