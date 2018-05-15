package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 商户信息表
 * </p>
 *
 * @author fudaqian123
 * @since 2018-04-10
 */
@TableName("card_merchantinfo")
public class CardMerchantinfo extends Model<CardMerchantinfo> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 商户名
	 */
	private String merchantname;
	/**
	 * 企业名称
	 */
	private String enterprisename;
	/**
	 * 企业地址
	 */
	private String address;
	/**
	 * 营业执照
	 */
	private String license;
	/**
	 * 法人身份证
	 */
	private String idcard;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 缴费日期
	 */
	private Date paydate;
	/**
	 * 截止日期
	 */
	private String enddate;
	/**
	 * 缴费金额
	 */
	private BigDecimal paymoney;
	/**
	 * 企业公众号appid
	 */
	private String appid;
	/**
	 * 企业公众号密钥
	 */
	private String secret;
	/**
	 * 部门id
	 */
	private Integer deptid;

	/**
	 * 身份证正面
	 */
	private String idcardfront;

	/**
	 * 身份证背面
	 */
	private String idcardback;

	/**
	 * 企业logo
	 */
	private String logo;
	/**
	 * 微信公众号accessToken
	 */
	private String accessToken;
	/**
	 * accessToken有效期时间戳
	 */
	private Long expiresIn;

	/**
	 * 二维码
	 */
	private String ercode;

	/**
	 * cardJsapiTicket有效期时间戳
	 */
	private Long cardExpiresIn;

	/**
	 * 微信公众号卡券cardJsapiTicket
	 */
	private String cardJsapiTicket;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getEnterprisename() {
		return enterprisename;
	}

	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getPaydate() {
		return paydate;
	}

	public void setPaydate(Date paydate) {
		this.paydate = paydate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public BigDecimal getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(BigDecimal paymoney) {
		this.paymoney = paymoney;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getIdcardfront() {
		return idcardfront;
	}

	public void setIdcardfront(String idcardfront) {
		this.idcardfront = idcardfront;
	}

	public String getIdcardback() {
		return idcardback;
	}

	public void setIdcardback(String idcardback) {
		this.idcardback = idcardback;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getErcode() {
		return ercode;
	}

	public void setErcode(String ercode) {
		this.ercode = ercode;
	}

	public Long getCardExpiresIn() {
		return cardExpiresIn;
	}

	public void setCardExpiresIn(Long cardExpiresIn) {
		this.cardExpiresIn = cardExpiresIn;
	}

	public String getCardJsapiTicket() {
		return cardJsapiTicket;
	}

	public void setCardJsapiTicket(String cardJsapiTicket) {
		this.cardJsapiTicket = cardJsapiTicket;
	}

	@Override
	public String toString() {
		return "CardMerchantinfo{" + "id=" + id + ", merchantname=" + merchantname + ", enterprisename="
				+ enterprisename + ", address=" + address + ", license=" + license + ", idcard=" + idcard + ", mobile="
				+ mobile + ", paydate=" + paydate + ", enddate=" + enddate + ", paymoney=" + paymoney + ", appid="
				+ appid + ", secret=" + secret + ", deptid=" + deptid + ", idcardfront=" + idcardfront + ", idcardback="
				+ idcardback + ", logo=" + logo + ", ercode=" + ercode + ", cardJsapiTicket=" + cardJsapiTicket
				+ ", cardExpiresIn=" + cardExpiresIn + "}";
	}

}
