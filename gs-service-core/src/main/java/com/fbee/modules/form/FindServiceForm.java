package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

public class FindServiceForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String privince;//省
	private String city;//市
	private String area;//区
	private String more;//查看更多
	private String ip;//ip
	
	public String getMore() {
		return more;
	}
	public void setMore(String more) {
		this.more = more;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPrivince() {
		return privince;
	}
	public void setPrivince(String privince) {
		this.privince = privince;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
	public String toString() {
		System.out.println();
		return "FindServiceForm [privince=" + privince + ", city=" + city + ", area=" + area + "]";
	}
	
}
