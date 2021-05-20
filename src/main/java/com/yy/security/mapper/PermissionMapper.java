package com.yy.security.mapper;

import com.yy.security.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper {

    List<PermissionEntity> findAllPermission();

}
