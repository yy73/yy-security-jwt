package com.yy.security.Util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONArray;
import com.yy.security.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ywl
 * @Date 2021/5/16 17:39
 * @Description JWT util
 */
@Component
public class JwtUtil {

    private static final Log log = LogFactory.get();

    private static final String CLAIM_KEY_CREATED = "created";


    @Value("${jwt.expirition}")
    private long expirition;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.role.claims}")
    private String claims;

    // 生成token
    public String generateJsonWebToken(UserEntity user) {
        String token = Jwts
                .builder()
                .claim(claims, user.getAuthorities())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirition * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return token;
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }


    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 获取用户角色
     *
     * @param token
     * @return
     */
    public List<SimpleGrantedAuthority> getUserRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        List roles = (List) claims.get(claims);
        String json = JSONArray.toJSONString(roles);
        List<SimpleGrantedAuthority>
                grantedAuthorityList =
                JSONArray.parseArray(json, SimpleGrantedAuthority.class);

        return grantedAuthorityList;
    }

    /**
     * 是否过期
     *
     * @param token
     * @return
     */
    public boolean isExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

    /**
     * 判断token是否已经失效
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    public Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从token中获取JWT中的负载
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());

        String compact = Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expirition))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return compact;
    }

}
