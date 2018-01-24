package com.fbee.modules.consts;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.core.utils.SessionUtils;

public interface RedisKey {

    enum User {
        WECHAT_INFO("gs.service.wechat.%s", "微信用户信息"),
        USER_INFO("gs.service.user.%s", "登陆用户信息");

        private String key;
        private String desc;

        User(String key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        public static User get(String key) {
            for (User pair : values()) {
                if (pair.key.equals(key)) {
                    return pair;
                }
            }
            return null;
        }

        public String getKey() {
            return String.format(this.key, SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN));
        }
        public String getKey(String token) {
            return String.format(this.key, token);
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }


}
