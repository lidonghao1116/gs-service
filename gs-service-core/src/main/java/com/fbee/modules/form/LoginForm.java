package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: LoginForm 
* @Description: TODO
* @author 贺章鹏
* @date 2016年12月27日 下午6:37:39 
*  
*/
public class LoginForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String mobile;//手机号
		
	private String password;//密码
	
	private String code;//手机验证码

	private String openid;//微信公众号
	private String unionid;//微信公众号

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
}
