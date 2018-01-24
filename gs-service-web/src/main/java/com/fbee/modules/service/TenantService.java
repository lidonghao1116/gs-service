package com.fbee.modules.service;

import java.util.List;
import java.util.Map;

import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.form.ReserveOrdersForm;
import com.fbee.modules.form.ServiceInfoForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.json.IndexJsonData;

/**
* @Description：站点首页接口
* @author fry
* @date 2017年1月19日 下午2:38:29
* 
*/
public interface TenantService {
	
	/**
	 * 根据租户id获取站点基本信息
	 * @param tenantId
	 * @return
	 */
	IndexJsonData getIndexInfo(Integer tenantId);
	/**
	 * 根据租户id获取服务项目信息
	 * @param tenantId
	 * @return
	 */
	
	List<Map<String, String>> getServiceItems(Integer tenantId);
	
	/**
	 * 根据租户id获取服务工种列表中文
	 * @param tenantId
	 * @return
	 */
	JsonResult getTenantsServiceItemList(Integer tenantId);

	/**
	 * 通过二级域名获取租户id
	 * @param domain
	 * @return
	 */
	Integer getTenantIdByDomain(String domain);
	
	/**
	 * 查询支付结果
	 * @param flowNo
	 * @return
	 */
	JsonResult getPayResult(String flowNo);

	String getPayOrderNo(String flowNo);

	/**
	 * 根据租户ID获取二级域名
	 * @param id
	 * @return
	 */
	String getDomainByTenantId(String id);
	
	/**
	 * 
	 * @author xiehui
	 * 阿姨预约面试提交
	 */
	public JsonResult subRequirementsQuery(Integer tenantId, MemberBean memeberbean, ReserveOrdersForm reserveordersform,
			ServiceInfoForm serviceinfoform);


	/**
	 *
	 * @author Baron
	 * 获取租户logo地址
	 */
	public JsonResult getlogopath(String domain);
	
	/**
	 * 银行列表
	 * @return
	 */
	public JsonResult getBankCode();
}

