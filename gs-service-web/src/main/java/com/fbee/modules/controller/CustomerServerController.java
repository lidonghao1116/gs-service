package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.Constants;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.bean.CaptchaCode;
import com.fbee.modules.core.bean.SmsCode;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.GenerateCaptcha;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.FindServiceForm;
import com.fbee.modules.form.TenantsJobsForm;
import com.fbee.modules.form.TenantsRegistForm;
import com.fbee.modules.interceptor.anno.Auth;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.json.CaptchaJsonData;
import com.fbee.modules.service.CustomerServerService;
import com.fbee.modules.service.JobRecruitService;
import com.fbee.modules.service.SmsService;
import com.fbee.modules.service.TenantService;
import com.fbee.modules.utils.DictionariesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:客户服务(门店)管理控制器
 * @author ZhangJie
 */
@Controller
@RequestMapping(RequestMappingURL.CUSTOMER_SERVER_BASE_URL)
public class CustomerServerController {

	@Autowired
	CustomerServerService customerServerService;


	@Autowired
	JobRecruitService jobRecruitService;

	@Autowired
	SmsService smsService;

	@Autowired
	TenantService tenantService;

	/**
	 * 附近家政公司列表查询
	 *
	 * @param request
	 * @param response
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@Guest
	@RequestMapping(value = RequestMappingURL.QUERY_NEARBY_COMPANY, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult queryNearbyCompany(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
			FindServiceForm findServiceForm) {
		try {
			Log.info(findServiceForm);
			String ip = getRemortIP(request);
			findServiceForm.setIp(ip);
			return customerServerService.queryNearbyCompany(findServiceForm, pageNumber, pageSize);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}

	}

	/**
	 * 找服务列表查询接口
	 *
	 * @param request
	 * @param response
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@Guest
	@RequestMapping(value = RequestMappingURL.QUERY_JOB, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult queryJob(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize, String showMore,
			String serviceType,String privince,String city,String age,String salaryType,String salary,String area) {
		try {
			return customerServerService.queryJob(pageNumber, pageSize, showMore, serviceType,privince,city,age,salaryType,salary,area);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}

	}

	/**
	 * 商户入驻接口（门店名称字母和数字，作为二级域名）
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@Auth
	@RequestMapping(value = RequestMappingURL.TENANTS_REGIST, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult tenantsRegist(HttpServletRequest request, HttpServletResponse response,
			TenantsRegistForm tenantsRegistForm) {
		try {

			HttpSession session = SessionUtils.getSession(request);
			String mobileNumber = tenantsRegistForm.getMobile();
			request.getSession().setAttribute("mobile", mobileNumber);

			if(!"prod".equalsIgnoreCase(Global.getConfig("env"))){
				return customerServerService.tenantsRegist(tenantsRegistForm);
			}

			//验证图形验证码
			CaptchaCode captchaObj = (CaptchaCode) session.getAttribute(GenerateCaptcha.RANDOMCODEKEY);
			if (captchaObj == null || captchaObj.isExpired()) { // 验证码过期
                    return JsonResult.failure(ResultCode.User.CAPTCHA_TIMEOUT);
			} else if (!captchaObj.getCode().toUpperCase().equals(tenantsRegistForm.getCaptcha().toUpperCase())) {// 验证码不正确
				return JsonResult.failure(ResultCode.User.CAPTCHA_ERROR);
			}

			// 验证短信验证码
			SmsCode smsCodeObj = (SmsCode) session.getAttribute(SmsCode.REG_SMS_CODE_KEY);
			if (smsCodeObj == null || smsCodeObj.isExpired() || !mobileNumber.equals(tenantsRegistForm.getMobile())) { // 短信验证码过期
				return JsonResult.failure(ResultCode.User.MSG_CAPTCHA_TIMEOUT);
			} else if (!smsCodeObj.getCode().equals(tenantsRegistForm.getSmsCode())) {// 短信验证码不正确
				return JsonResult.failure(ResultCode.User.CODE_ERROR);
			}
			Log.info(tenantsRegistForm.toString());
			return customerServerService.tenantsRegist(tenantsRegistForm);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/*
	 * 商户入驻商户详情
	 */
	@Auth
	@RequestMapping(value = RequestMappingURL.TENANTS_REGIST_INFO, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult tenantsRegistInfo(HttpServletRequest request,HttpServletResponse response,TenantsRegistForm tenantsRegistForm) {

		HttpSession session = request.getSession();
		String mobile = (String)session.getAttribute("mobile");
		if(StringUtils.isNotBlank(mobile)) {
			return customerServerService.tenantsRegistInfo(tenantsRegistForm,mobile);
		}
		return JsonResult.failure(ResultCode.ERROR);
	}

	/**
	 * 获取相同门店名的数量
	 *
	 * @param request
	 * @param response
	 * @param storeName
	 * @return
	 */
	@Auth
	@RequestMapping(value = RequestMappingURL.GETUNIQUENAME, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getUniqueName(HttpServletRequest request, HttpServletResponse response, String storeName) {
		return customerServerService.getUniqueName(storeName);
	}

	/**
	 * 查看手机号是否已被注册
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@Auth
	@RequestMapping(value = RequestMappingURL.GETUNIQUEPHONE, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getUniquePhone(HttpServletRequest request, HttpServletResponse response, String mobile) {
		return customerServerService.getUniquePhone(mobile);
	}

	/**
	 * 获取ip
	 *
	 * @param request
	 * @return
	 */
	private String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	/**
	 * 获取短信验证码
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@Auth
	@RequestMapping(value = RequestMappingURL.MSG_CAPTCHA_URL, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getMesCaptcha(HttpServletRequest request, HttpServletResponse response,
									@RequestParam(value = "mobile") String mobile,@RequestParam(value = "captcha") String captcha){
		try {

			HttpSession session = request.getSession();

			//验证图形验证码
			CaptchaCode captchaObj = (CaptchaCode) session.getAttribute(GenerateCaptcha.RANDOMCODEKEY);
			if (captchaObj == null || captchaObj.isExpired()) { // 验证码过期
				return JsonResult.failure(ResultCode.User.CAPTCHA_TIMEOUT);
			} else if (!captchaObj.getCode().toUpperCase().equals(captcha.toUpperCase())) {// 验证码不正确
				return JsonResult.failure(ResultCode.User.CAPTCHA_ERROR);
			}
			// 获取短信验证码
			SmsCode smsCode = customerServerService.getSmsCode(mobile);
			// 获取当前日期
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String addTime = formatter.format(currentTime);
			// 获取当日验证短信条数
			int count = smsService.getCount(mobile, addTime);

			// // 验证当日注册短信次数
			if (count > 10) {
				return JsonResult.failure(ResultCode.User.OVER_ERROR);
			}
			// 清空session
			session.removeAttribute(SmsCode.REG_SMS_CODE_KEY);
			// 获取session中短信验证码对象
			// SmsCode smsCode = smsService.sendRegisterSmsCode(mobile);
			session.setAttribute("mobile", mobile);
			// 短信发送成功
			if (smsCode != null) {
				session.setAttribute(SmsCode.REG_SMS_CODE_KEY, smsCode);
				return JsonResult.success(smsCode.getCode());
			}
			// 短信发送失败
			return JsonResult.failure(ResultCode.User.SMSSEND_FAILURE);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	// 图形码验证
	@Auth
	@RequestMapping(value = RequestMappingURL.CAPTCHA_URL, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getCaptcha(HttpServletRequest request, HttpServletResponse response) {
		CaptchaJsonData jsonData = new CaptchaJsonData();
		try {
			GenerateCaptcha randomCode = new GenerateCaptcha();
			String captcha = randomCode.getRandcodeBase64(request, response, null, null);

			if (StringUtils.isNotBlank(captcha)) {
				jsonData.setCaptcha(captcha);
				return JsonResult.success(jsonData);
			} else {
				return JsonResult.failure(ResultCode.User.CAPTCHA_FAILURE);
			}
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 * 银行code
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@Auth
	@RequestMapping(value = RequestMappingURL.BANK_CODE, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult bankCode(HttpServletRequest request, HttpServletResponse response) {

		try {

			/*
			 * HttpSession session = SessionUtils.getSession(request); UserBean userBean =
			 * (UserBean) session.getAttribute(Constants.USER_SESSION); if (userBean ==
			 * null) { return JsonResult.failure(ResultCode.SESSION_TIMEOUT); }
			 */

			return tenantService.getBankCode();
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}



	/***
	 * 获取薪资范围 列表
	 */
	@Auth
	@RequestMapping(value="getSalaryRange",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getSalaryRange(){
		return new JsonResult(true, "", DictionariesUtil.setSalaryRange());
	}

	/***
	 * 获取服务类型列表
	 * @return
	 */
	@Auth
	@RequestMapping(value = "getServiceMold", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getServiceMold(HttpServletRequest request,HttpServletResponse response,
									 @RequestParam(value = "serverType", defaultValue= "" ) String serverType
	){
		return new JsonResult(true, "", DictionariesUtil.setServiceMold(serverType));
	}

	/***
	 * 获取年龄范围列表
	 * @return
	 */
	@Auth
	@RequestMapping(value= "getAgeRange",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getAgerange(){
		return new JsonResult(true, "", DictionariesUtil.setAgerange());
	}

	/***
	 * 获取服务收入列表
	 * @return
	 */
	@Auth
	@RequestMapping(value = "getServiceIncome", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getServiceIncome(HttpServletRequest request,HttpServletResponse response,
									   @RequestParam(value = "incomeKey", defaultValue= "" ) String incomeKey
	){
		return new JsonResult(true, "", DictionariesUtil.setServiceIncomeList());
	}


	@Auth
	@RequestMapping(value = "getServiceType", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JsonResult getServiceType(TenantsJobsForm tenantsJobsForm, HttpServletRequest request,
									 HttpServletResponse response) {

		try{
			return jobRecruitService.getServiceType();
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
}
