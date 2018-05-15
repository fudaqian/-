package com.stylefeng.guns.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.util.CommonUtil;
import com.stylefeng.guns.modular.system.model.Userinfo;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;
import com.stylefeng.guns.modular.system.service.IUserinfoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 客户前端接口
 * 
 * @author 54476
 *
 */
@Controller
@RequestMapping("/api/user")
public class ApiUserinfoController extends BaseController {

	@Autowired
	private IUserinfoService userinfoService;
	
	@Autowired
	private ICardMerchantinfoService cardMerchantinfoService;
	
	/**
	 * 客户绑定手机号
	 */
	@ApiOperation(value = "客户绑定手机号", notes = "客户关注后需要绑定手机号")
	@RequestMapping(value = "/bindMobile" , method = RequestMethod.POST)
	@ResponseBody
	public JSONObject bindMobile(@ApiParam("客户ID，必填") @RequestParam(value = "id", required = true) Integer id,
			@ApiParam("手机号码") @RequestParam(value = "mobile", required = true) String mobile) {
		JSONObject jb = new JSONObject();
		Userinfo user = userinfoService.selectById(id);
		if(user == null) {
			jb.put("error", BizExceptionEnum.USER_NOT_EXIST.getMessage());
			jb.put("code", "error");
			return jb;
		}
		if (!CommonUtil.isMobile(mobile)) {
			jb.put("error", BizExceptionEnum.MOBILE_ERROR.getMessage());
			jb.put("code", "error");
		}
		user.setMobile(mobile);
		userinfoService.bindMobile(user);
		jb.put("data", user);
		jb.put("code", "success");
		return jb;
	}

	/**
	 * 获取客户详情
	 */
	@ApiOperation(value = "获取客户详情", notes = "通过客户id,获取客户信息详情")
	@RequestMapping(value = "/getdetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDetail(@ApiParam("客户ID，必填") @RequestParam(value = "id", required = true) Integer id) {
		JSONObject jb = new JSONObject();
		Userinfo user = userinfoService.selectById(id);
		if (user != null) {
			jb.put("data", user);
			jb.put("code", "success");
		} else {
			jb.put("error", BizExceptionEnum.USER_NOT_EXIST.getMessage());
			jb.put("code", "error");
		}
		return jb;
	}
	
	/**
	 * 获取deptid
	 */
	@ApiOperation(value = "获取deptid", notes = "通过客户id,获取deptid")
	@RequestMapping(value = "/getdeptid", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDeptId(@ApiParam("客户ID，必填") @RequestParam(value = "id", required = true) Integer id) {
		JSONObject jb = new JSONObject();
		Userinfo user = userinfoService.selectById(id);
		Integer deptId= cardMerchantinfoService.selectById(user.getMerchantid()).getDeptid();
		Integer merchantId= user.getMerchantid();
		if (user != null) {
			jb.put("deptId", deptId);
			jb.put("merchantId", merchantId);
			jb.put("code", "success");
		} 
		return jb;
	}
	

}
