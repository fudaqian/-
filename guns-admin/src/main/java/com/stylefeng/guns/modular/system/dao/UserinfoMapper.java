package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.Userinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 客户信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-02
 */
public interface UserinfoMapper extends BaseMapper<Userinfo> {

	List<Map<String, Object>> getListByCondition(@Param("merchantid")Integer merchantid, @Param("mobile")String mobile, @Param("isfollow")Integer isfollow);

	Userinfo getUserInfoByOpenid(@Param("openid")String openid);

}
