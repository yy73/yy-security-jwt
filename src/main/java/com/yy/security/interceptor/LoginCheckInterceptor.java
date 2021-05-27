package com.yy.security.interceptor;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.yy.security.Util.JwtUtil;
import com.yy.security.mapper.UserMapper;
import com.yy.security.user.CurrentUserHolder;
import com.yy.security.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author ywl
 * @Date 2021/5/27 10:04
 * @Description
 */

public class LoginCheckInterceptor implements HandlerInterceptor {
    private static final Log log = LogFactory.get();

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader(tokenHeader);
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            String username = jwtUtil.getUsername(authToken);
            UserInfo userInfo = this.userMapper.getUserInfo(username);
            CurrentUserHolder.set(userInfo);
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CurrentUserHolder.remove();
    }
}
