package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;
import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.ReserveOrdersEntity;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface ReserveOrdersMapper extends CrudDao<ReserveOrdersEntity>{
	
	/**
	 * 获取待处理订单数量
	 * @param userId
	 * @return
	 */
	String getCount(Integer userId);
	
	/**
	 * 获取用户预约订单数量
	 * @param memberid
	 * @param orderstatus
	 * @return
	 */
	Integer getReserveOrdersCount(Map<String, Object> map);
	
	/**
	 * 获取预约订单列表
	 * @param map
	 * @return
	 */
	List<ReserveOrdersEntity> getReserveOrdersList(Map<String, Object> map);

	List<ReserveOrdersEntity> getReserveWithStaffList(Map<String, Object> map);
	
	/**
	 * 取消订单
	 * @param orderNo
	 */
	void cancelReserveOrder(@Param("orderNo") String orderNo, @Param("cancelReason") String cancelreason);
	
	/**
	 * 订单详情
	 * @param orderNo
	 * @return
	 */
	Map<String, Object> reserveOrderDetails(String orderNo);

    ReserveOrdersEntity getWithStaffByOrderNo(String orderNo);
}