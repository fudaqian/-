package com.stylefeng.guns.modular.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.CouponMapper;
import com.stylefeng.guns.modular.system.model.CardCouponLog;
import com.stylefeng.guns.modular.system.model.Coupon;
import com.stylefeng.guns.modular.system.service.ICouponService;

@Service
@Transactional
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService{

	@Resource
	private CouponMapper couponmapper;
	
	@Override
	public List<Map<String, Object>> list(String condition ,String cardType,Integer deptId) {
		// TODO 自动生成的方法存根
		List<Map<String, Object>> messageList = new ArrayList<>(); 
        Wrapper<Coupon> wrapper = new EntityWrapper<>();
        
        if(ToolUtil.isNotEmpty(condition)){
        	wrapper = wrapper.eq("condition",condition);	
        }
        wrapper = wrapper.eq("cardType",cardType);
        wrapper = wrapper.eq("deptId",deptId);
        wrapper = wrapper.in("state", new Integer[]{0, 1});   
        wrapper = wrapper.orderBy("createDate", false);
		messageList = couponmapper.selectMaps(wrapper);
		
		return messageList;
	}

	@Override
	public Integer add(Coupon coupon,String deptName ,Integer deptId) {
		// TODO 自动生成的方法存根
		Integer code = 0;
		coupon.setCreateDate(DateUtil.getTime());
		coupon.setCreateUser(deptName);		
		coupon.setDeptId(deptId);		
		code = couponmapper.insert(coupon);

		return code;
	}

	@Override
	public Integer update(Coupon coupon,String deptName) {
		// TODO 自动生成的方法存根
		Integer code = 0;
		coupon.setCreateDate(DateUtil.getTime());
		coupon.setCreateUser(deptName);		
		code = couponmapper.updateById(coupon);
		return code;
	}

	@Override
	public Integer modifyState(Integer couponId, Integer state) {
		// TODO 自动生成的方法存根
		Coupon coupon = couponmapper.selectById(couponId);
		coupon.setState(state);
	    Integer code = couponmapper.updateById(coupon);
	    return code;
	}

	@Override
	public List<Map<String, Object>> apiList(Integer couponId , Integer deptId) {
		// TODO 自动生成的方法存根
		List<Map<String, Object>> messageList = couponmapper.apiList(couponId,deptId,DateUtil.getTime());	
		return messageList;
	}

	@Override
	public List<Map<String, Object>> getListByCustomer(List<CardCouponLog> customerHoldCardList) {
		// TODO 自动生成的方法存根
		
		List<Map<String, Object>> messageList = new ArrayList<>();
		
		//出现数据重复为持久化问题（改为关联查询方式）
		for(CardCouponLog cardCouponLog : customerHoldCardList){
			Map<String, Object> tempMap = new HashMap<String, Object>();
			Wrapper<Coupon> wrapper = new EntityWrapper<>();
	        wrapper = wrapper.eq("wxCouponId", cardCouponLog.getWxCouponId());
	        tempMap = couponmapper.selectMaps(wrapper).get(0);
	        tempMap.put("couponLogId", cardCouponLog.getId());
	        tempMap.put("couponLogCode", cardCouponLog.getCouponcode());
	        messageList.add(tempMap);
	        
		}
		return messageList;
	}

	@Override
	public Coupon selectByWxCouponId(String wxCouponId) {
		// TODO 自动生成的方法存根
        Wrapper<Coupon> wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("wxCouponId",wxCouponId);
        List<Coupon> list = couponmapper.selectList(wrapper);
		if(ToolUtil.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<Map<String, Object>> getCouponByType(String cardType,Integer deptId) {
		// TODO 自动生成的方法存根
		Wrapper<Coupon> wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("cardType",cardType);
        wrapper = wrapper.eq("state",1);
        wrapper = wrapper.eq("deptId",deptId);
        List<Map<String, Object>> list = couponmapper.selectMaps(wrapper);
		return list;
	}


}
