package com.fbee.modules.bean;

import com.fbee.modules.core.config.Global;

public class Constants {

    public class AUTH_KEY {
        public static final String TOKEN = "token";
        public static final String UID = "uid";
        public static final String REFERER = "Referer";
    }

    /**
     * 微信应用appID
     * */
    public static final String APPID = Global.getConfig("wechat.appid");
    public static final String SECERT = Global.getConfig("wechat.secret");
    public static final String MOBILE_HOST_NAME = Global.getConfig("mobile.hostname");

    //用户缓存
    public static final String USER_SESSION = SessionKeys.USER_SESSION;
    //会员缓存
    public static final String MEMBER_SESSION = SessionKeys.MEMBER_SESSION;
    //banner默认的系统图片
    public static final String DEFAULT_BANNER_IMAGE_KEY = "D_BANNER_IMAGE";
    //服务管理系统默认的图片地址
    public static final String DEFAULT_SERVICE_IMAGE_KEY = "D_SERVICE_IMAGE";
    //关于我们系统默认图片地址
    public static final String DEFAULT_ABOUNT_US_IMAGE_KEY = "D_ABOUNT_US_IMAGE";
    //关于我们系统最大上传图片个数
    public static final String MAX_ABOUNT_US_IMAGE_NUM = "NUM_ABOUNT_US_IMAGE";
    //系统服务工种
    public static final String SERVICE_TYPES = "SERVICE_TYPES";

    public static final String ENV = "env";

    public static final String TOKEN = "jiacer";

    public static final Integer PAGE_SIZE = 10;

    public static final String IMAGE_TYPE = ".JPG.PNG.BMP.GIF.JPEG";

    public static final String STATUS = "00";

    public static final String COMMA = ",";
    
    public static final String COMMS = "、";

    public class SHELF_TYPE {
        public static final String NOW_TIME = "01";
        public static final String APPOINT_TIME = "02";
    }

    public class ISUSABLE {
        public static final String YES = "1";
        public static final String NO = "0";
    }

    public static final String DEFAULT_PAGE_NO = "1";

    public static final String DEFAULT_PAGE_SIZE = "10";

    public static final String ORDERS_PAGE_SIZE = "3";

    public static final String RESERVEORDERSTATUS = "01";

    public static final String ORDERSTATUS = "05";

    public static final String DAYTON = "、";

    public static final String EMPTY = "";

    /*网站管理图片路径*/
    public static final String IMAGE_URL = "/images";

    /*网站管理图片路径*/
    public static final String WEB_SITE_IMGAE_BASE_PATH = "website";
    public static final String ABOUT_US_IMGAE_BASE_PATH = "aboutus";
    public static final String CONTACT_IMGAE_BASE_PATH = "contact";
    public static final String BANNER_IMGAE_BASE_PATH = "banner";

    public static final String YTX_DOMAIN = "sms.ytx.domain";
    public static final String YTX_PORT = "sms.ytx.port";
    public static final String YTX_ACCOUNT_SID = "sms.ytx.accountSid";
    public static final String YTX_AUTH_TOKEN = "sms.ytx.authToken";
    public static final String YTX_APP_ID = "sms.ytx.appId";
    //订单号前二位业务规则
    public static final String BD = "01";//本地订单
    public static final String WL = "02";//网络订单
    public static final String TFX = "03";//淘分享订单
    public static final String YY = "04";//预约订单

    public static final String JY = "05";//交易流水号--废
    public static final String ZF = "06";//支付流水号
    public static final String ZH = "07";//租户账户流水号
    public static final String CW = "08";//租户财务流水号
}
