package com.atguigu.crowd.funding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.funding.entity.Auth;
import com.atguigu.crowd.funding.entity.AuthExample;
import com.atguigu.crowd.funding.mapper.AuthMapper;
import com.atguigu.crowd.funding.service.api.AuthService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthMapper authMapper;

	@Override
	public List<Auth> getAllAuth() {
		return this.authMapper.selectByExample(new AuthExample());
	}

	@Override
	public List<Integer> selectAssignedAuthIdList(Integer roleId) {
		return this.authMapper.selectAssignedAuthIdList(roleId);
	}

	@Override
	public void updateRelationShipBetweenRoleAndAuth(Map<String, List<Integer>> assignDataMap) {
		List<Integer> roleIdList = assignDataMap.get("roleIdList");
		List<Integer> authIdList = assignDataMap.get("authIdList");
		Integer roleId = roleIdList.get(0); // 获取角色ID
		this.authMapper.deleteOldRelationShip(roleId);
		if (CrowdFundingUtils.collectionEffective(authIdList)) {
			this.authMapper.insertNewRelationShip(roleId, authIdList);
		}
	}
}
