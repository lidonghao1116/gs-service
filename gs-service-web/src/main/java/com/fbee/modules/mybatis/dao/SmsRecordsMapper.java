package com.fbee.modules.mybatis.dao;

import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.SmsRecordsEntity;

@MyBatisDao
public interface SmsRecordsMapper extends CrudDao<SmsRecordsEntity>{
	
	/**
	 * 根据时间和手机号查询记录条数
	 */
	int getCount(Map<String , String> map);
	
}