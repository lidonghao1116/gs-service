package com.fbee.modules.wechat.message.model;

/**
 * YEJ6zYFloJcpsVnQH1YjDtlUJbAUsrdFn3-U9voZNxU
 */
public class BusinessModel extends MessageModel {

    private Integer userId;

    private String title;
    private String keywordFirst;
    private String keywordSecond;
    private String keywordThird;
    private String remark;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywordFirst() {
        return keywordFirst;
    }

    public void setKeywordFirst(String keywordFirst) {
        this.keywordFirst = keywordFirst;
    }

    public String getKeywordSecond() {
        return keywordSecond;
    }

    public void setKeywordSecond(String keywordSecond) {
        this.keywordSecond = keywordSecond;
    }

    public String getKeywordThird() {
        return keywordThird;
    }

    public void setKeywordThird(String keywordThird) {
        this.keywordThird = keywordThird;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
