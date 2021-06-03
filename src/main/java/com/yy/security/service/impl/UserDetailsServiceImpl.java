package com.yy.security.service.impl;

import cn.hutool.json.JSONUtil;
import com.yy.security.Util.RedisUtil;
import com.yy.security.entity.PermissionEntity;
import com.yy.security.entity.UserEntity;
import com.yy.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author ywl
 * @Date 2021/5/16 15:35
 * @Description
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Object users = redisUtil.get("user:userDetail:" + username);

        if (users == null) {
            return null;
        }

        UserEntity userEntity = JSONUtil.toBean(JSONUtil.parseObj(users.toString()), UserEntity.class);


        List<PermissionEntity> permission = JSONUtil.toList(JSONUtil.parseArray(redisUtil.get("user:permission:" + userEntity.getUsername())), PermissionEntity.class);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        permission.forEach(user -> {
            authorities.add(new SimpleGrantedAuthority(user.getPermTag()));
        });
        userEntity.setAuthorities(authorities);
        return userEntity;
    }
}
