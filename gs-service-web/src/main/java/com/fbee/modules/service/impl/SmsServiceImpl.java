package com.fbee.modules.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.fbee.modules.core.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbee.modules.bean.Status;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.bean.SmsCode;
import com.fbee.modules.core.sms.SmsSendResult;
import com.fbee.modules.core.sms.SmsTemplates;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.mybatis.dao.SmsRecordsMapper;
import com.fbee.modules.mybatis.entity.SmsRecordsEntity;
import com.fbee.modules.service.SendSms;
import com.fbee.modules.service.SmsService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.service.sms.SendSmsYTX;



@Service
public class SmsServiceImpl extends BaseService implements SmsService {

	private final static Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
	
	SendSms sendSms = new SendSmsYTX();
	
	@Autowired
	SmsRecordsMapper smsRecordDao;
	
	@Override
	public SmsCode sendRegisterSmsCode(String mobile) {
		SmsCode smsCode = null;
		String code = String.valueOf(new Random().nextInt(9000) + 1000); // 注册验证码4位随机数字
		int liveMinites = 10; // 有效时间10分钟
		String[] datas = new String[]{code, liveMinites + "分钟"};
		String templateId = SmsTemplates.REG_SMS_CODE; // 模板ID
		String content = generateSmsContent(templateId, datas); // 短信内容
		
		SmsRecordsEntity entity = new SmsRecordsEntity();
		entity.setMobile(mobile);
		entity.setContentType(Status.SmsContentType.REG_SMS_CODE);
		entity.setContent(content);
		entity.setAddTime(new Date());

		String sendSmsbj = Global.getConfig("sendSmsbj");
		if(null != sendSmsbj&&"yes".equals(sendSmsbj)){
			// 发送短信-正式环境预生产环境发送短信
			SmsSendResult sendResult = sendSms.send(mobile, templateId, datas);
			if (SmsSendResult.SUCCESS.equals(sendResult.getResultCode())) { // 发送成功
				entity.setResult(Status.SmsResult.SUBMIT_SUCCESS);
				entity.setSmsId(sendResult.getSmsMessageSid());
				smsCode = new SmsCode(mobile, code, liveMinites);
				smsCode.setLiveTime(System.currentTimeMillis());
			} else { // 发送失败
				entity.setResult(Status.SmsResult.SUBMIT_FAILURE);
				entity.setFailedReason(sendResult.getResultMsg());
			}

			try {
				// 保存发送短信记录
				smsRecordDao.insert(entity);
			} catch (Exception e) {
				Log.error("保存发送短信记录异常", e);
			}
		}else{
			// 发送短信-测试环境不发送
			//SmsSendResult sendResult = sendSms.send(mobile, templateId, datas);
			SmsSendResult sendResult = new SmsSendResult();
			sendResult.setResultCode("000000");
			if (SmsSendResult.SUCCESS.equals(sendResult.getResultCode())) { // 发送成功
				//entity.setResult(Status.SmsResult.SUBMIT_SUCCESS);
				//entity.setSmsId(sendResult.getSmsMessageSid());
				smsCode = new SmsCode(mobile, code, liveMinites);
				smsCode.setLiveTime(System.currentTimeMillis());
			} else { // 发送失败
				entity.setResult(Status.SmsResult.SUBMIT_FAILURE);
				entity.setFailedReason(sendResult.getResultMsg());
			}

			try {
				// 保存发送短信记录
				//smsRecordDao.insert(entity);
			} catch (Exception e) {
				log.error("保存发送短信记录异常"+e.getMessage());
			}
		}

		
		return smsCode;
	}
	
	@Override
	public  SmsCode sendPaySuccess(String mobile,String companyName,String serviceName,String serviceStartTime) {
		
		try {
		String[] datas = new String[] {companyName,serviceName,serviceStartTime};
		String templateId = SmsTemplates.PAY_SUCCESS; // 模板ID
		String content = generateSmsContent(templateId, datas); // 短信内容
		SmsRecordsEntity entity = new SmsRecordsEntity();
		entity.setMobile(mobile);
		entity.setContentType(Status.SmsContentType.PAY_SUCCESS);
		entity.setContent(content);
		
		System.out.println("sendPaySuccess->mobile:" + mobile);
		System.out.println("sendPaySuccess->content:" + content);
		// 发送短信
		SmsSendResult sendResult = sendSms.send(mobile, templateId, datas);
		if (SmsSendResult.SUCCESS.equals(sendResult.getResultCode())) { // 发送成功
			entity.setResult(Status.SmsResult.SUBMIT_SUCCESS);
			entity.setSmsId(sendResult.getSmsMessageSid());
		} else { // 发送失败
			entity.setResult(Status.SmsResult.SUBMIT_FAILURE);
			entity.setFailedReason(sendResult.getResultMsg());
		}

		
			// 保存发送短信记录
			entity.setAddTime(new Date());
			smsRecordDao.insert(entity);
		} catch (Exception e) {
			log.error("保存发送短信记录异常"+e.getMessage());
		}
		return null;
	}


	/**
	 * 生成短信内容
	 * @param templateId 短信模板ID
	 * @param datas 参数
	 * @return
	 */
	private String generateSmsContent(String templateId, String[] datas) {
		String content = SmsTemplates.getContent(templateId); // 模板内容
		if (StringUtils.isNotBlank(content)) {
			int len = datas.length;
			String key = null;
			String value = null;
			for (int i = 0; i < len; i ++) {
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

	@Override
	public int getCount(String mobile, String addTime) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("mobile", mobile);
		map.put("addTime", addTime);
		return smsRecordDao.getCount(map);
	}


	/**
	 * 尾款支付发送短信通知
	 * @author zhangsongqiang
	 * @param mobile
	 * @param companyName
	 * @param serviceName
	 * @param serviceStartTime
	 * @return
	 */
	@Override
	public  SmsCode sendPaymentSuccess(String mobile,String companyName,String serviceName,String serviceStartTime) {

		try {
			String[] datas = new String[] {companyName,serviceName,serviceStartTime};
			String templateId = SmsTemplates.PAYMENT_SUCCESS; // 模板ID
			String content = generateSmsContent(templateId, datas); // 短信内容
			SmsRecordsEntity entity = new SmsRecordsEntity();
			entity.setMobile(mobile);
			entity.setContentType(Status.SmsContentType.PAYMENT_SUCCESS);
			entity.setContent(content);

			log.info("sendPaySuccess->mobile:" + mobile);
			log.info("sendPaySuccess->content:" + content);
			// 发送短信
			SmsSendResult sendResult = sendSms.send(mobile, templateId, datas);
			if (SmsSendResult.SUCCESS.equals(sendResult.getResultCode())) { // 发送成功
				entity.setResult(Status.SmsResult.SUBMIT_SUCCESS);
				entity.setSmsId(sendResult.getSmsMessageSid());
			} else { // 发送失败
				entity.setResult(Status.SmsResult.SUBMIT_FAILURE);
				entity.setFailedReason(sendResult.getResultMsg());
			}


			// 保存发送短信记录
			entity.setAddTime(new Date());
			smsRecordDao.insert(entity);
		} catch (Exception e) {
			log.error("保存发送短信记录异常"+e.getMessage());
		}
		return null;
	}

	/**
	 * 定金支付短信通知
	 * @author zhangsongqiang
	 * @param mobile
	 * @param companyName
	 * @param serviceName
	 * @return
	 */
	@Override
	public  SmsCode sendPaySuccessDing(String mobile,String companyName,String serviceName) {

		try {
			String[] datas = new String[] {companyName,serviceName};
			String templateId = SmsTemplates.PAYDJ_SUCCESS; // 模板ID
			String content = generateSmsContent(templateId, datas); // 短信内容
			SmsRecordsEntity entity = new SmsRecordsEntity();
			entity.setMobile(mobile);
			entity.setContentType(Status.SmsContentType.PAYDJ_SUCCESS);
			entity.setContent(content);

			log.info("sendPaySuccess->mobile:" + mobile);
			log.info("sendPaySuccess->content:" + content);
			// 发送短信
			SmsSendResult sendResult = sendSms.send(mobile, templateId, datas);
			if (SmsSendResult.SUCCESS.equals(sendResult.getResultCode())) { // 发送成功
				entity.setResult(Status.SmsResult.SUBMIT_SUCCESS);
				entity.setSmsId(sendResult.getSmsMessageSid());
			} else { // 发送失败
				entity.setResult(Status.SmsResult.SUBMIT_FAILURE);
				entity.setFailedReason(sendResult.getResultMsg());
			}


			// 保存发送短信记录
			entity.setAddTime(new Date());
			smsRecordDao.insert(entity);
		} catch (Exception e) {
			log.error("保存发送短信记录异常"+e.getMessage());
		}
		return null;
	}
}
