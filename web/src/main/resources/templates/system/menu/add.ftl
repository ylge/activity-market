<form id="menuAddForm" class="form-horizontal" method="post" action="/system/menu/save">
    <div class="modal-body">
        <input type="hidden" name="parentId" value="${menu.id}">
        <input type="hidden" name="level" value="${menu.level+1}">
        <div class="form-group">
            <label class="" id="nameLabel">菜单名称</label>
            <input type="text" class="form-control" name="name" placeholder="输入菜单名称..." required>
        </div>
        <div class="form-group">
            <label class="" id="urlLabel">请求地址</label>
            <input type="text" class="form-control" name="url" placeholder="输入请求地址..." required>
        </div>
        <div class="form-group">
            <label id="sortNameLabel">排序</label>
            <input type="number" class="form-control" name="sort" placeholder="输入排序..." required>
        </div>
        <div class="form-group">
            <label id="nickNameLabel">图标</label>
            <button id="target" name="icon" class="btn btn-default" data-icon="glyphicon-chevron-down"
                    role="iconpicker">
            </button>
        </div>
        <div class="form-group">
            <label>是否是菜单</label>
            <select name="isMenu" class="form-control" style="width: 100%;">
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
        </div>
    </div>
    <div class="modal-footer">
        <div class="pull-right">
            <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭
            </button>
            <button type="button" class="btn btn-primary btn-sm" onclick="menuAdd();"><i class="fa fa-save"></i>保存
            </button>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(function () {
        $('#target').iconpicker({
            align: 'center', // Only in div tag
            arrowClass: 'btn-danger',
            arrowPrevIconClass: 'glyphicon glyphicon-chevron-left',
            arrowNextIconClass: 'glyphicon glyphicon-chevron-right',
            cols: 10,
            footer: true,
            header: true,
            icon: '',
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
    var post_flag = false;

    function menuAdd() {
        if ($("#menuAddForm").valid()) {
            if (post_flag) return; //如果正在提交则直接返回，停止执行
            post_flag = true;//标记当前状态为正在提交状态
            $.post('/system/menu/save', $("#menuAddForm").serialize(), function (data) {
                if (data.code === 200) {
                    alertMsg("添加成功", "success");
                    $("#lgModal").modal('hide');
                    reloadTable(list_ajax);
                } else {
                    alertMsg("添加失败:" + data.msg, "success");
                    post_flag = false;
                }
            });
        } else {
            post_flag = false;
        }
    }

    $("#menuAddForm").validate({
        errorClass: 'text-danger',
        errorElement: "span"
    });

</script>