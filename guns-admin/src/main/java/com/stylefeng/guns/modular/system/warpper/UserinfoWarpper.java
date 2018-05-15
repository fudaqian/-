package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.common.constant.state.UserIsFollow;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.Map;

/**
 * 客户列表的包装
 *
 * @author fengshuonan
 * @date 2017年12月4日21:56:06
 */
public class UserinfoWarpper extends BaseControllerWarpper {

    public UserinfoWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Integer isfollow = (Integer) map.get("isfollow");
        map.put("isfollowName",  UserIsFollow.valueOf(isfollow));
    }

}
