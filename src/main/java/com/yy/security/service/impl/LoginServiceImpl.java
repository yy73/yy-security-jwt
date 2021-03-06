package com.yy.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.yy.security.Util.JwtUtil;
import com.yy.security.Util.RedisUtil;
import com.yy.security.entity.UserEntity;
import com.yy.security.entity.UserRegister;
import com.yy.security.entity.result.Response;
import com.yy.security.mapper.UserMapper;
import com.yy.security.service.LoginService;
import com.yy.security.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ywl
 * @Date 2021/5/21 19:48
 * @Description
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Response login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            return Response.error("用户名或密码错误，请重新登录!");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity userEntity = BeanUtil.copyProperties(userDetails, UserEntity.class);
        String token = jwtUtil.generateJsonWebToken(userEntity);
        return Response.success(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response register(UserRegister ur) {
        UserEntity userEntity = BeanUtil.copyProperties(ur, UserEntity.class);
        List<UserEntity> un = userMapper.getUser(userEntity.getUsername());
        // 根据用户名判断用户是否注册
        if (CollUtil.isNotEmpty(un)) {
            return Response.error("用户名重复");
        }
        // 密码加密
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setId(IdUtil.simpleUUID());
        userEntity.setEnabled(true);
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCredentialsNonExpired(true);

        userMapper.insertUser(userEntity);
        // 将用户信息 添加到 redis中
        UserInfo userInfo = userMapper.getUserInfo(userEntity.getUsername());
        redisUtil.hmSet("userInfo", userInfo.getUsername(), JSONUtil.toJsonStr(userInfo));
        return Response.success();
    }
}
