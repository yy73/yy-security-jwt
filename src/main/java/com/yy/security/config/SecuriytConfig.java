package com.yy.security.config;

import com.yy.security.Util.MD5Util;
import com.yy.security.entity.PermissionEntity;
import com.yy.security.filter.JWTAuthorizationFilter;
import com.yy.security.filter.JwtLoginFilter;
import com.yy.security.mapper.PermissionMapper;
import com.yy.security.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableWebSecurity
public class SecuriytConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    /**
     * 添加授权账户
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            /**
             *
             * @param rawPassword 用户输入得密码
             * @param encodedPassword
             * @return
             */
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String encode = MD5Util.encode((String) rawPassword);
                boolean result = encode.equals(encodedPassword);
                return result;
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.authorizeRequests();
        // 动态遍历权限
        List<PermissionEntity> permission = permissionMapper.findAllPermission();
        permission.forEach(p -> authorizeRequests.antMatchers(p.getUrl()).hasAnyAuthority(p.getPermTag()));


        authorizeRequests.antMatchers("/auth/login").permitAll()
                .antMatchers("/**").fullyAuthenticated()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .addFilter(new JwtLoginFilter(authenticationManager())).csrf().disable()
                .exceptionHandling() // 配置无权限认证/未登录认证
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 处理 未登录 操作
                .accessDeniedHandler(jwtAccessDeniedHandler) // 处理无权限操作
                .and()
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * 设置白名单
     *
     * @param web
     * @description: TODO
     * @return:
     * @auther: ywl
     * @date: 2021/5/19 19:33
     **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/demo/login");
    }

}
