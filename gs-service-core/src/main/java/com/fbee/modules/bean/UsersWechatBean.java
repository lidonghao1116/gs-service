package com.fbee.modules.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by baronli on 2017/6/3.
 */
public class UsersWechatBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表：users
     * 字段：ID
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：users
     * 字段：OPEN_ID
     * 注释：open_id
     *
     * @mbggenerated
     */
    private String openId;

    /**
     * 表：users
     * 字段：NICK
     * 注释：昵称
     *
     * @mbggenerated
     */
    private String nick;

    /**
     * 表：users
     * 字段：image
     * 注释：图像
     *
     * @mbggenerated
     */
    private String image;

    /**
     * 表：users
     * 字段：ADD_TIME
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
