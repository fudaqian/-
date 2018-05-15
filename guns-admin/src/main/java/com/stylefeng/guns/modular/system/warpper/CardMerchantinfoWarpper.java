package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.Map;

/**
 * 商户列表的包装
 *
 * @author fengshuonan
 * @date 2017年12月4日21:56:06
 */
public class CardMerchantinfoWarpper extends BaseControllerWarpper {

    public CardMerchantinfoWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        //Integer state = (Integer) map.get("state");
        //map.put("stateName",  StaffState.valueOf(state));
    }

}
