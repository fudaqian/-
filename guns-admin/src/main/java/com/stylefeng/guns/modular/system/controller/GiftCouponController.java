package com.stylefeng.guns.modular.system.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.StringArrayUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;
import com.stylefeng.guns.modular.system.model.Coupon;
import com.stylefeng.guns.modular.system.service.ICardCouponLogService;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;
import com.stylefeng.guns.modular.system.service.ICouponService;
import com.stylefeng.guns.modular.system.service.IWeixinService;
import com.stylefeng.guns.modular.system.warpper.CouponWarpper;

/**
 * 活动管理控制器
 *
 * @author fengshuonan
 * @Date 2017-05-23 18:52:34
 */
@Controller
@RequestMapping("/giftCoupon")
public class GiftCouponController extends BaseController {

    private String PREFIX = "/system/giftCoupon/";

    @Resource
    private ICouponService couponServiceImpl;
    @Resource
    private GunsProperties gunsProperties;
	@Resource
	private IWeixinService weixinServiceImpl;
	@Resource
	private ICardMerchantinfoService cardMerchantinfoServiceImpl;
	@Resource
	private ICardCouponLogService cardCouponLogService;
    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
    	
        return PREFIX + "coupon.html";
    }
    
    /**
     * 跳转到添加页
     */
    @RequestMapping("/add_view")
    public String add_view(HttpServletRequest request ,Model model) {
    	CardMerchantinfo cardMerchantinfo = cardMerchantinfoServiceImpl.getModelByDeptid(ShiroKit.getUser().getDeptId());
    	model.addAttribute("brandName", cardMerchantinfo.getMerchantname());
    	return PREFIX + "add.html";
    }
    
    /**
     * 跳转到修改页
     */
    @RequestMapping("/update_view/{couponId}")
    public String update_view(@PathVariable Integer couponId,Model model) {
    	Coupon coupon = couponServiceImpl.selectById(couponId);
    	CardMerchantinfo cardMerchantinfo = cardMerchantinfoServiceImpl.getModelByDeptid(ShiroKit.getUser().getDeptId());
    	model.addAttribute("brandName", cardMerchantinfo.getMerchantname());
    	model.addAttribute("coupon", coupon);
    	return PREFIX + "update.html";
    }
    /**
     * 跳转到投放页
     */
    @RequestMapping("/push_view/{couponId}")
    public String push(@PathVariable Integer couponId,Model model) {
    	Coupon coupon = couponServiceImpl.selectById(couponId);
    	model.addAttribute("coupon", coupon);
    	return PREFIX + "push.html";
    }
    
    /**
     * 添加奖励券
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid Coupon coupon) {
//    	CardMerchantinfo cardMerchantinfo = cardMerchantinfoServiceImpl.getModelByDeptid(ShiroKit.getUser().getDeptId());
//    	String access_token = weixinServiceImpl.getAccessToken(cardMerchantinfo);
//    	
//    	String couponId = "";
//    	try {
//			couponId = weixinServiceImpl.add(coupon, access_token,cardMerchantinfo);
//			coupon.setWxCouponId(couponId);
//		} catch (ParseException e) {
//			// TODO 自动生成的 catch 块
//			return new GunsException(BizExceptionEnum.CARD_CREATE_ERROR);
//		}
//    	if(ToolUtil.isEmpty(coupon.getWxCouponId())){
//    		return new GunsException(BizExceptionEnum.CARD_CREATE);
//    	}
    	coupon.setCardType("Gift");
    	coupon.setWxCouponId(StringArrayUtil.getStringArrayByUUID(1)[0]);
    	Integer code = couponServiceImpl.add(coupon,ShiroKit.getUser().getName(),ShiroKit.getUser().getDeptId());
    	if(ToolUtil.isNotEmpty(code)){
    		return SUCCESS;
    	}
    	return ERROR;
    }
    
    /**
     * 修改奖励券
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid Coupon coupon) {
//    	CardMerchantinfo cardMerchantinfo = cardMerchantinfoServiceImpl.getModelByDeptid(ShiroKit.getUser().getDeptId());  	
//    	String access_token = weixinServiceImpl.getAccessToken(cardMerchantinfo);
//    	String couponId = "";
//    	try {
//			couponId = weixinServiceImpl.add(coupon, access_token ,cardMerchantinfo);
//			coupon.setWxCouponId(couponId);
//		} catch (ParseException e) {
//			// TODO 自动生成的 catch 块
//			return new GunsException(BizExceptionEnum.CARD_CREATE_ERROR);
//		}
//    	if(ToolUtil.isEmpty(coupon.getWxCouponId())){
//    		return new GunsException(BizExceptionEnum.CARD_CREATE);
//    	}
    	Integer code = couponServiceImpl.update(coupon,ShiroKit.getUser().getName());
    	if(ToolUtil.isNotEmpty(code)){
    		return SUCCESS;
    	}
    	return ERROR;
    }
    
    /**
     * 修改奖励券状态
     */
    @RequestMapping(value = "/stateUpdate")
    @ResponseBody
    public Object stateUpdate(Integer couponId,Integer state) {
    	Integer code = couponServiceImpl.modifyState(couponId,state);
    	if(ToolUtil.isNotEmpty(code))
    		return SUCCESS;
    	return ERROR;
    }
    
    /**
     * 获取奖励券列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	List<Map<String, Object>> result = couponServiceImpl.list(condition,"Gift",ShiroKit.getUser().getDeptId());
    	return super.warpObject(new CouponWarpper(result));
    }
    
    /**
     * 删除奖励券
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object deleteCoupon(Integer couponId) {
//    	CardMerchantinfo cardMerchantinfo = cardMerchantinfoServiceImpl.getModelByDeptid(ShiroKit.getUser().getDeptId());
//    	String access_token = weixinServiceImpl.getAccessToken(cardMerchantinfo);
//        Coupon coupon = couponServiceImpl.selectById(couponId);
//        if(weixinServiceImpl.deleteCoupon(coupon.getWxCouponId(),access_token)){
        	couponServiceImpl.deleteById(couponId);
//        }
        return SUCCESS_TIP;
    }
    
    /**
     * 投放优惠券
     */
    @RequestMapping(value = "/pushCoupon")
    @ResponseBody
    public Object pushCoupon(@Valid Coupon couponbo) {
//    	CardMerchantinfo cardMerchantinfo = cardMerchantinfoServiceImpl.getModelByDeptid(ShiroKit.getUser().getDeptId());
    	Coupon coupon = couponServiceImpl.selectById(couponbo.getId());
//    	String access_token = weixinServiceImpl.getAccessToken(cardMerchantinfo);
    	String[] strArray = null;
    	Integer num = couponbo.getCanUseNum();
    	int model = num / 100;
    	for (int i = 1 ; i <= model + 1 ; i ++){
	    	if(num > 100){
	    		strArray = StringArrayUtil.getStringArrayByUUID(100);
//	    		weixinServiceImpl.codeDeposit(coupon,strArray,access_token);
	    		cardCouponLogService.saveCouponLogByArray(strArray, coupon.getWxCouponId(),ShiroKit.getUser().getDeptId());
	    		num = num - 100;
	    	}else{
	    		strArray = StringArrayUtil.getStringArrayByUUID(num);
//	    		weixinServiceImpl.codeDeposit(coupon,strArray,access_token);
	    		cardCouponLogService.saveCouponLogByArray(strArray, coupon.getWxCouponId(),ShiroKit.getUser().getDeptId());
	    	}
    	}
    	coupon.setStock(coupon.getStock()+num);
    	couponServiceImpl.updateById(coupon);
    	return SUCCESS;
    }

}
