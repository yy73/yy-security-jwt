package com.yy.security.service;

import com.yy.security.entity.UserRegister;
import com.yy.security.entity.result.Response;

/**
 * @Author ywl
 * @Date 2021/5/21 19:47
 * @Description
 */
public interface LoginService {


    /**
     * @param userName 账号
     * @param password 密码
     * @description: 用户登录接口，登录成功后 返回 token值
     * @return:
     * @auther: ywl
     * @date: 2021/5/21 20:22
     **/
    Response login(String userName, String password);

    /**
     * @param ur
     * @description: 用户注册
     * @return:
     * @auther: ywl
     * @date: 2021/5/21 20:23
     **/

    Response register(UserRegister ur);
}
