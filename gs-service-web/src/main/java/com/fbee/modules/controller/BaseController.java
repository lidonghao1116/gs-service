package com.fbee.modules.controller;

import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.service.DictionarysCacheService;
import com.fbee.modules.util.FileUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;


/**
 * 
* @ClassName: BaseController 
* @Description: TODO
* @author 贺章鹏
* @date 2016年12月27日 下午4:30:26 
*
 */
@RestController
@RequestMapping
public class BaseController {


	@Autowired
	DictionarysCacheService dictionarysCacheService;

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
	}

	@Guest
	@RequestMapping
	@ResponseBody
	public String welcome(){
		return "hello world!";
	}

	@Guest
	@RequestMapping(value = "/api", method = RequestMethod.GET)
	@ResponseBody
	public String api() throws IOException, URISyntaxException {

		String api  = FileUtil.readSourceAsString("api.json");
		return api;
	}


	/**
	 * init cache
	 * @return
	 */
	@Guest
	@RequestMapping(value = "initCache", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult initCache() {
		return dictionarysCacheService.initCacheData();
	}

}
