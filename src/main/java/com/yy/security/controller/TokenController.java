package com.yy.security.controller;

import com.yy.security.Util.JwtUtil;
import com.yy.security.user.CurrentUserHolder;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author ywl
 * @Date 2021/5/20 10:35
 * @Description
 */
@RestController
@RequestMapping("/jwt")
public class TokenController {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 获取 token中用户名
     *
     * @param token
     * @return
     */
    @GetMapping("/getUsername")
    public String getUsername(String token) {
        return jwtUtil.getUsername(token);
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    @GetMapping("/isTokenExpired")
    public boolean isTokenExpired(String token) {
        return jwtUtil.isExpiration(token);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    @GetMapping("/refreshToken")
    public String refreshToken(String token) {
        return jwtUtil.refreshToken(token);
    }

}
