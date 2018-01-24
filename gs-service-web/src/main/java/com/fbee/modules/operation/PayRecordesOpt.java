package com.fbee.modules.operation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import com.fbee.modules.mybatis.model.TenantsFinanceRecords;
import com.fbee.modules.mybatis.model.TenantsTradeRecords;
import com.fbee.modules.mybatis.model.TradeRecords;

public class PayRecordesOpt {

	/**
	 * 取消定金
	 * @param InOutAmount
	 * @param tenantId
	 * @param inOutNo
	 * @return
	 */
	public TenantsFinanceRecords buildWithdjTenantsFinanceRecords(BigDecimal InOutAmount,Integer tenantId,String inOutNo) {
		TenantsFinanceRecords tenantsFinanceRecords = new TenantsFinanceRecords();
		tenantsFinanceRecords.setInOutNo(inOutNo);//交易流水号
		tenantsFinanceRecords.setTenantId(tenantId);//租户id
		tenantsFinanceRecords.setPayType("10");//交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现 06会员续费 10取消订单退定金
		tenantsFinanceRecords.setInOutAmount(InOutAmount);//交易金额
		tenantsFinanceRecords.setTransType("02");//交易方式 01线上 02线下
		tenantsFinanceRecords.setInOutType("02");//取消订单收支类型,收支类型为支出
		tenantsFinanceRecords.setAddTime(new Date());//添加时间
		tenantsFinanceRecords.setIsUsable("1"); //1代表可用 0代表逻辑删除
		tenantsFinanceRecords.setStatus("02");//01待处理 02处理中 03已处理
		tenantsFinanceRecords.setPayeeId(tenantId);//收款方id
		return tenantsFinanceRecords;
	}

	/**
	 * 插入支付总表
	 * @param xmlDataMap
	 * @param tradeFlowNo
	 * @return
	 * @throws ParseException
	 */
	public TradeRecords buildTradeRecords(Map<String, Object> xmlDataMap,String tradeFlowNo,String OrderNo,String orderStatus) throws ParseException{
		TradeRecords tradeRecords = new TradeRecords();
		tradeRecords.setTradeFlowNo(tradeFlowNo); //支付流水号
		if (orderStatus.equals("01")) {
			tradeRecords.setTradeType("04");//支付类型 01充值 02提现 03退款04支付定金05支付尾款
		}else if(orderStatus.equals("03")) {
			tradeRecords.setTradeType("05");//支付类型 01充值 02提现 03退款04支付定金05支付尾款
		}else {
			tradeRecords.setTradeType("01");//支付类型 01充值 02提现 03退款04支付定金05支付尾款
		}
		BigDecimal total_fee = new BigDecimal((String)xmlDataMap.get("total_fee"));
		BigDecimal danwei = new BigDecimal("0.01");
		tradeRecords.setTradeAmount(total_fee.multiply(danwei));//支付金额(单位换算)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		tradeRecords.setTradeTime(sdf.parse((String)xmlDataMap.get("time_end")));//支付时间
		tradeRecords.setTradeStatis("02");//支付状态 01处理中 02支付成功 03支付失败
		tradeRecords.setInitiatorType("01");//发起方类型 01客户 02租户
		tradeRecords.setOrderNo(OrderNo);//订单号
		String tradetype = (String)xmlDataMap.get("trade_type");//交易类型
		if(tradetype.equals("pay.alipay.native")){
			tradeRecords.setTradeChannel("01");//支付宝
		}else if(tradetype.equals("pay.weixin.native")){
			tradeRecords.setTradeChannel("02");//微信扫码
		}else{
			tradeRecords.setTradeChannel("03");//公众号
		}
		tradeRecords.setAddTime(new Date());//添加时间
		tradeRecords.setTradeChannelNo((String)xmlDataMap.get("out_transaction_id"));//支付渠道流水号
		tradeRecords.setIsUsable("1");//1代表可用 0代表逻辑删除
		return tradeRecords;
	}

	/**
	 * 插入充值时支付总表
	 * @param xmlDataMap
	 * @param tradeFlowNo
	 * @return
	 * @throws ParseException
	 */
	public TradeRecords buildRechargeTradeRecords(Map<String, Object> xmlDataMap,String tradeFlowNo) throws ParseException{
		TradeRecords tradeRecords = new TradeRecords();
		tradeRecords.setTradeFlowNo(tradeFlowNo); //支付流水号
		tradeRecords.setTradeType("01");//支付类型 01充值 02提现 03退款04支付定金05支付尾款
		BigDecimal total_fee = new BigDecimal((String)xmlDataMap.get("total_fee"));
		BigDecimal danwei = new BigDecimal("0.01");
		tradeRecords.setTradeAmount(total_fee.multiply(danwei));//支付金额(单位换算)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		tradeRecords.setTradeTime(sdf.parse((String)xmlDataMap.get("time_end")));//支付时间
		tradeRecords.setTradeStatis("02");//支付状态 01处理中 02支付成功 03支付失败
		tradeRecords.setInitiatorType("02");//发起方类型 01客户 02租户
		tradeRecords.setOrderNo("0000");//充值时支付表中的订单号
		String tradetype = (String)xmlDataMap.get("trade_type");//交易类型
		if(tradetype.equals("pay.alipay.native")){
			tradeRecords.setTradeChannel("01");//支付宝
		}else if(tradetype.equals("pay.weixin.native")){
			tradeRecords.setTradeChannel("02");//微信扫码
		}else{
			tradeRecords.setTradeChannel("03");//公众号
		}
		tradeRecords.setAddTime(new Date());//添加时间
		tradeRecords.setTradeChannelNo((String)xmlDataMap.get("out_transaction_id"));//支付渠道流水号
		tradeRecords.setIsUsable("1");//1代表可用 0代表逻辑删除
		return tradeRecords;
	}

	/**
	 * 充值失败时支付总表
	 * @param xmlDataMap
	 * @param tradeFlowNo
	 * @return
	 * @throws ParseException
	 */
	public TradeRecords buildFailTradeRecords(Map<String, Object> xmlDataMap,String tradeFlowNo) throws ParseException{
		TradeRecords tradeRecords = new TradeRecords();
		tradeRecords.setTradeFlowNo(tradeFlowNo); //支付流水号
		tradeRecords.setTradeType("01");//支付类型 01充值 02提现 03退款
		BigDecimal total_fee = new BigDecimal((String)xmlDataMap.get("total_fee"));
		BigDecimal danwei = new BigDecimal("0.01");
		tradeRecords.setTradeAmount(total_fee.multiply(danwei));//支付金额(单位换算)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		tradeRecords.setTradeTime(sdf.parse((String)xmlDataMap.get("time_end")));//支付时间
		tradeRecords.setTradeStatis("03");//支付状态 01处理中 02支付成功 03支付失败
		tradeRecords.setFailureMsg((String)xmlDataMap.get("pay_info"));//失败信息
		tradeRecords.setInitiatorType("02");//发起方类型 01客户 02租户
		tradeRecords.setOrderNo("0000");//充值时支付表中的订单号
		String tradetype = (String)xmlDataMap.get("trade_type");//交易类型
		if(tradetype.equals("pay.alipay.native")){
			tradeRecords.setTradeChannel("01");//支付宝
		}else if(tradetype.equals("pay.weixin.native")){
			tradeRecords.setTradeChannel("02");//微信扫码
		}else{
			tradeRecords.setTradeChannel("03");//公众号
		}
		tradeRecords.setAddTime(new Date());//添加时间
		tradeRecords.setTradeChannelNo((String)xmlDataMap.get("out_transaction_id"));//支付渠道流水号
		tradeRecords.setIsUsable("1");//1代表可用 0代表逻辑删除
		return tradeRecords;
	}

	/**
	 * 订单支付失败时支付总表
	 * @param xmlDataMap
	 * @param tradeFlowNo
	 * @param orderNo
	 * @return
	 * @throws ParseException
	 */
	public TradeRecords buildOrderFailTradeRecords(Map<String, Object> xmlDataMap,String tradeFlowNo,String orderNo) throws ParseException{
		TradeRecords tradeRecords = new TradeRecords();
		tradeRecords.setTradeFlowNo(tradeFlowNo); //支付流水号
		tradeRecords.setTradeType("01");//支付类型 01充值 02提现 03退款
		BigDecimal total_fee = new BigDecimal((String)xmlDataMap.get("total_fee"));
		BigDecimal danwei = new BigDecimal("0.01");
		tradeRecords.setTradeAmount(total_fee.multiply(danwei));//支付金额(单位换算)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		tradeRecords.setTradeTime(sdf.parse((String)xmlDataMap.get("time_end")));//支付时间
		tradeRecords.setTradeStatis("03");//支付状态 01处理中 02支付成功 03支付失败
		tradeRecords.setFailureMsg((String)xmlDataMap.get("pay_info"));//失败信息
		tradeRecords.setInitiatorType("02");//发起方类型 01客户 02租户
		tradeRecords.setOrderNo(orderNo);//订单号
		String tradetype = (String)xmlDataMap.get("trade_type");//交易类型
		if(tradetype.equals("pay.alipay.native")){
			tradeRecords.setTradeChannel("01");//支付宝
		}else if(tradetype.equals("pay.weixin.native")){
			tradeRecords.setTradeChannel("02");//微信扫码
		}else{
			tradeRecords.setTradeChannel("03");//公众号
		}
		tradeRecords.setAddTime(new Date());//添加时间
		tradeRecords.setTradeChannelNo((String)xmlDataMap.get("out_transaction_id"));//支付渠道流水号
		tradeRecords.setIsUsable("1");//1代表可用 0代表逻辑删除
		return tradeRecords;
	}

	/**
	 * 插入租户交易总表
	 * @param xmlDataMap
	 * @param orderMap
	 * @param payNo
	 * @param inOutNo
	 * @return
	 */
	public TenantsFinanceRecords buildTenantsFinanceRecords(Map<String, Object> xmlDataMap,Map<String, Object> orderMap,
															String payNo,String inOutNo) {
		TenantsFinanceRecords tenantsFinanceRecords = new TenantsFinanceRecords();
		tenantsFinanceRecords.setInOutNo(inOutNo);//交易流水号
		tenantsFinanceRecords.setTenantId((Integer)orderMap.get("TENANT_ID"));//租户id
		tenantsFinanceRecords.setStaffId((Integer)orderMap.get("STAFF_ID"));//阿姨id
		tenantsFinanceRecords.setPayNo(payNo);//支付流水号
		tenantsFinanceRecords.setPayType("01");//交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现 06会员续费 07报名费 08住宿费 09佣金费10取消订单退定金11:服务费
		BigDecimal total_fee = new BigDecimal((String)xmlDataMap.get("total_fee"));
		BigDecimal danwei = new BigDecimal("0.01");
		tenantsFinanceRecords.setInOutAmount(total_fee.multiply(danwei));//交易金额
		tenantsFinanceRecords.setInOutType("01");//收支类型 01收入 02支出
		tenantsFinanceRecords.setTransType("01");//交易方式 01线上 02线下
		tenantsFinanceRecords.setAddTime(new Date());//添加时间
		tenantsFinanceRecords.setIsUsable("1"); //1代表可用 0代表逻辑删除
		tenantsFinanceRecords.setStatus("03");//01待处理 02处理中 03已处理
		tenantsFinanceRecords.setDraweeId((Integer)orderMap.get("MEMBER_ID"));//付款方id
		tenantsFinanceRecords.setDraweeType("01");//付款方类型01客户02家政机构03业务员04家政员05平台
		tenantsFinanceRecords.setPayeeId((Integer)orderMap.get("TENANT_ID"));//收款方id
		tenantsFinanceRecords.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
		String orderStatus = (String) orderMap.get("ORDER_STATUS");//订单状态
		if(orderStatus.equals("01")){
			tenantsFinanceRecords.setRemarks("定金支付");//备注
		}else if(orderStatus.equals("03")){
			tenantsFinanceRecords.setRemarks("尾款支付");//备注
		}
		return tenantsFinanceRecords;
	}

	/**
	 * 插入账户充值租户交易总表
	 * @param xmlDataMap
	 * @param payNo
	 * @param inOutNo
	 * @return
	 */
	public TenantsFinanceRecords buildRechargeTenantsFinanceRecords(Map<String, Object> xmlDataMap,String payNo,String inOutNo,Integer tenantId) {
		TenantsFinanceRecords tenantsFinanceRecords = new TenantsFinanceRecords();
		tenantsFinanceRecords.setInOutNo(inOutNo);//交易流水号
		tenantsFinanceRecords.setTenantId(tenantId);//租户id
		tenantsFinanceRecords.setPayNo(payNo);//支付流水号
		tenantsFinanceRecords.setPayType("04");//交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现 06会员续费
		tenantsFinanceRecords.setInOutType("01");//收支类型 01收入 02支出
		BigDecimal total_fee = new BigDecimal((String)xmlDataMap.get("total_fee"));
		BigDecimal danwei = new BigDecimal("0.01");
		tenantsFinanceRecords.setInOutAmount(total_fee.multiply(danwei));//交易金额
		tenantsFinanceRecords.setTransType("01");//交易方式 01线上 02线下
		tenantsFinanceRecords.setAddTime(new Date());//添加时间
		tenantsFinanceRecords.setIsUsable("1"); //1代表可用 0代表逻辑删除
		tenantsFinanceRecords.setStatus("03");//01待处理 02处理中 03已处理
		tenantsFinanceRecords.setRemarks("账户充值");//备注
		tenantsFinanceRecords.setDraweeId(tenantId);//付款方id
		tenantsFinanceRecords.setDraweeType("02");//付款方类型 01客户02家政机构03业务员04家政员05平台
		//tenantsFinanceRecords.setPayeeId(tenantId);//收款方id 
		tenantsFinanceRecords.setPayeeType("05");//收款方类型01客户02家政机构03业务员04家政员05平台
		return tenantsFinanceRecords;
	}

	/**
	 * 插入租户资金变动轨迹表
	 * @param xmlDataMap
	 * @param orderMap
	 * @param tradeNo
	 * @param InOutNo
	 * @return
	 */
	public  TenantsTradeRecords bulidTenantsTradeRecords(Map<String, Object> xmlDataMap,Map<String, Object> orderMap,String tradeNo,String InOutNo, String orderNo){
		TenantsTradeRecords tenantsTradeRecords=new TenantsTradeRecords();
		tenantsTradeRecords.setTradeNo(tradeNo);//账户流水号
		tenantsTradeRecords.setTenantId((Integer)orderMap.get("TENANT_ID"));//租户id
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			tenantsTradeRecords.setTradeTime(sdf.parse((String)xmlDataMap.get("time_end")));//交易时间
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tenantsTradeRecords.setFinanceType("01"); //财务类型 01收入 02支出
		BigDecimal trade_amount=new BigDecimal((String)xmlDataMap.get("total_fee"));//交易金额
		BigDecimal danwei = new BigDecimal("0.01");
		tenantsTradeRecords.setTradeAmount(trade_amount.multiply(danwei));//换算之后
		tenantsTradeRecords.setStatus("02");//交易状态 01处理中 02交易成功 03交易失败
		tenantsTradeRecords.setInOutNo(InOutNo);//交易流水号
		tenantsTradeRecords.setOrderNo(orderNo);//订单编号
		String orderStatus = (String) orderMap.get("SHARE_ORDER_STATUS");//订单状态,用于判断支付定金还是尾款
		if(orderStatus.equals("01")){ //待支付定金
			tenantsTradeRecords.setTradeType("06"); //交易类型  01：充值 02：提现 03:冻结 04:解冻 05：手续费 06：支付定金 07：支付尾款 08成单奖励
			tenantsTradeRecords.setRemarks("定金支付");//备注
		}else if(orderStatus.equals("03")){ //待支付尾款
			tenantsTradeRecords.setTradeType("07"); //交易类型  01：充值 02：提现 03:冻结 04:解冻 05：手续费 06：支付定金 07：支付尾款 08成单奖励
			tenantsTradeRecords.setRemarks("尾款支付");//备注
		}
		tenantsTradeRecords.setAddTime(new Date());//添加时间
		tenantsTradeRecords.setIsUsable("1");//是否可用 0不可用 1可用
		return tenantsTradeRecords;
	}

	/**
	 * 插入账户充值租户资金变动轨迹表
	 * @param xmlDataMap
	 * @param tradeNo
	 * @param InOutNo
	 * @return
	 */
	public  TenantsTradeRecords bulidRechargeTenantsTradeRecords(Map<String, Object> xmlDataMap,String tradeNo,String InOutNo,Integer tenantId){
		TenantsTradeRecords tenantsTradeRecords=new TenantsTradeRecords();
		tenantsTradeRecords.setTradeNo(tradeNo);//账户流水号
		tenantsTradeRecords.setTenantId(tenantId);//租户id
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			tenantsTradeRecords.setTradeTime(sdf.parse((String)xmlDataMap.get("time_end")));//交易时间
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tenantsTradeRecords.setFinanceType("01"); //财务类型 01收入 02支出
		BigDecimal trade_amount=new BigDecimal((String)xmlDataMap.get("total_fee"));//交易金额
		BigDecimal danwei = new BigDecimal("0.01");
		tenantsTradeRecords.setTradeAmount(trade_amount.multiply(danwei));//换算之后
		tenantsTradeRecords.setStatus("02");//交易状态 01处理中 02交易成功 03交易失败
		tenantsTradeRecords.setInOutNo(InOutNo);//交易流水号
		tenantsTradeRecords.setTradeType("01"); //交易类型  01：充值 02：提现 03:冻结 04:解冻 05：手续费 06：支付定金 07：支付尾款 08成单奖励
		tenantsTradeRecords.setAddTime(new Date());//添加时间
		tenantsTradeRecords.setIsUsable("1");//是否可用 0不可用 1可用
		tenantsTradeRecords.setRemarks("账户充值");//备注
		return tenantsTradeRecords;
	}

	/**
	 * 插入非分享方资金变动轨迹表
	 * @param tradeTime
	 * @param tradeAmount
	 * @param tenantId
	 * @param tradeNo
	 * @param InOutNo
	 * @return
	 */
	public  TenantsTradeRecords bulidShareTenantsTradeRecords(String tradeTime,String tradeAmount,String orderSource,Integer tenantId,String tradeNo,String InOutNo){
		TenantsTradeRecords tenantsTradeRecords=new TenantsTradeRecords();
		tenantsTradeRecords.setTradeNo(tradeNo);//账户流水号
		tenantsTradeRecords.setTenantId(tenantId);//非分享方id
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			tenantsTradeRecords.setTradeTime(sdf.parse(tradeTime));//交易时间
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(orderSource.equals("01")){ //如果订单来源是淘分享订单,则是非分享方
			tenantsTradeRecords.setFinanceType("01"); //财务类型 01收入 02支出
		}else{
			tenantsTradeRecords.setFinanceType("02"); //财务类型 01收入 02支出
		}
		BigDecimal trade_amount=new BigDecimal(tradeAmount);//交易金额
		BigDecimal danwei = new BigDecimal("0.01");
		tenantsTradeRecords.setTradeAmount(trade_amount.multiply(danwei));//换算之后
		tenantsTradeRecords.setStatus("02");//交易状态 01处理中 02交易成功 03交易失败
		tenantsTradeRecords.setInOutNo(InOutNo);//交易流水号
		tenantsTradeRecords.setTradeType("08"); //交易类型  01：充值 02：提现 03:冻结 04:解冻 05：手续费 06：支付定金 07：支付尾款 08成单奖励
		tenantsTradeRecords.setAddTime(new Date());//添加时间
		tenantsTradeRecords.setIsUsable("1");//是否可用 0不可用 1可用
		tenantsTradeRecords.setRemarks("成单奖励");//备注
		return tenantsTradeRecords;
	}

	/**
	 * 插入解冻时租户的资金变动轨迹
	 * @param tradeTime
	 * @param tradeAmount
	 * @param tenantId
	 * @param tradeNo
	 * @param InOutNo
	 * @return
	 */
	public  TenantsTradeRecords bulidthawTenantsTradeRecords(Date tradeTime,String tradeAmount,Integer tenantId,String tradeNo,String InOutNo){
		TenantsTradeRecords tenantsTradeRecords = new TenantsTradeRecords();
		tenantsTradeRecords.setTradeNo(tradeNo);//账户流水号
		tenantsTradeRecords.setTenantId(tenantId);//非分享方id
		tenantsTradeRecords.setTradeTime(tradeTime);//交易时间
		tenantsTradeRecords.setFinanceType("01"); //财务类型 01收入 02支出
		BigDecimal trade_amount=new BigDecimal(tradeAmount);//交易金额
		tenantsTradeRecords.setTradeAmount(trade_amount);//换算之后交易金额(成单奖励)
		tenantsTradeRecords.setStatus("02");//交易状态 01处理中 02交易成功 03交易失败
		tenantsTradeRecords.setInOutNo(InOutNo);//交易流水号
		tenantsTradeRecords.setTradeType("04"); //交易类型  01：充值 02：提现 03:冻结 04:解冻 05：手续费 06：支付定金 07：支付尾款 08成单奖励
		tenantsTradeRecords.setAddTime(new Date());//添加时间
		tenantsTradeRecords.setIsUsable("1");//是否可用 0不可用 1可用
		tenantsTradeRecords.setRemarks("保证金冻结金额解冻");//备注
		return tenantsTradeRecords;
	}

}
