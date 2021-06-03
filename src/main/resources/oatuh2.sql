create table sys_permission
(
    id       varchar(32)  not null,
    permName varchar(50)  null,
    permTag  varchar(50)  null,
    url      varchar(255) null comment '请求url'
);

INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES ('1', '查询用户', '/showMember', '/showMember');
INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES ('2', '添加用户', '/addMember', '/addMember');
INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES ('3', '修改用户', '/updateMember', '/updateMember');
INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES ('4', '删除用户', '/delMember', '/delMember');
INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES ('5', 'token验证', '/jwt/isTokenExpired', '/jwt/isTokenExpired');
INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES ('6', '获取token名称', '/jwt/getUsername', '/jwt/getUsername');
INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES ('7', '刷新token', '/jwt/refreshToken', '/jwt/refreshToken');


create table sys_role
(
    id       varchar(32) not null
        primary key,
    roleName varchar(50) null,
    roleDesc varchar(50) null
);

INSERT INTO oauth2.sys_role (id, roleName, roleDesc) VALUES ('1', 'admin', '管理员');
INSERT INTO oauth2.sys_role (id, roleName, roleDesc) VALUES ('2', 'add_user', '添加管理员');


create table sys_role_permission
(
    id      varchar(32) not null
        primary key,
    perm_id varchar(32) null,
    role_id varchar(32) null
);

INSERT INTO oauth2.sys_role_permission (id, perm_id, role_id) VALUES ('1', '1', '1');
INSERT INTO oauth2.sys_role_permission (id, perm_id, role_id) VALUES ('2', '2', '1');
INSERT INTO oauth2.sys_role_permission (id, perm_id, role_id) VALUES ('3', '3', '1');
INSERT INTO oauth2.sys_role_permission (id, perm_id, role_id) VALUES ('4', '4', '2');
INSERT INTO oauth2.sys_role_permission (id, perm_id, role_id) VALUES ('5', '2', '2');
INSERT INTO oauth2.sys_role_permission (id, perm_id, role_id) VALUES ('6', '5', '1');


create table sys_user
(
    id                    varchar(32)  not null
        primary key,
    username              varchar(50)  null,
    realname              varchar(50)  null,
    password              varchar(256) null,
    createDate            date         null,
    lastLoginTime         date         null,
    enabled               int(5)       null,
    accountNonExpired     int(5)       null,
    accountNonLocked      int(5)       null,
    credentialsNonExpired int(5)       null
);

INSERT INTO oauth2.sys_user (id, username, realname, password, createDate, lastLoginTime, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired) VALUES ('-1896518299', 'lisi', '李四', '$2a$10$EQiyDflZ5iNecFfa/0f.9uT8mjrioa5f/WVSW88DlyRGDyp8m9Lly', null, null, 1, 1, 1, 1);
INSERT INTO oauth2.sys_user (id, username, realname, password, createDate, lastLoginTime, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired) VALUES ('1', 'admin', '张三', '$2a$10$EQiyDflZ5iNecFfa/0f.9uT8mjrioa5f/WVSW88DlyRGDyp8m9Lly', '2018-11-13', '2018-11-13', 1, 1, 1, 1);
INSERT INTO oauth2.sys_user (id, username, realname, password, createDate, lastLoginTime, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired) VALUES ('2', 'user', '小余', '$2a$10$EQiyDflZ5iNecFfa/0f.9uT8mjrioa5f/WVSW88DlyRGDyp8m9Lly', '2018-11-13', '2018-11-13', 1, 1, 1, 1);
INSERT INTO oauth2.sys_user (id, username, realname, password, createDate, lastLoginTime, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired) VALUES ('ee3635685eb9427fa3ba1fe638c6ef19', '15736950515', '未央', '$2a$10$ajSoQ9vCDQvs6asLrlZmFeWlKlqTMhbUfpY0VmYkmI94e8nyojrHq', null, null, 1, 1, 1, 1);



create table sys_user_role
(
    id      varchar(32) not null
        primary key,
    role_id varchar(32) null,
    user_id varchar(32) null
);

INSERT INTO oauth2.sys_user_role (id, role_id, user_id) VALUES ('1', '1', '1');
INSERT INTO oauth2.sys_user_role (id, role_id, user_id) VALUES ('2', '2', '2');