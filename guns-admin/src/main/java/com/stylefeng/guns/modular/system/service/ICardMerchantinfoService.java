package com.stylefeng.guns.modular.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;

/**
 * <p>
 * 商户信息表 服务类
 * </p>
 *
 * @author fudaqian123
 * @since 2018-04-10
 */
public interface ICardMerchantinfoService extends IService<CardMerchantinfo> {

	/**
	 * 根据名称获取商户列表
	 * @param name 帐户名/企业名称
	 * @return
	 */
	List<Map<String, Object>> getListByName(String name);
	
	/**
	 * 根据部门id获取商户实体
	 * @param deptid 部门id
	 * @return
	 */
	CardMerchantinfo getModelByDeptid(Integer deptid);

	/**
	 * 新增商户--新建用户，新增角色，新建部门
	 * @param cardMerchantinfo
	 */
	void addMerchantinfo(CardMerchantinfo cardMerchantinfo);

}
