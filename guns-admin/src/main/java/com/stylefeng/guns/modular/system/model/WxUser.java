package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.core.util.DateUtil;

/**
 * <p>
 * 微信用户
 * </p>
 *
 * @author fudaqian
 * @since 2017-07-11
 */
@TableName("wx_user")
public class WxUser extends Model<WxUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户昵称
     */
	private String nickname;
	/**
	 * openid
	 */
	private String openid;
	/**
	 * 性别
	 */
	private Integer sex;
    /**
     * 用户是否关注
     */
	private Integer subscribe;
	/**
	 * 用户关注时间
	 */
	private String subscribeTime;
	/**
	 * 会员卡
	 */
	private String vipCard;
	/**
	 * 用户姓名
	 */
	private String username;
	/**
	 * 电话号码
	 */
	private String telPhone;
	/**
	 * 所属门店
	 */
	private Integer shopId;
	
	@Override
	protected Serializable pkVal() {
		// TODO 自动生成的方法存根
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getSubscribe_time() {
		return subscribeTime;
	}

	public void setSubscribe_time(Integer subscribeTime) {
		this.subscribeTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(subscribeTime);
	}

	public String getVipCard() {
		return vipCard;
	}

	public void setVipCard(String vipCard) {
		this.vipCard = vipCard;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	@Override
	public String toString() {
		return "WxUser {id=" + id + ", nickname=" + nickname + ", openid=" + openid + ", sex=" + sex + ", subscribe="
				+ subscribe + ", subscribeTime=" + subscribeTime + ", vipCard=" + vipCard + ", username=" + username
				+ ", telPhone=" + telPhone + ", shopId=" + shopId + "}";
	}
	
}
