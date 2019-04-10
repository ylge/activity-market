<form id="roleUpdateForm">
    <div class="modal-body">
        <div class="row">
            <div class="col-md-3">
                <#if roles ??>
                    <ul class="list-group">
                        <#list roles as role>
                            <a class="list-group-item" onclick="menuTree(this,${role.roleId})">${role.name}</a>
                        </#list>
                    </ul>
                </#if>
            </div>
            <div class="col-md-9">
                <div id="menuTree"></div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <div class="pull-right">
            <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                    class="fa fa-close"></i>关闭
            </button>
            <button type="button" class="btn btn-primary btn-sm" onclick="roleMenuSave();"><i
                    class="fa fa-save"></i>保存
            </button>
        </div>
    </div>
</form>
<script type="text/javascript">
    var _roleId;
    function menuTree(data, roleId) {
        $('.list-group').each(function () {
            var group = $(this);
            group.find(".list-group-item.active").removeClass("active");
        });
        $(data).addClass('active');
        _roleId = roleId;
        $.getJSON('system/role/menutree', {roleId: _roleId}, function (data) {
            if (data.code === 200) {
                $('#menuTree').treeview({
                    data: data.data,
                    showIcon: false,
                    highlightSelected: true,
                    showCheckbox: true,
                    onNodeChecked: nodeChecked,
                    onNodeUnchecked: nodeUnchecked
                }).treeview('expandAll', {levels: 2, silent: true});
            } else {
                alertMsg("加载失败" + data.msg, "success");
            }
        });
    }
    function roleMenuSave() {
        var checkNodes = $('#menuTree').treeview('getChecked');
        var _menuIds = [];
        $.each(checkNodes, function (i, obj) {
            _menuIds[i] = obj.id;
        });
        var roleMenu = {};
        roleMenu.roleId = _roleId;
        roleMenu.menuIds = _menuIds.join();
        $.post('/system/role/grant', roleMenu, function (data) {
            if (data.code === 200) {
                $("#lgModal").modal('hide');
            }
            alertMsg(data.msg, "success");
        })
    }
</script>
