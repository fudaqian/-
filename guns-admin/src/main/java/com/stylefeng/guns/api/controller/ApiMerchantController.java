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
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 商户前端接口
 * 
 * @author 54476
 *
 */
@Controller
@RequestMapping("/api/merchant")
public class ApiMerchantController extends BaseController {

	@Autowired
	private ICardMerchantinfoService cardMerchantinfoService;
	

	/**
	 * 获取商户详情
	 */
	@ApiOperation(value = " 获取商户详情", notes = "通过商户id,获取商户信息详情")
	@RequestMapping(value = "/getdetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDetail(@ApiParam("商户ID，必填") @RequestParam(value = "id", required = true) Integer id) {
		JSONObject jb = new JSONObject();
		CardMerchantinfo info = cardMerchantinfoService.selectById(id);
		if (info != null) {
			jb.put("data", info);
			jb.put("code", "success");
		} else {
			jb.put("error", BizExceptionEnum.USER_NOT_EXIST.getMessage());
			jb.put("code", "error");
		}
		return jb;
	}
	

}
