package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.Staffinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 员工信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-02
 */
public interface StaffinfoMapper extends BaseMapper<Staffinfo> {

	List<Map<String, Object>> getListByCondition(@Param("merchantid")Integer merchantid,@Param("name")String name,@Param("state")Integer state,@Param("nickname")String nickname);

	Staffinfo getStaffByOpenid(@Param("openid")String openid);

	Staffinfo getStaffByIsadmin();
	
}
