package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;

/**
 * <p>
 * 商户信息表 Mapper 接口
 * </p>
 *
 * @author fudaqian123
 * @since 2018-04-10
 */
public interface CardMerchantinfoMapper extends BaseMapper<CardMerchantinfo> {

	List<Map<String, Object>> getListByName(@Param("name")String name);

	CardMerchantinfo getModelByDeptid(@Param("deptid")Integer deptid);

}
