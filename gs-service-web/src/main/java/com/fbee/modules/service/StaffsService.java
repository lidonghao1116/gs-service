package com.fbee.modules.service;

import java.util.List;
import java.util.Map;

import com.fbee.modules.jsonData.basic.JsonResult;

/**
* @Description：
* @author fry
* @date 2017年1月20日 下午2:53:19
* 
*/
public interface StaffsService {
	/**
	 * 根据租户id获取推荐阿姨信息
	 * @param tenantId
	 * @return
	 */

	List<Map<String, String>> getRecomMendListByTenantId(Integer tenantId);
}

