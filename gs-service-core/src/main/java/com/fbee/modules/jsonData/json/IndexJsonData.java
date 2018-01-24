package com.fbee.modules.jsonData.json;

import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.ModelSerializable;

/**
 * @Description：站点首页jsondata
 * @author fry
 * @date 2017年1月19日 下午2:49:16
 * 
 */
public class IndexJsonData implements ModelSerializable {

	private static final long serialVersionUID = 1L;

	private String wesiteName;// 站点名称
	
	private String contactAddress;// 联系地址
	
	private String contactPhone;// 站点联系电话
	
	private String bannerUrl;//banner URL
	
	private String contactPhone1;// 悬浮联系电话
	
	private String qqOne;
	
	private String qqTwo;
	
	private String qqThree;
	
	private String qrCode; //微信二维码
	
	
	public String getWesiteName() {
		return wesiteName;
	}

	public void setWesiteName(String wesiteName) {
		this.wesiteName = wesiteName;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	
	public String getContactPhone1() {
		return contactPhone1;
	}

	public void setContactPhone1(String contactPhone1) {
		this.contactPhone1 = contactPhone1;
	}



	public String getQqOne() {
		return qqOne;
	}

	public void setQqOne(String qqOne) {
		this.qqOne = qqOne;
	}

	public String getQqTwo() {
		return qqTwo;
	}

	public void setQqTwo(String qqTwo) {
		this.qqTwo = qqTwo;
	}

	public String getQqThree() {
		return qqThree;
	}

	public void setQqThree(String qqThree) {
		this.qqThree = qqThree;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	
	
}
