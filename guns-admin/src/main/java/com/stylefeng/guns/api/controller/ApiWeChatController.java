package com.stylefeng.guns.api.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.state.IsAdmin;
import com.stylefeng.guns.core.common.constant.state.StaffState;
import com.stylefeng.guns.core.common.constant.state.UserIsFollow;
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;
import com.stylefeng.guns.modular.system.model.Staffinfo;
import com.stylefeng.guns.modular.system.model.Userinfo;
import com.stylefeng.guns.modular.system.service.ICardCouponLogService;
import com.stylefeng.guns.modular.system.service.ICardMerchantinfoService;
import com.stylefeng.guns.modular.system.service.IStaffinfoService;
import com.stylefeng.guns.modular.system.service.IUserinfoService;
import com.stylefeng.guns.modular.system.service.IWeixinService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.TicketAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.ticket.Ticket;
import weixin.popular.bean.user.User;
import weixin.popular.util.JsonUtil;

/**
 * 微信接口
 * 
 * @author 54476
 *
 */
@Controller
@RequestMapping("/api/weixin")
public class ApiWeChatController extends BaseController {

	@Resource
	private IStaffinfoService staffinfoService;

	@Resource
	private IUserinfoService userinfoService;

	@Resource
	private ICardMerchantinfoService merchantinfoService;

	@Resource
	private ICardCouponLogService cardCouponLogService;
	@Resource
	private IWeixinService weixinServiceImpl;

	private static final String RETURN_STAFF_LOGIN = "redirect:http://mf.ecommu.cn/dist/#/staffList/"; // 员工登录后跳转前端地址
	private static final String RETURN_USER_LOGIN = "redirect:http://mf.ecommu.cn/dist/#/couponsList/"; // 客户登录后跳转前端地址
	private static final String RETURN_ERROR_LOGIN = "redirect:http://mf.ecommu.cn/dist/#/error/"; // 登录后跳转前端错误地址
	private static final String RETURN_BIND_MOBILE = "redirect:http://mf.ecommu.cn/dist/#/userInput/"; // 客户绑定手机号页面
	private static final String RETURN_SUCCESS = "redirect:http://mf.ecommu.cn/dist/#/success/"; // 员工绑定信息成功跳转页
	private static final String RETURN_BIND_STAFF = "redirect:http://mf.ecommu.cn/dist/#/staffInput/"; // 员工绑定员工信息页
	private static final String REDIRECT_USER_SHARE = "http://mf.ecommu.cn/admin/api/weixin/redirectUrlShare"; // 用户分享授权后跳转地址

	private static final String SHRAE_USER_LOGIN = "redirect:http://mf.ecommu.cn/dist/#/reCouponsInfo/"; // 客户点击分享链接后登录地址

	/**
	 * 获取企业授权后的信息 回调
	 * 
	 * @param code
	 * @param state
	 *            商户id
	 * @return
	 */
	@RequestMapping(value = "/redirectUrlStaff", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView redirectUrlStaff(String code, Integer state) {
		CardMerchantinfo merchant = merchantinfoService.selectById(state);
		SnsToken json = SnsAPI.oauth2AccessToken(merchant.getAppid(), merchant.getSecret(), code);
		String openid = json.getOpenid();
		String access_token = json.getAccess_token();
		String lang = "zh_CN"; // 简体中文
		User user = SnsAPI.userinfo(access_token, openid, lang);
		String nickname = user.getNickname();
		String headUrl = user.getHeadimgurl();

		Staffinfo info = staffinfoService.getStaffByOpenid(openid);
		if (info != null) {
			if (info.getMobile() != null && info.getName() != null) {
				return new ModelAndView(RETURN_SUCCESS);
			} else {
				return new ModelAndView(RETURN_BIND_STAFF + info.getId());
			}
		} else {
			Staffinfo staff = new Staffinfo();
			staff.setOpenid(openid);
			staff.setHead(headUrl);
			staff.setNickname(nickname);
			staff.setMerchantid(state);
			staff.setState(StaffState.JOB.getCode());
			staff.setIsadmin(IsAdmin.ISADMIN.getCode());
			staffinfoService.insert(staff);
			return new ModelAndView(RETURN_BIND_STAFF + staff.getId());
		}
	}

	/**
	 * 员工登录 回调
	 * 
	 * @param code
	 * @param state
	 *            商户id
	 * @return
	 */
	@RequestMapping(value = "/redirectStaffLogin", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView redirectStaffLogin(String code, String state) {

		CardMerchantinfo merchant = merchantinfoService.selectById(state);
		SnsToken json = SnsAPI.oauth2AccessToken(merchant.getAppid(), merchant.getSecret(), code);
		String openid = json.getOpenid();
		Staffinfo info = staffinfoService.getStaffByOpenid(openid);

		if (info != null) {
			return new ModelAndView(RETURN_STAFF_LOGIN+info.getId()); // 跳转到前端登录成功后的页面
		} else {
			return new ModelAndView(RETURN_ERROR_LOGIN); // 跳转到404页面
		}
	}

	/**
	 * 客户登录 回调
	 * 
	 * @param code
	 * @param state
	 *            商户id
	 * @return
	 */
	@RequestMapping(value = "/redirectUserLogin", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView redirectUserLogin(String code, Integer state) {
		CardMerchantinfo merchant = merchantinfoService.selectById(state);
		SnsToken json = SnsAPI.oauth2AccessToken(merchant.getAppid(), merchant.getSecret(), code);
		String openid = json.getOpenid();
		Userinfo info = userinfoService.getUserInfoByOpenid(openid);

		if (info != null) {
			if (info.getMobile() != null) {
				return new ModelAndView(RETURN_USER_LOGIN + info.getId()); // 跳转到客户前端登录成功后的页面
			} else {
				return new ModelAndView(RETURN_BIND_MOBILE + info.getId()); // 跳转到绑定手机号页面
			}
		} else {
			String access_token = json.getAccess_token();
			String lang = "zh_CN"; // 简体中文
			User user = SnsAPI.userinfo(access_token, openid, lang);
			String nickname = user.getNickname();
			String headUrl = user.getHeadimgurl();
			Userinfo userinfo = new Userinfo();
			userinfo.setIsfollow(UserIsFollow.FOLLOW.getCode());
			userinfo.setOpenid(openid);
			userinfo.setHead(headUrl);
			userinfo.setNickname(nickname);
			userinfo.setMerchantid(state);
			userinfoService.insert(userinfo);
			return new ModelAndView(RETURN_BIND_MOBILE + userinfo.getId()); // 跳转到绑定手机号页面
		}
	}

	/**
	 * 分享跳转
	 * @param merchantId 商户id
	 * @param couponId 卡券id
	 * @param type 分享者类型 1 员工 2 客户
	 * @param shareId 分享者id 
	 * @return
	 */
	 
	
	@RequestMapping(value = "/shareUrl", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView shareUrl(Integer merchantId, Integer couponId, String shareId) {
		System.out.println(merchantId+"===="+couponId+"======"+shareId);
		CardMerchantinfo merchant = merchantinfoService.selectById(merchantId);
		String state = merchantId+","+couponId+","+shareId+",";
		String shareUrl = SnsAPI.connectOauth2Authorize(merchant.getAppid(), REDIRECT_USER_SHARE, true, state); // 生成分享授权url
		String url = "redirect:" + shareUrl;
		return new ModelAndView(url); // 分享回调
	}

	/**
	 * 分享 回调
	 * 
	 * @param code
	 * @param state 
	 *            
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/redirectUrlShare", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView redirectUrlShare(String code, String state) {
		String[] arr = state.split(",");
		Integer merchantId = Integer.valueOf(arr[0]);
		CardMerchantinfo merchant = merchantinfoService.selectById(merchantId);
		SnsToken json = SnsAPI.oauth2AccessToken(merchant.getAppid(), merchant.getSecret(), code);
		String openid = json.getOpenid();
		Userinfo info = userinfoService.getUserInfoByOpenid(openid);
		if (info != null) {
			state += info.getId();
			System.out.println(SHRAE_USER_LOGIN + state+"????????");
			return new ModelAndView(SHRAE_USER_LOGIN + state); // 客户点击分享链接后登录地址
		} else {
			String access_token = json.getAccess_token();
			String lang = "zh_CN"; // 简体中文
			User user = SnsAPI.userinfo(access_token, openid, lang);
			String nickname = user.getNickname();
			String headUrl = user.getHeadimgurl();
			Userinfo userinfo = new Userinfo();
			userinfo.setIsfollow(UserIsFollow.FOLLOW.getCode());
			userinfo.setOpenid(openid);
			userinfo.setHead(headUrl);
			userinfo.setNickname(nickname);
			userinfo.setMerchantid(merchantId);
			userinfoService.insert(userinfo);
			state += userinfo.getId();
			System.out.println(SHRAE_USER_LOGIN + state+"======");
			return new ModelAndView(SHRAE_USER_LOGIN + state); // 客户点击分享链接后登录地址
		}
	}

	@ApiOperation(value = "获取签名", notes = "获取签名")
	@RequestMapping(value = "/getSignature")
	@ResponseBody
	public JSONObject getSignature(@ApiParam("商户ID，必填") @RequestParam(value = "id", required = true) Integer id,
			@ApiParam("当前页面链接，必填") @RequestParam(value = "href", required = true) String href) {
		JSONObject jb = new JSONObject();
		CardMerchantinfo merchant = merchantinfoService.selectById(id);
		String access_token = weixinServiceImpl.getAccessToken(merchant);
		Ticket ticket = TicketAPI.ticketGetticket(access_token);
		String jsapi_ticket = ticket.getTicket();
		String noncestr = UUID.randomUUID().toString().replace("-", "");// 随机字符串
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);// 时间戳
		String url = href;
		String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
				+ url;
		String signature = SHA1(str);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("appId", merchant.getAppid());
		map.put("nonceStr", noncestr);
		map.put("timeStamp", timestamp);
		map.put("signature", signature);
		jb.put("data", JsonUtil.toJSONString(map));
		return jb;
	}
	
	@ApiOperation(value = "获取卡券签名", notes = "获取卡券签名")
	@RequestMapping(value = "/getCardSignature", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getCardSignature(@ApiParam("商户ID，必填") @RequestParam(value = "id", required = true) Integer id,
			@ApiParam("cardid") @RequestParam(value = "cardid", required = true) String cardid) {
		JSONObject jb = new JSONObject();
		CardMerchantinfo merchant = merchantinfoService.selectById(id);
		String jsapi_ticket = null;
		String access_token = weixinServiceImpl.getAccessToken(merchant);
		if (merchant.getCardExpiresIn() == null || merchant.getCardExpiresIn() < (new Date()).getTime()) {
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token
					+ "&type=wx_card";
			try {
				HttpClient httpClient = HttpClients.createDefault();
				// 声明请求方式
				HttpGet httpGet = new HttpGet(url);
				// 获取相应数据，这里可以获取相应的数据
				HttpResponse httpResponse = httpClient.execute(httpGet);
				// 拿到实体
				HttpEntity httpEntity = httpResponse.getEntity();
				// 获取结果，这里可以正对相应的数据精细字符集的转码
				String result = "";
				if (httpEntity != null) {
					result = EntityUtils.toString(httpEntity, "utf-8");
				}
				JSONObject demoJson = JSONObject.parseObject(result);
				jsapi_ticket = demoJson.getString("ticket");

				Long cardExpiresIn = Long.valueOf(demoJson.getString("expires_in")) + new Date().getTime();
				merchant.setCardExpiresIn(cardExpiresIn);
				merchant.setCardJsapiTicket(jsapi_ticket);
				merchant.updateById();
				// 关闭连接
				httpGet.releaseConnection();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			jsapi_ticket = merchant.getCardJsapiTicket();
		}

		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);// 时间戳
		String[] strArr = { timestamp, jsapi_ticket, cardid };
		Arrays.sort(strArr);
		strArr = Arrays.toString(strArr).substring(1, Arrays.toString(strArr).lastIndexOf("]")).split(",");
		String str = "";
		for (int i = 0; i < strArr.length; i++) {
			str += strArr[i].trim();
		}
		String signature = SHA1(str);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("timeStamp", timestamp);
		map.put("signature", signature);
		map.put("cardId", cardid);
		jb.put("data", JsonUtil.toJSONString(map));
		return jb;
	}
	
	public static String SHA1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
