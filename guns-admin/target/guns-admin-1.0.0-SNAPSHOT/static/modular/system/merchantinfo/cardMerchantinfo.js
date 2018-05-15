/**
 * 商户管理管理初始化
 */
var CardMerchantinfo = {
    id: "CardMerchantinfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardMerchantinfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '账户名', field: 'merchantname', visible: true, align: 'center', valign: 'middle'},
            {title: '企业名称', field: 'enterprisename', visible: true, align: 'center', valign: 'middle'},
            {title: '企业地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
            {title: '法人身份证', field: 'idcard', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
            {title: '截止日期', field: 'enddate', visible: true, align: 'center', valign: 'middle'},
            {title: '企业公众号appid', field: 'appid', visible: true, align: 'center', valign: 'middle'},
            {title: '企业公众号密钥', field: 'secret', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
CardMerchantinfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CardMerchantinfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加商户管理
 */
CardMerchantinfo.openAddCardMerchantinfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加商户管理',
        area: ['1000px', '620px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cardMerchantinfo/cardMerchantinfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看商户管理详情
 */
CardMerchantinfo.openCardMerchantinfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '商户管理详情',
            area: ['1000px', '620px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cardMerchantinfo/cardMerchantinfo_update/' + CardMerchantinfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除商户管理
 */
CardMerchantinfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/cardMerchantinfo/delete", function (data) {
            Feng.success("删除成功!");
            CardMerchantinfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("cardMerchantinfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询商户管理列表
 */
CardMerchantinfo.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    CardMerchantinfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CardMerchantinfo.initColumn();
    var table = new BSTable(CardMerchantinfo.id, "/cardMerchantinfo/list", defaultColunms);
    table.setPaginationType("client");
    CardMerchantinfo.table = table.init();
});
