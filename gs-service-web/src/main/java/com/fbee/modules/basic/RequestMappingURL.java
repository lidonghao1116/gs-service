package com.fbee.modules.basic;

/**
 * 系统请求URL路径
 *
 * @author 贺章鹏
 * @ClassName: RequestMappingURL
 * @Description: TODO
 * @date 2016年12月9日 下午3:19:40
 */
public interface RequestMappingURL {

    /*********************************基础接口：用户、我*****************************************/
    public static final String USERS_BASE_URL = "/{domain}/api/user";//用户接口基础地址

    public static final String USERS_INFO_URL = "/{domain}/api/userInfo";//用户接口基础地址

    public static final String COMMON_BASE_URL = "/{domain}/api/common";//公用接口基础地址

    public static final String SYSTEM_BASE_URL = "/{domain}/api/system";//系统管理

    public static final String ORDER_BASE_URL = "/{domain}/api/orders";//订单管理

    public static final String STAFF_BASE_URL = "/{domain}/api/staffs";//员工管理

    public static final String WEBSITE_BASE_URL = "/{domain}/api/website";//网站管理

    public static final String CUSTOMER_BASE_URL = "/{domain}/api/customers";//客户管理

    public static final String INDEX_BASE_URL = "/{domain}/api/index";//首页管理

    public static final String BANNER_BASE_URL = "/{domain}/api/banner";//banner接口

    public static final String JOB_RECRUIT = "/{domain}/api/jobRecruit";//岗位招聘

    public static final String HOUSE_KEEPING = "/{domain}/api/houseKeeping"; //家政服务请求URL

    public static final String MEMBERS_BASE_URL = "/{domain}/api/member";//会员接口基础地址

    public static final String Service_BASE_URL = "/{domain}/api/service";//服务定制

    public static final String ReserveOrders_BASE_URL = "/{domain}/api/reserveorders";//预约订单

    public static final String Orders_BASE_URL = "/{domain}/api/orders";//预约订单

    public static final String Withdrawals_BASE_URL = "/{domain}/api/withdrawals";//立即提现

    public static final String AboutUs_BASE_URL = "/{domain}/api/aboutus";//关于我们

    public static final String PayResult_BASE_URL = "/sinopay/payResult";//支付

    public static final String TENANTS_REGIST = "/tenantsRegist";//商户注册

    public static final String TENANTS_REGIST_INFO = "/tenantsRegistinfo"; //商户注册详细信息

    public static final String GET_APPS_LOGO = "/getAppsLogo";//获取logo地址

    public static final String QUERY_JOB = "/queryJob";//找工作

    public static final String QUERY_NEARBY_COMPANY = "/queryNearbyCompany";//找服务

    public static final String CUSTOMER_SERVER_BASE_URL = "/api/custServer";//客户服务(门店)管理控制器

    public static final String MSG_CAPTCHA_URL = "/getMsgCaptcha";// 短信验证码

    public static final String GETUNIQUENAME = "/getUniqueName";//获取相同门店名的数量

    public static final String GETUNIQUEPHONE = "/getUniquePhone";//获取相同门店名的数量

    /*********************************子级接口*****************************************/

    public static final String COUNT_URL = "/getcount"; //待处理订单数量

    public static final String SUBREQ_URL = "/subreq"; //需求提交

    public static final String Withdrawals_URL = "/withdrawals"; //提现提交

    public static final String LOGIN_URL = "/login";//登陆接口

    public static final String LOGOUT_URL = "/logout"; // 退出

    public static final String CAPTCHA_URL = "/getCaptcha";//图形验证码

    public static final String GETMEMBERINFO = "/getMemberInfo";//我的资料查询

    public static final String UPDATEMEMBERINFO = "/updateMemberInfo";//我的资料修改

    public static final String SENDSMS = "/sendSms";//短信验证码

    public static final String BANK_CODE = "/bankCode";//银行卡信息

    public static final String USER_MENUS_URL = "/userMenus";//用户菜单

    public static final String INFO_URL = "/info";//详细信息

    public static final String MODIFY_URL = "/modify";//详细信息

    public static final String STAFFRECOMMEND_URL = "/staffRecommend";//推荐阿姨信息

    public static final String UPDATESTAFFRECOMMEND_URL = "/updateStaffRecommend";//更换推荐阿姨

    public static final String STAFFSINFO_URL = "/staffsInfo";//所有阿姨信息

    public static final String STAFFINFO_URL = "/staffInfo";//每个阿姨信息

    public static final String STAFFSINFOLIKE_URL = "/staffsInfoLike";//模糊查询阿姨列表

    public static final String BANNER_UPLOAD_URL = "/upload"; // Banner图片上传

    public static final String BANNER_UPDATE = "/save"; // Banner设置保存

    public static final String SERITEMS_URL = "/serviceItems"; // 服务工种列表

    public static final String RESERVEORDERS_URL = "/reserveorders"; // 预约订单列表
    public static final String RESERVE_ORDER_LIST_URL = "/reserve/list"; // 预约订单列表

    public static final String ORDERS_URL = "/orders"; // 订单列表
    public static final String ORDER_LIST_URL = "/list"; // 订单列表

    public static final String getUsInfo_URL = "/getusinfo"; // 关于我们

    public static final String RESERVEORDERSDETAILS_URL = "/reserveordersdeails"; // 预约订单详情
    public static final String RESERVE_ORDER_DETAIL_URL = "/deails"; // 预约订单详情

    public static final String ORDERSDETAILS_URL = "/ordersdeails"; // 订单详情
    public static final String ORDERS_DETAILS_URL = "/deails"; // 订单详情

    public static final String CANCELRESERVEORDER_URL = "/cancelreserveorders"; // 取消预约订单

    public static final String CANCELORDER_URL = "/cancelorder"; // 取消订单

    public static final String SERITEM_URL = "/serviceItem"; // 服务工种

    public static final String UPDATESERITEM_URL = "/updateServiceItem"; // 服务工种

    public static final String CHANGESTAFF_URL = "/changeStaff"; //更换阿姨


    public static final String CHANGEHS_URL = "/changehs";//本地订单-更换阿姨

    /*********************************员工管理模块*****************************************/
    public static final String GET_CERT_INFO = "getCertInfo";//根据身份证获取阿姨信息

    public static final String SAVE_STAFF_BASE_INFO = "/add/baseInfo";//保存阿姨基础信息

    public static final String SAVE_STAFF_JOB_INFO = "/add/jobInfo";//保存阿姨求职信息

    public static final String SAVE_STAFF_MEDIA_INFO = "/add/mediaInfo";//保存阿姨视频图片信息

    public static final String SAVE_STAFF_BANK_INFO = "/add/bankInfo";//保存阿姨银行信息

    public static final String SAVE_STAFF_POLICY_INFO = "/add/policyInfo";//保存阿姨信息

    public static final String QUERY_STAFF = "/queryStaff";//查询

    public static final String GET_STAFF_DETAILS = "/getDetails";//详情


    /*********************************网站管理模块--bannner*****************************************/
    public static final String WEBSITE_GET_INDEX_INFO = "/getIndexInfo";//获取网站管理-首页管理信息

    public static final String WEBSITE_GET_CONTACT_INFO = "/getContactInfo";//获取网站管理-联系方式信息

    public static final String WEBSITE_UPDATE_CONTACT_INFO = "/updateContactInfo";//网站管理-更新联系方式信息

    public static final String WEBSITE_UPLOAD_IMG = "/uploadImg";//网站管理-更新联系方式信息

    /*招聘管理*/
    public static final String TENANTS_JOBS_INDEX = "/tenantsJobsIndex";                 //招聘管理首页请求URL
    public static final String GET_TENANTS_JOBS_LIST_INFO = "/getTenantsJobsListInfo";  //查询-招聘信息列表
    public static final String SAVE_TENANTS_JOBS_INFO = "/saveTenantsJobsInfo";         //保存招聘信息
    public static final String UPDATE_TENANTS_JOBS_INFO = "/updateTenantsJobsInfo";     //更新招聘信息
    public static final String GET_TENANTS_JOBS_INFO_BY_ID = "/getTenantsJobsDetail";   //招聘信息回显页面
    public static final String GET_SERVICE_TYPE = "/getServiceType";                     //获取服务工种下拉列表
    /*关于我们*/
    public static final String ABOUT_US_INDEX = "/aboutUsIndex";                         //关于我们首页请求URL
    public static final String GET_ABOUT_US_INFO = "/queryAboutUsInfo";                 //查询-页面信息填充初始化
    public static final String SAVE_ABOUT_US_INFO = "/saveAboutUsInfo";                  //保存-页面基本信息保存
    public static final String DELETE_ABOUT_US_INFO = "/deleteAboutUsInfo";              //删除-文件信息上载更新索引
    /*家政服务*/
    public static final String SERVICE_ITEMS = "/serviceItems";                 //查询-获取服务工种中文列表
    public static final String OBJEC_CONTENT = "/objectContent";                  //查询-获取服务工种的对象与内容
    public static final String STAFF_PERSON_INFO_LIST = "/StaffPersonInfoList";   //查询-获取阿姨信息列表
    public static final String STAFF_PERSON_INFO = "/StaffPersonInfo";   //查询-获取阿姨个人身份和基本信息列表
    public static final String STAFF_VIDEO_IMG = "/StaffVideoImg";   //查询-获取阿姨视频和照片信息
    public static final String STAFF_CERT_TYPE = "/StaffCertType";   //查询-获取证件信息列表
    public static final String STAFF_SERVICE_INFO = "/StaffServiceInfo";   //查询-获取阿姨服务信息列表
    /********************************* 页面字典参数和下拉框模块 *****************************/
    public static final String GET_DICTIONARY_DATA = "/getDictionaryData/{typeCode}";// 获取对应的字典参数参数List
    public static final String GET_AREA_DATA = "/getAreaData/{typeCode}";// 获取区域参数list
    public static final String GET_SKILLS_DATA = "/getSKillsData/{typeCode}";// 获取技能特点（对应服务工种）、个人特点、服务类型
    public static final String CUSTOMER_UPDATE_URL = "/updatecustomer";// 用户修改

    public static final String IS_LOGIN = "/isLogin";// 是否登录

    /********************************* 职业资格证书查询Test *****************************************/
    public static final String ZSCX_BASE_URL = "/api/zscx";// 职业资格证书接口基础地址
    public static final String ZSCX_CKECK_URL = "/zscxCheck";//证书数据爬取
    public static final String ZSCX_CAPTCHA_URL = "/zscxcaptcha";//职业资格证书验证码
    public static final String QUERY_CERT_INFO = "/queryCertInfo";//证书查询


    public static final String WECHAT_BASE_URL = "/api/WechatInfo";
    public static final String GET_WECHAT_INFO = "/getWechatInfo";
    public static final String WECHAT_AUTHRIZE = "/wechatAuthrize";
    public static final String PROCESS_URL = "/process"; // 微信验证


}
