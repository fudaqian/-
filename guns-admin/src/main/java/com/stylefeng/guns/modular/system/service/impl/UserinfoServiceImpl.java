package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.modular.system.model.Userinfo;
import com.stylefeng.guns.modular.system.service.IUserinfoService;
import com.stylefeng.guns.modular.system.dao.UserinfoMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户信息表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-02
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements IUserinfoService {

	/**
	 * 根据手机号和是否关注查询列表
	 * @param mobile 手机
	 * @param isfollow 是否关注（1 已关注 0 未关注）
	 * @return
	 */
	public List<Map<String, Object>> getListByCondition(Integer merchantid, String mobile, Integer isfollow) {
		
		List<Map<String, Object>> list = this.baseMapper.getListByCondition(merchantid, mobile, isfollow);
		return list;
	}

	/**
	 * 根据微信openid 获取用户实体。
	 * @param openid
	 * @return
	 */
	public Userinfo getUserInfoByOpenid(String openid) {
		
		Userinfo user = this.baseMapper.getUserInfoByOpenid(openid);
		return user;
	}

	/**
	 * 绑定客户手机号
	 */
	public void bindMobile(Userinfo user) {
		this.updateById(user);
	}

}
