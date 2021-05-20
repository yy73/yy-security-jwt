create table sys_permission
(
    id       int(10)      not null
        primary key,
    permName varchar(50)  null,
    permTag  varchar(50)  null,
    url      varchar(255) null comment '请求url'
);

INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES (1, '查询用户', 'showMember', '/showMember');
INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES (2, '添加用户', 'addMember', '/addMember');
INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES (3, '修改用户', 'updateMember', '/updateMember');
INSERT INTO oauth2.sys_permission (id, permName, permTag, url) VALUES (4, '删除用户', 'delMember', '/delMember');

create table sys_role
(
    id       int(10)     not null
        primary key,
    roleName varchar(50) null,
    roleDesc varchar(50) null
);

INSERT INTO oauth2.sys_role (id, roleName, roleDesc) VALUES (1, 'admin', '管理员');
INSERT INTO oauth2.sys_role (id, roleName, roleDesc) VALUES (2, 'add_user', '添加管理员');


create table sys_role_permission
(
    role_id int(10) null,
    perm_id int(10) null,
    constraint FK_Reference_3
        foreign key (role_id) references sys_role (id),
    constraint FK_Reference_4
        foreign key (perm_id) references sys_permission (id)
);

INSERT INTO oauth2.sys_role_permission (role_id, perm_id) VALUES (1, 1);
INSERT INTO oauth2.sys_role_permission (role_id, perm_id) VALUES (1, 2);
INSERT INTO oauth2.sys_role_permission (role_id, perm_id) VALUES (1, 3);
INSERT INTO oauth2.sys_role_permission (role_id, perm_id) VALUES (2, 4);
INSERT INTO oauth2.sys_role_permission (role_id, perm_id) VALUES (2, 2);


create table sys_user
(
    id                    int(10)     not null
        primary key,
    username              varchar(50) null,
    realname              varchar(50) null,
    password              varchar(50) null,
    createDate            date        null,
    lastLoginTime         date        null,
    enabled               int(5)      null,
    accountNonExpired     int(5)      null,
    accountNonLocked      int(5)      null,
    credentialsNonExpired int(5)      null
);

INSERT INTO oauth2.sys_user (id, username, realname, password, createDate, lastLoginTime, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired) VALUES (1, 'admin', '张三', '009bd57a0515cd4586f4fc5c720d1944', '2018-11-13', '2018-11-13', 1, 1, 1, 1);
INSERT INTO oauth2.sys_user (id, username, realname, password, createDate, lastLoginTime, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired) VALUES (2, 'user', '小余', '009bd57a0515cd4586f4fc5c720d1944', '2018-11-13', '2018-11-13', 1, 1, 1, 1);


create table sys_user_role
(
    user_id int(10) null,
    role_id int(10) null,
    constraint FK_Reference_1
        foreign key (user_id) references sys_user (id),
    constraint FK_Reference_2
        foreign key (role_id) references sys_role (id)
);

INSERT INTO oauth2.sys_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO oauth2.sys_user_role (user_id, role_id) VALUES (2, 2);