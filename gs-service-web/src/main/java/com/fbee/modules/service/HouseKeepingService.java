package com.fbee.modules.service;

import java.util.Map;

import com.fbee.modules.form.extend.StaffInfoForm;
import com.fbee.modules.jsonData.basic.JsonResult;

public interface HouseKeepingService {

	/**
	 * 根据租户id获取服务工种列表中文
	 * @param tenantId
	 * @return
	 */
	JsonResult getStaffServiceItemList(Integer tenantId);
	/**
	 * 获取服务对象与内容
	 * @return
	 */
	JsonResult getObjectContent(Map<Object,Object> map);
	/**
	 * 获取员工个人信息列表
	 * @param staffInfoForm
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	JsonResult getStaffPersonInfo(StaffInfoForm staffInfoForm,int pageNumber,int pageSize);


	/**
	 * 获取员工的视频和照片
	 * @param staffId
	 * @param type
	 * @return
	 */
	JsonResult getImgVedioList(int staffId,String type);
	/**
	 * 获取员工的证件信息
	 * @param staffId
	 * @return
	 */
	JsonResult getStaffCertsType(int staffId);
	/**
	 * 获取阿姨服务信息
	 * @return
	 */
	JsonResult getStaffServiceInfo(int staffId);
//	/**
//	 * 根据租户id和staffId获取阿姨的详情
//	 * @param tenantId
//	 * @param staffId
//	 * @return
//	 */
//	JsonResult getDetails(Integer tenantId, Integer staffId);
	/**
	 * 获取员工个人信息
	 * @param staffId
	 * @return
	 */
	JsonResult getStaffPersonInfoByPrimaryKey(int staffId);
	
}
