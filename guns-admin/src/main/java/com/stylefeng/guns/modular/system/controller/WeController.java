package com.stylefeng.guns.modular.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;
import com.stylefeng.guns.modular.system.service.IWeixinService;
import com.stylefeng.guns.modular.system.service.IWexinUserService;

import weixin.popular.bean.user.User;

/**
 * 微信控制器
 *
 * @Date 2017-05-23 18:52:34
 */
@Controller
@RequestMapping("/weixin")
public class WeController extends BaseController {

    private String PREFIX = "/system/weixin/";

    @Resource
    private IWeixinService wexinServiceImpl;
    @Resource
    private IWexinUserService wexinUserServiceImpl;
    @Resource
    private GunsProperties gunsProperties;
	@Resource
	private ICardMerchantinfoService cardMerchantinfoServiceImpl;
    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "weixin.html";
    }
    
    /**
     * 获取授权页面
     */
    @RequestMapping("/wexinOauth")
    @ResponseBody
    public String oauth() {
    	CardMerchantinfo cardMerchantinfo = cardMerchantinfoServiceImpl.getModelByDeptid(ShiroKit.getUser().getDeptId());
    	return wexinServiceImpl.getWxAuth(cardMerchantinfo);
    }	
    
    /**
     * 保存微信授权用户信息
     */
    @RequestMapping(value="/saveInfo" , method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void getWexinUserInfo(String code , String state) {
    	CardMerchantinfo cardMerchantinfo = cardMerchantinfoServiceImpl.getModelByDeptid(ShiroKit.getUser().getDeptId());
    	String openid = wexinServiceImpl.getOpenId(code, state,cardMerchantinfo);
    	String access_token = wexinServiceImpl.getAccessToken(cardMerchantinfo);
    	User user = wexinServiceImpl.getWxUserInfo(access_token, openid);
    	
    	wexinUserServiceImpl.add(user);
    }

}
