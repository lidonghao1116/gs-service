package com.fbee.modules.mybatis.dao;

import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.ReserveOrdersCustomerInfoEntity;
import com.fbee.modules.mybatis.model.ReserveOrdersCustomerInfo;

@MyBatisDao
public interface ReserveOrdersCustomerInfoMapper extends CrudDao<ReserveOrdersCustomerInfoEntity>{
	
	int deleteByPrimaryKey(String orderNo);

    int insert(ReserveOrdersCustomerInfo record);

    int insertSelective(ReserveOrdersCustomerInfo record);

    ReserveOrdersCustomerInfo selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(ReserveOrdersCustomerInfo record);

    int updateByPrimaryKey(ReserveOrdersCustomerInfo record);

	Map<String, Object> reserveOrderDetails(String orderNo);
	
}