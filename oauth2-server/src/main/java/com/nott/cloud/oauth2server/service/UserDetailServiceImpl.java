package com.nott.cloud.oauth2server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nott
 * @Date 2022/7/14
 */

@Service //UserDetailsService是security自带的接口
public class UserDetailServiceImpl implements UserDetailsService {

    private List<User> userList;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> list = userList.stream().filter(user -> user.getUsername().equals(s)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @PostConstruct //加载一个非静态的void方法，并且在服务器加载Servlet的时候运行，并且只会被服务器执行一次
    public void initData() {
        String pwd = passwordEncoder.encode("123456");
        // AuthorityUtils.commaSeparatedStringToAuthorityList() 是Spring Security 提供的
        // 该方法用于将逗号隔开的权限集字符串切割成可用权限对象列表
        userList = new ArrayList<>();
        userList.add(new User("nott", pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("zhangshan", pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        userList.add(new User("lisi", pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }
}
