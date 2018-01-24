package com.fbee.modules.service.impl;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.bean.Status;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.consts.RedisKey;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.LoginForm;
import com.fbee.modules.form.MemberInfoForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.MembersInfoMapper;
import com.fbee.modules.mybatis.dao.SmsRecordsMapper;
import com.fbee.modules.mybatis.dao.TenantsOperateRecordsMapper;
import com.fbee.modules.mybatis.entity.MembersInfoEntity;
import com.fbee.modules.mybatis.entity.TenantsOperateRecordsEntity;
import com.fbee.modules.mybatis.model.MembersInfo;
import com.fbee.modules.mybatis.model.MembersInfoExample;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.service.MembersInfoService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.bean.WechatUserinfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MembersInfoServiceImpl extends BaseService implements MembersInfoService{

	private static final Logger log = LoggerFactory.getLogger(MembersInfoServiceImpl.class);
	
	@Autowired
	MembersInfoMapper membersInfoDao;

	@Autowired
	SmsRecordsMapper smsRecordDao;
	
	@Autowired
	TenantsOperateRecordsMapper operateRecorDao;

	private JedisTemplate redis = JedisUtils.getJedisTemplate();

	@Override
	@Transactional
	public JsonResult login(LoginForm loginForm, HttpServletRequest request) {
		WechatUserinfoBean wechatInfo = null;
		if(StringUtils.isNotBlank(SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN))) {
			String wechatJson = redis.get(RedisKey.User.WECHAT_INFO.getKey());
			if (StringUtils.isBlank(wechatJson)) {
				log.info("wechatJson is null in redis");
				return JsonResult.failure(ErrorCode.WX_GET_USER_INFO_ERROR);
			}
			wechatInfo = JsonUtils.fromJson(wechatJson, WechatUserinfoBean.class);
			if (wechatInfo == null) {
				log.info("wechat is null as convert from wechatJson ");
				return JsonResult.failure(ErrorCode.WX_GET_USER_INFO_ERROR);
			}
			log.info("======== wechat json ===" + JsonUtils.toJson(wechatInfo));
		}
		//根据手机号获取会员信息
		MembersInfo membersInfo = membersInfoDao.getByMobile(loginForm.getMobile());
		//手机已注册过会员
		MemberBean memberBean = new MemberBean();
	    //手机没有注册会员,自动注册会员
		if(membersInfo==null){

			MembersInfoEntity member = new MembersInfoEntity();
			member.setMobile(loginForm.getMobile());
			member.setRegisterTime(new Date());
			member.setSalt(loginForm.getMobile());
			member.setUserStatus("00");
			member.setIsLocked("0");
			if(wechatInfo != null){
				member.setOpenid(wechatInfo.getOpenid());
				member.setUnionid(wechatInfo.getUnionid());
				member.setSex(wechatInfo.getSex());
				member.setHeadImage(wechatInfo.getHeadImgUrl());
				member.setNickname(wechatInfo.getNickname());
			}
			membersInfoDao.insertMember(member);

			memberBean.setMobile(loginForm.getMobile());
			memberBean.setRegisterTime(new Date());
			memberBean.setSalt(loginForm.getMobile());
			memberBean.setUserStatus("00");
			memberBean.setIsLocked("0");
			memberBean.setId(member.getId());
			memberBean.setName(member.getName());
			//用户信息存储到redis
			if(StringUtils.isNotBlank(SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN))){
				redis.set(RedisKey.User.USER_INFO.getKey(), JsonUtils.toJson(memberBean), 30 * 24 * 60 * 60);
			}
			SessionUtils.getSession().setAttribute(Constants.MEMBER_SESSION, memberBean);
			return JsonResult.success(memberBean);
		}
		//更新登录时间,绑定微信openId
		membersInfo.setLastLoginTime(new Date());
		if(wechatInfo != null){
			if(StringUtils.isNotBlank(membersInfo.getOpenid()) && !membersInfo.getOpenid().equals(wechatInfo.getOpenid())){
				log.info(String.format("error openid for login.  wx_openid[%s], member_openid[%s]", wechatInfo.getOpenid(), membersInfo.getOpenid()));
				return JsonResult.failure(ErrorCode.WX_OPENID_ALREADY_BIND_ERROR);
			}
			membersInfo.setOpenid(wechatInfo.getOpenid());
			membersInfo.setUnionid(wechatInfo.getUnionid());
			if(StringUtils.isBlank(membersInfo.getHeadImage()) || StringUtils.isBlank(membersInfo.getNickname())){
				membersInfo.setNickname(wechatInfo.getNickname());
				membersInfo.setHeadImage(wechatInfo.getHeadImgUrl());
			}
		}

		membersInfoDao.updateByPrimaryKey(membersInfo);

		memberBean.setMobile(membersInfo.getMobile());
		memberBean.setRegisterTime(membersInfo.getRegisterTime());
		memberBean.setSalt(membersInfo.getMobile());
		memberBean.setUserStatus(membersInfo.getUserStatus());
		memberBean.setIsLocked(membersInfo.getIsLocked());
		memberBean.setId(membersInfo.getId());
		memberBean.setName(membersInfo.getName());

		//用户信息存储到redis
		redis.set(RedisKey.User.USER_INFO.getKey(), JsonUtils.toJson(memberBean));
		SessionUtils.getSession().setAttribute(Constants.MEMBER_SESSION, memberBean);
		return JsonResult.success(memberBean);
	}
	
	@Override
	public int getCount(String mobile, String addTime) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("mobile", mobile);
		map.put("addTime", addTime);
		return smsRecordDao.getCount(map);
	}
	

	/**
	 * 我的资料查询
	 */
	@Override
	public JsonResult getMemberInfo(MemberBean memeberbean) {
		MembersInfoExample example = new MembersInfoExample();
		example.createCriteria().andIdEqualTo(memeberbean.getId());
		List<MembersInfo> list = membersInfoDao.selectByExample(example);
		for(MembersInfo member:list){
			if(StringUtils.isNotBlank(member.getSex())){
				member.setSex(member.getSex()+ "," +DictionarysCacheUtils.getMemberSex(member.getSex()));
			}
			if(StringUtils.isNotBlank(member.getProvice())){
				member.setProvice(member.getProvice()+ "," +DictionarysCacheUtils.getProvinceName(member.getProvice()));
			}
			if(StringUtils.isNotBlank(member.getCity())){
				member.setCity(member.getCity()+ "," +DictionarysCacheUtils.getCityName(member.getCity()));
			}
			if(StringUtils.isNotBlank(member.getCounty())){
				member.setCounty(member.getCounty()+ "," +DictionarysCacheUtils.getCountyName(member.getCounty()));
			}
		}
		return JsonResult.success(list);
	}

	@Override
	public MembersInfo getByOpenId(String openId){
		MembersInfo member = membersInfoDao.getByOpenid(openId);
		return member;
	}

	/**
	 * 我的资料修改
	 */
	@Override
	public JsonResult updateMemberInfo(MemberBean memeberbean,MemberInfoForm memberInfoForm) {
		MembersInfo record = new MembersInfo();
		record.setId(memeberbean.getId());
		record.setName(memberInfoForm.getName());
		record.setSex(memberInfoForm.getSex());
		record.setQq(memberInfoForm.getQq());
		record.setWeixin(memberInfoForm.getWeixin());
		record.setProvice(memberInfoForm.getProvice());
		record.setCity(memberInfoForm.getCity());
		record.setCounty(memberInfoForm.getCounty());
		record.setAddress(memberInfoForm.getAddress());
		record.setHeadImage(memberInfoForm.getHeadImage());
		record.setNickname(memberInfoForm.getNickname());
		membersInfoDao.updateByPrimaryKeySelective(record);
		return JsonResult.success(null);
	}
	
	@Override
	public JsonResult logout(HttpServletRequest request, Integer tenantId) {
		HttpSession session = SessionUtils.getSession(request);
		try {
			MemberBean memberBean=(MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
			TenantsOperateRecordsEntity record = new TenantsOperateRecordsEntity();
			record.setOperateAccount(memberBean.getMobile());
			record.setOperateTime(new Date());
			record.setAction(Status.Actions.LOGOUT);
			record.setTenantId(tenantId);
			operateRecorDao.insert(record);
		} catch (Exception e) {
			Log.error(String.format(ErrorMsg.SAVE_LOGOUT_ERR, e));
		}
		session.invalidate();
		return JsonResult.success(null);
	}
	
}
