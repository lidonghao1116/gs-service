package com.fbee.modules.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.fbee.modules.bean.TenantsContactBarBean;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.form.TenantsAboutUsForm;
import com.fbee.modules.form.TenantsContactBarForm;
import com.fbee.modules.form.TenantsJobsForm;
import com.fbee.modules.jsonData.basic.JsonResult;

/** 
* @ClassName: WebsiteService 
* @Description: 网站管理服务接口
* @author 贺章鹏
* @date 2017年1月6日 下午1:30:27 
*  
*/
public interface WebsiteService {

	/**
	 * @param tenantId
	 * @return
	 */
	JsonResult getWebsiteIndexInfo(Integer tenantId);

	/**
	 * 租户招聘管理<br/>
	 * 获取招聘列表信息
	 * @param serviceType
	 * @param pageSize 
	 * @param pageNumber 
	 * @return
	 */
	JsonResult getTenantsJobsInfoList(String serviceType, int pageNumber, int pageSize);

	/**
	 * 租户招聘管理<br/>
	 * 保存租户招聘信息
	 * @param tenantId
	 * @param tenantsJobsForm
	 * @return
	 */
	JsonResult saveTenantsJobsInfo(Integer tenantId, TenantsJobsForm tenantsJobsForm);

	/**
	 * 租户招聘管理<br/>
	 * 获取服务工种
	 * @return
	 */
	JsonResult getServiceType();
	
	/**
	 * 租户招聘管理<br/>
	 * 根据主键获取租户招聘信息
	 * @param id
	 * @return
	 */
	JsonResult getTenantsJobsDetail(Integer id);
	
	/**
	 * 租户招聘管理<br/>
	 * 更新租户招聘信息
	 * @param id
	 * @return
	 */
	JsonResult updateTenantsJobsInfo(TenantsJobsForm tenantsJobsForm);
	/**
	 * 关于我们管理<br/>
	 * 保存关于我们图文信息组
	 * @param request 
	 * @param integer 
	 * @param tenantsAboutUsForm 
	 * @return
	 */
	JsonResult saveAboutUsInfo(HttpServletRequest request, Integer integer, TenantsAboutUsForm tenantsAboutUsForm);
	/**
	 * 关于我们管理<br/>
	 * 删除图文信息组
	 * @param id 
	 * @return
	 */
	JsonResult deleteAboutUsInfo(Integer id);
	/**
	 * 关于我们管理<br/>
	 * 获取关于我们图文信息组
	 * @param tenantId
	 * @return
	 */
	JsonResult getAboutUsInfo(Integer tenantId);

	/**
	 * 关于我们<br/>
	 * 图片上传
	 * @param file
	 * @return
	 */
	JsonResult aboutUsUpload(MultipartFile[] file);
	
	/**
	 * 联系方式
	 * 通过租户的id获取租户
	 * @param tenantId
	 * @return
	 */
	TenantsContactBarBean getContactBarByTenantId(Integer tenantId);
	
	/**
	 * 联系方式
	 * 更新
	 * @param tenantId
	 * @param tenantsContactBarForm
	 * @return
	 */
	JsonResult updateTenantsContactBar(UserBean userBean,TenantsContactBarForm tenantsContactBarForm);

	/**
	 * 联系方式
	 * 二维码图片上传
	 * @param tenantId
	 * @param file
	 * @return
	 */
	JsonResult uploadImg(Integer tenantId,MultipartFile file);

	
}
