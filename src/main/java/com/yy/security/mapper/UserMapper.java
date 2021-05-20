package com.yy.security.mapper;


import com.yy.security.entity.PermissionEntity;
import com.yy.security.entity.UserEntity;
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
     * 查询用户的权限根据用户查询权限
     *
     * @param userName
     * @return
     */
    List<PermissionEntity> findPermissionByUsername(@Param("userName") String userName);
}