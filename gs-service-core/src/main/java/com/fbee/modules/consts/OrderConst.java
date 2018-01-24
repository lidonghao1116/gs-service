package com.fbee.modules.consts;

/**
 * 订单状态
 * Created by gaoyan on 05/07/2017.
 */
public interface OrderConst {

    /**
     * 订单状态
     * 01 待支付定金 02 待面试 03 待支付尾款 04 已完成 05 已取消 06 待完成 07已结单 08结单后阿姨更换处理09待完成(淘分享)',
     */
    enum OrderStatus{

        WAIT_PAY_DEPOSIT("01", "待支付定金"),
        WAIT_INTERVIEW("02", "待面试"),
        WAIT_PAY_BALANCE("03", "待支付尾款"),
        FINISHED("04", "已完成"),
        CANCELED("05", "已取消"),
        WAIT_FINISH("06", "待完成"),
        SETTLEMENTED("07", "已结单"),
        CHANGE_STAFF_AFTER_SETTLED("08", "结单后阿姨更换处理"),
        WAIT_SHARE("09", "待完成（淘分享）");

        private String code;
        private String desc;

        private OrderStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static OrderStatus get(String code) {
            for (OrderStatus pair : values()) {
                if (pair.code.equals(code)) {
                    return pair;
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * 支付状态
     */
    enum PayStatus{
        WAIT_PAY("01", "待支付"),
        PAID("02", "已支付"),
        CANCELED("03", "已取消");

        private String code;
        private String desc;

        private PayStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static PayStatus get(String code) {
            for (PayStatus pair : values()) {
                if (pair.code.equals(code)) {
                    return pair;
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * 支付渠道
     *
     */
    enum TradeChannel{
        ALIPAY("01", "支付宝","pay.alipay.native"),
        WECHATPAY("02", "微信","pay.weixin.native"),
        WX_JS_PAY("03", "微信公众号","pay.weixin.jspay"),
        QUERY_STATUS("04", "查询订单状态","unified.trade.query");

        private String code;
        private String desc;
        private String service;

        private TradeChannel(String code, String desc,String service) {
            this.code = code;
            this.desc = desc;
            this.service = service;
        }

        public static TradeChannel get(String code) {
            for (TradeChannel pair : values()) {
                if (pair.code.equals(code)) {
                    return pair;
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }
    }


}
