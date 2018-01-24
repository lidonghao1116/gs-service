package com.fbee.modules.jsonData.extend;

import java.util.Date;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffFinanceInfoJson 
* @Description: 财务记录
* @author 贺章鹏
* @date 2017年1月5日 下午4:01:43 
*  
*/
public class StaffFinanceInfoJson implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Date addTime;//添加时间

	private String type;//收支类目
	
	private String amount;//收付金额
	
	private String operator;//操作人
	
	private String remark;//备注

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
