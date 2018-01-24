package com.fbee.modules.service;

import java.math.BigDecimal;

/**
 * 公共业务层类
 * @author ZhangJie
 *
 */
public interface CommonService {
	
	/**
	 * 根据订单的类型创建订单号<br>
	 * 订单号规则为<br>
	 * 01,02,03,04代表订单类型目前就这几种类型<br>
	 * 加年月日后加5位-->
	 * @param orderType
	 * @return
	 */
	String createOrderNo(String orderType);

	void frozenAmount(Integer tenantId, BigDecimal money, String orderNo, String remarks);

	void thawAmount(Integer tenantId, BigDecimal money, String orderNo, String remarks) ;
}
