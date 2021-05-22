package com.yy.security.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.yy.security.Util.MD5Util;
import com.yy.security.entity.PermissionEntity;
import com.yy.security.filter.JWTAuthorizationFilter;
//import com.yy.security.filter.JwtLoginFilter;
import com.yy.security.mapper.PermissionMapper;
import com.yy.security.service.impl.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;

@Component
@EnableWebSecurity
public class SecuriytConfig extends WebSecurityConfigurerAdapter {

    private static final Log log = LogFactory.get();

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Value("${ignore.urls:}")
    private String[] ingores;

    /**
     * 添加授权账户
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.authorizeRequests();
        // 动态遍历权限
        List<PermissionEntity> permission = permissionMapper.findAllPermission();
        permission.forEach(p -> authorizeRequests.antMatchers(p.getUrl()).hasAnyAuthority(p.getPermTag()));


        authorizeRequests
                .antMatchers(HttpMethod.OPTIONS).permitAll()//跨域请求会先进行一次options请求
                .antMatchers("/**").authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .exceptionHandling() // 配置无权限认证/未登录认证
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 处理 未登录 操作
                .accessDeniedHandler(jwtAccessDeniedHandler) // 处理无权限操作
                .and()
                // 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
        log.info("加载白名单开始--------------------", Level.INFO);
        web.ignoring().antMatchers(ingores);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthorizationFilter jwtAuthenticationTokenFilter() {
        return new JWTAuthorizationFilter();
    }

}
