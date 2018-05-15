package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 客户信息表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-02
 */
@TableName("card_userinfo")
public class Userinfo extends Model<Userinfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关联商户id
     */
    private Integer merchantid;
    /**
     * 用户微信openid
     */
    private String openid;
    /**
     * 微信头像
     */
    private String head;
    /**
     * 微信昵称
     */
    private String nickname;
    /**
     * 绑定手机号
     */
    private String mobile;
    /**
     * 是否关注（1 已关注 0 未关注）
     */
    private Integer isfollow;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(Integer merchantid) {
		this.merchantid = merchantid;
	}

	public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getIsfollow() {
        return isfollow;
    }

    public void setIsfollow(Integer isfollow) {
        this.isfollow = isfollow;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Userinfo{" +
        "id=" + id +
        ", merchantid=" + merchantid +
        ", openid=" + openid +
        ", head=" + head +
        ", nickname=" + nickname +
        ", mobile=" + mobile +
        ", isfollow=" + isfollow +
        "}";
    }
}
