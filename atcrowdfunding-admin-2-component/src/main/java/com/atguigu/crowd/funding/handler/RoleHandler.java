package com.atguigu.crowd.funding.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.service.api.RoleService;
import com.github.pagehelper.PageInfo;

// @Controller
// @ResponseBody
// @Controller + @ResponseBody合并为@RestController一个注解
@RestController
public class RoleHandler {
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/role/update/role")
//	@ResponseBody
	public ResultEntity<String> editRole(Role role) {
		this.roleService.editRole(role);
		return ResultEntity.successWithoutData();
	}
	
	@RequestMapping("/role/save/role")
//	@ResponseBody
	public ResultEntity<String> saveRole(@RequestParam("roleName") String roleName) {
		this.roleService.saveRole(roleName);
		return ResultEntity.successWithoutData();
	}
	
	@RequestMapping("/role/batch/remove")
//	@ResponseBody
	public ResultEntity<String> batchRemove(@RequestBody List<Integer> roleIdList) {
		this.roleService.batchRemove(roleIdList);
		return ResultEntity.successWithoutData();
	}
	
	@RequestMapping("/role/get/list/by/id/list")
//	@ResponseBody
	public ResultEntity<List<Role>> getRoleListByIdList(@RequestBody List<Integer> roleIdList) {
		List<Role> roleList = this.roleService.getRoleListByIdList(roleIdList);
		return ResultEntity.successWithData(roleList);
	}
	
	@RequestMapping("/role/search/by/keyword")
//	@ResponseBody
	public ResultEntity<PageInfo<Role>> search(
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		// 查询数据并得到PageInfo对象
		PageInfo<Role> list = this.roleService.queryForKeywordWithPage(pageNum, pageSize, keyword);
		// 封装查询结果为对象后返回给前端
		return ResultEntity.successWithData(list);
	}
	
}
