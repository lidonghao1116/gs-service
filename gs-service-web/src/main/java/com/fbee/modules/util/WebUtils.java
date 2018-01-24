package com.fbee.modules.util;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.core.utils.SessionUtils;

public class WebUtils {


    /**
     * 获取当前session用户
     * @return
     */
    public static MemberBean getMember(){
        MemberBean memeberbean = (MemberBean) SessionUtils.getSession().getAttribute(Constants.MEMBER_SESSION);
        return memeberbean;
    }
}
