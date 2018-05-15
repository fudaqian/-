package com.stylefeng.guns.modular.system.service;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.modular.system.model.WxUser;

import weixin.popular.bean.user.User;


public interface IWexinUserService {

	List<Map<String, Object>> list();
	
	WxUser add(User user);

	WxUser update(WxUser wxUser);

}
