package com.fbee.modules.mybatis.model;

public class TenantsStaffsFeatures extends TenantsStaffsFeaturesKey {
	private static final long serialVersionUID = 1L;
	/**
     * 表：tenants_staffs_features
     * 字段：FEATURE_VALUE
     * 注释：特点值
     *
     * @mbggenerated
     */
    private String featureValue;

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue == null ? null : featureValue.trim();
    }
}