package com.atguigu.crowd.funding.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.funding.entity.Auth;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.service.api.AuthService;
import com.atguigu.crowd.funding.service.api.RoleService;

@Controller
public class AssignHandler {
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthService authService;
	
	@RequestMapping("/assign/do/assign")
	@ResponseBody
	public ResultEntity<String> doRoleAssignAuth(@RequestBody Map<String, List<Integer>> assignDataMap) {
		System.out.println("assignDataMap = " + assignDataMap);
		this.authService.updateRelationShipBetweenRoleAndAuth(assignDataMap);
		return ResultEntity.successWithoutData();
	}
	
	@RequestMapping("/assign/get/assigned/auth/id/list")
	@ResponseBody
	public ResultEntity<List<Integer>> getAssignedAuthIdList(@RequestParam("roleId") Integer roleId) {
		System.out.println("roleId = " + roleId);
		List<Integer> authIdList = this.authService.selectAssignedAuthIdList(roleId);
		System.out.println("authIdList = " + authIdList);
		return ResultEntity.successWithData(authIdList);
	}
	
	@RequestMapping("/assign/get/all/auth")
	@ResponseBody
	public ResultEntity<List<Auth>> getAllAuth() {
		List<Auth> authList = this.authService.getAllAuth();
		return ResultEntity.successWithData(authList);
	}
	
	/**
	 * 分配角色（由于roleIdList不一定每次都有，所以设置为非必须，因为可以将用户设置为没有任何的角色，即登录进来看不到任何的菜单信息）
	 * @param roleIdList 角色ID数据
	 * @param adminId 指定的用户ID
	 * @param pageNum 页码
	 * @return 重定向到分页列表页面
	 */
	@RequestMapping("/assign/role")
	public String doAssignRole(@RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList, @RequestParam("adminId") Integer adminId, @RequestParam("pageNum") String pageNum) {
		this.roleService.updateRelationship(adminId, roleIdList);
		return "redirect:/admin/query/for/search.html?pageNum=" + pageNum;
	}
	
	@RequestMapping("/assign/to/assign/role/page")
	public String toAssignRolePage(@RequestParam("adminId") Integer adminId, Model model) {
		// 查询已分配角色
		List<Role> assignedRoleList = this.roleService.getAssignedRoleList(adminId); 
		// 查询未分配角色
		List<Role> unAssignedRoleList = this.roleService.getUnAssignedRoleList(adminId); 
		model.addAttribute("assignedRoleList", assignedRoleList);
		model.addAttribute("unAssignedRoleList", unAssignedRoleList);
		return "assign-role";
	}
}
