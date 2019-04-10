<form id="departmentAddForm" class="form-horizontal">
    <div class="modal-body">
        <div class="form-group">
            <label>部门名称</label>
            <input type="text" class="form-control" name="name" placeholder="输入部门名称..." required>
        </div>
        <div class="form-group">
            <label>所属公司</label>
            <select name="companyId" class="selectpicker form-control" data-live-search="true">
                        <#if companys??>
                            <option value="">请选择</option>
                            <#list companys as company>
                                <option value="${company.companyId}">${company.shortName!}</option>
                            </#list>
                        </#if>
            </select>
        </div>
        <div class="form-group">
            <label>描述</label>
            <input type="text" class="form-control" name="comment" id="comment" placeholder="输入备注...">
        </div>
    </div>
    <div class="modal-footer">
        <div class="pull-right">
            <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                    class="fa fa-close"></i>关闭
            </button>
            <button type="button" class="btn btn-primary btn-sm" onclick="securitySave();"><i
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

    function securitySave() {
        if ($("#departmentAddForm").valid()) {
            $.ajax({
                url: 'system/department/add',
                type: 'post',
                dataType: 'json',
                data: $("#departmentAddForm").serialize(),
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

    $("#departmentAddForm").validate({
        errorClass: 'text-danger',
        errorElement: "span"
    });
</script>