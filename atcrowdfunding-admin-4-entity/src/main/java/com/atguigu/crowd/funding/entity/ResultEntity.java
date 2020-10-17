package com.atguigu.crowd.funding.entity;

/**
 * 项目中所有的Ajax请求的统一响应格式，作为项目的开发规范
 * 
 * @author lishangxing
 * @param <T> 具体返回的数据类型
 */
public class ResultEntity<T> {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";
	public static final String NO_MESSAGE = "NO_MESSAGE";
	public static final String NO_DATA = "NO_DATA";

	private String result;
	private String message;
	private T data;
	
	/**
	 * 返回成功方法（不携带返回结果）
	 * @return 成功，不携带返回结果
	 */
	public static ResultEntity<String> successWithoutData() {
		return new ResultEntity<String>(SUCCESS, NO_MESSAGE, NO_DATA);
	}
	
	/**
	 * 返回成功方法（携带返回结果）
	 * @param <E> 指定具体类型
	 * @param data 返回的数据
	 * @param message 返回的消息
	 * @return 成功，携带返回结果和指定消息
	 */
	public static <E> ResultEntity<E> successWithData(E data) {
		return new ResultEntity<E>(SUCCESS, NO_MESSAGE, data);
	}
	
	/**
	 * 返回失败方法（携带返回结果）
	 * @param <E> 指定具体类型
	 * @param data 返回的数据
	 * @param message 返回的消息
	 * @return 失败，携带返回结果
	 */
	public static <E> ResultEntity<E> failed(E data, String message) {
		return new ResultEntity<E>(FAILED, message, data);
	}

	public ResultEntity() {
	}

	public ResultEntity(String result, String message, T data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultEntity [result=" + result + ", message=" + message + ", data=" + data + "]";
	}
}
