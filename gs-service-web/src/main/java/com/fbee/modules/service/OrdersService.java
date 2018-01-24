package com.fbee.modules.service;

import java.util.Map;

import org.apache.http.client.methods.HttpPost;

import com.fbee.modules.jsonData.basic.JsonResult;

/** 
* @ClassName: OrdersService 
* @Description: 订单
* @author 赵利壮
* @date 2017年1月3日 上午10:49:07 
*  
*/

public interface OrdersService {

	/**
	 * 我的-订单列表
	 * @param id
	 * @param orderstatus
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	JsonResult getOrdersList(Integer memberid, String orderstatus, int pageNumber, int pageSize);
	JsonResult getOrderWithStaffList(Integer memberid, String orderstatus, int pageNumber, int pageSize);
	
	/**
	 * 订单详情
	 * @param orderNo
	 * @return
	 */
	JsonResult orderDetails(String orderNo);
	JsonResult getOrderDetails(String orderNo);
	/**
	 * 取消订单
	 * @param orderNo
	 * @param cancleReason
	 * @return
	 */
	JsonResult cancleOrder(String orderNo,String cancleReason,String counterFee,String tradeAmount);
	/** 
	 * 获取订单
	 * @param orderNo
	 * @return
	 */
	Map<String,Object> getOrder(String orderNo);
	
	/**
	 * 支付定金、尾款,插入支付流水和订单号对应关系
	 * @param flowNo
	 * @param orderNo
	 * @return
	 */
	JsonResult payment(String flowNo,String orderNo);

	/**
	 * 更换阿姨
	 * @param orderNo
	 * @param customerRemark
	 * @return
	 */
	JsonResult changeStaff(String orderNo, String customerRemark);
	
	/**
	 * 发送http请求
	 */
	HttpPost sendBeforePayRequest();
	
	/**
	 * 发送http请求
	 */
	HttpPost sendPayRequest();
	
	HttpPost sendPcPayRequest();
	
	/**
	 * 验证金额
	 * @param orderNo
	 * @param payAmount
	 * @return
	 */
	String verificationPayAmount(String orderNo, String payAmount);



	/**
	 * 本地订单-更换阿姨
	 * @param orderNo
	 * @param type
	 * @param remark
	 * @return
	 */
	JsonResult changehs(String orderNo, String type, String remark) ;

}
