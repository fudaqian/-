package com.stylefeng.guns.modular.system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;

/**
 * 商户管理控制器
 *
 * @author fengshuonan
 * @Date 2018-04-10 09:46:23
 */
@Controller
@RequestMapping("/cardMerchantinfo")
public class CardMerchantinfoController extends BaseController {

    private String PREFIX = "/system/merchantinfo/";

    @Autowired
    private ICardMerchantinfoService cardMerchantinfoService;
    
    /**
     * 跳转到商户管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "cardMerchantinfo.html";
    }

    /**
     * 跳转到添加商户管理
     */
    @RequestMapping("/cardMerchantinfo_add")
    public String cardMerchantinfoAdd() {
        return PREFIX + "cardMerchantinfo_add.html";
    }

    /**
     * 跳转到修改商户管理
     */
    @RequestMapping("/cardMerchantinfo_update/{cardMerchantinfoId}")
    public String cardMerchantinfoUpdate(@PathVariable Integer cardMerchantinfoId, Model model) {
        CardMerchantinfo cardMerchantinfo = cardMerchantinfoService.selectById(cardMerchantinfoId);
        model.addAttribute("item",cardMerchantinfo);
        LogObjectHolder.me().set(cardMerchantinfo);
        return PREFIX + "cardMerchantinfo_edit.html";
    }

    /**
     * 获取商户管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String name) {
    	
    	List<Map<String, Object>> list = cardMerchantinfoService.getListByName(name);
        return list;
    }

    /**
     * 新增商户管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardMerchantinfo cardMerchantinfo) {
    	
    	cardMerchantinfoService.addMerchantinfo(cardMerchantinfo);
    	
    	
        return SUCCESS_TIP;
    }

    /**
     * 删除商户管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardMerchantinfoId) {
        cardMerchantinfoService.deleteById(cardMerchantinfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改商户管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardMerchantinfo cardMerchantinfo) {
        cardMerchantinfoService.updateById(cardMerchantinfo);
        return SUCCESS_TIP;
    }

    /**
     * 商户管理详情
     */
    @RequestMapping(value = "/detail/{cardMerchantinfoId}")
    @ResponseBody
    public Object detail(@PathVariable("cardMerchantinfoId") Integer cardMerchantinfoId) {
        return cardMerchantinfoService.selectById(cardMerchantinfoId);
    }
}
