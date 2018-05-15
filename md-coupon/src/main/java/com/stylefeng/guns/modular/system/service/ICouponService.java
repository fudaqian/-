package com.stylefeng.guns.modular.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.CardCouponLog;
import com.stylefeng.guns.modular.system.model.Coupon;


public interface ICouponService extends IService<Coupon>{

	List<Map<String, Object>> list(String condition, String cardType,Integer deptId);
	
	Integer add(Coupon coupon,String deptName ,Integer deptId);

	Integer update(Coupon coupon,String deptName);

	Integer modifyState(Integer couponId, Integer state);

	List<Map<String, Object>> apiList(Integer couponId, Integer deptId);

	List<Map<String, Object>> getListByCustomer(List<CardCouponLog> customerHoldCardList);

	Coupon selectByWxCouponId(String wxCouponId);

	List<Map<String, Object>> getCouponByType(String cardType,Integer deptId);

}
