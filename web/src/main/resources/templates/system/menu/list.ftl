<div class="row">
    <div class="box">
        <div class="box-header">
            <h3 class="box-title">菜单管理</h3>
        </div>
        <div class="box-body">
            <table id="menu_tab"></table>
        </div>
    </div>
</div>
<script type="text/javascript">
    var menu_tab;
    var $menu_tab = $('#menu_tab');
    $(function () {
        menu_tab = $('#menu_tab').bootstrapTable({
            url: "system/menu/page",
            method: 'get',
            striped: true,                      //是否显示行间隔色
            // height: $(window).height(),
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            idField: 'id',
            columns: [
                {title: "菜单名称", field: "name"},
                {title: "菜单URL", field: "url"},
                {title: "菜单类型", field: "isMenu", formatter: typeFormatter},
                {title: "排序", field: "sort", formatter: menuSort},
                {title: "操作", field: "operate", align: 'center', formatter: operateFormatter}
            ],
            treeShowField: 'name',
            parentIdField: 'parentId',
            onLoadSuccess: function (data) {
                $menu_tab.treegrid({
                    // initialState: 'collapsed',// 所有节点都折叠 默认展开
                    // treeColumn: 0,
                    expanderExpandedClass: 'fa fa-minus',
                    expanderCollapsedClass: 'fa fa-plus',
                    onChange: function () {
                        $menu_tab.bootstrapTable('resetWidth');
                    }
                });
                //只展开树形的第一级节点
                // $menu_tab.treegrid('getRootNodes').treegrid('expand');
            }
        });
    });

    function menuSort(value, row, index) {
        return [
            '<input type="text" name="sort" value="' + row.sort + '" id="' + row.id + '" style="width:30px" onblur="updateMenuSort(this)"/>'
        ].join('');

    }

    // 格式化按钮
    function operateFormatter(value, row, index) {
        var result;
        result = [
            '<a href="/system/menu/add/' + row.id + '" onclick="menuToListAjax()" target="modal">',
            '<i class="fa fa-plus"></i>添加',
            '</a>  ',
        ];
        if (row.id != 1) {
            result.push(
                    '<a href="/system/menu/edit/' + row.id + '" onclick="menuToListAjax()" target="modal">',
                    '<i class="fa fa-edit"></i>修改',
                    '</a>  ',
                    '<a href="/system/menu/delete/' + row.id + '" callback="menuReload();" data-body="确认要删除吗？" target="ajaxTodo">',
                    '<i class="fa fa-remove"></i>删除',
                    '</a>  ',
            );
        }
        return result.join('');

    }

    // 格式化类型
    function typeFormatter(value, row, index) {
        if (value === 1) {
            return '菜单';
        }
        if (value === 0) {
            return '按钮';
        }
        return '-';
    }

    //更新排序
    function updateMenuSort(date) {
        debugger;
        var _id = $(date).attr("id");
        var _sort = $(date).val();
        if (_sort !== '') {
            $.post('/system/menu/save', {
                id: _id,
                sort: _sort
            }, function (data) {
                if (data.code === 200) {
                    alertMsg("修改成功", "success");
                }
            });
        }
    }

    function menuToListAjax() {
        list_ajax = menu_tab;
    }

    function menuReload() {
        reloadTable(menu_tab);
    }
</script>