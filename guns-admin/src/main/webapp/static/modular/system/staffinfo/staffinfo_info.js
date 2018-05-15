/**
 * 初始化员工详情对话框
 */
var StaffinfoInfoDlg = {
    staffinfoInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                }
            }
        },
        mobile: {
            validators: {
                notEmpty: {
                    message: '手机不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
StaffinfoInfoDlg.clearData = function() {
    this.staffinfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StaffinfoInfoDlg.set = function(key, val) {
    this.staffinfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StaffinfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StaffinfoInfoDlg.close = function() {
    parent.layer.close(window.parent.Staffinfo.layerIndex);
}

/**
 * 收集数据
 */
StaffinfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('openid')
    .set('name')
    .set('mobile')
}

/**
 * 验证数据是否为空
 */
StaffinfoInfoDlg.validate = function () {
    $('#staffinfoForm').data("bootstrapValidator").resetForm();
    $('#staffinfoForm').bootstrapValidator('validate');
    return $("#staffinfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
StaffinfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/staffinfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.Staffinfo.table.refresh();
        StaffinfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.staffinfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StaffinfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/staffinfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.Staffinfo.table.refresh();
        StaffinfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.staffinfoInfoData);
    ajax.start();
}

$(function() {
   Feng.initValidator("staffinfoForm", StaffinfoInfoDlg.validateFields);
});
