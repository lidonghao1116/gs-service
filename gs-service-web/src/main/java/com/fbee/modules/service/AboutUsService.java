package com.fbee.modules.service;

import java.util.List;
import java.util.Map;

/** 
* @ClassName: AboutUsService 
* @Description: 关于我们
* @author 赵利壮
* @date 2017年1月3日 上午10:49:07 
*  
*/

public interface AboutUsService {

	/**
	 * 关于我们详细信息
	 * @param tenantId
	 * @return
	 */
	Map<String, Object> getUsInfo(int tenantId);
	
}
