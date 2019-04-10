<form id="userUpdateForm" class="form-horizontal">
    <div class="modal-body">
        <input type="hidden" name="userId" value="${sysUser.userId}">
        <div class="form-group">
            <label id="userNoLabel">账号</label>
            <input type="text" class="form-control" name="username" value="${sysUser.username}" placeholder="输入账号..."
                   required>
        </div>
        <div class="form-group">
            <label id="passwordLabel">密码</label>
            <input type="password" class="form-control" name="password" placeholder="******"
            >
        </div>
        <div class="form-group">
            <label id="nickNameLabel">昵称</label>
            <input type="text" class="form-control" name="name" value="${sysUser.name}" placeholder="输入昵称..." required>
        </div>
        <div class="form-group">
            <label id="phoneLabel">手机号码</label>
            <input type="text" class="form-control" name="phone" value="${sysUser.phone}" placeholder="输入手机号码..."
                   required>
        </div>
    </div>
    <div class="modal-footer">
        <div class="pull-right">
            <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                    class="fa fa-close"></i>关闭
            </button>
            <button type="button" class="btn btn-primary btn-sm" onclick="userSave();"><i
                    class="fa fa-save"></i>保存
            </button>
        </div>
    </div>
</form>
<script type="text/javascript">
    var userParam = $("#userUpdateForm");

    function userSave() {
        if (!userParam.valid()) {
            return;
        }
        $.post('system/user/update', userParam.serialize(), function (data) {
            if (data.code === 200) {
                $("#lgModal").modal('hide');
                alertMsg("修改成功", "success");
                reloadTable(list_ajax);
            } else {
                alertMsg("修改失败:" + data.msg, "danger");
            }
        });
        userParam.validate({
            errorClass: 'text-danger',
            errorElement: "span"
        });
    }
</script>