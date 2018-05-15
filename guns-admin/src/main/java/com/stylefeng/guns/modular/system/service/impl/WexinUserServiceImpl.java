package com.stylefeng.guns.modular.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.stylefeng.guns.modular.system.dao.WxUserMapper;
import com.stylefeng.guns.modular.system.model.WxUser;
import com.stylefeng.guns.modular.system.service.IWexinUserService;

import weixin.popular.bean.user.User;

@Service
public class WexinUserServiceImpl implements IWexinUserService {

	@Resource
	WxUserMapper wxUserMapper;
	
	@Override
	public List<Map<String, Object>> list() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public WxUser add(User user) {
		// TODO 自动生成的方法存根
		WxUser wxUser = userToWxUser(user);
		wxUserMapper.insert(wxUser);
		
		return wxUser;
	}

	@Override
	public WxUser update(WxUser wxUser) {
		// TODO 自动生成的方法存根
		return null;
	}

	public static WxUser userToWxUser(User user){
		
		WxUser wxUser = new WxUser();
		wxUser.setNickname(user.getNickname());
		wxUser.setOpenid(user.getOpenid());
		wxUser.setSex(user.getSex());
		wxUser.setSubscribe(user.getSubscribe());
		wxUser.setSubscribe_time(user.getSubscribe_time()); 
		
		return wxUser;
	}
}
