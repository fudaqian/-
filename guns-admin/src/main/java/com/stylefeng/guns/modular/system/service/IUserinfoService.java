package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.model.Userinfo;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 客户信息表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-02
 */
public interface IUserinfoService extends IService<Userinfo> {

	/**
	 * 根据手机号和是否关注查询列表
	 * @param mobile 手机
	 * @param isfollow 是否关注（1 已关注 0 未关注）
	 * @return
	 */
	List<Map<String, Object>> getListByCondition(Integer merchantid, String mobile, Integer isfollow);
	
	/**
	 * 根据微信openid 获取用户实体。
	 * @param openid
	 * @return
	 */
	Userinfo getUserInfoByOpenid(String openid);

	/**
	 * 绑定客户手机号
	 * @param user
	 */
	void bindMobile(Userinfo user);

}
