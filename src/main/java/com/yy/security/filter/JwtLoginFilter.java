package com.yy.security.filter;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.security.Util.JwtUtil;
import com.yy.security.entity.UserEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ywl
 * @Date 2021/5/17 10:14
 * @Description 生成token
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    //    获取授权管理, 创建JwtLoginFilter时获取
    private AuthenticationManager authenticationManager;

    /**
     * 创建JwtLoginFilter,构造器，定义后端登陆接口-【/auth/login】，当调用该接口直接执行 attemptAuthentication 方法
     *
     * @param authenticationManager
     */
    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserEntity user = new ObjectMapper()
                    .readValue(req.getInputStream(), UserEntity.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
     *
     * @param req
     * @param res
     * @param chain
     * @param auth
     * @description: TODO
     * @return:
     * @auther: ywl
     * @date: 2021/5/19 20:29
     **/
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        UserEntity userEntity = (UserEntity) auth.getPrincipal();
        String token = JwtUtil.generateJsonWebToken(userEntity);


        // 将生成得token 已json 返回
        Map result = new HashMap<>();
        result.put("code", "200");
        result.put("token", token);
        result.put("message", "操作成功");
        res.setCharacterEncoding("utf-8");
        res.setContentType("application/json");
        res.getWriter().println(JSONUtil.parse(result));
        res.getWriter().flush();
    }

    /**
     * 认证失败，调用方法
     *
     * @param request
     * @param response
     * @param failed
     * @description: TODO
     * @return:
     * @auther: ywl
     * @date: 2021/5/19 20:38
     **/

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }
}
