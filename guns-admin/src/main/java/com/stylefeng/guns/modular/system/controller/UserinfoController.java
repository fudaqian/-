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
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;
import com.stylefeng.guns.modular.system.model.Userinfo;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;
import com.stylefeng.guns.modular.system.service.IUserinfoService;
import com.stylefeng.guns.modular.system.warpper.UserinfoWarpper;

/**
 * 客户控制器
 *
 * @author fengshuonan
 * @Date 2018-04-02 15:22:43
 */
@Controller
@RequestMapping("/userinfo")
public class UserinfoController extends BaseController {

    private String PREFIX = "/system/userinfo/";

    @Autowired
    private IUserinfoService userinfoService;
    
    @Autowired
    private ICardMerchantinfoService cardMerchantinfoService;

    /**
     * 跳转到客户首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "userinfo.html";
    }

    /**
     * 跳转到添加客户
     */
    @RequestMapping("/userinfo_add")
    public String userinfoAdd() {
        return PREFIX + "userinfo_add.html";
    }

    /**
     * 跳转到修改客户
     */
    @RequestMapping("/userinfo_update/{userinfoId}")
    public String userinfoUpdate(@PathVariable Integer userinfoId, Model model) {
        Userinfo userinfo = userinfoService.selectById(userinfoId);
        model.addAttribute("item",userinfo);
        LogObjectHolder.me().set(userinfo);
        return PREFIX + "userinfo_edit.html";
    }

    /**
     * 获取客户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String mobile,Integer isfollow) {
    	Integer deptid = ShiroKit.getUser().getDeptId();	//部门id 
    	CardMerchantinfo merchant =	cardMerchantinfoService.getModelByDeptid(deptid);
    	List<Map<String,Object>> stringObjectMap = userinfoService.getListByCondition(merchant.getId(),mobile,isfollow);
        return super.warpObject(new UserinfoWarpper(stringObjectMap));
    }

    /**
     * 新增客户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Userinfo userinfo) {
        userinfoService.insert(userinfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除客户
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userinfoId) {
        userinfoService.deleteById(userinfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改客户
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Userinfo userinfo) {
        userinfoService.updateById(userinfo);
        return SUCCESS_TIP;
    }

    /**
     * 客户详情
     */
    @RequestMapping(value = "/detail/{userinfoId}")
    @ResponseBody
    public Object detail(@PathVariable("userinfoId") Integer userinfoId) {
        return userinfoService.selectById(userinfoId);
    }
}
