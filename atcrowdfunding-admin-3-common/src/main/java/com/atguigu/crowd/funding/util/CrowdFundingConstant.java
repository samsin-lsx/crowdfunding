package com.atguigu.crowd.funding.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目使用的常量信息
 * @author lishangxing
 */
public class CrowdFundingConstant {
	public static final String ATTR_NAME_MESSAGE = "MESSAGE";
	public static final String ATTR_NAME_LOGIN_ADMIN = "LOGIN-ADMIN";
	public static final String ATTR_NAME_PAGE_INFO = "PAGE-INFO";
	
	public static final String MESSAGE_LOGIN_FIALED = "用户名或密码不正确，请核对后重新登录！";
	public static final String MESSAGE_CODE_INVALID = "密码明文不是有效字符串，请核对密码后再进行登录！";
	public static final String MESSAGE_LOGIN_DENIED = "您还未登录，请先进行登录！";
	public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "此帐号已存在，请重新设置！";
	
	public static final Map<String, String> EXCEPTION_MESSAGE_MAP = new HashMap<>(); // 系统异常消息
	
	static {
		EXCEPTION_MESSAGE_MAP.put("java.lang.ArithmeticException", "算术异常！");
		EXCEPTION_MESSAGE_MAP.put("java.lang.RuntimeException", "运行时异常！");
		EXCEPTION_MESSAGE_MAP.put("com.atguigu.crowd.funding.LoginException", "登录异常！");
		EXCEPTION_MESSAGE_MAP.put("org.springframework.security.access.AccessDeniedException", "访问权限不足，请联系管理员！");
	}
}
