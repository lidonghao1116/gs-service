package com.fbee.modules.service;

import com.fbee.modules.core.bean.SmsCode;

public interface SmsService {

	SmsCode sendRegisterSmsCode(String mobile);


/*	SmsRecordsEntity getSmsRecordBySmsId(String smsId);

	void confirmSmsSendResult(SmsRecordsEntity entity);*/

	int getCount(String mobile, String addTime);

	SmsCode sendPaySuccess(String mobile, String companyName, String serviceName, String serviceStartTime);

	SmsCode sendPaySuccessDing(String mobile, String companyName, String serviceName);

	SmsCode sendPaymentSuccess(String mobile, String companyName, String serviceName, String serviceStartTime);
	
}
