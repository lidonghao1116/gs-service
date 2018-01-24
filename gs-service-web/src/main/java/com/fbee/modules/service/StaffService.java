package com.fbee.modules.service;

import com.fbee.modules.jsonData.basic.JsonResult;

/** 
* @ClassName: StaffService 
* @Description: 阿姨
* @author 赵利壮
* @date 2017年1月3日 上午10:49:07 
*  
*/

public interface StaffService {
	
	
	/**
	 * 阿姨详情
	 * @param staffId
	 * @param tenantId
	 * @return
	 */
	JsonResult getStaffInfo(String staffId,String tenantId);

}
