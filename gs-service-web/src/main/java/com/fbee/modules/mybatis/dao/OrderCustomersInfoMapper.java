package com.fbee.modules.mybatis.dao;

import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.OrderCustomersInfoEntity;
import com.fbee.modules.mybatis.model.OrderCustomersInfo;

@MyBatisDao
public interface OrderCustomersInfoMapper extends CrudDao<OrderCustomersInfoEntity>{

	int deleteByPrimaryKey(String orderNo);

    int insert(OrderCustomersInfo record);

    int insertSelective(OrderCustomersInfo record);

    OrderCustomersInfo selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(OrderCustomersInfo record);

    int updateByPrimaryKey(OrderCustomersInfo record);
    
    Map<String, Object> orderDetails(String orderNo);

}