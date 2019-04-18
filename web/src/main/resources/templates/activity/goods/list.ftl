<div class="row goods">
    <div class="box">
        <div class="box-storeer">
            <h3 class="box-title">活动管理</h3>
        </div>
        <div class="box-body">
            <div class="row">
                <div class="col-md-2">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-search"></i></span>
                        <input type="text" class="form-control" name="goodsName" placeholder="活动名称...">
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-search"></i></span>
                        <input type="text" class="form-control" name="storePhone" placeholder="商家手机号...">
                    </div>
                </div>
                <#--<div class="col-md-2">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-search"></i></span>
                        <input type="text" class="form-control" name="storeName" placeholder="商家名称...">
                    </div>
                </div>-->
                <div class="col-md-3">
                    <button type="button" onclick="goodsReload()" class="btn btn-primary">搜索</button>
                        <@shiro.hasPermission name="activity/goods/add">
                        <a class="btn btn-primary" onclick="goodsToListAjax()" target="modal"
                           href="activity/goods/add">添加活动</a>
                        </@shiro.hasPermission>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <table id="goods_tab" class="table table-bordered table-striped">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var goods_tab;
    $(function () {
        $(".selectpicker").selectpicker({
            noneSelectedText: '请选择'
        });
        goods_tab = $('#goods_tab').bootstrapTable({
            height: tableModel.getHeight(),
            url: "activity/goods/page",
            method: 'get',
            striped: true,                      //是否显示行间隔色
            pagination: true, //分页
            queryParams: queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            sortName: "goodsId",
            sortOrder: "desc",
            columns: [
                {title: "活动Id", field: "goodsId"},
                {title: "活动商品名称", field: "goodsName"},
                {
                    title: "活动图片", field: "goodsImage",
                    formatter: function (value, row, index) {
                        var s;
                        if (row.goodsImage != null) {
                            var url = row.goodsImage;
                            s = '<img onclick="imageView(\'' + url + '\')" style="width:60px;height:40px;margin:-5px auto;" src="' + url + '" />';
                        }
                        return s;
                    }
                },
                {title: "价格", field: "goodsPrice"},
                {title: "开始时间", field: "beginTime"},
                {title: "结束时间", field: "endTime"},
                {title: "参与人数", field: "joinNumber"},
                {title: "收入", field: "storeIncome"},
                {title: "支出", field: "storeWithdraw"},
                {title: "商家", field: "storeName"},
                {title: "商家联系方式", field: "storePhone"},
                {title: "状态", field: "status", formatter: tableModel.getState},
                {title: "操作", field: "operate", align: 'center', formatter: operateFormatter}
            ],
        });
    });

    function queryParams(params) {
        params.storeName = $(".goods input[name='storeName']").val();
        params.goodsName = $(".goods input[name='goodsName']").val();
        params.storePhone = $(".goods input[name='storePhone']").val();
        params.beginTime = $(".goods input[name='beginTime']").val();
        params.endTime = $(".goods input[name='endTime']").val();
        return params;
    }

    function operateFormatter(value, row, index) {
        var result = [
            <@shiro.hasPermission name="activity/goods/edit">
            '<a target="modal"  onclick="goodsToListAjax()" href="activity/goods/edit/' + row.goodsId + '" >',
            '<i class="fa fa-edit"></i>详情',
            '</a>  ',
            </@shiro.hasPermission>
            '<a target="modal"  href="activity/report/income/' + row.goodsId + '" >',
            '<i class="fa fa-edit"></i>收入列表',
            '</a>  ',
            '<a target="modal"  href="activity/report/withdraw/' + row.goodsId + '" >',
            '<i class="fa fa-edit"></i>支出列表',
            '</a>  ',
            '<a target="modal"  href="activity/user/manage/' + row.goodsId + '" >',
            '<i class="fa fa-edit"></i>设置店员',
            '</a>'
        ];
        if (row.status === 1) {
            <@shiro.hasPermission name="activity/goods/delete">
                result.push(
                        '<a callback="goodsReload();" data-body="确认要禁用吗？" target="ajaxTodo" href="activity/goods/delete/' + row.goodsId + '/0">',
                        '<i class="fa fa-lock"></i>禁用',
                        '</a>',
                );
            </@shiro.hasPermission>
        } else {
            <@shiro.hasPermission name="activity/goods/delete">
                result.push(
                        '<a callback="goodsReload();" data-body="确认要启用吗？" target="ajaxTodo" href="activity/goods/delete/' + row.goodsId + '/1">',
                        '<i class="fa fa-unlock"></i>启用',
                        '</a>',
                );
            </@shiro.hasPermission>
        }
        return result.join('');
    }

    function goodsToListAjax() {
        list_ajax = goods_tab;
    }

    function goodsReload() {
        reloadTable(goods_tab);
    }

</script>
