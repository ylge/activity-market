<div class="row">
    <div class="col-md-12">
        <form id="companyEditForm">
            <div class="modal-body">
                <input type="hidden" name="companyId" value="${company.companyId}">
                <div class="form-group">
                    <label>编码</label>
                    <input type="text" class="form-control" name="code" value="${company.code}" placeholder="输入邮编..." required>
                </div>
                <div class="form-group">
                    <label>公司名称</label>
                    <input type="text" class="form-control" name="name"  value="${company.name}" placeholder="输入公司名称..." required>
                </div>
                <div class="form-group">
                    <label>简称</label>
                    <input type="text" class="form-control" name="shortName" value="${company.shortName}" placeholder="输入简称..." required>
                </div>
                <div class="form-group">
                    <label>地址</label>
                    <input type="text" class="form-control" name="address" value="${company.address}" placeholder="输入地址..." required>
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
    </div>
</div>
<script type="text/javascript">
    function companySave() {
        if ($("#companyEditForm").valid()) {
            $.ajax({
                url: 'system/company/update',
                type: 'post',
                dataType: 'json',
                data: $("#companyEditForm").serialize(),
                success: function (data) {
                    if (data.code===200) {
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

    $("#companyEditForm").validate({
        errorClass: 'text-danger',
        errorElement: "span"
    });
</script>