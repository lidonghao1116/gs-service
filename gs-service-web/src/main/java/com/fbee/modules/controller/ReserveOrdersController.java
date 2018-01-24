package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.ReserveOrdersService;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** 
* @ClassName: ReserveOrdersController 
* @Description: 预约订单控制器
* @author 赵利壮
* @date 2016年12月28日 下午12:04:07 
*  
*/

@Controller
@RequestMapping(RequestMappingURL.ReserveOrders_BASE_URL)
public class ReserveOrdersController {
	
	@Autowired
	ReserveOrdersService reserveOrdersService;

	/**
	 * 我的-预约订单查询
	 * rewrite to getReserveOrderList
	 * @param request
	 * @param response
	 * @param pageNumber
	 * @param pageSize
	 * @param orderstatus
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = RequestMappingURL.RESERVEORDERS_URL, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getStaffServiceItemList(HttpServletRequest request, HttpServletResponse response,
											  @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
											  @RequestParam(value = "pageSize", defaultValue = Constants.ORDERS_PAGE_SIZE) int pageSize,
											  @RequestParam(value = "orderstatus") String orderstatus){
		try {
			HttpSession session = SessionUtils.getSession(request);
			MemberBean memeberbean=(MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
			if(memeberbean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return reserveOrdersService.getReserveOrdersList(memeberbean.getId(), orderstatus, pageNumber, pageSize);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 * 我的-预约订单查询
	 * @param request
	 * @param response
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.RESERVE_ORDER_LIST_URL, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getReserveOrderList(HttpServletRequest request, HttpServletResponse response,
											  @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
											  @RequestParam(value = "pageSize", defaultValue = Constants.ORDERS_PAGE_SIZE) int pageSize){
		try {
			HttpSession session = SessionUtils.getSession(request);
			MemberBean memeberbean=(MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
			if(memeberbean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return reserveOrdersService.getReserveWithStaffList(memeberbean.getId(), pageNumber, pageSize);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 我的-取消订单
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.CANCELRESERVEORDER_URL, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult cancellationOfReserveOrder(@RequestParam("orderNo") String orderNo, @RequestParam("cancelReason") String cancelReason){
		try {
				HttpSession session = SessionUtils.getSession();
				MemberBean memeberbean=(MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
				if(memeberbean == null){
					return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
				}
				return reserveOrdersService.cancellationOfReserveOrder(orderNo, cancelReason);
			} catch (Exception e) {
				Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
				return JsonResult.failure(ResultCode.ERROR);
			}
	}
	
	/**
	 * 我的-预约订单详情
	 * rewrite to reserveOrderDetail
	 * @param request
	 * @param response
	 * @param orderNo
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = RequestMappingURL.RESERVEORDERSDETAILS_URL, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult reserveOrderDetails(HttpServletRequest request, HttpServletResponse response,
			 String orderNo){
		try {
				HttpSession session = SessionUtils.getSession(request);
				MemberBean memeberbean=(MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
				if(memeberbean == null){
					return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
				}
				return reserveOrdersService.reserveOrderDetails(orderNo);
			} catch (Exception e) {
				Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
				return JsonResult.failure(ResultCode.ERROR);
			}
	}


	/**
	 * 我的-预约订单详情
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.RESERVE_ORDER_DETAIL_URL, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult reserveOrderDetail(String orderNo){
		try {
			HttpSession session = SessionUtils.getSession();
			MemberBean memeberbean=(MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
			if(memeberbean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return reserveOrdersService.reserveOrderDetail(orderNo);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	
}
