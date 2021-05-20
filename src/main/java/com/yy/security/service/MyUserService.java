package com.yy.security.service;

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

/**
 * @Author ywl
 * @Date 2021/5/16 15:35
 * @Description
 */
@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userMapper.findByUsername(username);
        if (userEntity == null) {
            return null;
        }

        List<PermissionEntity> permission = userMapper.findPermissionByUsername(userEntity.getUsername());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        permission.forEach(user -> {
            authorities.add(new SimpleGrantedAuthority(user.getPermTag()));
        });
        userEntity.setAuthorities(authorities);
        return userEntity;
    }
}
