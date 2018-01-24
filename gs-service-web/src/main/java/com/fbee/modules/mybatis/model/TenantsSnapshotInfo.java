package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class TenantsSnapshotInfo implements Serializable{
	
	private Integer tenantId;
	private String websiteName;
	private String domain;
	
	private String tenantsPhone;

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getTenantsPhone() {
		return tenantsPhone;
	}

	public void setTenantsPhone(String tenantsPhone) {
		this.tenantsPhone = tenantsPhone;
	}

}