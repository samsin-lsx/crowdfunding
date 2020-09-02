package com.atguigu.crowd.funding.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 项目
 * 
 * @author lishangxing
 *
 */
public class CrowdFundingUtils {
	private CrowdFundingUtils() {
	}

	/**
	 * 使用
	 * @param source
	 * @return
	 */
	public static String md5(String source) {
		char[] characters = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
