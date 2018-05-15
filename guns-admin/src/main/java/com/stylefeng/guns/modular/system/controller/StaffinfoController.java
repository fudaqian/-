package com.stylefeng.guns.modular.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.state.IsAdmin;
import com.stylefeng.guns.core.common.constant.state.StaffState;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.qr.ImgQrTool;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;
import com.stylefeng.guns.modular.system.model.Staffinfo;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;
import com.stylefeng.guns.modular.system.service.IStaffinfoService;
import com.stylefeng.guns.modular.system.warpper.StaffinfoWarpper;

import weixin.popular.api.SnsAPI;


/**
 * 员工控制器
 *
 * @author fengshuonan
 * @Date 2018-04-02 15:22:28
 */
@Controller
@RequestMapping("/staffinfo")
public class StaffinfoController extends BaseController {

    private String PREFIX = "/system/staffinfo/";

	private static final String REDIRECT_STAFF_LOGIN = "http://mf.ecommu.cn/admin/api/weixin/redirectStaffLogin";  //员工登录后地址
	private static final String REDIRECT_USER_LOGIN = "http://mf.ecommu.cn/admin/api/weixin/redirectUserLogin";  //客户授权登录授权后回调跳转地址
	private static final String REDIRECT_URI_STAFF = "http://mf.ecommu.cn/admin/api/weixin/redirectUrlStaff";  //员工授权后跳转地址
    
    @Autowired
    private IStaffinfoService staffinfoService;
    
    @Autowired
    private ICardMerchantinfoService cardMerchantinfoService;
    
    @Resource
    private GunsProperties gunsProperties;

    /**
     * 跳转到员工首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "staffinfo.html";
    }

    /**
     * 跳转到添加员工
     */
    @RequestMapping("/staffinfo_add")
    public String staffinfoAdd() {
        return PREFIX + "staffinfo_add.html";
    }
    
    /**
     * 跳转到获取员工微信URL
     */
    @RequestMapping("/staffinfo_url")
    public String staffinfoUrl(Model model) {
    	Integer deptid = ShiroKit.getUser().getDeptId();	//部门id 
    	CardMerchantinfo merchant =	cardMerchantinfoService.getModelByDeptid(deptid);
    	String url = SnsAPI.connectOauth2Authorize(merchant.getAppid(), REDIRECT_URI_STAFF , true, merchant.getId().toString());  //生成新增员工url
    	String staffUrl = SnsAPI.connectOauth2Authorize(merchant.getAppid(), REDIRECT_STAFF_LOGIN , true, merchant.getId().toString());  //生成员工登录url
    	String userUrl = SnsAPI.connectOauth2Authorize(merchant.getAppid(), REDIRECT_USER_LOGIN , true, merchant.getId().toString());  //生成客户登录url
    	model.addAttribute("url", url);
    	model.addAttribute("staffUrl", staffUrl);
    	model.addAttribute("userUrl", userUrl);
        return PREFIX + "staffinfo_url.html";
    }

    /**
     * 跳转到获取员工二维码
     */
    @RequestMapping("/staffinfo_image")
    public String staffinfoImage(Model model) {
    	Integer deptid = ShiroKit.getUser().getDeptId();	//部门id 
    	CardMerchantinfo merchant =	cardMerchantinfoService.getModelByDeptid(deptid);
    	String url = SnsAPI.connectOauth2Authorize(merchant.getAppid(), REDIRECT_URI_STAFF , true, merchant.getId().toString());  //生成新增员工url
    	String fileUrl = gunsProperties.getFileUploadPath();
    	ImgQrTool.createSimpleQr(url, 200, 200, fileUrl+deptid+".jpg");
    	merchant.setErcode(deptid+".jpg");
    	merchant.updateById();
    	model.addAttribute("ercode", merchant.getErcode());
        return PREFIX + "staffinfo_image.html";
    }

    /**
     * 跳转到修改员工
     */
    @RequestMapping("/staffinfo_update/{staffinfoId}")
    public String staffinfoUpdate(@PathVariable Integer staffinfoId, Model model) {
        Staffinfo staffinfo = staffinfoService.selectById(staffinfoId);
        model.addAttribute("item",staffinfo);
        LogObjectHolder.me().set(staffinfo);
        return PREFIX + "staffinfo_edit.html";
    }

    /**
     * 获取员工列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String name,Integer state,String nickname) {
    	Integer deptid = ShiroKit.getUser().getDeptId();	//部门id 
    	CardMerchantinfo info =	cardMerchantinfoService.getModelByDeptid(deptid);
    	List<Map<String, Object>> stringObjectMap = staffinfoService.getListByCondition(info.getId(),name,state,nickname);
        return super.warpObject(new StaffinfoWarpper(stringObjectMap));
    	
    }

    /**
     * 新增员工
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Staffinfo staffinfo) {
    	staffinfo.setState(StaffState.JOB.getCode());
        staffinfoService.insert(staffinfo);
        return SUCCESS_TIP;
    }
    
    /**
     * 设置管理员
     */
    @RequestMapping(value = "/admin")
    @ResponseBody
    public Object setAdmin(@RequestParam Integer staffinfoId) {
    	Staffinfo staffinfo = staffinfoService.selectById(staffinfoId);
    	staffinfo.setIsadmin(IsAdmin.ISADMIN.getCode());
        staffinfoService.updateById(staffinfo);
        return SUCCESS_TIP;
    }

    /**
     * 取消管理员
     */
    @RequestMapping(value = "/notadmin")
    @ResponseBody
    public Object cancelAdmin(@RequestParam Integer staffinfoId) {
    	Staffinfo staffinfo = staffinfoService.selectById(staffinfoId);
    	staffinfo.setIsadmin(IsAdmin.NOTADMIN.getCode());
        staffinfoService.updateById(staffinfo);
        return SUCCESS_TIP;
    }
    
    /**
     * 删除员工
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer staffinfoId) {
    	Staffinfo staffinfo = staffinfoService.selectById(staffinfoId);
    	staffinfo.setState(StaffState.QUIT.getCode());
        staffinfoService.deleteById(staffinfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改员工
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Staffinfo staffinfo) {
    	staffinfo.setState(StaffState.JOB.getCode());
        staffinfoService.updateById(staffinfo);
        return SUCCESS_TIP;
    }

    /**
     * 员工详情
     */
    @RequestMapping(value = "/detail/{staffinfoId}")
    @ResponseBody
    public Object detail(@PathVariable("staffinfoId") Integer staffinfoId) {
        return staffinfoService.selectById(staffinfoId);
    }
}
