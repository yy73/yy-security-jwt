package com.yy.security.mapper;


import com.yy.security.entity.PermissionEntity;
import com.yy.security.entity.UserEntity;
import com.yy.security.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名称查询
     *
     * @param userName
     * @return
     */
    UserEntity findByUsername(@Param("userName") String userName);

    /**
     * 获取所有的用户信息
     *
     * @return
     */
    List<UserEntity> findByUsernameList();


    /**
     * 根据用户名称获取用户
     *
     * @param userName
     * @return
     */
    List<UserEntity> getUser(@Param("userName") String userName);

    /**
     * 添加用户
     *
     * @param userEntity
     * @return
     */
    int insertUser(UserEntity userEntity);

    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    UserInfo getUserInfo(@Param("userName") String userName);

    /**
     * 获取所有的用户信息
     *
     * @return
     */
    List<UserInfo> getUserInfoList();

    /**
     * 查询用户的权限根据用户查询权限
     *
     * @param userName
     * @return
     */
    List<PermissionEntity> findPermissionByUsername(@Param("userName") String userName);

}