/**
 * 客户管理初始化
 */
var Userinfo = {
    id: "UserinfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Userinfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '微信昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
            {title: '绑定手机号', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
            {title: '是否关注', field: 'isfollowName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Userinfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Userinfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加客户
 */
Userinfo.openAddUserinfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加客户',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userinfo/userinfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看客户详情
 */
Userinfo.openUserinfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '客户详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/userinfo/userinfo_update/' + Userinfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除客户
 */
Userinfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/userinfo/delete", function (data) {
            Feng.success("删除成功!");
            Userinfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userinfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询客户列表
 */
Userinfo.search = function () {
    var queryData = {};
    queryData['mobile'] = $("#mobile").val();
    queryData['isfollow'] = $("#isfollow").val();
    Userinfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Userinfo.initColumn();
    var table = new BSTable(Userinfo.id, "/userinfo/list", defaultColunms);
    table.setPaginationType("client");
    Userinfo.table = table.init();
});
