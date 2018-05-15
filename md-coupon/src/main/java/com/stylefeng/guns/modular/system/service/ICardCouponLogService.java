package com.stylefeng.guns.modular.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.CardCouponLog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fudaqian
 * @since 2018-04-17
 */
public interface ICardCouponLogService extends IService<CardCouponLog> {
	void saveCouponLogByArray(String[] couponArray,String wxCouponId,Integer deptId);

	CardCouponLog selectByWxcouponId(String wxCouponId,Integer deptId);

	Integer updateByCouponcode(String couponcode,Integer deptId,Integer state);
	
	Integer updateState(String code, Integer state ,Integer deptId);	
	
	CardCouponLog getCardCouponLogByCode(String code,Integer deptId);

	Integer updateByCouponcode(String couponcode, Integer deptId, Integer state, String sharingid, String userId);

	List<CardCouponLog> getListByCustomer(String openId, Integer deptId, String wxCouponId);

	boolean isReceive(String wxCouponId, Integer deptId, String userId);
}
