package com.atguigu.crowd.funding.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public List<Admin> getAll() {
		return this.adminMapper.selectByExample(new AdminExample());
	}

	@Override
	public Admin login(String loginAcct, String userPswd) {
		// 拼接查询条件
		AdminExample adminExample = new AdminExample();
		adminExample.createCriteria().andLoginAcctEqualTo(loginAcct);
		// 根据登录帐号查询
		List<Admin> list = this.adminMapper.selectByExample(adminExample);
		if (!CrowdFundingUtils.collectionEffective(list)) {
			return null;
		}
		Admin admin = list.get(0);
		if (admin == null) {
			return null;
		}
		// 比较密码的密文
		String userPswdDatabase = admin.getUserPswd();
		String userPswdBrowser = CrowdFundingUtils.md5(userPswd);
		if (Objects.equals(userPswdDatabase, userPswdBrowser)) { // 比较两个密文是否相等
			return admin;
		}
		return null;
	}

	@Override
	public PageInfo<Admin> queryForKeywordSearch(Integer pageNum, Integer pageSize, String keyword) {
		// 调用PageHelper插件中的开启分页方法
		PageHelper.startPage(pageNum, pageSize);
		// 执行分页查询
		List<Admin> list = this.adminMapper.selectAdminListByKeyword(keyword);
		// 将List集合封装成PageInfo对象
		return new PageInfo<Admin>(list);
	}

	@Override
	public void batchRemove(List<Integer> adminIdList) {
		AdminExample adminExample = new AdminExample();
		adminExample.createCriteria().andIdIn(adminIdList);
		this.adminMapper.deleteByExample(adminExample);
	}

	@Override
	public void saveAdmin(Admin admin) {
		admin.setUserPswd(CrowdFundingUtils.md5(admin.getUserPswd())); // 密码进行MD5加密
		this.adminMapper.insert(admin);
	}

	@Override
	public Admin getAdminById(Integer adminId) {
		return this.adminMapper.selectByPrimaryKey(adminId);
	}
	
	@Override
	public void updateAdmin(Admin admin) {
		admin.setUserPswd(CrowdFundingUtils.md5(admin.getUserPswd()));
		this.adminMapper.updateByPrimaryKey(admin);
	}

}
