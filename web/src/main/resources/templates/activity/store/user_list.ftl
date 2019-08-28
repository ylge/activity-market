<div class="row storeUser">
    <div class="box">
        <div class="box-header">
            <h3 class="box-title">店员设置</h3>
        </div>
        <div class="box-body">
            <input type="hidden" name="goodsId" value="${goodsId!}">
            <div class="row">
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-search"></i></span>
                        <input type="text" class="form-control" name="nickName" placeholder="微信昵称...">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-search"></i></span>
                        <input type="text" class="form-control" name="phone" placeholder="用户手机号...">
                    </div>
                </div>
                <div class="col-md-4">
                    <button type="button" onclick="storeUserReload()" class="btn btn-primary">搜索</button>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <table id="storeUser_tab" class="table table-bordered table-striped">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var storeUser_tab;
    $(function () {
        $(".selectpicker").selectpicker({
            noneSelectedText: '请选择'
        });
        storeUser_tab = $('#storeUser_tab').bootstrapTable({
            height: tableModel.getHeight(),
            url: "activity/user/page",
            method: 'get',
            striped: true,                      //是否显示行间隔色
            pagination: true, //分页
            queryParams: queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            columns: [
                {title: "用户id", field: "userId"},
                {title: "微信昵称", field: "nickName"},
                {
                    title: "头像", field: "avatar",
                    formatter: function (value, row, index) {
                        var s;
                        if (row.avatar != null) {
                            var url = row.avatar;
                            s = '<img style="width:60px;height:40px;margin:-5px auto;" src="' + url + '" />';
                        }
                        return s;
                    }
                },
                {title: "手机号", field: "phone"},
                {title: "身份", field: "goodsId", formatter: getUser},
                // {title: "状态", field: "status", formatter: tableModel.getState},
                {title: "操作", field: "operate", align: 'center', formatter: operateFormatter}
            ],
        });
    });

    function queryParams(params) {
        params.nickName = $(".storeUser input[name='nickName']").val();
        params.phone = $(".storeUser input[name='phone']").val();
        params.goodsId = $(".storeUser input[name='goodsId']").val();
        return params;
    }

    function getUser(value, row, index) {
        if (value !== '0' && value != null) {
            return "店员";
        } else {
            return "普通用户"
        }

    }

    function operateFormatter(value, row, index) {
        var result = [];
        if (row.goodsId === '0') {
            result.push(
                '<a href="#" onclick="updateStoreUser(' + row.userId + ',1,${goodsId!})">',
                '<i class="fa fa-edit"></i>设为店员',
                '</a>',
            );

        } else {
            result.push(
                '<a href="#" onclick="updateStoreUser(' + row.userId + ',0,0)">',
                '<i class="fa fa-edit"></i>设为普通用户',
                '</a>',
            );
        }
        return result.join('');
    }


    function updateStoreUser(userId, status, goodsId) {
        var url = 'activity/user/delete';
        $.post(url, {'userId': userId, 'status': status, 'goodsId': goodsId},
            function (result) {
                console.log(result);
                if (result.code === 200) {
                    alertMsg("操作成功", "success");
                    storeUserReload();
                } else {
                    alertMsg("操作失败:" + result.msg, "danger");
                }
            }
        );
    }

    function storeUserReload() {
        reloadTable(storeUser_tab);
    }

</script>
