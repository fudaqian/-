package com.stylefeng.guns.modular.system.warpper;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

/**
 * 优惠券列表的包装类
 *
 * @author fudaqian
 * @date 2017年2月19日15:07:29
 */
public class CouponWarpper extends BaseControllerWarpper {

    public CouponWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getMenuStatusName((Integer) map.get("state")));
        try {
			map.put("useTime", ConstantFactory.me().getUseTime(map));
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }
}
