package com.stylefeng.guns.modular.system.warpper;

import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.state.IsAdmin;
import com.stylefeng.guns.core.common.constant.state.StaffState;

/**
 * 员工列表的包装
 *
 * @author fengshuonan
 * @date 2017年12月4日21:56:06
 */
public class StaffinfoWarpper extends BaseControllerWarpper {

    public StaffinfoWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Integer state = (Integer) map.get("state");
        Integer isadmin = (Integer) map.get("isadmin");
        map.put("stateName",  StaffState.valueOf(state));
        map.put("adminName",  IsAdmin.valueOf(isadmin));
    }

}
