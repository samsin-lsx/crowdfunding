package com.atguigu.crowd.funding.mapper;

import com.atguigu.crowd.funding.entity.Auth;
import com.atguigu.crowd.funding.entity.AuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);
    
    /**
     * 根据角色ID查询对应的权限数据ID
     * @param roleId 角色ID
     * @return 返回对应的权限数据ID
     */
    public List<Integer> selectAssignedAuthIdList(Integer roleId);

    /**
     * 删除旧的角色权限关系表的数据
     * @param roleId 角色ID
     */
	public void deleteOldRelationShip(Integer roleId);
	
	/**
	 * 保存新的角色权限关系表的数据
	 * @param roleId 角色ID
	 * @param authIdList 权限ID数据集合
	 */
	public void insertNewRelationShip(@Param("roleId") Integer roleId, @Param("authIdList") List<Integer> authIdList);

	/**
	 * 根据用户ID查询对应的角色信息，而后再查询对应的权限信息
	 * @param id 用户ID
	 * @return 角色对应的权限集合
	 */
	public List<Auth> selectAuthListByAdminId(Integer id);
}