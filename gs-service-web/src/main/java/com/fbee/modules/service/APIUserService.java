package com.fbee.modules.service;

import com.fbee.modules.jsonData.basic.JsonResult;

/**
 * @Description: TODO
 * @author hzp
 * @date 2016-6-12
 *
 */
public interface APIUserService {

	/**
	 * 根据用户openId获取用户
	 * @param openId
	 * @param nickName 
	 * @return
	 */
	JsonResult getUserByOpenId(String openId, String nickName,Integer memberId);

	JsonResult getUserByOpenId(String openId);


}
