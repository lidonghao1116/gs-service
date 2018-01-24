package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsStaffsMediaEntity;

@MyBatisDao
public interface TenantsStaffsMediaMapper extends CrudDao<TenantsStaffsMediaEntity>{
	List<Map> getImgVedioList(int staffId,String type);
	/**
	 * @param staffId
	 */
	List<TenantsStaffsMediaEntity> getAllMedias(Integer staffId);
}