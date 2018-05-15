/**
 * 员工管理初始化
 */
var Staffinfo = {
    id: "StaffinfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Staffinfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: 'openid', field: 'openid', visible: true, align: 'center', valign: 'middle'},
            {title: '微信昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '手机', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'stateName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Staffinfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Staffinfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击查看获取员工的微信信息的URL
 */
Staffinfo.openAddStaffinfo2 = function () {
    var index = layer.open({
        type: 2,
        title: '获取微信信息URL',
        area: ['700px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/staffinfo/staffinfo_url'
    });
    this.layerIndex = index;
};

/**
 * 点击查看新增员工二维码
 */
Staffinfo.openImage = function () {
    var index = layer.open({
        type: 2,
        title: '新增员工二维码',
        area: ['400px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/staffinfo/staffinfo_image'
    });
    this.layerIndex = index;
};

/**
 * 点击添加员工
 */
Staffinfo.openAddStaffinfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加员工',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/staffinfo/staffinfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看员工详情
 */
Staffinfo.openStaffinfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '员工详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/staffinfo/staffinfo_update/' + Staffinfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除员工
 */
Staffinfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/staffinfo/delete", function (data) {
            Feng.success("删除成功!");
            Staffinfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("staffinfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询员工列表
 */
Staffinfo.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['nickname'] = $("#nickname").val();
    queryData['state'] = $("#state").val();
    Staffinfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Staffinfo.initColumn();
    var table = new BSTable(Staffinfo.id, "/staffinfo/list", defaultColunms);
    table.setPaginationType("client");
    Staffinfo.table = table.init();
});
