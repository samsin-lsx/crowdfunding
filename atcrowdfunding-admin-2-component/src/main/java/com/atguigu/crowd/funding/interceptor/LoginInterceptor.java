package com.atguigu.crowd.funding.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.google.gson.Gson;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Admin admin = (Admin) request.getSession().getAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN);
		if (admin == null) {
			// 【1】判断当前请求是否是异步请求
			if (CrowdFundingUtils.checkAsyncRequest(request)) {
				ResultEntity<String> resultEntity = ResultEntity.failed(ResultEntity.NO_DATA, CrowdFundingConstant.MESSAGE_LOGIN_DENIED);
				String json = new Gson().toJson(resultEntity);
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(json);
				return false;
			}
			request.setAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_LOGIN_DENIED);
			request.getRequestDispatcher("/WEB-INF/admin-login.jsp").forward(request, response);
			return false;
		}
		return true;
	}
}
