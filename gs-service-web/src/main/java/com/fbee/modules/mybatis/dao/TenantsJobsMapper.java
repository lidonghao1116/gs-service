package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.TenantsJobs;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface TenantsJobsMapper extends CrudDao<TenantsJobs>{

	/**
	 * 招聘信息总数
	 * @param map
	 * @return
	 */
	Integer getTenantsJobsInfoCount(Map map);
	
	/**
	 * 招聘信息列表
	 * @return
	 */
	List<Map> getTenantsJobsInfoList(Map paramMap);

	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	TenantsJobs selectByPrimaryKey(Integer id);
	
    TenantsJobs getJobByOrderNo(String orderNo);
}