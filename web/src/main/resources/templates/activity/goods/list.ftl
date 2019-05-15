<div class="row goods">
    <div class="box">
        <div class="box-header">
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
                    <button type="button" onclick="goodsReload()" class="btn btn-primary">查询</button>
                        <@shiro.hasPermission name="activity/goods/add">
                        <a class="btn btn-primary" onclick="goodsToListAjax()" target="modal"
                           href="activity/goods/add">新建活动</a>
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
                {
                    title: "活动信息", field: "goodsName",
                    formatter: function (value, row, index) {
                        return '活动ID : ' + row.goodsId +
                                '<br>名称 : ' + row.goodsName +
                                '<br>价格 : ' + row.goodsPrice +
                                '<br>连接 : <a href="#" onclick="copy()">点击复制</a><br>' + '<input id="copy" style="opacity: 0;width: 10px" value="www.hbysg.club/marketing/?i=' + row.goodsId + '">';
                    }
                },
                {
                    title: "活动图片", field: "goodsImage",
                    formatter: function (value, row, index) {
                        var s;
                        if (row.goodsImage != null) {
                            var url = row.goodsImage;
                            s = '<img onclick="imageView(\'' + url + '\')" style="width:60px;height:60px;margin:-5px auto;" src="' + url + '" />';
                        }
                        return s;
                    }
                },
                {title: "活动时间", field: "beginTime", formatter: activityTime},
                {title: "数据统计", field: "storeIncome", formatter: dataCount},
                {title: "商家信息", field: "storeName", formatter: storeInfo},
                {title: "状态", field: "status", formatter: tableModel.getState},
                {title: "操作", field: "operate", align: 'center', formatter: operateFormatter}
            ]
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
            '<i class="fa fa-edit"></i> 修改活动',
            '</a><br>',
            </@shiro.hasPermission>
            '<a target="modal"  href="activity/user/manage/' + row.goodsId + '" >',
            '<i class="fa fa-user"></i> 设置店员',
            '</a><br>'
        ];
        result.push(
                '<a href="activity/report/income/' + row.goodsId + '" >',
                '<i class="fa fa-line-chart"></i> 收入列表',
                '</a><br>',
                '<a href="activity/report/withdraw/' + row.goodsId + '" >',
                '<i class="fa fa-line-chart"></i> 支出列表',
                '</a><br>'
        );
        if (row.status === 1) {
            <@shiro.hasPermission name="activity/goods/delete">
                result.push(
                        '<a callback="goodsReload();" data-body="确认要禁用吗？" target="ajaxTodo" href="activity/goods/delete/' + row.goodsId + '/0">',
                        '<i class="fa fa-lock"></i> 关闭活动<br>',
                        '</a>',
                );
            </@shiro.hasPermission>
        } else {
            <@shiro.hasPermission name="activity/goods/delete">
                result.push(
                        '<a callback="goodsReload();" data-body="确认要启用吗？" target="ajaxTodo" href="activity/goods/delete/' + row.goodsId + '/1">',
                        '<i class="fa fa-unlock"></i> 打开活动<br>',
                        '</a>',
                );
            </@shiro.hasPermission>
        }
        return result.join('');
    }

    function activityUrl(value, row, index) {
        return '<a href="#" onclick="copy()">点击复制</a><br>' + '<input id="copy" style="opacity: 0;width: 10px" value="www.hbysg.club/marketing/?i=' + row.goodsId + '">';
    }

    function copy() {
        var e = document.getElementById("copy");
        e.select(); // 选择对象
        document.execCommand("Copy"); // 执行浏览器复制命令
        alertMsg("复制成功！", "success");
    }

    function goodsToListAjax() {
        list_ajax = goods_tab;
    }

    function goodsReload() {
        reloadTable(goods_tab);
    }

    function activityTime(value, row, index) {
        return "创建时间 ：" + row.createTime + "<br>开始时间 ：" + row.beginTime + "<br>结束时间 ：" + row.endTime;
    }

    function storeInfo(value, row, index) {
        return "名称 ：" + row.storeName +
                "<br>电话 ：" + row.storePhone +
                "<br>联系人 ：" + row.linkName;
    }

    function dataCount(value, row, index) {
        return '参与人数：<span class="label label-primary">' + row.joinNumber + '</span><br>' +
                '收入金额：<span class="label label-success">' + row.storeIncome + '</span><br>' +
                '支出金额：<span class="label label-danger">' + row.storeWithdraw + '</span>';
    }

    /*function storeIncomeExport(goodsId) {
        var url = "activity/report/income/export?";
        if(isEmpty(goodsId)){
            url = url + "&goodsId=" + goodsId;
        }
        window.location.href = url;
    }*/

</script>
