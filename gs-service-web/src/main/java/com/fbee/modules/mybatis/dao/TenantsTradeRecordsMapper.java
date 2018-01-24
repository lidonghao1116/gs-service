package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsTradeRecordsEntity;
import com.fbee.modules.mybatis.model.TenantsTradeRecords;

@MyBatisDao
public interface TenantsTradeRecordsMapper extends CrudDao<TenantsTradeRecordsEntity>{
	
	 /**
	  * 插入一条记录
	  * @param entity
	  * @return
	  */
	int insertRecords(TenantsTradeRecordsEntity entity);

	int deleteByPrimaryKey(String tradeNo);

    int insert(TenantsTradeRecords record);

    int insertSelective(TenantsTradeRecords record);

    TenantsTradeRecords selectByPrimaryKey(String tradeNo);

    int updateByPrimaryKeySelective(TenantsTradeRecords record);

    int updateByPrimaryKey(TenantsTradeRecords record);

}