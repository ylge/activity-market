<#--图片展示功能页-->
<div class="modal fade" id="imageModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title text-primary">
                    <i class="fa fa-pencil"></i>
                    图片展示
                </h4>
            </div>
            <img src="" alt="" style="width: 100%;" id="imageShow">
            <div class="modal-footer">
                <button class="btn btn-close" aria-hidden="true" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<script>
    function imageView(url) {
        $("#imageShow").attr("src", url);
        $("#imageModal").modal('show');
    }
</script>