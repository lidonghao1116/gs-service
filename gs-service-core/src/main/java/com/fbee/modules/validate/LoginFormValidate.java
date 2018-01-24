package com.fbee.modules.validate;

import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.LoginForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;

/** 
* @ClassName: LoginFormValidate 
* @Description: TODO
* @author 贺章鹏
* @date 2016年12月28日 上午10:39:57 
*  
*/
public class LoginFormValidate {
	
	public static JsonResult validLogin(LoginForm loginForm){
		JsonResult jsonResult =JsonResult.success(null);
		
		if(loginForm==null){
			return JsonResult.failure(ResultCode.PARAMS_ERROR);
		}
		
		if(StringUtils.isBlank(loginForm.getMobile())){
			return JsonResult.failure(ResultCode.User.MOBILE_IS_NULL);
		}
		if(StringUtils.isBlank(loginForm.getCode())){
			return JsonResult.failure(ResultCode.User.CODE_IS_NULL);
		}
		if(StringUtils.isBlank(loginForm.getCode())){
			return JsonResult.failure(ResultCode.User.CODE_IS_NULL);
		}
		return jsonResult;
	}

}
