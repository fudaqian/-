package com.stylefeng.guns.modular.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.common.constant.Const;
import com.stylefeng.guns.core.common.constant.state.ManagerStatus;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.dao.CardMerchantinfoMapper;
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;

/**
 * <p>
 * 商户信息表 服务实现类
 * </p>
 *
 * @author fudaqian123
 * @since 2018-04-10
 */
@Service
@Transactional
public class CardMerchantinfoServiceImpl extends ServiceImpl<CardMerchantinfoMapper, CardMerchantinfo> implements ICardMerchantinfoService {

	 @Autowired
	 private IDeptService deptService;
	    
     @Autowired
     private IUserService userService;
	
     @Autowired
     private ICardMerchantinfoService cardMerchantinfoService;
	/**
	 * 根据名称获取商户列表
	 */
	public List<Map<String, Object>> getListByName(String name) {

		List<Map<String, Object>> list = this.baseMapper.getListByName(name);
		return list;
	}

	/**
	 * 根据部门id获取商户实体
	 */
	public CardMerchantinfo getModelByDeptid(Integer deptid) {

		return this.baseMapper.getModelByDeptid(deptid);
	}

	/**
	 * 新增商户--新建用户，新增角色，新建部门
	 */

	
	public void addMerchantinfo(CardMerchantinfo cardMerchantinfo) {
		//新建顶级下的部门
    	Dept dept = new Dept();
    	dept.setPid(0);
    	dept.setPids("[0],");
    	dept.setFullname(cardMerchantinfo.getEnterprisename());
    	dept.setSimplename(cardMerchantinfo.getEnterprisename());
    	deptService.insert(dept);
    	
    	//新建用户，该用户关联部门，角色。角色为事先设置好的。
    	User user = new User();
    	user.setAccount(cardMerchantinfo.getMerchantname());
    	user.setName(cardMerchantinfo.getEnterprisename());
    	user.setPhone(cardMerchantinfo.getMobile());
    	user.setDeptid(dept.getId());
    	user.setRoleid(Const.ROLE_ID);
    	user.setCreatetime(new Date());
    	user.setStatus(ManagerStatus.OK.getCode());
    	user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5("000000", user.getSalt()));
        userService.insert(user);
        
        //新建商户信息，关联部门
        cardMerchantinfo.setDeptid(dept.getId());
        cardMerchantinfoService.insert(cardMerchantinfo);
		
	}

}
