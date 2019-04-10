<div class="row">
    <div class="box">
        <div class="box-header">
            <h3 class="box-title">公司管理</h3>
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
                    <button type="button" onclick="companyReload()" class="btn btn-primary">搜索</button>
                        <@shiro.hasPermission name="system/company/add">
                        <a class="btn btn-primary" onclick="companyToListAjax()" target="modal"
                           href="system/company/add">添加</a>
                        </@shiro.hasPermission>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <table id="company_tab">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var company_tab;
    $(function () {
        company_tab = $('#company_tab').bootstrapTable({
            url: "system/company/page",
            method: 'get',
            striped: true,                      //是否显示行间隔色
            pagination: true, //分页
            queryParams: queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            columns: [
                {title: "序号", field: "companyId"},
                {title: "公司名称", field: "name"},
                {title: "公司简称", field: "shortName"},
                {title: "公司编码", field: "code"},
                {title: "地址", field: "address"},
                {title: "状态", field: "status", formatter: tableModel.getState},
                {title: "创建时间", field: "createTime", sortable: true},
                {title: "创建人", field: "createBy"},
                {title: "操作", field: "operate", align: 'center', formatter: operateFormatter}
            ]
        })
    });

    function queryParams(params) {
        params.name = $("input[name='companyName']").val();
        console.log(params);
        return params;
    }

    function operateFormatter(value, row, index) {
        return [
            <@shiro.hasPermission name="system/company/edit">,
            '<a href="system/company/edit/' + row.companyId + '" onclick="companyToListAjax()" target="modal" modal="lg" >',
            '<i class="fa fa-edit"></i>修改',
            '</a>  ',
            </@shiro.hasPermission>,
            <@shiro.hasPermission name="system/company/delete">,
            '<a callback="companyReload();" data-body="确认要删除吗？" target="ajaxTodo" href="system/company/delete/' + row.companyId + '">',
            '<i class="fa fa-remove"></i>删除',
            '</a>',
            </@shiro.hasPermission>
        ].join('');
    }

    function companyToListAjax() {
        list_ajax = company_tab;
    }

    function companyReload() {
        reloadTable(company_tab);
    }
</script>
