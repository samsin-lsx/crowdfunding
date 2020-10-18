package com.atguigu.crowd.funding.mapper;

import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.entity.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    public List<Role> selectRoleForKeyword(String keyword);

	List<Role> selectAssignedRoleList(Integer adminId);

	List<Role> selectUnAssignRoleList(Integer adminId);

	void deleteOldAdminRoleRelationship(Integer adminId);

	void insertNewAdminRoleRelationship(@Param("adminId") Integer adminId, @Param("roleIdList") List<Integer> roleIdList);
}