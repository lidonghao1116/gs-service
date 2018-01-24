package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: TenantsJobsForm 
* @Description: 关于我们信息新增修改
* @author 张杰
*/
public class TenantsAboutUsForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
    private Integer id; //P
    private Integer tenantId; //租户id
    private String content;//内容
    private String images; //图片url
    
    private String content_1;//内容
    private String content_2;//内容
    
	public String getContent_1() {
		return content_1;
	}
	public void setContent_1(String content_1) {
		this.content_1 = content_1;
	}
	public String getContent_2() {
		return content_2;
	}
	public void setContent_2(String content_2) {
		this.content_2 = content_2;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	
}
