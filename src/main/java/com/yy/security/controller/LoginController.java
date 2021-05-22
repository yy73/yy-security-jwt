package com.yy.security.controller;

import com.yy.security.entity.UserRegister;
import com.yy.security.entity.result.Response;
import com.yy.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public Response login(String userName, String password) {
        return loginService.login(userName, password);
    }

    @PostMapping("/register")
    public Response register(@RequestBody UserRegister ur) {
        return loginService.register(ur);
    }
}
