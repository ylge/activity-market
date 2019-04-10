<div class="row">
    <div class="col-md-12">
        <form id="departmentEditForm">
            <div class="modal-body">
                <input type="hidden" name="departmentId" value="${department.departmentId}">
                <div class="form-group">
                    <label>名称</label>
                    <input type="text" class="form-control" name="name" value="${department.name}"
                           placeholder="输入部门名称..." required>
                </div>
                <div class="form-group">
                    <label>公司名称</label>
                    <select name="companyId" class="selectpicker form-control" data-live-search="true" required>
                        <#if companys??>
                            <#list companys as company>
                                <option value="${company.companyId}"
                                        <#if company.companyId ==department.companyId>selected</#if> >${company.name}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
                <div class="form-group">
                    <label>描述</label>
                    <input type="text" class="form-control" name="comment" id="comment" value="${department.comment}"
                           placeholder="输入备注..." required>
                </div>
            </div>
            <div class="modal-footer">
                <div class="pull-right">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                            class="fa fa-close"></i>关闭
                    </button>
                    <button type="button" class="btn btn-primary btn-sm" onclick="departmentSave();"><i
                            class="fa fa-save"></i>保存
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        debugger;
        $(".selectpicker").selectpicker({
            noneSelectedText: '请选择'
        });
    });

    function departmentSave() {
        if ($("#departmentEditForm").valid()) {
            $.ajax({
                url: 'system/department/update',
                type: 'post',
                dataType: 'json',
                data: $("#departmentEditForm").serialize(),
                success: function (data) {
                    if (data.code === 200) {
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

    $("#departmentEditForm").validate({
        errorClass: 'text-danger',
        errorElement: "span"
    });
</script>