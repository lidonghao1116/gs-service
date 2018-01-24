package com.fbee.modules.controller;

import com.fbee.modules.bean.MemberAddressInfo;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.MemberAddressService;
import com.fbee.modules.util.WebUtils;
import com.fbee.modules.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description：用户地址管理
 * @author ian
 * @date 2017年10月24日
 * 
 */
@Controller
@RequestMapping("/api/user/address")
public class MemberAddressController {

	private final static Logger log = LoggerFactory.getLogger(MemberAddressController.class);

	@Autowired
	private MemberAddressService memberAddressService;

	/**
	 * 查询用户地址列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public JsonResult list() {
		try {
			MemberBean bean = WebUtils.getMember();
			if(bean == null){
				log.info("user need login");
				return JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
			}

			List<MemberAddressInfo> list = memberAddressService.find(bean.getId());

			return JsonResult.success(list);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(ResultCode.getMsg(ResultCode.ERROR) + e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 * 查询用户地址详情
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult get(@PathVariable("id") Integer id) {
		try {
			MemberBean bean = WebUtils.getMember();
			if(bean == null){
				log.info("user need login");
				return JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
			}

			MemberAddressInfo address = memberAddressService.get(id, bean.getId());

			return JsonResult.success(address);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(ResultCode.getMsg(ResultCode.ERROR) + e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 * 新增用户地址信息
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public JsonResult add(MemberAddressInfo address) {
		try {
			MemberBean bean = WebUtils.getMember();
			if(bean == null){
				log.info("user need login");
				return JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
			}
			log.info(JsonUtils.toJson(address));
			// valid
			if(address == null
					|| StringUtils.isBlank(address.getUserName())
					|| StringUtils.isBlank(address.getPhone())
					|| StringUtils.isBlank(address.getProvince())
					|| StringUtils.isBlank(address.getCity())
					|| StringUtils.isBlank(address.getDistrict())
					|| StringUtils.isBlank(address.getAddress())){
				return JsonResult.failure(ErrorCode.REQUEST_PARAM_EMPTY);
			}

			address.setMemberId(bean.getId());
			address.setAddAccount(bean.getName());

			Integer count = memberAddressService.count(bean.getId());
			if(count >= 3){
				log.info("address is too much, can not greater then 3");
				return JsonResult.failure(ErrorCode.ADD_COUNT_TOO_MUCH_ERROR);
			}
			memberAddressService.add(address, bean.getId());

			return JsonResult.success(address.getId());

		} catch (Exception e) {
			e.printStackTrace();
			log.error(ResultCode.getMsg(ResultCode.ERROR) + e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 * 修改用户地址信息
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult update(@PathVariable("id") Integer id, MemberAddressInfo address) {
		try {
			MemberBean bean = WebUtils.getMember();
			if(bean == null){
				log.info("user need login");
				return JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
			}
			// valid
			if(address == null
					|| StringUtils.isBlank(address.getUserName())
					|| StringUtils.isBlank(address.getPhone())
					|| StringUtils.isBlank(address.getProvince())
					|| StringUtils.isBlank(address.getCity())
					|| StringUtils.isBlank(address.getDistrict())
					|| StringUtils.isBlank(address.getAddress())){
				return JsonResult.failure(ErrorCode.REQUEST_PARAM_EMPTY);
			}
			address.setId(id);
			address.setModifyAccount(bean.getName());
			memberAddressService.update(address, bean.getId());
			return JsonResult.success("");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ResultCode.getMsg(ResultCode.ERROR) + e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 * 修改用户默认地址
	 * @return
	 */
	@RequestMapping(value = "/{id}/@default", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult updateDefault( @PathVariable("id") Integer id) {
		try {
			MemberBean bean = WebUtils.getMember();
			if(bean == null){
				log.info("user need login");
				return JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
			}

			memberAddressService.updateDefault(id, bean.getId(), bean.getName());

			return JsonResult.success("");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ResultCode.getMsg(ResultCode.ERROR) + e.getMessage());
			return JsonResult.failure(ResultCode.ERROR);
		}
	}


	/**
	 * 删除用户地址信息
	 * @return
	 */
	@RequestMapping(value = "/{id}/@delete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete( @PathVariable("id") Integer id) {
		try {
			MemberBean bean = WebUtils.getMember();
			if(bean == null){
				log.info("user need login");
				return JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
			}

			memberAddressService.delete(id, bean.getId(), bean.getName());

			return JsonResult.success("");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ResultCode.getMsg(ResultCode.ERROR) + e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	@RequestMapping(value = "/newOne", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getNewOne(){
		try {
			MemberBean bean = WebUtils.getMember();
			if(bean == null){
				log.info("user need login");
				return JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
			}
			MemberAddressInfo memberAddressInfo = memberAddressService.findNewOne(bean.getId());
			return JsonResult.success(memberAddressInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ResultCode.getMsg(ResultCode.ERROR) + e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
}
