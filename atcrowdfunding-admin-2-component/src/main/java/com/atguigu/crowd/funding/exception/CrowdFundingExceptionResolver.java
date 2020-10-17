package com.atguigu.crowd.funding.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.google.gson.Gson;

/**
 * 项目异常映射处理器
 * @author lishangxing
 */
@ControllerAdvice
public class CrowdFundingExceptionResolver {
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView catchException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 【1】异步请求返回的是JSON数据
		if (CrowdFundingUtils.checkAsyncRequest(request)) { // 如果是异步请求
			String exceptionClassName = exception.getClass().getName(); // 获取异常类型
			String message = CrowdFundingConstant.EXCEPTION_MESSAGE_MAP.get(exceptionClassName); // 根据异常类型在常量中的映射进行友好提示错误消息
			if (message == null) {
				message = "系统未知错误！";
			}
			ResultEntity<String> resultEntity = ResultEntity.failed(ResultEntity.NO_DATA, message);
			// 将resultEntity对象转换为JSON字符串
			String json = new Gson().toJson(resultEntity);
			// 将json异常数据返回给浏览器
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(json);
			return null;
		}
		// 【2】同步请求返回的是页面
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.setViewName("system-error");
		return mav;
	}
}
