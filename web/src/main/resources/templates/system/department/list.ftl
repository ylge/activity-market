<div class="row">
    <div class="box">
        <div class="box-header">
            <h3 class="box-title">部门管理</h3>
        </div>
        <div class="box-body">
            <div class="row">
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-search"></i></span>
                        <input type="text" class="form-control" name="companyName" placeholder="根据公司名称搜索...">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-search"></i></span>
                        <input type="text" class="form-control" name="name" placeholder="根据部门名称搜索...">
                    </div>
                </div>
                <div class="col-md-4">
                    <button type="button" onclick="departmentReload()" class="btn btn-primary">搜索</button>
                        <@shiro.hasPermission name="system/department/add">
                        <a class="btn btn-primary" onclick="departmentToListAjax()" target="modal"
                           href="system/department/add">添加</a>
                        </@shiro.hasPermission>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <table id="department_tab" class="table table-bordered table-striped">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var department_tab;
    $(function () {
        department_tab = $('#department_tab').bootstrapTable({
            // height : tableModel.getHeight(),
            url: "system/department/page",
            method: 'get',
            striped: true,                      //是否显示行间隔色
            pagination: true, //分页
            queryParams: queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            columns: [
                {title: "ID", field: "departmentId"},
                {title: "部门名称", field: "name"},
                {title: "所属公司", field: "companyName"},
                {title: "描述", field: "comment"},
                {title: "状态", field: "status", formatter: tableModel.getState},
                {title: "创建时间", field: "createTime", sortable: true},
                {title: "创建人", field: "createBy"},
                {title: "修改时间", field: "updateTime"},
                {title: "修改人", field: "updateBy"},
                {title: "操作", field: "operate", align: 'center', formatter: operateFormatter}
            ]
        })
    });

    function queryParams(params) {
        params.name = $("input[name='name']").val();
        params.companyName = $("input[name='companyName']").val();
        return params;
    }

    function operateFormatter(value, row, index) {
        return [
            <@shiro.hasPermission name="system/department/edit">,
            '<a target="modal"  onclick="departmentToListAjax()" href="system/department/edit/' + row.departmentId + '" >',
            '<i class="fa fa-edit"></i>修改',
            '</a>  ',
            </@shiro.hasPermission>,
            <@shiro.hasPermission name="system/department/delete">,
            '<a callback="departmentReload();" data-body="确认要删除吗？" target="ajaxTodo" href="system/department/delete/' + row.departmentId + '">',
            '<i class="fa fa-remove"></i>删除',
            '</a>',
            </@shiro.hasPermission>
        ].join('');
    }

    function departmentToListAjax() {
        list_ajax = department_tab;
    }

    function departmentReload() {
        reloadTable(department_tab);
    }
</script>
