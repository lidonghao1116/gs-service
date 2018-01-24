package com.fbee.modules.service.impl;

import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.mybatis.dao.TenantsStaffsInfoMapper;
import com.fbee.modules.mybatis.dao.TsysMaxNoMapper;
import com.fbee.modules.mybatis.model.TenantsStaffsInfo;
import com.fbee.modules.mybatis.model.TsysMaxNo;
import com.fbee.modules.mybatis.model.TsysMaxNoExample;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.DealAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 工共实现类
 * @author ZhangJie
 *
 */
@Service
public class CommonServiceImpl implements CommonService{

	@Autowired
	TsysMaxNoMapper tSysMaxNoMapper;
	@Autowired
	DealAccountService dealAccountService;
	@Autowired
	TenantsStaffsInfoMapper tenantsStaffsInfoMapper;
	
	@Override
	public synchronized String createOrderNo(String orderType) {
		if (StringUtils.isBlank(orderType)) {
			return null;
		}
		String maxNo = "";
		//获取日期
		String currentDate = DateUtils.dateToStr(new Date(), "yyyy-MM-dd"); 
		currentDate = currentDate.replaceAll("-", "");
		StringBuffer sb = new StringBuffer();
		sb.append(orderType);
		sb.append(currentDate);
		TsysMaxNoExample sysMaxNoExample = new TsysMaxNoExample();
		sysMaxNoExample.createCriteria().andMaxNoLike(sb.toString()+"%").andMaxNoTypeEqualTo(orderType);
		List<TsysMaxNo> maxNoList = tSysMaxNoMapper.selectByExample(sysMaxNoExample);
		if (maxNoList!=null&&maxNoList.size()>0){
			TsysMaxNo sysMaxNo = maxNoList.get(0);
			String orderNo =  sysMaxNo.getMaxNo().substring(2);
			maxNo = String.valueOf(Long.parseLong(orderNo)+1);
			sysMaxNo.setMaxNo(orderType+maxNo);
			tSysMaxNoMapper.updateByPrimaryKey(sysMaxNo);
		}else {
			TsysMaxNo sysMaxNo = new TsysMaxNo();
			maxNo = sb.append("00001").toString();
			sysMaxNo.setSysMaxNoId(null);
			sysMaxNo.setMaxNo(maxNo);
			sysMaxNo.setMaxNoType(orderType);
			tSysMaxNoMapper.insert(sysMaxNo);
			return maxNo;
		}
		return orderType+maxNo;
	}

	@Override
	public void frozenAmount(Integer tenantId, BigDecimal money, String orderNo, String remarks){
		
		String bussType = "03" ;//交易类型 01：充值 02：提现 03:冻结 04:解冻 05：手续费
		String payType = "02" ; //财务类型 01收入 02支出
		String state = "02" ;   //交易状态 01处理中 02已处理 03交易失败
		//冻结200块保证金-->插入租户交易轨迹表
		dealAccountService.dealAccountTrace(tenantId, bussType, payType, state, money, orderNo, remarks);
		//更新账户总表
		dealAccountService.dealAccount(tenantId, bussType, payType, state, money);

	}

	@Override
	public void thawAmount(Integer tenantId, BigDecimal money, String orderNo, String remarks) {

		String bussType = "04" ;//交易类型 01：充值 02：提现 03:冻结 04:解冻 05：手续费
		String payType = "01" ; //财务类型 01收入 02支出
		String state = "02" ;   //交易状态 01处理中 02已处理 03交易失败
		//解冻200块保证金-->插入租户交易轨迹表
		dealAccountService.dealAccountTrace(tenantId, bussType, payType, state, money, orderNo, remarks);
		//更新账户总表
		dealAccountService.dealAccount(tenantId, bussType, payType, state, money);
	}
	
	/**
	 * 提示提交成功，将当前阿姨状态置为"01-分享中 02-未分享"
	 * @param staffId
	 * @param type 分享状态 01分享中 02未分享 
	 * @return
	 */
	public void updateStaffStatus(Integer staffId,String type){
		TenantsStaffsInfo tenantsStaffsInfo = tenantsStaffsInfoMapper.selectByPrimaryKey2(staffId);
		tenantsStaffsInfo.setShareStatus(type);
		tenantsStaffsInfo.setModifyTime(new Date());
		tenantsStaffsInfoMapper.updateByPrimaryKey(tenantsStaffsInfo);
	}
}
