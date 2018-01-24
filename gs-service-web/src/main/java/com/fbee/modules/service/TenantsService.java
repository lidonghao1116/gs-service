package com.fbee.modules.service;

import com.fbee.modules.form.ReserveOrdersForm;
import com.fbee.modules.form.ServiceInfoForm;
import com.fbee.modules.jsonData.basic.JsonResult;

/** 
* @ClassName: TenantsService 
* @Description: 租户服务
* @author 贺章鹏
* @date 2017年1月3日 上午10:49:07 
*  
*/
public interface TenantsService {
	
	/**
	 * 根据租户id获取服务工种列表中文
	 * @param tenantId
	 * @return
	 */
	JsonResult getTenantsServiceItemList(Integer tenantId);

	/**
	 * 提交需求
	 * @param tenantId
	 * @param id
	 * @param reserveordersform
	 * @return
	 */
	JsonResult subRequirements(Integer tenantId, Integer id, ReserveOrdersForm reserveordersform,ServiceInfoForm serviceinfoform);

//	/**
//	 * 新增员工信息
//	 * @param tenantId
//	 * @param staffBaseInfoForm
//	 * @return
//	 */
//	JsonResult addBaseInfo(Integer tenantId, StaffBaseInfoForm staffBaseInfoForm);
//
//	/**
//	 * 保存（新增or修改）员工（阿姨）银行卡信息
//	 * @param tenantId
//	 * @param staffBankForm
//	 * @return
//	 */
//	JsonResult saveStaffBank(Integer tenantId, StaffBankForm staffBankForm);
//
//	/**
//	 * 保存（新增or修改）员工（阿姨）保单信息
//	 * @param tenantId
//	 * @param staffPolicyForm
//	 * @return
//	 */
//	JsonResult saveStaffPolicy(Integer tenantId, StaffPolicyForm staffPolicyForm);
//
//	/**
//	 * 保存（新增or修改）员工（阿姨）求职信息
//	 * @param tenantId
//	 * @param staffJobForm
//	 * @return
//	 */
//	JsonResult saveStaffJob(Integer tenantId, StaffJobForm staffJobForm);
//
//	/**
//	 * 
//	 * @param tenantId
//	 * @param staffQueryForm
//	 * @param pageSize 
//	 * @param pageNumber 
//	 * @return
//	 */
//	JsonResult queryStaff(Integer tenantId, StaffQueryForm staffQueryForm, int pageNumber, int pageSize);
//	
//	/**
//	 * 获取员工的服务工种中文
//	 * @param tenantId
//	 * @param staffId
//	 * @return
//	 */
//	String getStaffServiceItems(Integer tenantId,Integer staffId);
//
//	/**
//	 * 根据租户id和staffId获取阿姨的详情
//	 * @param tenantId
//	 * @param staffId
//	 * @return
//	 */
//	JsonResult getDetails(Integer tenantId, Integer staffId);
//
//
//	
//	/**
//	 * 根据租户id获取服务工种中文
//	 * @param serviceItemCode
//	 * @param tenantId
//	 * @return
//	 */
//	JsonResult selectByPrimaryKey(String serviceItemCode, Integer tenantId);
//	
//	/**
//	 * 更新服务工种
//	 * @param tenantsServiceItems
//	 * @return
//	 */
//	JsonResult updateByPrimaryKeySelective(ServiceItemsForm serviceItemsForm);
//	
//	/**
//	 * 获取推荐阿姨列表
//	 * @param tenantId
//	 * @return
//	 */
//	JsonResult selectRecommendList(Integer tenantId);
//	
//	/**
//	 * 更新推荐阿姨列表
//	 * @param tenantId
//	 * @return
//	 */
//	JsonResult updateRecommend(RecommendForm recommendForm);
//	
//	/**
//	 * 获取阿姨列表
//	 * @param tenantId
//	 * @return
//	 */
//	JsonResult selectStaffInfoList(Integer tenantId, Integer pageNumber, Integer pageSize);
//	
//	/**
//	 * 获取阿姨个人信息
//	 * @param tenantId
//	 * @param staffId
//	 * @return
//	 */
//	JsonResult getStaffInfoByStaffId(Integer tenantId, Integer staffId);
//	
//	/**
//	 * 模糊查询获取阿姨列表
//	 * @param tenantId
//	 * @param staffName
//	 * @return
//	 */
//	JsonResult getStaffInfoLikeStaffName(Integer tenantId, String staffName);
	
}
