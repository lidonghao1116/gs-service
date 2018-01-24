package com.fbee.modules.service.impl;

import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.Status;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.bean.SmsCode;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.sms.SmsSendResult;
import com.fbee.modules.core.sms.SmsTemplates;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.FindServiceForm;
import com.fbee.modules.form.TenantsRegistForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.dao.CustomerServerMapper;
import com.fbee.modules.mybatis.dao.SmsRecordsMapper;
import com.fbee.modules.mybatis.dao.TenantsApplicationMapper;
import com.fbee.modules.mybatis.dao.TenantsAppsMapper;
import com.fbee.modules.mybatis.entity.SmsRecordsEntity;
import com.fbee.modules.mybatis.model.TenantsApplication;
import com.fbee.modules.mybatis.model.TenantsApplicationExample;
import com.fbee.modules.mybatis.model.TenantsAppsExample;
import com.fbee.modules.service.CustomerServerService;
import com.fbee.modules.service.SendSms;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.DictionariesUtil;
import com.fbee.modules.utils.DictionarysCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CustomerServerServiceImpl extends BaseService implements CustomerServerService {

	@Autowired
	SendSms sendSms;
	@Autowired
	SmsRecordsMapper smsRecordMapper;
	@Autowired
	private TenantsAppsMapper tenantsAppsMapper;
	@Autowired
	TenantsApplicationMapper tenantsApplicationMapper;
	@Autowired
	CustomerServerMapper customerServerMapper;

	@Override
	public JsonResult queryNearbyCompany(FindServiceForm findServiceForm, int pageNumber, int pageSize) {
		try {

			// 直接查询数据前台分页
			if (StringUtils.isBlank(findServiceForm.getMore())) {
				List<Map> resultList = customerServerMapper.queryNearbyCompany();
				if (resultList.size() > 0) {
					for (Map map : resultList) {
						String serviceCode = (String) map.get("serviceCode");
						serviceCode = sortToStr(serviceCode);
						map.put("serviceCode", serviceCode);
						String privinceCode = (String) map.get("privince");
						map.put("privince", DictionarysCacheUtils.getProvinceName(privinceCode));
						String areaCode = (String) map.get("city");
						map.put("area", DictionarysCacheUtils.getCityName(areaCode));

					}
				}
				return JsonResult.success(resultList);
			}
			// 获取总条数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("privince", findServiceForm.getPrivince());
			map.put("city", findServiceForm.getCity());
			map.put("area", findServiceForm.getArea());
			Integer totalCount = customerServerMapper.queryNearbyCompanyCount(map);
			// 分页实体
			Page<Map> page = new Page<Map>();
			page.setPage(pageNumber);
			page.setRowNum(pageSize);
			// 最大页数判断
			int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
			if (pageM > 0) {
				page.setPage(pageM);
			}
			if (totalCount > 0) {
				map.put("offset", page.getOffset());
				map.put("pageSize", page.getRowNum());
				List<Map> resultList = customerServerMapper.queryNearbyCompanyLimit(map);
				if (resultList.size() > 0) {
					for (Map map2 : resultList) {
						String serviceCode = (String) map2.get("serviceCode");
						serviceCode = sortToStr(serviceCode);
						map2.put("serviceCode", serviceCode);
						String privinceCode = (String) map2.get("privince");
						map2.put("privince", DictionarysCacheUtils.getProvinceName(privinceCode));
						String areaCode = (String) map2.get("city");
						map2.put("area", DictionarysCacheUtils.getCityName(areaCode));
					}
				}
				page.setRows(resultList);
				page.setRecords(totalCount.longValue());
			}
			return JsonResult.success(page);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
			return JsonResult.failure(ResultCode.DATA_ERROR);
		}

	}

	private Map StrMap(Map map, String salaryQu) {
		if (salaryQu.equals("y_01")) {
			map.put("cxbs", "1");
			map.put("beginSalary", "4000");
		}
		if (salaryQu.equals("y_02")) {
			map.put("cxbs", "2");
			map.put("beginSalary", "4000");
			map.put("endSalary", "6999");
		}
		if (salaryQu.equals("y_03")) {
			map.put("cxbs", "2");
			map.put("beginSalary", "7000");
			map.put("endSalary", "9999");
		}
		if (salaryQu.equals("y_04")) {
			map.put("cxbs", "2");
			map.put("beginSalary", "10000");
			map.put("endSalary", "14999");
		}
		if (salaryQu.equals("y_05")) {
			map.put("cxbs", "3");
			map.put("beginSalary", "15000");
		}

		if (salaryQu.equals("t_01")) {
			map.put("cxbs", "1");
			map.put("beginSalary", "150");
		}
		if (salaryQu.equals("t_02")) {
			map.put("cxbs", "2");
			map.put("beginSalary", "150");
			map.put("endSalary", "249");
		}
		if (salaryQu.equals("t_03")) {
			map.put("cxbs", "2");
			map.put("beginSalary", "250");
			map.put("endSalary", "349");
		}
		if (salaryQu.equals("t_04")) {
			map.put("cxbs", "2");
			map.put("beginSalary", "350");
			map.put("endSalary", "499");
		}
		if (salaryQu.equals("t_05")) {
			map.put("cxbs", "3");
			map.put("beginSalary", "500");
		}

		if (salaryQu.equals("s_01")) {
			map.put("cxbs", "1");
			map.put("beginSalary", "30");
		}
		if (salaryQu.equals("s_02")) {
			map.put("cxbs", "2");
			map.put("beginSalary", "30");
			map.put("endSalary", "49");
		}
		if (salaryQu.equals("s_03")) {
			map.put("cxbs", "2");
			map.put("beginSalary", "50");
			map.put("endSalary", "69");
		}
		if (salaryQu.equals("s_04")) {
			map.put("cxbs", "2");
			map.put("beginSalary", "70");
			map.put("endSalary", "99");
		}
		if (salaryQu.equals("s_05")) {
			map.put("cxbs", "3");
			map.put("beginSalary", "100");
		}
		return map;
	}



	@Override
	public JsonResult queryJob(int pageNumber, int pageSize, String showMore, String serviceType,String privince,String city,String ageQu,String salaryTypeQu,String salaryQu,String area) {
		/**
		showMore = "01";
		ageQu = "00";
		salaryTypeQu = "04";
		salaryQu = "t_04";
		**/

		if(serviceType==null){
			serviceType = "";
		}
		if(privince==null){
			privince = "";
		}
		if(city==null){
			city = "";
		}
		if(ageQu==null){
			ageQu = "";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceType", serviceType);// 查询条件--服务工种
		map.put("privince", privince);//省
		map.put("city", city);//市
		map.put("area", area);//区
		if(ageQu != null){
			if(!ageQu.equals("00")){//不限
				map.put("age", ageQu);//年龄
			}
		}
		map.put("salaryType", salaryTypeQu);
		if(salaryQu != null){
			map = this.StrMap(map, salaryQu);
		}
		try {
			// 显示更多为空-->直接查询100条数据-->不用分页
			if (StringUtils.isBlank(showMore)) {
				List<Map> resultList = customerServerMapper.queryJob(map);
				for (Map result : resultList) {
					String str = "";
					str = (String) result.get("loginName");
					if(StringUtils.isNotBlank(str)){
						result.put("loginName", str.charAt(0)+"老师");
					}
					// 省
					str = (String) result.get("privince");
					if (StringUtils.isNotBlank(str)) {
						result.put("privince", DictionarysCacheUtils.getProvinceName(str));
					} else {
						result.put("privince", "");
					}
					// 市
					str = (String) result.get("city");
					if (!StringUtils.isBlank(str)) {
						result.put("city", DictionarysCacheUtils.getCityName(str));
					} else {
						result.put("city", "");
					}
					// 区
					str = (String) result.get("area");
					if (!StringUtils.isBlank(str)) {
						result.put("area", DictionarysCacheUtils.getCountyName(str));
					} else {
						result.put("area", "");
					}
					String age = (String)result.get("age");
            		if (!StringUtils.isBlank(age)) {
            			if(age.equals("01") || age.equals("02") || age.equals("03") || age.equals("04")){
            				result.put("age",DictionariesUtil.getAgerange(age));
            			}
            			if(age.equals("00")){
            				result.put("age","不限");
            			}
            		}
            		String salaryType = (String)result.get("salaryType");
            		if (!StringUtils.isBlank(salaryType)) {
            			BigDecimal b = new BigDecimal(result.get("salary").toString());
            			result.put("salary",b.setScale(2).toString());
            			result.put("salaryTypeValue", DictionariesUtil.getSalaryRange(salaryType));
            		}
				}
				return JsonResult.success(resultList);
			}
			// 获取总条数
			Integer totalCount = this.customerServerMapper.queryJobCount(map);
			// 分页实体
			Page<Map> page = new Page<Map>();
			page.setPage(pageNumber);
			page.setRowNum(pageSize);
			// 最大页数判断
			int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
			if (pageM > 0) {
				page.setPage(pageM);
			}
			if (totalCount > 0) {
				map.put("offset", page.getOffset());
				map.put("pageSize", page.getRowNum());
				List<Map> resultList = customerServerMapper.queryJobLimit(map);
				for (Map result : resultList) {
					String str = "";
					str = (String) result.get("loginName");
					if(StringUtils.isNotBlank(str)){
						result.put("loginName", str.charAt(0)+"老师");
					}
					// 省
					str = (String) result.get("privince");
					if (StringUtils.isNotBlank(str)) {
						result.put("privince", DictionarysCacheUtils.getProvinceName(str));
					} else {
						result.put("privince", "");
					}
					// 市
					str = (String) result.get("city");
					if (!StringUtils.isBlank(str)) {
						result.put("city", DictionarysCacheUtils.getCityName(str));
					} else {
						result.put("city", "");
					}
					// 区
					str = (String) result.get("area");
					if (!StringUtils.isBlank(str)) {
						result.put("area", DictionarysCacheUtils.getCountyName(str));
					} else {
						result.put("area", "");
					}
					String age = (String)result.get("age");
            		if (!StringUtils.isBlank(age)) {
            			if(age.equals("01") || age.equals("02") || age.equals("03") || age.equals("04")){
            				result.put("age",DictionariesUtil.getAgerange(age));
            			}
            			if(age.equals("00")){
            				result.put("age","不限");
            			}
            		}
            		String salaryType = (String)result.get("salaryType");
            		if (!StringUtils.isBlank(salaryType)) {
            			BigDecimal bd = new BigDecimal(result.get("salary").toString());
            			result.put("salary",bd.setScale(2).toString());
						result.put("salaryTypeValue", DictionariesUtil.getSalaryRange(salaryType));
            		}
				}
				page.setRows(resultList);
				page.setRecords(totalCount.longValue());
			}
			return JsonResult.success(page);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(ResultCode.DATA_ERROR);
		}
	}


	@Override
	public JsonResult queryJobDetail(Integer id) {
		return null;
	}


	@Override
	public JsonResult tenantsRegist(TenantsRegistForm tenantsRegistForm) {
		// 查看手机号是否被注册
		TenantsApplicationExample mobileExample = new TenantsApplicationExample();
		mobileExample.createCriteria().andRegisterPhoneEqualTo(tenantsRegistForm.getMobile());
		int mobileCount = tenantsApplicationMapper.countByExample(mobileExample);
		// 如果被注册
		if (mobileCount != 0) {
			// return JsonResult.failure(ResultCode.User.UNIQUE_PHONE_INFO);
			return JsonResult.failure(ResultCode.User.UNIQUE_STATUS_INFO);
		}

		return JsonResult.success(mobileExample);
	}

	@Override
	public JsonResult tenantsRegistInfo(TenantsRegistForm tenantsRegistForm,String mobile) {
		TenantsApplication tenantsApplication = new TenantsApplication();
		if (StringUtils.isNotBlank(mobile)) {
			Date date = new Date();
			tenantsApplication.setAddTime(date);// 申请日期
			tenantsApplication.setTenantStatus("01");// 审核状态
			tenantsApplication.setRegisterPhone(mobile);// 注册手机号
			tenantsApplication.setTenantName(tenantsRegistForm.getStoreName());// 门店名称
			tenantsApplication.setPrivince(tenantsRegistForm.getPrivince());// 省
			tenantsApplication.setCity(tenantsRegistForm.getCity());// 市
			tenantsApplication.setArea(tenantsRegistForm.getArea());// 区
			tenantsApplication.setContactAddress(tenantsRegistForm.getAddress());// 详细地址
			tenantsApplication.setTelephonenumber(tenantsRegistForm.getTelephonenumber());// 门店电话
			tenantsApplication.setAgentName(tenantsRegistForm.getApplicantName());// 申请人姓名
			tenantsApplication.setIdNumber(tenantsRegistForm.getIdCardNo());// 身份证号码
			tenantsApplication.setIdPhotopositive(tenantsRegistForm.getIdPhotopositive());// 身份证照片正面
			tenantsApplication.setIdPhotonegative(tenantsRegistForm.getIdPhotonegative());// 身份证照片反面
			tenantsApplication.setBusinessName(tenantsRegistForm.getBusinessName());// 工商名称
			tenantsApplication.setSocialInformationCode(tenantsRegistForm.getSocialInformationCode());// 社会信息代码
			tenantsApplication.setLicenceImg(tenantsRegistForm.getBusinessLicensePhoto());// 营业执照照片
			tenantsApplication.setAccountProperties(tenantsRegistForm.getAccountProperties());// 账号性质 01：个人账号 02：企业账号
			tenantsApplication.setAccountName(tenantsRegistForm.getAccountName());// 账号名称（账号所属人名称）
			tenantsApplication.setBankAccount(tenantsRegistForm.getBankAccount());// 银行账号
			tenantsApplication.setOpeningBand(tenantsRegistForm.getOpeningBand());// 开户行
			tenantsApplication.setOpeningBandBranch(tenantsRegistForm.getOpeningBandBranch());// 开户支行
			tenantsApplicationMapper.insert(tenantsApplication);
			return JsonResult.success(null);
		}
		return JsonResult.failure(ResultCode.ERROR);
	}

	/**
	 * 字符串code排序并把code转换拼成字符串
	 * 
	 * @param serviceCode
	 * @return
	 */
	private static String sortToStr(String serviceCode) {
		String serverTypeName = "";
		if (StringUtils.isBlank(serviceCode)) {
			return null;
		}
		String[] split = serviceCode.split(",");
		List<String> asList = Arrays.asList(split);
		Collections.sort(asList);
		StringBuffer sb = new StringBuffer();
		for (String string : asList) {
			serverTypeName = DictionarysCacheUtils.getServiceTypeName(string);
			sb.append(serverTypeName).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	@Override
	public SmsCode getSmsCode(String mobile) {
		SmsCode smsCode = null;
		String code = String.valueOf(new Random().nextInt(900000) + 100000); // 短信验证码6位随机数字
		int liveMinites = 10; // 有效时间10分钟
		String[] datas = new String[] { code, liveMinites + "" };
		String templateId = SmsTemplates.REG_SMS_CODE; // 模板ID
		String content = generateSmsContent(templateId, datas); // 短信内容
		SmsRecordsEntity entity = new SmsRecordsEntity();
		entity.setMobile(mobile);
		entity.setContentType(Status.SmsContentType.RESET_PWD_SMS_CODE);
		entity.setContent(content);
		// 发送短信
		SmsSendResult sendResult = sendSms.send(mobile, templateId, datas);
		if (SmsSendResult.SUCCESS.equals(sendResult.getResultCode())) { // 发送成功
			entity.setResult(Status.SmsResult.SUBMIT_SUCCESS);
			entity.setSmsId(sendResult.getSmsMessageSid());
			smsCode = new SmsCode(mobile, code, liveMinites);
			smsCode.setCreateTime(new Date().getTime());
		} else { // 发送失败
			entity.setResult(Status.SmsResult.SUBMIT_FAILURE);
			entity.setFailedReason(sendResult.getResultMsg());
		}

		try {
			// 保存发送短信记录
			entity.setAddTime(new Date());
			smsRecordMapper.insert(entity);
		} catch (Exception e) {
			Log.error("保存发送短信记录异常", e);
		}
		return smsCode;
	}

	/**
	 * 生成短信内容
	 * 
	 * @param templateId
	 *            短信模板ID
	 * @param datas
	 *            参数
	 * @return
	 */
	private String generateSmsContent(String templateId, String[] datas) {
		String content = SmsTemplates.getContent(templateId); // 模板内容
		if (StringUtils.isNotBlank(content)) {
			int len = datas.length;
			String key = null;
			String value = null;
			for (int i = 0; i < len; i++) {
				key = "{" + (i + 1) + "}";
				value = datas[i] == null ? "" : datas[i];
				try {
					content = content.replace(key, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return content;
	}

	/**
	 * 查询是否有相同门店
	 * 
	 * @param storeName
	 * @return
	 */
	public JsonResult getUniqueName(String storeName) {
		TenantsApplicationExample example = new TenantsApplicationExample();
		example.createCriteria().andTenantNameEqualTo(storeName);
		int count = tenantsApplicationMapper.countByExample(example);
		if (count != 0) {
			return JsonResult.failure(ResultCode.User.UNIQUE_NAME_INFO);
		} else {
			return JsonResult.success(null);
		}
	}

	/**
	 * 查看手机号是否已被注册
	 */
	public JsonResult getUniquePhone(String mobile) {
		TenantsAppsExample example = new TenantsAppsExample();
		example.createCriteria().andRegisterPhoneEqualTo(mobile);
		int count = tenantsAppsMapper.countByExample(example);
		if (count != 0) {
			return JsonResult.failure(ResultCode.User.UNIQUE_PHONE_INFO);
		} else {
			return JsonResult.success(null);
		}
	}

}
