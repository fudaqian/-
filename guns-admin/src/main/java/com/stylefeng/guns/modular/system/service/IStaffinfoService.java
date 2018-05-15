package com.stylefeng.guns.modular.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.Staffinfo;

/**
 * <p>
 * 员工信息表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-02
 */
public interface IStaffinfoService extends IService<Staffinfo> {

	/**
	 * 根据条件获取员工列表
	 * @param name
	 */
	List<Map<String, Object>> getListByCondition(Integer merchantid, String name, Integer state, String nickname);
	
	/**
	 * 根据openid查询员工实体
	 * @param openid
	 * @return
	 */
	
	Staffinfo getStaffByOpenid(String openid);

	/**
	 * 绑定员工信息
	 * @param staff
	 */
	void bindInfo(Staffinfo staff);

}
