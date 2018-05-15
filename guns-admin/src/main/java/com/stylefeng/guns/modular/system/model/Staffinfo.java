package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 员工信息表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-02
 */
@TableName("card_staffinfo")
public class Staffinfo extends Model<Staffinfo> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 关联商户id
	 */
	private Integer merchantid;
	/**
	 * 员工微信openid
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
	 * 姓名
	 */
	private String name;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 在职状态（1 在职 0 离职）
	 */
	private Integer state;

	/**
	 * 是否管理员（管理员可进行核销卡券 ‘1’ 是 ‘0’ 否）
	 */
	private Integer isadmin;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(Integer isadmin) {
		this.isadmin = isadmin;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Staffinfo{" + "id=" + id + ", merchantid=" + merchantid + ", openid=" + openid + ", head=" + head
				+ ", nickname=" + nickname + ", name=" + name + ", mobile=" + mobile + ", state=" + state + ", isadmin="
				+ isadmin + "}";
	}

}
