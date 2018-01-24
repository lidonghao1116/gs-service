package com.fbee.modules.service.impl;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.bean.UsersWechatBean;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.MembersInfoMapper;
import com.fbee.modules.mybatis.dao.UsersMapper;
import com.fbee.modules.mybatis.entity.UsersEntity;
import com.fbee.modules.mybatis.model.MembersInfo;
import com.fbee.modules.service.APIUserService;
import com.fbee.modules.service.basic.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description: TODO
 * @author hzp
 * @date 2016-6-12
 *
 */
@Service
public class APIUserServiceImpl extends BaseService implements APIUserService {

	@Autowired
	UsersMapper usersMapper;

	@Autowired
	MembersInfoMapper membersInfoDao;
	
	@Override
	public JsonResult getUserByOpenId(String openId, String nickName,Integer memberId) {
		MemberBean memberBean=new MemberBean();
		try {
			UsersEntity user = usersMapper.getUserByOpenId(openId);
			if(user==null){
				user=new UsersEntity();
				user.setOpenId(openId);
				user.setAddTime(new Date());
				user.setNick(nickName);
				user.setMemberId(memberId);
				usersMapper.insert(user);//首次登录保存openid
			}else if(null != user.getMemberId()&&!"".equals(user.getMemberId())){
				MembersInfo membersInfo = membersInfoDao.selectByPrimaryKey(user.getMemberId());
				memberBean.setMobile(membersInfo.getMobile());
				memberBean.setRegisterTime(membersInfo.getRegisterTime());
				memberBean.setSalt(membersInfo.getMobile());
				memberBean.setUserStatus(membersInfo.getUserStatus());
				memberBean.setIsLocked(membersInfo.getIsLocked());
				memberBean.setId(membersInfo.getId());
				memberBean.setName(membersInfo.getName());
			}
			UsersWechatBean usersWechatBean = new UsersWechatBean();
			usersWechatBean.setOpenId(openId);
			usersWechatBean.setNick(nickName);
			if(null == user.getId()){
				user = usersMapper.getUserByOpenId(openId);
			}
			usersWechatBean.setId(user.getId());
			System.out.println(openId+"=openId========nickName=="+nickName+"=====user.getId()==="+user.getId());
			memberBean.setUsersWechatBean(usersWechatBean);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(new Integer("9999"));
		}
		return JsonResult.success(memberBean);
	}

	@Override
	public JsonResult getUserByOpenId(String openId) {
		UsersEntity user =usersMapper.getUserByOpenId(openId);
		return JsonResult.success(user);
	}
}
