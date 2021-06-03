package com.yy.security.user;

import lombok.Data;

import java.util.Date;

/**
 * @Author ywl
 * @Date 2021/5/26 20:02
 * @Description
 */
@Data
public class UserInfo {
    private String id;
    private String username;
    private String realname;
    private Date createDate;
    private Date lastLoginTime;
    private boolean enabled;
    // 账号是否到期
    private boolean accountNonExpired;
    //账号是否被锁定
    private boolean accountNonLocked;
    // 证书是否到期
    private boolean credentialsNonExpired;
}
