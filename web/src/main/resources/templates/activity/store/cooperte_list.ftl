<div class="row cooperte">
    <div class="box">
        <div class="box-header">
            <h3 class="box-title">业务合作</h3>
        </div>
        <div class="box-body">
            <div class="row">
                <div class="col-sm-12">
                    <table id="cooperte_tab" class="table table-bordered table-striped">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var cooperte_tab;
    $(function () {
        $(".selectpicker").selectpicker({
            noneSelectedText: '请选择'
        });
        cooperte_tab = $('#cooperte_tab').bootstrapTable({
            height: tableModel.getHeight(),
            url: "activity/cooperte/page",
            method: 'get',
            striped: true,                      //是否显示行间隔色
            pagination: true, //分页
            queryParams: queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            columns: [
                {title: "ID", field: "id"},
                {title: "姓名", field: "userName"},
                {title: "手机号", field: "phone"},
                {title: "时间", field: "createTime"},
                // {title: "操作", field: "operate", align: 'center', formatter: operateFormatter}
            ],
        });
    });

    function queryParams(params) {
        params.nickName = $(".cooperte input[name='nickName']").val();
        params.phone = $(".cooperte input[name='phone']").val();
        params.goodsId = $(".cooperte input[name='goodsId']").val();
        return params;
    }

    function storeUserReload() {
        reloadTable(cooperte_tab);
    }

</script>
