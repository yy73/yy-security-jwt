package com.yy.security.entity;

import lombok.Data;

@Data
public class PermissionEntity {
	private String id;
	// 权限名称
	private String permName;
	// 权限标识
	private String permTag;
	// 请求url
	private String url;
}
