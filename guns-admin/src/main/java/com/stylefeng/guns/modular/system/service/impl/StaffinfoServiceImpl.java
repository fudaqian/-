package com.stylefeng.guns.modular.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.system.dao.StaffinfoMapper;
import com.stylefeng.guns.modular.system.model.Staffinfo;
import com.stylefeng.guns.modular.system.service.IStaffinfoService;

/**
 * <p>
 * 员工信息表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-02
 */
@Service
public class StaffinfoServiceImpl extends ServiceImpl<StaffinfoMapper, Staffinfo> implements IStaffinfoService {

	/**
	 * 根据用户名获取员工列表
	 * 
	 * @param name
	 */
	public List<Map<String, Object>> getListByCondition(Integer merchantid, String name, Integer state,
			String nickname) {

		List<Map<String, Object>> list = this.baseMapper.getListByCondition(merchantid, name, state, nickname);
		return list;
	}

	/**
	 * 根据openid获取员工实体
	 */
	public Staffinfo getStaffByOpenid(String openid) {

		Staffinfo staff = this.baseMapper.getStaffByOpenid(openid);
		return staff;
	}

	/**
	 * 绑定员工信息
	 */
	public void bindInfo(Staffinfo staff) {
		this.updateById(staff);
	}

}
