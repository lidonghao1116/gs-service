package com.fbee.modules.mybatis.dao;


import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.entity.UsersEntity;

@MyBatisDao
public interface UsersMapper extends CrudDao<UsersEntity> {

	/**
	 * 根据用户openId获取用户
	 * @param openId
	 * @return
	 */
	UsersEntity getUserByOpenId(String openId);


}