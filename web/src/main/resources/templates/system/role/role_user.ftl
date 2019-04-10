<div class="row roleUser">
    <div class="col-md-6">
        <div class="box box-info">
            <div class="box-header">
                <h3 class="box-title">已选用户</h3>
            </div>
            <div class="box-body">
                <table id="role_user_tab">
                </table>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="box box-info">
            <div class="box-header">
                <h3 class="box-title">待选用户</h3>
            </div>
            <div class="box-body">
                <div class="row">
                    <div class="col-md-8">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-search"></i></span>
                            <input type="text" class="form-control" name="userName" placeholder="根据用户名称搜索...">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <button type="button" onclick="userSearch()" class="btn btn-primary">搜索</button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <table id="role_user_table">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var role_user_tab;
    var role_user_table;
    $(function () {
        role_user_tab = $('#role_user_tab').bootstrapTable({
            url: "system/role/user",
            method: 'get',
            striped: true,                      //是否显示行间隔色
            pagination: true, //分页
            queryParams: queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            columns: [
                {title: "ID", field: "userId"},
                {title: "名称", field: "name"},
                {title: "操作", field: "operate", align: 'center', formatter: operateFormatter}
            ]
        });
        role_user_table = $('#role_user_table').bootstrapTable({
            url: "system/role/user",
            method: 'get',
            striped: true,                      //是否显示行间隔色
            pagination: true, //分页
            queryParams: queryParams1,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            columns: [
                {title: "ID", field: "userId"},
                {title: "名称", field: "name"},
                {title: "操作", field: "operate", align: 'center', formatter: operateFormatter1}
            ]
        });
    });

    function queryParams(params) {
        params.roleId = ${roleId};
        params.type = 1;
        return params;
    }

    function queryParams1(params) {
        debugger;
        params.roleId = ${roleId};
        params.name = $(".roleUser input[name='userName']").val();
        params.type = 2;
        return params;
    }

    function operateFormatter(value, row, index) {
        var result;
        result = [
            '<a callback="roleUserReload();" href="system/role/user/delete/${roleId}/' + row.userId + '" data-body="确认要移除吗？" target="ajaxTodo">',
            '<i class="fa fa-remove"></i>移除',
            '</a>  ',
        ];
        return result.join('');
    }

    function operateFormatter1(value, row, index) {
        var result;
        result = [
            '<a callback="roleUserReload();" href="system/role/user/add/${roleId}/' + row.userId + '" data-body="确认要添加吗？" target="ajaxTodo" >',
            '<i class="fa fa-plus"></i>添加',
            '</a>  ',
        ];
        return result.join('');
    }

    function roleUserReload() {
        reloadTable(role_user_tab);
        reloadTable(role_user_table);
    }

    function userSearch() {
        reloadTable(role_user_table);
    }

</script>