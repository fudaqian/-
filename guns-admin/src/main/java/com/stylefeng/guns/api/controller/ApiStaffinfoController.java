package com.stylefeng.guns.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.util.CommonUtil;
import com.stylefeng.guns.modular.system.model.Staffinfo;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;
import com.stylefeng.guns.modular.system.service.IStaffinfoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 员工前端接口
 * @author 54476
 *
 */
@Controller
@RequestMapping("/api/staff")
public class ApiStaffinfoController extends BaseController{

	@Autowired
    private IStaffinfoService staffinfoService;
	
	@Autowired
	private ICardMerchantinfoService cardMerchantinfoService;
	/**
	 * 员工绑定员工信息
	 */
	@ApiOperation(value = "员工绑定手机号,姓名", notes = "员工绑定手机号,姓名")
	@RequestMapping(value = "/bindStaffinfo" , method = RequestMethod.POST)
	@ResponseBody
	public JSONObject bindStaffinfo(@ApiParam("员工ID，必填") @RequestParam(value = "id", required = true) Integer id,
			@ApiParam("手机号码") @RequestParam(value = "mobile", required = true) String mobile,
			@ApiParam("姓名") @RequestParam(value = "name", required = true) String name) {
		JSONObject jb = new JSONObject();
		Staffinfo staff = staffinfoService.selectById(id);
		if(staff == null) {
			jb.put("error", BizExceptionEnum.STAFF_NOT_EXIST.getMessage());
			jb.put("code", "error");
			return jb;
		}
		if(!CommonUtil.isMobile(mobile)) {
			jb.put("error", BizExceptionEnum.MOBILE_ERROR.getMessage());
			jb.put("code", "error");
			return jb;
		}
		if(StringUtils.isEmpty(name)) {
			jb.put("error", BizExceptionEnum.STAFF_NAME_IANULL.getMessage());
			jb.put("code", "error");
			return jb;
		}
		staff.setMobile(mobile);
		staff.setName(name);
		staffinfoService.bindInfo(staff);
		jb.put("data", staff);
		jb.put("code", "success");
		return jb;
	}
	
	 /**
     * 获取员工详情
     */
	@ApiOperation(value = "获取员工详情", notes = "通过员工id,获取员工信息详情")
	@RequestMapping(value = "/getdetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDetail(@ApiParam("员工ID，必填") @RequestParam(value = "id", required = true) Integer id) {
    	JSONObject jb = new JSONObject();
    	Staffinfo staff = staffinfoService.selectById(id);
    	if(staff != null) {
    		jb.put("data", staff);
        	jb.put("code", "success");
    	}else {
    		jb.put("error", BizExceptionEnum.ERROR_CREATE_DICT.getMessage());
    		jb.put("code", "error");
    	}
        return jb;
    }
    
	/**
	 * 获取deptid
	 */
	@ApiOperation(value = "获取deptid", notes = "通过员工id,获取deptid")
	@RequestMapping(value = "/getdeptid", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDeptId(@ApiParam("员工ID，必填") @RequestParam(value = "id", required = true) Integer id) {
		JSONObject jb = new JSONObject();
		Staffinfo user = staffinfoService.selectById(id);
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
