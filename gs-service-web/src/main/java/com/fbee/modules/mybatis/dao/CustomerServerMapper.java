package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.AreasEntity;

@MyBatisDao
public interface CustomerServerMapper extends CrudDao<AreasEntity>{
	List<Map> queryJob(Map<String, Object> map);
	Integer queryJobCount(Map<String, Object> map);
	List<Map> queryJobLimit(Map<String, Object> map); 
	List<Map> queryNearbyCompany();
	Integer queryNearbyCompanyCount(Map<String, Object> map);
	List<Map> queryNearbyCompanyLimit(Map<String, Object> map);
}