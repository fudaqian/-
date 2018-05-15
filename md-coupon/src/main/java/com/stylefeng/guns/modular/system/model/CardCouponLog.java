package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 卡券记录表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-02
 */
@TableName("card_coupon_log")
public class CardCouponLog extends Model<Coupon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 卡劵id
     */
    private String wxCouponId;
    /**
     * 使用者Id
     */
    private String userId;
    /**
     * 分享者id
     */
    private String sharingid;
    /**
     * 使用状态（0 未领取 1 已领取 2 已核销  3 已分配）
     */
    private Integer usestate;
    /**
     * 领取时间
     */
    private String receivetime;
    /**
     * 使用时间
     */
    private String usetime;
    /**
     * 分润
     */
    private BigDecimal shareprofit;
    /**
     * 优惠码
     */
    private String couponcode;
    /**
     * 商户id
     */
    private Integer deptId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getWxCouponId() {
		return wxCouponId;
	}

	public void setWxCouponId(String wxCouponId) {
		this.wxCouponId = wxCouponId;
	}

    public Integer getUsestate() {
        return usestate;
    }

    public void setUsestate(Integer usestate) {
        this.usestate = usestate;
    }

    public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	public String getUsetime() {
		return usetime;
	}

	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}

	public BigDecimal getShareprofit() {
        return shareprofit;
    }

    public void setShareprofit(BigDecimal shareprofit) {
        this.shareprofit = shareprofit;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSharingid() {
		return sharingid;
	}

	public void setSharingid(String sharingid) {
		this.sharingid = sharingid;
	}

	@Override
    public String toString() {
        return "Coupon{" +
        "id=" + id +
        ", wxCouponId=" + wxCouponId +
        ", sharingid=" + sharingid +
        ", usestate=" + usestate +
        ", receivetime=" + receivetime +
        ", usetime=" + usetime +
        ", shareprofit=" + shareprofit +
        ", couponcode=" + couponcode +
        ", deptId=" + deptId +
        ", userId=" + userId +
        ", sharingid=" + sharingid +
        "}";
    }
}
