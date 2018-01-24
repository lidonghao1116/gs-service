package com.fbee.modules.service;

import com.fbee.modules.jsonData.basic.JsonResult;

/** 
* @ClassName: ReserveOrdersService 
* @Description: 预约订单
* @author 赵利壮
* @date 2017年1月3日 上午10:49:07 
*  
*/

public interface ReserveOrdersService {

	/**
	 * 预约订单列表查询
	 * rewrite to getReserveWithStaffList
	 * @return
	 */
	@Deprecated
	JsonResult getReserveOrdersList(Integer memberid,String orderstatus,int pageNumber, int pageSize);

	JsonResult getReserveWithStaffList(Integer memberid,int pageNumber, int pageSize);
	
	/**
	 * 取消订单
	 * @param orderNo
	 * @return
	 */
	JsonResult cancellationOfReserveOrder(String orderNo, String cancelReason);
	
	/**
	 * 预约订单详情
	 * @param orderNo
	 * @return
	 */
	@Deprecated
	JsonResult reserveOrderDetails(String orderNo);

    JsonResult reserveOrderDetail(String orderNo);
}

