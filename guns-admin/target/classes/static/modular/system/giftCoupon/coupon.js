/**
 * 栏目管理初始化
 */
var GiftCoupon = {
    id: "CouponTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
GiftCoupon.initColumn = function () {
	var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'couponName', align: 'center', valign: 'middle', sortable: true},
        {title: '有效期', field: 'useTime', align: 'center', valign: 'middle', sortable: true},
        {title: '金额', field: 'amount', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createDate', align: 'center', valign: 'middle', sortable: true},
        {title: '创建人', field: 'createUser', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle', sortable: true},
        {field: 'Desc',title: '操作',align: 'center',formatter : operateFormatter, events : operateEvents}
        ]
    return columns;
};

/**
 * 检查是否选中
 */
GiftCoupon.check = function () {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	GiftCoupon.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
GiftCoupon.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加卡卷',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/giftCoupon/add_view'
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 打开查看栏目详情
 */
GiftCoupon.openUpdate = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '新闻详情',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/giftCoupon/update_view/' + this.seItem.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
};
/**
 * 删除优惠券
 */
GiftCoupon.openDelete = function () {
	
	if (this.check()) {
		var couponId = GiftCoupon.seItem.id;
		console.log(couponId);
		var operation = function(){
		    var ajax = new $ax(Feng.ctxPath + "/giftCoupon/delete", function (data) {
		        Feng.success("删除成功!");
		        GiftCoupon.table.refresh();
		    }, function (data) {
		        Feng.error("删除失败!" + data.responseJSON.message + "!");
		    });
		    ajax.set("couponId",couponId);
		    ajax.start();
		};
		Feng.confirm("是否删除该优惠券?", operation);
	}
};
/**
 * 投放优惠券
 */
GiftCoupon.openPush = function () {
	if (this.check()) {
		var index = layer.open({
			type: 2,
			title: '投放优惠券',
			area: ['800px', '450px'], //宽高
			fix: false, //不固定
			maxmin: true,
			content: Feng.ctxPath + '/giftCoupon/push_view/' + this.seItem.id
		});
		layer.full(index);
		this.layerIndex = index;
	}
};

/**
 * 作废
 */
GiftCoupon.del = function (row) {
	var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/giftCoupon/stateUpdate", function (data) {
            Feng.success("作废成功!");
            GiftCoupon.table.refresh();
        }, function (data) {
            Feng.error("作废失败!" + data.responseJSON.message + "!");
        });
        ajax.set("couponId",row.id);
        ajax.set("state",2);
        ajax.start();
	};
	Feng.confirm("是否作废该优惠券?", operation);
};

/**
 * 启用
 */
GiftCoupon.UpdateStateOne = function (row) {
		var ajax = new $ax(Feng.ctxPath + "/giftCoupon/stateUpdate", function (data) {
			Feng.success("操作成功!");
			GiftCoupon.table.refresh();
		}, function (data) {
			Feng.error("删除失败!" + data.responseJSON.message + "!");
		});
		ajax.set("couponId",row.id);
		ajax.set("state",1);
		ajax.start();
};

/**
 * 停用
 */
GiftCoupon.UpdateStateZero = function (row) {
		var ajax = new $ax(Feng.ctxPath + "/giftCoupon/stateUpdate", function (data) {
			Feng.success("操作成功!");
			GiftCoupon.table.refresh();
		}, function (data) {
			Feng.error("删除失败!" + data.responseJSON.message + "!");
		});
		ajax.set("couponId",row.id);
		ajax.set("state",0);
		ajax.start();
	
};

/**
 * 查询栏目列表
 */
GiftCoupon.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    News.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = GiftCoupon.initColumn();
    var table = new BSTable("CouponTable", "/giftCoupon/list", defaultColunms);
    table.setPaginationType("client");
    GiftCoupon.table = table.init();
});

function operateFormatter(value, row, index) {
	if(row.state == 1){
		return ['<button id="couponUpdateStateZero"  type="button" class="btn btn-primary btn-xs">停用</button>'+'<button id="couponDel"  type="button" class="btn btn-primary btn-xs button-margin">作废</button>'
            ].join('');
	}else{
		return ['<button id="couponUpdateStateOne"  type="button" class="btn btn-primary btn-xs">启用</button>'+'<button id="couponDel"  type="button" class="btn btn-primary btn-xs button-margin">作废</button>'
            ].join('');
	}
    
};

window.operateEvents = {
		"click #couponDel":function(e,value,row,index){
			Coupon.del(row);
		},
		"click #couponUpdateStateOne":function(e,value,row,index){
			Coupon.UpdateStateOne(row);
		},
		"click #couponUpdateStateZero":function(e,value,row,index){
			Coupon.UpdateStateZero(row);
		}
}
