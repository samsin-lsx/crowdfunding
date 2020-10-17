package com.atguigu.crowd.funding.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目常用工具类
 * @author lishangxing
 */
public class CrowdFundingUtils {
	private CrowdFundingUtils() {}
	
	/**
	 * 校验一个请求是否是异步请求
	 * @param request 请求对象
	 * @return 如果是异步请求返回true，否则返回false
	 */
	public static boolean checkAsyncRequest(HttpServletRequest request) {
		// 获取请求消息头信息
		String accept = request.getHeader("Accept");
		String xRequest = request.getHeader("X-Requested-With");
		// 判断请求消息头信息是否包含有目标特征
		if ((stringEffective(accept) && accept.contains("application/json")) || (stringEffective(xRequest) && xRequest.contains("XMLHttpRequest"))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证Map集合是否有效
	 * @param map 待验证的Map集合
	 * @return true表示有效、false表示无效
	 */
	public static <K, V> boolean mapEffective(Map<K, V> map) {
		return map != null && map.size() > 0;
	}
	
	/**
	 * 验证List、Set集合是否有效
	 * @param collection 待验证的集合
	 * @return true表示有效、false表示无效
	 */
	public static <E> boolean collectionEffective(Collection<E> collection) {
		return collection != null && collection.size() > 0;
	}
	
	/**
	 * 验证字符串是否有效
	 * @param source 待验证的字符串
	 * @return true表示有效、false表示无效
	 */
	public static boolean stringEffective(String source) {
		return source != null && source.length() > 0;
	}

	/**
	 * 使用MD5算法加密
	 * @param source 明文
	 * @return 加密后的密文
	 */
	public static String md5(String source) {
		// 如果传入的字符串为异常，抛出异常通知方法的调用者
		if (!stringEffective(source)) {
			throw new RuntimeException(CrowdFundingConstant.MESSAGE_CODE_INVALID);
		}
		StringBuilder builder = new StringBuilder();
		char[] characters = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5"); // 执行加密操作的核心对象
			byte[] inputBytes = source.getBytes(); // 将要加密的明文转换为字节数组
			byte[] outputBytes = digest.digest(inputBytes); // 执行加密
			for (int x = 0; x < outputBytes.length; x++) {
				byte b = outputBytes[x]; // 获取当前直接数值
				int lowValue = b & 15; // 获取低四位
				int highValue = (b >> 4) & 15; // 右移四位得到高四位
				char lowCharacter = characters[lowValue]; // 以低四位的值为下标从字符数组中获取对应的字符
				char highCharacter = characters[highValue]; // 以高四位的值为下标从字符数组中获取对应的字符
				builder.append(highCharacter).append(lowCharacter);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
