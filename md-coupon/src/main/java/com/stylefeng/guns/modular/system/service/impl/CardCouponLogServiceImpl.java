package com.stylefeng.guns.modular.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.CardCouponLogMapper;
import com.stylefeng.guns.modular.system.model.CardCouponLog;
import com.stylefeng.guns.modular.system.service.ICardCouponLogService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fudaqian123
 * @since 2018-04-17
 */
@Service
public class CardCouponLogServiceImpl extends ServiceImpl<CardCouponLogMapper, CardCouponLog>
		implements ICardCouponLogService {

	@Resource
	CardCouponLogMapper cardCouponLogMapper;

	@Override
	public void saveCouponLogByArray(String[] couponArray, String wxCouponId ,Integer deptId) {
		// TODO 自动生成的方法存根
		for (int i = 0; i <= couponArray.length - 1; i++) {
			CardCouponLog cardCouponLog = new CardCouponLog();
			cardCouponLog.setWxCouponId(wxCouponId);
			cardCouponLog.setCouponcode(couponArray[i]);
			cardCouponLog.setUsestate(0);
			cardCouponLog.setDeptId(deptId);
			cardCouponLogMapper.insert(cardCouponLog);
		}
	}

	@Override
	public synchronized CardCouponLog selectByWxcouponId(String cardId, Integer deptId) {
		// TODO 自动生成的方法存根
		Wrapper<CardCouponLog> wrapper = new EntityWrapper<>();
		wrapper.eq("wxCouponId", cardId);
		wrapper.eq("usestate", 0);
		CardCouponLog cardCouponLog = cardCouponLogMapper.selectList(wrapper).get(0);
		updateByCouponcode(cardCouponLog.getCouponcode(), deptId, 3);
		return cardCouponLog;
	}

	@Override
	public Integer updateByCouponcode(String couponcode, Integer deptId, Integer state) {
		// TODO 自动生成的方法存根
		Wrapper<CardCouponLog> wrapper = new EntityWrapper<>();
		wrapper.eq("couponcode", couponcode);
		wrapper.eq("deptId", deptId);
		CardCouponLog cardCouponLog = cardCouponLogMapper.selectList(wrapper).get(0);
		cardCouponLog.setUsestate(state);
		cardCouponLog.setUsetime(DateUtil.getTime(new Date()));
		int code = cardCouponLogMapper.updateById(cardCouponLog);

		return code;
	}

	@Override
	public Integer updateState(String code, Integer deptId, Integer state) {
		// TODO 自动生成的方法存根
		Integer resultCode = updateByCouponcode(code, deptId, state);

		return resultCode;
	}

	@Override
	public CardCouponLog getCardCouponLogByCode(String code, Integer deptId) {
		// TODO 自动生成的方法存根
		Wrapper<CardCouponLog> wrapper = new EntityWrapper<>();
		wrapper.eq("couponcode", code);
		wrapper.eq("deptId", deptId);
		CardCouponLog cardCouponLog = cardCouponLogMapper.selectList(wrapper).get(0);

		return cardCouponLog;
	}

	@Override
	public Integer updateByCouponcode(String couponcode, Integer deptId, Integer state, String sharingid,
			String userId) {
		// TODO 自动生成的方法存根
		Wrapper<CardCouponLog> wrapper = new EntityWrapper<>();
		wrapper.eq("couponcode", couponcode);
		wrapper.eq("deptId", deptId);
		CardCouponLog cardCouponLog = cardCouponLogMapper.selectList(wrapper).get(0);
		cardCouponLog.setUsestate(state);
		cardCouponLog.setUserId(userId);
		cardCouponLog.setSharingid(sharingid);
		cardCouponLog.setReceivetime(DateUtil.getTime(new Date()));
		int code = cardCouponLogMapper.updateById(cardCouponLog);

		return code;
	}

	@Override
	public List<CardCouponLog> getListByCustomer(String openId, Integer deptId, String wxCouponId) {
		// TODO 自动生成的方法存根
		Wrapper<CardCouponLog> wrapper = new EntityWrapper<>();
		if(ToolUtil.isNotEmpty(wxCouponId)){
			wrapper.eq("wxCouponId", wxCouponId);
		}
		wrapper.eq("userId", openId);
		wrapper.eq("deptId", deptId);
		wrapper.eq("usestate", 1);
		List<CardCouponLog> cardCouponLogs = cardCouponLogMapper.selectList(wrapper);
		return cardCouponLogs;
	}

	@Override
	public boolean isReceive(String wxCouponId, Integer deptId, String userId) {
		// TODO 自动生成的方法存根
		Wrapper<CardCouponLog> wrapper = new EntityWrapper<>();
		wrapper.eq("userId", userId);
		wrapper.eq("deptId", deptId);
		wrapper.eq("usestate", 1);
		wrapper.eq("wxCouponId", wxCouponId);
		List<CardCouponLog> cardCouponLogs = cardCouponLogMapper.selectList(wrapper);
		if (cardCouponLogs.size() > 0) {
			return true;
		}
		return false;
	}

}
