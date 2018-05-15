package com.stylefeng.guns.modular.system.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.CardMerchantinfoMapper;
import com.stylefeng.guns.modular.system.model.CardMerchantinfo;
import com.stylefeng.guns.modular.system.model.Coupon;
import com.stylefeng.guns.modular.system.service.ICouponService;
import com.stylefeng.guns.modular.system.service.IWeixinService;

import weixin.popular.api.CardAPI;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.card.AdvancedInfo;
import weixin.popular.bean.card.AdvancedInfoUseCondition;
import weixin.popular.bean.card.BaseInfo;
import weixin.popular.bean.card.BaseInfoDateInfo;
import weixin.popular.bean.card.BaseInfoSku;
import weixin.popular.bean.card.Cash;
import weixin.popular.bean.card.CashCard;
import weixin.popular.bean.card.code.checkcode.CodeCheckCode;
import weixin.popular.bean.card.code.checkcode.CodeCheckCodeResult;
import weixin.popular.bean.card.code.consume.CodeConsume;
import weixin.popular.bean.card.code.consume.CodeConsumeResult;
import weixin.popular.bean.card.code.deposit.CodeDeposit;
import weixin.popular.bean.card.code.deposit.CodeDepositResult;
import weixin.popular.bean.card.code.get.CodeGet;
import weixin.popular.bean.card.code.get.CodeGetResult;
import weixin.popular.bean.card.code.getdepositcount.CodeGetDepositCount;
import weixin.popular.bean.card.code.getdepositcount.CodeGetDepositCountResult;
import weixin.popular.bean.card.create.CreateResult;
import weixin.popular.bean.card.modifystock.ModifyStock;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.token.Token;
import weixin.popular.bean.user.User;
import weixin.popular.util.JsonUtil;

@Service
public class WeixinServiceImpl implements IWeixinService {
	@Resource
	private GunsProperties gunsProperties;
	@Resource
	private CardMerchantinfoMapper merchantinfoMapper;
	@Resource
	private ICouponService couponService;
	
	
	private BaseInfo baseInfo;

	private AdvancedInfo advancedInfo;

	private Cash cash;

	private CashCard cashCard;

	@Override
	public List<Map<String, Object>> list(String condition) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
    public String getWxAuth(CardMerchantinfo cardMerchantinfo) {
        String myserveUrl = gunsProperties.getServeUrl();
        String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+cardMerchantinfo.getAppid()+"&redirect_uri="+myserveUrl+"/weixin/saveInfo"+"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
        return redirectUrl;
    }
    
	@Override
    public String getOpenId(String code,String state,CardMerchantinfo cardMerchantinfo) {
		SnsToken snsToken = SnsAPI.oauth2AccessToken(cardMerchantinfo.getAppid(), cardMerchantinfo.getSecret(), code);
		return snsToken.getOpenid();
    }
	
	@Override
	public Integer destroy(String code, String wxCouponId , String accessToken) {
		// TODO 自动生成的方法存根
		if(codeGet(code,accessToken,wxCouponId)){
			CodeConsume codeConsume = new CodeConsume();
			codeConsume.setCardId(wxCouponId);
			codeConsume.setCode(code);
			CodeConsumeResult codeConsumeResult = CardAPI.codeConsume(accessToken, codeConsume);
			return 0;
		}
		return 1;
		
	}
	
	@Override
	public boolean codeGet(String code, String accessToken, String wxCouponId) {
		// TODO 自动生成的方法存根
		CodeGet codeGet = new CodeGet();
		codeGet.setCode(code);
		codeGet.setCardId(wxCouponId);
		codeGet.setCheckConsume(false);
		CodeGetResult codeGetResult = CardAPI.codeGet(accessToken,codeGet);
		return codeGetResult.getCanConsume();
	}
	
	@Override
	public User getWxUserInfo(String access_token, String openid) {
		// TODO 自动生成的方法存根
		User user = UserAPI.userInfo(access_token, openid);
		return user;
	}
	
	@Override
	public String add(Coupon coupon, String access_token,CardMerchantinfo cardMerchantinfo) throws ParseException {
		this.setBaseInfo(coupon,cardMerchantinfo);
		this.setAdvancedInfo(coupon);
		this.setCash(coupon);
		this.setCashCard();

		CreateResult createResult = CardAPI.create(access_token, this.cashCard);
		if (createResult.isSuccess()) {
			System.out.println(createResult.getCardId());
			return createResult.getCardId();
		} else {
			System.out.println(createResult.getErrcode());
			System.out.println(createResult.getErrmsg());
		}

		return "";
	}

	@Override
	public String getAccessToken(CardMerchantinfo cardMerchantinfo) {
		Token token = new Token();
		String access_token = cardMerchantinfo.getAccessToken();
		Long expiresIn = cardMerchantinfo.getExpiresIn();
		Long nowDate = new Date().getTime();
		if (ToolUtil.isEmpty(access_token) || expiresIn <= nowDate) {
			token = TokenAPI.token(cardMerchantinfo.getAppid(),cardMerchantinfo.getSecret());
			cardMerchantinfo.setAccessToken(token.getAccess_token());
			cardMerchantinfo.setExpiresIn(new Date().getTime() + token.getExpires_in()*900);
			merchantinfoMapper.updateById(cardMerchantinfo);
		}
		return cardMerchantinfo.getAccessToken();
	}
	
    @Override
    public boolean deleteCoupon(String wxCouponId, String accessToken) {
    	BaseResult baseResult = CardAPI.deleteByCardId(accessToken, wxCouponId);
    	return baseResult.isSuccess();
    }
    
    public Integer codeDeposit(Coupon coupon, String[] code,String accessToken) {
        CodeDeposit codeDeposit = new CodeDeposit();
        codeDeposit.setCardId(coupon.getWxCouponId());
        codeDeposit.setCode(code);
        CodeDepositResult codeDepositResult = CardAPI.codeDeposit(accessToken, codeDeposit);
        if (codeDepositResult.isSuccess()) {
            System.out.println("重复导入数" + codeDepositResult.getDuplicateCode());
            System.out.println("失败个数" + codeDepositResult.getFailCode());
            System.out.println("成功个数" + codeDepositResult.getSuccCode());

            CodeGetDepositCount codeGetDepositCount = new CodeGetDepositCount();
            codeGetDepositCount.setCardId(codeDeposit.getCardId());
            CodeGetDepositCountResult codeGetDepositCountResult = CardAPI.codeGetDepositCount(accessToken, codeGetDepositCount);
            System.out.println("已经成功存入的code数目" + codeGetDepositCountResult.getCount());

            CodeCheckCode codeCheck = new CodeCheckCode();
            codeCheck.setCardId(codeDeposit.getCardId());
            codeCheck.setCode(code);
            CodeCheckCodeResult codeCheckCodeResult = CardAPI.codeCheckCode(accessToken, codeCheck);
            System.out.println("已经成功存入的code" + Arrays.toString(codeCheckCodeResult.getExistCode()));
            System.out.println("没有存入的code" + Arrays.toString(codeCheckCodeResult.getNotExistCode()));

            ModifyStock modifystock = new ModifyStock();
            modifystock.setCardId(codeDeposit.getCardId());
            modifystock.setIncreaseStockValue(codeDepositResult.getSuccCode().length);
            BaseResult baseResult = CardAPI.modifyStock(accessToken, modifystock);

            if (baseResult.isSuccess()) {          	
            	return codeDepositResult.getSuccCode().length;
            } else {
                System.out.println(baseResult.getErrcode());
                System.out.println(baseResult.getErrmsg());
                return 0;
            }
        } else {
            System.out.println(codeDepositResult.getErrcode());
            System.out.println(codeDepositResult.getErrmsg());
            return 0;
        }
    }

	private void setBaseInfo(Coupon coupon, CardMerchantinfo cardMerchantinfo) throws ParseException {
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setLogoUrl(cardMerchantinfo.getLogo());
		baseInfo.setBrandName(cardMerchantinfo.getMerchantname());
		baseInfo.setCodeType("CODE_TYPE_QRCODE");
		Calendar calendar = Calendar.getInstance();
		baseInfo.setTitle(coupon.getCouponName());
		baseInfo.setColor("Color010");
		baseInfo.setNotice("使用时向服务员出示此券");
		baseInfo.setDescription(coupon.getDescription());
		BaseInfoDateInfo dateInfo = new BaseInfoDateInfo();
		dateInfo.setType(coupon.getType());
		if (coupon.getType().equals("DATE_TYPE_FIX_TIME_RANGE")) {
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(coupon.getBeginTimestamp()));
			dateInfo.setBeginTimestamp(new Long(calendar.getTimeInMillis() / 1000).intValue());
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(coupon.getEndTimestamp()));
			dateInfo.setEndTimestamp(new Long(calendar.getTimeInMillis() / 1000).intValue());
		} else {
			dateInfo.setFixedBeginTerm(0);
			dateInfo.setFixedTerm(coupon.getFixedTerm());
		}
		baseInfo.setDateInfo(dateInfo);

		BaseInfoSku sku = new BaseInfoSku();
		sku.setQuantity(0);
		baseInfo.setSku(sku);
		// baseInfo.setGetLimit(3);
		baseInfo.setUseCustomCode(true);
		baseInfo.setGetCustomCodeMode("GET_CUSTOM_CODE_MODE_DEPOSIT");
		baseInfo.setBindOpenid(false);
		baseInfo.setCanShare(true);
		baseInfo.setCanGiveFriend(false);

		// Integer array[] = { 123 };
		// baseInfo.setLocationIdList(array);

		baseInfo.setCenterTitle("立即使用");
		// baseInfo.setServicePhone("020-88888888");
		// baseInfo.setCenterSubTitle("按钮下方的wording");
		// baseInfo.setCenterUrl("www.qq.com");
		// baseInfo.setCustomUrlName("立即使用");
		// baseInfo.setCustomUrl("http://www.qq.com");
		// baseInfo.setCustomUrlSubTitle("6个汉字tips");
		// baseInfo.setPromotionUrlName("更多优惠");
		// baseInfo.setPromotionUrl("http://www.qq.com");
		// baseInfo.setSource("大众点评");
		this.baseInfo = baseInfo;
	}

	private void setAdvancedInfo(Coupon coupon) {
		AdvancedInfo advancedInfo = new AdvancedInfo();
		AdvancedInfoUseCondition useCondition = new AdvancedInfoUseCondition();
		useCondition.setAcceptCategory("全场商品");
		useCondition.setRejectCategory("");
		useCondition.setCanUseWithOtherDiscount(false);
		advancedInfo.setUseCondition(useCondition);

		// AdvancedInfoAbstract infoAbstract = new AdvancedInfoAbstract();
		// infoAbstract.setAbstractText("微信餐厅推出多种新季菜品，期待您的光临");
		// String[] strs =
		// {"http://mmbiz.qpic.cn/mmbiz/p98FjXy8LacgHxp3sJ3vn97bGLz0ib0Sfz1bjiaoOYA027iasqSG0sjpiby4vce3AtaPu6cIhBHkt6IjlkY9YnDsfw/0"};
		// infoAbstract.setIconUrlList(strs);
		// advancedInfo.setAbstractInfo(infoAbstract);

		// List<AdvancedInfoTextImage> list = new
		// ArrayList<AdvancedInfoTextImage>();
		// AdvancedInfoTextImage advancedInfoTextImage = new
		// AdvancedInfoTextImage();
		// advancedInfoTextImage.setText("http://mmbiz.qpic.cn/mmbiz/p98FjXy8LacgHxp3sJ3vn97bGLz0ib0Sfz1bjiaoOYA027iasqSG0sjpiby4vce3AtaPu6cIhBHkt6IjlkY9YnDsfw/0");
		// advancedInfoTextImage.setText("此菜品精选食材，以独特的烹饪方法，最大程度地刺激食 客的味蕾");
		// list.add(advancedInfoTextImage);
		// advancedInfoTextImage = new AdvancedInfoTextImage();
		// advancedInfoTextImage.setText("http://mmbiz.qpic.cn/mmbiz/p98FjXy8LacgHxp3sJ3vn97bGLz0ib0Sfz1bjiaoOYA027iasqSG0sjpiby4vce3AtaPu6cIhBHkt6IjlkY9YnDsfw/0");
		// advancedInfoTextImage.setText("此菜品迎合大众口味，老少皆宜，营养均衡");
		// list.add(advancedInfoTextImage);
		// advancedInfo.setTextImageList(list);

		// List<AdvancedInfoTimeLimit> list1 = new
		// ArrayList<AdvancedInfoTimeLimit>();
		// AdvancedInfoTimeLimit advancedInfoTimeLimit = new
		// AdvancedInfoTimeLimit();
		// advancedInfoTimeLimit.setType("MONDAY");
		// advancedInfoTimeLimit.setBeginHour(0);
		// advancedInfoTimeLimit.setEndHour(0);
		// advancedInfoTimeLimit.setBeginMinute(10);
		// advancedInfoTimeLimit.setEndMinute(59);
		// list1.add(advancedInfoTimeLimit);
		// advancedInfoTimeLimit = new AdvancedInfoTimeLimit();
		// advancedInfoTimeLimit.setType("HOLIDAY");
		// list1.add(advancedInfoTimeLimit);
		// advancedInfo.setTimeLimit(list1);

		// strs = new String[]{"BIZ_SERVICE_FREE_WIFI",
		// "BIZ_SERVICE_WITH_PET",
		// "BIZ_SERVICE_FREE_PARK",
		// "BIZ_SERVICE_DELIVER"};
		// advancedInfo.setBusinessService(strs);

		this.advancedInfo = advancedInfo;
	}

	private void setCash(Coupon coupon) throws ParseException {
		this.cash = new Cash();
		cash.setBaseInfo(this.baseInfo);
		cash.setAdvancedInfo(this.advancedInfo);
		cash.setLeastCost(coupon.getLeastCost());
		cash.setReduceCost(Integer.valueOf(coupon.getAmount()));
	}

	private void setCashCard() throws ParseException {
		this.cashCard = new CashCard();
		cashCard.setCash(this.cash);
		System.out.println(JsonUtil.toJSONString(cashCard));
	}

}
