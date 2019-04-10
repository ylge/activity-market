<form id="userUpdateForm" class="form-horizontal">
    <input type="hidden" name="userId" value="${userId}">
    <div class="modal-body">
        <div class="form-group">
            <label id="userNoLabel">原密码</label>
            <input type="password" class="form-control" name="oldPassword" placeholder="原密码..."
                   required>
        </div>
        <div class="form-group">
            <label id="passwordLabel">新密码</label>
            <input type="password" class="form-control" name="newPassword" placeholder="新密码..."
                   required>
        </div>
    </div>
    <div class="modal-footer">
        <div class="pull-right">
            <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                    class="fa fa-close"></i>关闭
            </button>
            <button type="button" class="btn btn-primary btn-sm" onclick="pwdSave();"><i
                    class="fa fa-save"></i>保存
            </button>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(function () {
        $(".selectpicker").selectpicker({
            noneSelectedText: '请选择'
        });
    });
    var userParam = $("#userUpdateForm");

    function pwdSave() {
        if (!userParam.valid()) {
            return;
        }
        $.post('system/user/updatePassword', userParam.serialize(), function (data) {
            if (data.code === 200) {
                $("#lgModal").modal('hide');
                alertMsg("修改成功", "success");
                window.location.href = "logout";
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