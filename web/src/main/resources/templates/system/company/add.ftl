<form id="companyAddForm" class="form-horizontal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">公司新增</h4>
    </div>
    <div class="modal-body">
        <div class="form-group">
            <label>编码</label>
            <input type="text" class="form-control" name="code" id="code" placeholder="输入公司编码..." required>
        </div>
        <div class="form-group">
            <label>公司名称</label>
            <input type="text" class="form-control" name="name" placeholder="输入公司名称..." required>
        </div>
        <div class="form-group">
            <label>简称</label>
            <input type="text" class="form-control" name="shortName" placeholder="输入简称..." required>
        </div>
        <div class="form-group">
            <label>地址</label>
            <input type="text" class="form-control" name="address" placeholder="输入地址..." required>
        </div>
    </div>
    <div class="modal-footer">
        <div class="pull-right">
            <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                    class="fa fa-close"></i>关闭
            </button>
            <button type="button" class="btn btn-primary btn-sm" onclick="companySave();"><i
                    class="fa fa-save"></i>保存
            </button>
        </div>
    </div>
</form>
<script type="text/javascript">
    function companySave() {
        if ($("#companyAddForm").valid()) {
            $.ajax({
                url: 'system/company/add',
                type: 'post',
                dataType: 'json',
                data: $("#companyAddForm").serialize(),
                success: function (data) {
                    if (data.code == 200) {
                        $("#lgModal").modal('hide');
                        alertMsg("添加成功", "success");
                        reloadTable(list_ajax);
                    } else {
                        alertMsg("添加失败:" + data.msg, "success");
                    }
                }
            });
        }
    }

    $("#companyAddForm").validate({
        errorClass: 'text-danger',
        errorElement: "span"
    });
</script>