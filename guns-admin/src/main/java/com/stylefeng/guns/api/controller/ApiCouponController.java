package com.stylefeng.guns.api.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.CardCouponLog;
import com.stylefeng.guns.modular.system.model.Coupon;
import com.stylefeng.guns.modular.system.service.ICardCouponLogService;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;
import com.stylefeng.guns.modular.system.service.ICouponService;
import com.stylefeng.guns.modular.system.service.IStaffinfoService;
import com.stylefeng.guns.modular.system.service.IUserinfoService;
import com.stylefeng.guns.modular.system.service.IWeixinService;
import com.stylefeng.guns.modular.system.warpper.CouponWarpper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 优惠券接口
 * 
 * @author fudaqian
 *
 */
@Controller
@RequestMapping("/api/coupon")
public class ApiCouponController extends BaseController {

	@Resource
	private ICardCouponLogService cardCouponLogService;
	@Resource
	private ICouponService couponService;
	@Resource
	private IWeixinService weixinService;
	@Resource
	private ICardMerchantinfoService cardMerchantinfoServiceImpl;
	@Resource
	private IStaffinfoService staffinfoService;
	@Resource
	private IUserinfoService userinfoService;

	/**
	 * 保存领取的卡劵状态
	 */
	@ApiOperation(value = "保存领取的卡劵状态", notes = "保存领取的卡劵状态")
	@RequestMapping(value = "/saveCouponState", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object saveCouponState(
			@ApiParam("卡劵Id，必填") @RequestParam(value = "couponId", required = true) String couponId,
			@ApiParam("商户Id，必填") @RequestParam(value = "deptId", required = true) Integer deptId,
			@ApiParam("分享人Id，必填") @RequestParam(value = "sharingid", required = true) String sharingid,
			@ApiParam("领取人Id，必填") @RequestParam(value = "userId", required = true) String userId) {
		Coupon coupon = couponService.selectById(couponId);
		if (cardCouponLogService.isReceive(coupon.getWxCouponId(), deptId, userId)) {
			return new GunsException(BizExceptionEnum.CARD_TYPE_RECEIVE_ERROR);
		}
		CardCouponLog cardCouponLog = cardCouponLogService.selectByWxcouponId(coupon.getWxCouponId(), deptId);
		cardCouponLogService.updateByCouponcode(cardCouponLog.getCouponcode(), deptId, 1, sharingid, userId);
		return cardCouponLog;
	}

	/**
	 * 员工获取卡劵列表
	 */
	@ApiOperation(value = "获取可领取卡劵列表", notes = "获取可领取卡劵列表")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JSONObject list(@ApiParam("卡劵Id，非必填") @RequestParam(value = "couponId", required = false) Integer couponId,
			@ApiParam("商户Id，必填") @RequestParam(value = "deptId", required = true) Integer deptId) {
		JSONObject jb = new JSONObject();
		List<Map<String, Object>> result = couponService.apiList(couponId, deptId);
		jb.put("data", super.warpObject(new CouponWarpper(result)));
		jb.put("code", "success");
		return jb;
	}

	/**
	 * 客户获取卡劵列表
	 */
	@ApiOperation(value = "客户已获取卡劵列表", notes = "客户已获取卡劵列表")
	@RequestMapping(value = "/getListByCustomer", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JSONObject getListByCustomer(
			@ApiParam("客户openID，必填") @RequestParam(value = "openId", required = true) String openId,
			@ApiParam("卡券wxCouponId，非必填") @RequestParam(value = "wxCouponId", required = false) String wxCouponId,
			@ApiParam("商户ID，必填") @RequestParam(value = "deptId", required = true) Integer deptId) {
		JSONObject jb = new JSONObject();
		List<CardCouponLog> customerHoldCardList = cardCouponLogService.getListByCustomer(openId, deptId, wxCouponId);
		List<Map<String, Object>> result = couponService.getListByCustomer(customerHoldCardList);
		jb.put("data", super.warpObject(new CouponWarpper(result)));
		jb.put("code", "success");
		return jb;
	}

	/**
	 * 核销卡劵
	 */
	@ApiOperation(value = "核销卡劵", notes = "核销卡劵")
	@RequestMapping(value = "/destroy", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JSONObject list(@ApiParam("优惠券code码，必填") @RequestParam(value = "code", required = true) String code,
			@ApiParam("商户id，必填") @RequestParam(value = "deptId", required = true) Integer deptId,
			@ApiParam("是否微信核销，非必填") @RequestParam(value = "isWXdestroy", required = false) boolean isWXdestroy) {
		// if(isWXdestroy){
		// CardMerchantinfo cardMerchantinfo =
		// cardMerchantinfoServiceImpl.getModelByDeptid(deptId);
		// String access_token = weixinService.getAccessToken(cardMerchantinfo);
		// String wxCouponId = cardCouponLogService.getCardCouponLogByCode(code,
		// deptId).getWxCouponId();
		//
		// Integer wxResultCode = weixinService.destroy(code,wxCouponId,access_token);
		// if(wxResultCode != 0){
		// throw new GunsException(BizExceptionEnum.CARD_TYPE_ERROR);
		// }
		// }
		JSONObject jb = new JSONObject();
		CardCouponLog cardCouponLog = cardCouponLogService.getCardCouponLogByCode(code, deptId);

		if (cardCouponLog.getUsestate() != 1) {
			jb.put("code", 500);
			return jb;
		}
		// 查找分享人（为空时直接核销，为员工时直接核销，为客户时赠送客户优惠券）
		if (ToolUtil.isNotEmpty(cardCouponLog.getSharingid())) {
			if (ToolUtil.isEmpty(staffinfoService.getStaffByOpenid(cardCouponLog.getSharingid()))) {
				if (ToolUtil.isNotEmpty(userinfoService.getUserInfoByOpenid(cardCouponLog.getSharingid()))) {
					Coupon coupon = couponService.selectByWxCouponId(cardCouponLog.getWxCouponId());
					CardCouponLog cardCouponLogUse = cardCouponLogService.selectByWxcouponId(
							couponService.selectById(coupon.getUserrewardvalue()).getWxCouponId(), deptId);
					cardCouponLogService.updateByCouponcode(cardCouponLogUse.getCouponcode(), deptId, 1, null,
							cardCouponLog.getSharingid());
				}
			}
		}

		Integer resultCode = cardCouponLogService.updateState(code, deptId, 2);
		if (resultCode != 0) {
			jb.put("code", 200);
			return jb;
		}
		jb.put("code", 500);
		return jb;
	}

}
