package com.fbee.modules.service;

import javax.servlet.http.HttpServletRequest;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.form.LoginForm;
import com.fbee.modules.form.MemberInfoForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.model.MembersInfo;

public interface MembersInfoService {

	/**
	 *   会员登录
	 */
	JsonResult login(LoginForm loginForm, HttpServletRequest request);
	
	/**
	 * 我的资料查询
	 * @param memeberbean
	 * @return
	 */
	JsonResult getMemberInfo(MemberBean memeberbean);
	
	/**
	 * 我的资料修改
	 * @param memeberbean
	 * @return
	 */
	JsonResult updateMemberInfo(MemberBean memeberbean,MemberInfoForm memberInfoForm);

	int getCount(String mobile, String addTime);

	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	JsonResult logout(HttpServletRequest request, Integer tenantId);

    MembersInfo getByOpenId(String openId);
}
