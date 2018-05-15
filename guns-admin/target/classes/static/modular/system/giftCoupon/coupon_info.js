/**
 * 菜单详情对话框
 */
var GiftCouponInfoDlg = {
	couponInfoData : {},
	validateFields : {}
};

/**
 * 清除数据
 */
GiftCouponInfoDlg.clearData = function() {
	this.couponInfoData = {};
}

/**
 * 设置对话框中的数据
 * 
 * @param key
 *            数据的名称
 * @param val
 *            数据的具体值
 */
GiftCouponInfoDlg.set = function(key, val) {
	this.couponInfoData[key] = (typeof value == "undefined") ? $("#" + key)
			.val() : value;
	return this;
}

/**
 * 设置对话框中的数据
 * 
 * @param key
 *            数据的名称
 * @param val
 *            数据的具体值
 */
GiftCouponInfoDlg.get = function(key) {
	return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
GiftCouponInfoDlg.close = function() {
	parent.layer.close(window.parent.GiftCoupon.layerIndex);
}

/**
 * 收集数据
 */
GiftCouponInfoDlg.collectData = function() {
	this.set('id').set('couponName').set('amount').set('type').set(
			'beginTimestamp').set('leastCost').set('canUseNum').set(
			'canParticipateNum').set('sharetitle').set('sharecontent').set(
			'sharepicture').set('stafftrewardtype').set('staffrewardvalue')
			.set('usertrewardtype').set('userrewardvalue').set('endTimestamp')
			.set('fixedTerm').set('description').set('contentDetail');
}

/**
 * 验证数据是否为空
 */
GiftCouponInfoDlg.validate = function() {

}

GiftCouponInfoDlg.clickType = function() {
	console.log(this.val());
	$('#type').val(this.val());
}

/**
 * 提交添加
 */
GiftCouponInfoDlg.addSubmit = function() {
	$('#contentDetail').val($('#summernote').code());
	this.clearData();
	this.collectData();
	// 提交信息
	var ajax = new $ax(Feng.ctxPath + "/giftCoupon/add", function(data) {
		Feng.success("添加成功!");
		window.parent.GiftCoupon.table.refresh();
		GiftCouponInfoDlg.close();
	}, function(data) {
		Feng.error("添加失败!" + data.responseJSON.message + "!");
	});
	ajax.set(this.couponInfoData);
	ajax.start();
}

/**
 * 提交修改
 */
GiftCouponInfoDlg.editSubmit = function() {

	this.clearData();
	this.collectData();

	// 提交信息
	var ajax = new $ax(Feng.ctxPath + "/giftCoupon/update", function(data) {
		Feng.success("修改成功!");
		window.parent.GiftCoupon.table.refresh();
		GiftCouponInfoDlg.close();
	}, function(data) {
		Feng.error("修改失败!" + data.responseJSON.message + "!");
	});
	ajax.set(this.couponInfoData);
	ajax.start();
}

/**
 * 提交投放
 */
GiftCouponInfoDlg.pushSubmit = function() {

	this.clearData();
	this.collectData();

	// 提交信息
	var ajax = new $ax(Feng.ctxPath + "/giftCoupon/pushCoupon", function(data) {
		Feng.success("投放成功!");
		window.parent.GiftCoupon.table.refresh();
		GiftCouponInfoDlg.close();
	}, function(data) {
		Feng.error("投放失败!" + data.responseJSON.message + "!");
	});
	ajax.set(this.couponInfoData);
	ajax.start();
}

$(function() {
	if ($("#typeValue").val() != undefined) {
		$('input[type=radio][name=radio1]').each(function() {
			if ($(this).val() == $("#typeValue").val()) {
				$(this).attr("checked", "true");
			}
		});
	}

	// 初始化头像上传
	var avatarUp = new $WebUpload("sharepicture");
	avatarUp.init();

	$('input[type=radio][name=radio1]').click(function() {
		$('#type').val(($(this).val()));
	});
});
