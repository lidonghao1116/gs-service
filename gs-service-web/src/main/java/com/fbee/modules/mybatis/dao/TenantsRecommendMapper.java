package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsRecommendEntity;

@MyBatisDao
public interface TenantsRecommendMapper extends CrudDao<TenantsRecommendEntity>{

	/**
	 * @param tenantId
	 * @return
	 */
	List<Map<String, String>> getRecomMendListByTenantId(Integer tenantId);
	
	/**
	 * 更新阿姨推荐
	 * @param tenantId
	 * @return
	 */
	int update(TenantsRecommendEntity tenantsRecommendEntity);
}