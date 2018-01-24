package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;

@MyBatisDao
public interface TenantsUsersMapper extends CrudDao<TenantsUsersEntity>{

	/**
	 * 根据账号获取登陆用户
	 * @param loginAccount
	 * @return
	 */
	TenantsUsersEntity getByLoginAccount(String loginAccount);
	
	/**
	 * 根据手机号获取登陆用户
	 * @param telephone
	 * @return
	 */
	TenantsUsersEntity getByTelephone(String telephone);
	
	/**
	 * 添加新用户
	 * @param 
	 * @return 
	 */
	int insert(TenantsUsersEntity tenantsUsersEntity); 
	
}