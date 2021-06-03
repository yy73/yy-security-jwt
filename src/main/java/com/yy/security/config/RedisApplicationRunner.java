package com.yy.security.config;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.yy.security.Util.RedisUtil;
import com.yy.security.entity.PermissionEntity;
import com.yy.security.entity.UserEntity;
import com.yy.security.mapper.UserMapper;
import com.yy.security.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author ywl
 * @Date 2021/6/3 10:56
 * @Description 启动时 加载redis数据 到缓存中
 */
@Component
public class RedisApplicationRunner implements ApplicationRunner {

    private static final Log log = LogFactory.get();

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisUtil.removeAll("user:*");
        log.info("------->>>>>>>>> 清除用户信息成功");
        List<UserInfo> userInfoList = userMapper.getUserInfoList();
        for (UserInfo userInfo : userInfoList) {
            redisUtil.set("user:userInfo:" + userInfo.getUsername(), JSONUtil.toJsonStr(userInfo));
        }


        List<UserEntity> usernameList = userMapper.findByUsernameList();
        for (UserEntity userEntity : usernameList) {
            redisUtil.set("user:userDetail:" + userEntity.getUsername(), JSONUtil.toJsonStr(userEntity));

            List<PermissionEntity> permission = userMapper.findPermissionByUsername(userEntity.getUsername());
            redisUtil.set("user:permission:" + userEntity.getUsername(), JSONUtil.toJsonStr(permission));
        }
        log.info("添加用户信息成功<<<<<<<<--------------");

    }
}
