/**
 * 初始化商户管理详情对话框
 */
var CardMerchantinfoInfoDlg = {
    cardMerchantinfoInfoData : {},
    validateFields: {
    	merchantname: {
            validators: {
                notEmpty: {
                    message: '帐户名不能为空'
                }
            }
        },
        mobile: {
            validators: {
                notEmpty: {
                    message: '手机不能为空'
                }
            }
        },
        enterprisename: {
        	validators: {
        		notEmpty: {
        			message: '企业名称不能为空'
        		}
        	}
        },
        address: {
        	validators: {
        		notEmpty: {
        			message: '企业地址不能为空'
        		}
        	}
        },
        idcard: {
        	validators: {
        		notEmpty: {
        			message: '法人身份证不能为空'
        		}
        	}
        },
        idcardfront: {
        	validators: {
        		notEmpty: {
        			message: '请上传手持身份证正面'
        		}
        	}
        },
        idcardback: {
        	validators: {
        		notEmpty: {
        			message: '请上传手持身份证背面'
        		}
        	}
        },
        license: {
        	validators: {
        		notEmpty: {
        			message: '请上传营业执照'
        		}
        	}
        },
        logo: {
        	validators: {
        		notEmpty: {
        			message: '请上传企业logo'
        		}
        	}
        },
        enddate: {
        	validators: {
        		notEmpty: {
        			message: '截止日期不能为空'
        		}
        	}
        },
        appid: {
        	validators: {
        		notEmpty: {
        			message: '微信公众号appid不能为空'
        		}
        	}
        },
        secret: {
        	validators: {
        		notEmpty: {
        			message: '微信公众号密匙不能为空'
        		}
        	}
        }
    }
};

/**
 * 清除数据
 */
CardMerchantinfoInfoDlg.clearData = function() {
    this.cardMerchantinfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardMerchantinfoInfoDlg.set = function(key, val) {
    this.cardMerchantinfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardMerchantinfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardMerchantinfoInfoDlg.close = function() {
    parent.layer.close(window.parent.CardMerchantinfo.layerIndex);
}

/**
 * 收集数据
 */
CardMerchantinfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('merchantname')
    .set('enterprisename')
    .set('address')
    .set('license')
    .set('idcard')
    .set('idcardfront')
    .set('idcardback')
    .set('mobile')
    .set('enddate')
    .set('appid')
    .set('secret')
    .set('logo');
}

/**
 * 验证数据是否为空
 */
CardMerchantinfoInfoDlg.validate = function () {
    $('#cardMerchantinfoForm').data("bootstrapValidator").resetForm();
    $('#cardMerchantinfoForm').bootstrapValidator('validate');
    return $("#cardMerchantinfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
CardMerchantinfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardMerchantinfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardMerchantinfo.table.refresh();
        CardMerchantinfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardMerchantinfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardMerchantinfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardMerchantinfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardMerchantinfo.table.refresh();
        CardMerchantinfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardMerchantinfoInfoData);
    ajax.start();
}

$(function() {

	Feng.initValidator("cardMerchantinfoForm", CardMerchantinfoInfoDlg.validateFields);
	 // 初始化头像上传
    var avatarUp = new $WebUpload("idcardfront");
    var avatarUp2 = new $WebUpload("idcardback");
    var avatarUp3 = new $WebUpload("license");
    var avatarUp4 = new $WebUpload("logo");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();
    avatarUp2.setUploadBarId("progressBar");
    avatarUp2.init();
    avatarUp3.setUploadBarId("progressBar");
    avatarUp3.init();
    avatarUp4.setUploadBarId("progressBar");
    avatarUp4.init();
});
