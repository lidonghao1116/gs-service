package com.fbee.modules.redis.consts;


public interface RedisKey {

    enum User{
        UA("ua.%s", "用户授权标识，存储用户信息，期限1天");


        private String key;
        private String desc;

        private User(String key, String desc) {
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

        public String getKey(String placeholder) {
            return String.format(key, placeholder);
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