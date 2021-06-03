package com.yy.security.controller;

import com.yy.security.Util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ywl
 * @Date 2021/6/3 10:43
 * @Description redis 测试
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/hello")
    public String hello() {
        redisUtil.set("hello", "redis in hello");
        return (String) redisUtil.get("hello");
    }
}
