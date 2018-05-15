package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.Coupon;

/**
 * <p>
  * 优惠券表 Mapper 接口
 * </p>
 *
 * @author fudaqian
 * @since 2017-07-11
 */
public interface CouponMapper extends BaseMapper<Coupon> {

	List<Map<String, Object>> apiList(@Param("couponId")Integer couponId, @Param("deptId")Integer deptId ,@Param("nowDate")String nowDate);

}