<form id="roleAddForm" class="form-horizontal">
    <div class="modal-body">
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label id="userNoLabel">角色名称</label>
                    <input type="text" class="form-control" id="roleName" name="name" placeholder="角色名称..."
                           required>
                </div>
                <div class="form-group">
                    <label id="phoneLabel">备注</label>
                    <input type="text" class="form-control" id="roleRemark" name="remark" placeholder="描述...">
                </div>
            </div>
            <div class="col-md-9">
                <div id="menuTree" style=" overflow-y:auto;height:650px;"></div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <div class="pull-right">
            <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                    class="fa fa-close"></i>关闭
            </button>
            <button type="button" class="btn btn-primary btn-sm" onclick="roleSave();"><i
                    class="fa fa-save"></i>保存
            </button>
        </div>
    </div>
    </div>
</form>
<script type="text/javascript">
    $("#lgModal").on("shown.bs.modal", function () {
        /*$('.list-group').each(function () {
            var group = $(this);
            group.find(".list-group-item.active").removeClass("active");
        });*/
        $.getJSON('system/role/menutree', {roleId: '0'}, function (data) {
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
    });

    function roleSave() {
        if ($("#roleAddForm").valid()) {
            var checkNodes = $('#menuTree').treeview('getChecked');
            var _menuIds = [];
            $.each(checkNodes, function (i, obj) {
                _menuIds[i] = obj.id;
            });
            var role = {};
            role.name = $("#roleName").val();
            role.remark = $("#roleRemark").val();
            role.menuIds = _menuIds.join();
            $.post('/system/role/add', role, function (data) {
                if (data.code == 200) {
                    $("#lgModal").modal('hide');
                    alertMsg("添加成功", "success");
                    reloadTable(list_ajax);
                } else {
                    alertMsg("添加失败:" + data.msg, "success");
                }
            });
        }
        $("#roleAddForm").validate({
            errorClass: 'text-danger',
            errorElement: "span"
        });
    }
</script>