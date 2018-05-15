package com.stylefeng.guns.modular.system.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.stylefeng.guns.modular.system.model.CardMerchantinfo;
import com.stylefeng.guns.modular.system.model.Coupon;

import weixin.popular.bean.user.User;



public interface IWeixinService {

	List<Map<String, Object>> list(String condition);
	
	String add(Coupon coupon, String access_token, CardMerchantinfo cardMerchantinfo) throws ParseException;
	
	String getAccessToken (CardMerchantinfo cardMerchantinfo);

	String getWxAuth (CardMerchantinfo cardMerchantinfo);
	
    /**
     * 微信授权后回调获取用户openid
     * @param cardMerchantinfo 
     *
     * @param param
     * @return
     */
    String getOpenId(String code,String state, CardMerchantinfo cardMerchantinfo) ;
 
    
    User getWxUserInfo(String access_token,String openid) ;
    
    boolean deleteCoupon(String wxCouponId, String accessToken);

    Integer codeDeposit(Coupon coupon, String[] code,String accessToken);
	
	Integer destroy(String code, String wxCouponId, String access_token);
	
	boolean codeGet(String code, String accessToken, String wxCouponId);
    
}
