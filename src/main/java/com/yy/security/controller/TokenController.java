package com.yy.security.controller;

import com.yy.security.Util.JwtUtil;
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

    /**
     * 检查token
     *
     * @param token
     * @return
     */
    @GetMapping("/checkJWT")
    public Claims checkJWT(String token) {
        return JwtUtil.checkJWT(token);
    }

    /**
     * 获取 token中用户名
     *
     * @param token
     * @return
     */
    @GetMapping("/getUsername")
    public String getUsername(String token) {
        return JwtUtil.getUsername(token);
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    @GetMapping("/isTokenExpired")
    public boolean isTokenExpired(String token) {
        return JwtUtil.isExpiration(token);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    @GetMapping("/refreshToken")
    public String refreshToken(String token) {
        return JwtUtil.refreshToken(token);
    }

}
