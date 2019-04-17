<form id="activityAddForm" class="form-horizontal" enctype="multipart/form-data">
    <div class="modal-body">
        <div class="form-group">
            <label><span style="color: red">*</span>活动商品名称</label>
            <input type="text" class="form-control" name="goodsName" maxlength="10"
                   placeholder="最多10个字，不要包含英文标点！" required>
        </div>
        <div class="form-group">
            <label><span style="color: red">*</span>活动商品图片</label><br>
            <img src="/adminlte/dist/img/timg.jpg" style="width: 80px;height: 60px" onclick="imageClick('goodsImg')"
                 class="goodsImg">
            <input name="goodsFile" id="goodsImg" type="file" style="display: none" required>
        </div>
        <div class="form-group">
            <label>价格</label><span style="color: red">最多一位小数</span>
            <input type="text" class="form-control" placeholder="最多一位小数" name="goodsPrice" onchange="limitInput(this)">
        </div>
        <div class="form-group">
            <label>限购数量</label>
            <input type="number" class="form-control" name="purchaseLimit" min="1" step=1 value="1"
                   onkeyup="this.value=this.value.replace(/\D/g,'')"
                   placeholder="必须正整数"
                   onafterpaste="this.value=this.value.replace(/\D/g,'')"
                   required>
        </div>
        <div class="form-group">
            <label><span style="color: red">*</span>活动背景图</label><br>
            <img src="/adminlte/dist/img/timg.jpg" style="width: 80px;height: 60px"
                 onclick="imageClick('backgroundImageImg')" class="backgroundImageImg">
            <input id="backgroundImageImg" name="backgroundImageFile" type="file" style="display: none" required>
        </div>
        <div class="form-group">
            <label><span style="color: red">*</span>活动详情图</label><br>
            <img src="/adminlte/dist/img/timg.jpg" style="width: 80px;height: 60px"
                 onclick="imageClick('goodsDetailImg')" class="goodsDetailImg">
            <input id="goodsDetailImg" name="goodsDetailFile" type="file" style="display: none" required>
        </div>
        <div class="form-group">
            <label>页面音乐</label>
            <input type="file" class="form-control" name="activityMusicFile">
        </div>
        <div class="form-group">
            <label><span style="color: red">*</span>商家二维码</label><br>
            <img src="/adminlte/dist/img/timg.jpg" class="storeCodeImage" onclick="imageClick('storeCodeImage')"
                 style="width: 80px;height: 60px">
            <input type="file" name="storeCodeFile" id="storeCodeImage" style="display: none">
        </div>
        <div class="form-group">
            <label style="display: block; "><span style="color: red">*</span>商家地址</label>
            <input type="text" class="form-control" name="storeAddress"
                   placeholder="详细地址" required>
        </div>
        <div class="form-group">
            <label><span style="color: red">*</span>商家电话</label>
            <input type="text" class="form-control" name="storePhone" placeholder="只能输入数字"
                   onblur="isPoneAvailable(this)" required>
        </div>
        <div class="form-group">
            <label><span style="color: red">*</span>联系人</label>
            <input type="text" class="form-control" name="linkName" placeholder="联系人" required>
        </div>
        <div class="form-group">
            <label><span style="color: red">*</span>商家名称</label>
            <input type="text" class="form-control" name="storeName" placeholder="店铺名称" required>
        </div>
        <div class="form-group">
            <label>活动时间</label><br>
            <button type="button" class="btn btn-default" id="daterange_btn">
                <i class="fa fa-calendar"></i>
                <span>时间区间</span>
                <i class="fa fa-caret-up"></i>
            </button>
            <input type="hidden" name="beginTime">
            <input type="hidden" name="endTime">
        </div>
        <div class="form-group">
            <label>返现红包金额</label>
            <input type="text" class="form-control" name="rewardAmount">
        </div>
    </div>
    <div class="modal-footer">
        <div class="pull-right">
            <button type="button" class="ladda-button" data-size="xs" data-dismiss="modal">
                <span class="ladda-label"><i class="fa fa-close"></i>关闭</span></button>
            </button>
            <button id="save" type="button" class="ladda-button" data-size="xs" data-color="mint" data-style="zoom-in">
                <span class="ladda-label"><i class="fa fa-save"></i>保存</span></button>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(function () {
        $(".selectpicker").selectpicker();
        daterangInit();
    });

    function daterangInit() {
        //初始化显示当前时间
        $('#daterange_btn span').html(moment().format(locale.format) + ' - ' + moment().format(locale.format));
        $("input[name='beginTime']").val(moment().format(locale.format));
        $("input[name='endTime']").val(moment().format(locale.format));
        //日期控件初始化
        $('#daterange_btn').daterangepicker(
                {
                    'locale': locale,
                    autoApply: true,
                    opens: "right",
                    drops: "up",
                    startDate: moment().subtract(7, 'days'),
                    endDate: moment()
                },
                function (start, end) {
                    $('#daterange_btn span').html(start.format(locale.format) + ' - ' + end.format(locale.format));
                    $("input[name='beginTime']").val(start.format(locale.format));
                    $("input[name='endTime']").val(end.format(locale.format));

                }
        )
    }


    $("#save").click(function () {
        if ($("#activityAddForm").valid()) {
            var l = Ladda.create(this);
            l.start();
            var formData = new FormData($("#activityAddForm")[0]);
            $.ajax({
                url: 'activity/goods/add',
                type: 'post',
                dataType: 'json',
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.code === 200) {
                        $("#lgModal").modal('hide');
                        alertMsg("添加成功", "success");
                        reloadTable(list_ajax);
                    } else {
                        alertMsg("添加失败:" + data.msg, "danger");
                    }
                    l.stop();
                }
            });
        }
    });


    $("#activityAddForm").validate({
        errorClass: 'text-danger',
        errorElement: "span"
    });

    function imageClick(data) {
        $("#" + data).click(); //隐藏了input:file样式后，点击头像就可以本地上传
        $("#" + data).change(function () {
            var objUrl = getObjectURL(this.files[0]); //获取图片的路径，该路径不是图片在本地的路径
            if (objUrl) {
                $("." + data).attr("src", objUrl); //将图片路径存入src中，显示出图片
            }
        });
    }
</script>