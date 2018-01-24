package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.page.form.TenantJobResumeForm;
import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.TenantsJobResume;

import java.util.List;

@MyBatisDao
public interface TenantsJobResumesMapper extends CrudDao<TenantsJobResume> {


    List<TenantsJobResume> getTenantsJobsResumesList(TenantJobResumeForm form);

    List<TenantsJobResume> getMyResumesBoxList(TenantJobResumeForm form);

}
