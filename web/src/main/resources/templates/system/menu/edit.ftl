<form id="menuEditForm">
    <input type="hidden" name="id" value="${menu.id}">
    <div class="modal-body">
        <div class="form-group">
            <label class="" id="nameLabel">菜单名称</label>
            <input type="text" class="form-control" name="name" id="name" value="${menu.name!}" placeholder="输入菜单名称...">
        </div>
        <div class="form-group">
            <label class="" id="urlLabel">请求地址</label>
            <input type="text" class="form-control" name="url" id="url" value="${menu.url!}" placeholder="输入请求地址...">
        </div>
        <div class="form-group">
            <label id="nickNameLabel">排序</label>
            <input type="text" class="form-control" name="sort" id="sort" value="${menu.sort!}" placeholder="输入排序...">
        </div>
        <div class="form-group">
            <label id="nickNameLabel">图标</label>
            <input type="hidden" id="icon" value="${menu.icon!}"></input>
            <button id="target" name="icon" class="btn btn-default" data-icon="glyphicon-chevron-down"
                    role="iconpicker">
            </button>
        </div>
        <div class="form-group">
            <label>是否是菜单</label>
            <select name="isMenu" class="form-control select2" style="width: 100%;">
                <option <#if menu.isMenu==1> selected="selected"</#if> value="1">是</option>
                <option <#if menu.isMenu==0> selected="selected"</#if> value="0">否</option>
            </select>
        </div>
    </div>
    <div class="modal-footer">
        <div class="pull-right">
            <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭
            </button>
            <button type="button" class="btn btn-primary btn-sm" onclick="menuSave();"><i class="fa fa-save"></i>更新
            </button>
        </div>
    </div>
</form>

<script type="text/javascript">
    $(function () {
        var icon = $("#icon").val();
        $('#target').iconpicker({
            align: 'center', // Only in div tag
            arrowClass: 'btn-danger',
            arrowPrevIconClass: 'glyphicon glyphicon-chevron-left',
            arrowNextIconClass: 'glyphicon glyphicon-chevron-right',
            cols: 10,
            footer: true,
            header: true,
            icon: icon,
            iconset: 'fontawesome',
            labelHeader: '{0} of {1} pages',
            labelFooter: '{0} - {1} of {2} icons',
            placement: 'bottom', // Only in button tag
            rows: 6,
            search: true,
            searchText: 'Search',
            selectedClass: 'btn-success',
            unselectedClass: ''
        });
    });

    function menuSave() {
        $.post('/system/menu/save', $("#menuEditForm").serialize(), function (data) {
            if (data.code === 200) {
                $("#lgModal").modal('hide');
                alertMsg("更新成功", "success");
                reloadTable(list_ajax);
            } else {
                alertMsg("更新失败:" + data.msg, "success");
            }
        });
    }
</script>