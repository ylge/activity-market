<div class="row">
    <div class="box">
        <div class="box-header">
            <h3 class="box-title">角色管理</h3>
        </div>
        <div class="box-body">
            <div class="row">
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-search"></i></span>
                        <input type="text" class="form-control" id="name" placeholder="根据角色名称搜索...">
                    </div>
                </div>
                <div class="col-md-4">
                    <button type="button" onclick="roleReload()" class="btn btn-primary">搜索</button>
                        <@shiro.hasPermission name="system/role/add">
                            <a class="btn btn-primary" onclick="roleToListAjax()" target="modal" modal="lg"
                               href="system/role/add">添加</a>
                        </@shiro.hasPermission>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table id="role_tab">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var role_tab;
    $(function () {
        role_tab = $('#role_tab').bootstrapTable({
            // height : tableModel.getHeight(),
            url: "system/role/page",
            method: 'get',
            striped: true,                      //是否显示行间隔色
            pagination: true, //分页
            queryParams: queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            columns: [
                {title: "ID", field: "roleId"},
                {title: "角色名称", field: "name"},
                {title: "角色描述", field: "remark"},
                {title: "状态", field: "status", formatter: tableModel.getState},
                {title: "创建时间", field: "createTime", sortable: true},
                {title: "操作", field: "operate", align: 'center', formatter: operateFormatter}
            ]
        });
    });

    function queryParams(params) {
        params.name= $("#name").val()
        return params;
    }

    function operateFormatter(value, row, index) {
        return [
            <@shiro.hasPermission name="system/role/edit">,
            '<a target="modal" modal="lg" onclick="roleToListAjax()"  href="system/role/user/' + row.roleId + '" >',
            '<i class="fa fa-user"></i>用户管理',
            '</a>  ',
            '<a target="modal" modal="lg" onclick="roleToListAjax()"  href="system/role/edit/' + row.roleId + '" >',
            '<i class="fa fa-edit"></i>修改',
            '</a>  ',
            </@shiro.hasPermission>,
            <@shiro.hasPermission name="system/role/delete">,
            '<a callback="roleReload();" data-body="确认要删除吗？" target="ajaxTodo" href="system/role/delete/' + row.roleId + '">',
            '<i class="fa fa-remove"></i>删除',
            '</a>',
            </@shiro.hasPermission>
        ].join('');
    }

    function roleToListAjax() {
        list_ajax = role_tab;
    }

    function roleReload() {
        reloadTable(role_tab);
    }
</script>