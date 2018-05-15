package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.web.util.HtmlUtils;

/**
 * <p>
 * 优惠券
 * </p>
 *
 * @author fudaqian
 * @since 2017-07-11
 */
@TableName("card_coupon")
public class Coupon extends Model<Coupon> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 优惠券名称(不少于9个字)
     */
	private String couponName;
	/**
	 * 表示起用金额（单位为分）,如果无起用门槛则填0。<br>
	 * 添加必填，不支持修改  
	 */
	private Integer leastCost;
	/**
	 * 卡券类型(Cash ： 代金券   Gift ： 奖励券)
	 */
	private String cardType;
    /**
     * 优惠券金额
     */
	private String amount;
	/**
	 * DATE_TYPE_FIX_TIME_RANGE： 表示固定日期区间； <br>
	 * DATE_TYPE_FIX_TERM：表示固定时长<br>
	 * 添加必填
	 */
	private String type;
	/**
	 * type为DATE_TYPE_FIX_TIME_RANGE时专用，表示起用时间。<br>
	 * 从1970年1月1日00:00:00至起用时间的秒数，<br>
	 * 最终需转换为字符串形态传入。（东八区时间，单位为秒） <br>
	 * 添加必填
	 */
	private String beginTimestamp;
	/**
	 * 表示结束时间，建议设置为截止日期的23:59:59过期。（东八区时间，单位为秒）<br>
	 * 可用于DATE_TYPE_FIX_TERM时间类型，表示卡券统一过期时间，建议设置为截止日期的23:59:59过期。（东八区时间，单位为秒），<br>
	 * 设置了fixed_term卡券，当时间达到end_timestamp时卡券统一过期<br>
	 * 添加必填
	 */
	private String endTimestamp;
	/**
	 * type为DATE_TYPE_FIX_TERM时专用，表示自领取后多少天内有效，不支持填写0。 <br>
	 * 添加必填，不支持修改
	 */
	private Integer fixedTerm;
	/**
	 * 卡券使用说明，字数上限为1024个汉字。<br>
	 * 添加必填
	 */
	private String description;
	/**
	 * 优惠券状态（1.启用，0.停用，2.作废）
	 */
	private Integer state;
    /**
     * 版本（乐观锁保留字段）
     */
	private Integer version;
	/**
	 * 图文内容
	 */
	private String contentDetail;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 微信卡券id
	 */
	private String wxCouponId;
	/**
	 * 可用数量
	 */
	private Integer canUseNum;
	/**
	 * 可分享数量 
	 */
	private Integer canParticipateNum;
	/**
	 * 可使用数量
	 */
	private String canUseTime;
    /**
     * 分享标题
     */
    private String sharetitle;
    /**
     * 分享内容
     */
    private String sharecontent;
    /**
     * 分享图片
     */
    private String sharepicture;
    /**
     * 分享URL
     */
    private String shareurl;
    /**
     * 员工奖励类型（1 卡券面值的比例 2 固定金额）
     */
    private Integer stafftrewardtype;
    /**
     * 员工奖励面值
     */
    private BigDecimal staffrewardvalue;
    /**
     * 用户奖励类型（1 红包 2 券）
     */
    private Integer usertrewardtype;
    /**
     * 用户奖励券Id
     */
    private Integer userrewardvalue;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 部门Id
     */
    private Integer deptId;
	
	@Override
	protected Serializable pkVal() {
		// TODO 自动生成的方法存根
		return null;
	}

	public String getWxCouponId() {
		return wxCouponId;
	}

	public void setWxCouponId(String wxCouponId) {
		this.wxCouponId = wxCouponId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getContentDetail() {
		return contentDetail;
	}

	public void setContentDetail(String contentDetail) {
		this.contentDetail = HtmlUtils.htmlUnescape(contentDetail);
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		if(couponName.length() >= 9){
			this.couponName = String.valueOf(couponName.substring(1, 9));
		}else{
			this.couponName = couponName;
		}		
	}

	public Integer getLeastCost() {
		return leastCost;
	}

	public void setLeastCost(Integer leastCost) {
		this.leastCost = leastCost;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBeginTimestamp() {
		return beginTimestamp;
	}

	public void setBeginTimestamp(String beginTimestamp) {
		if(ToolUtil.isNotEmpty(beginTimestamp)){
			try {
				this.beginTimestamp = DateUtil.getDay(new SimpleDateFormat("yyyy-MM-dd").parse(beginTimestamp));
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else{
			this.beginTimestamp = DateUtil.getTime();
		}
		
	}

	public String getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(String endTimestamp) {
		if(ToolUtil.isNotEmpty(endTimestamp)){
			try {
				this.endTimestamp = DateUtil.getDay(new SimpleDateFormat("yyyy-MM-dd").parse(endTimestamp));
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else{
			this.endTimestamp = DateUtil.getTime();
		}
	}

	public Integer getFixedTerm() {
		return fixedTerm;
	}

	public void setFixedTerm(Integer fixedTerm) {
		this.fixedTerm = fixedTerm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getCanUseNum() {
		return canUseNum;
	}

	public void setCanUseNum(Integer canUseNum) {
		this.canUseNum = canUseNum;
	}

	public Integer getCanParticipateNum() {
		return canParticipateNum;
	}

	public void setCanParticipateNum(Integer canParticipateNum) {
		this.canParticipateNum = canParticipateNum;
	}

	public String getCanUseTime() {
		return canUseTime;
	}

	public void setCanUseTime(String canUseTime) {
		this.canUseTime = canUseTime;
	}
	
	public String getSharetitle() {
		return sharetitle;
	}

	public void setSharetitle(String sharetitle) {
		this.sharetitle = sharetitle;
	}

	public String getSharecontent() {
		return sharecontent;
	}

	public void setSharecontent(String sharecontent) {
		this.sharecontent = sharecontent;
	}

	public String getSharepicture() {
		return sharepicture;
	}

	public void setSharepicture(String sharepicture) {
		this.sharepicture = sharepicture;
	}

	public String getShareurl() {
		return shareurl;
	}

	public void setShareurl(String shareurl) {
		this.shareurl = shareurl;
	}

	public Integer getStafftrewardtype() {
		return stafftrewardtype;
	}

	public void setStafftrewardtype(Integer stafftrewardtype) {
		this.stafftrewardtype = stafftrewardtype;
	}

	public BigDecimal getStaffrewardvalue() {
		return staffrewardvalue;
	}

	public void setStaffrewardvalue(BigDecimal staffrewardvalue) {
		this.staffrewardvalue = staffrewardvalue;
	}

	public Integer getUsertrewardtype() {
		return usertrewardtype;
	}

	public void setUsertrewardtype(Integer usertrewardtype) {
		this.usertrewardtype = usertrewardtype;
	}

	public Integer getUserrewardvalue() {
		return userrewardvalue;
	}

	public void setUserrewardvalue(Integer userrewardvalue) {
		this.userrewardvalue = userrewardvalue;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	@Override
	public String toString() {
		return "Coupon {id=" + id + ", couponName=" + couponName + ", leastCost=" + leastCost + ", cardType="
				+ cardType + ", amount=" + amount + ", type=" + type + ", beginTimestamp=" + beginTimestamp
				+ ", endTimestamp=" + endTimestamp + ", fixedTerm=" + fixedTerm + ", description=" + description
				+ ", state=" + state + ", version=" + version + ", contentDetail=" + contentDetail + ", createUser="
				+ createUser + ", createDate=" + createDate + ", wxCouponId=" + wxCouponId + ", canUseNum=" + canUseNum
				+ ", canParticipateNum=" + canParticipateNum + ", canUseTime=" + canUseTime + ", sharetitle="
				+ sharetitle + ", sharecontent=" + sharecontent + ", sharepicture=" + sharepicture + ", shareurl="
				+ shareurl + ", stafftrewardtype=" + stafftrewardtype + ", staffrewardvalue=" + staffrewardvalue
				+ ", usertrewardtype=" + usertrewardtype + ", userrewardvalue=" + userrewardvalue + ", deptId=" + deptId + ", stock=" + stock + "}";
	}
	
	
	
}
