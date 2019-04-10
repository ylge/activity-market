var tableModel = (function () {
    return {
        getHeight: function () {
            // return $(window).height() - $('.content-header').outerHeight(true);
            return $(window).height() * 0.7;
        },
        getState: function (value, row, index) {
            if (value === 1) {
                return '<span class="label label-success">正常</span>';
            } else {
                return '<span class="label label-danger">禁用</span>';
            }
            // return value == 1 ? "启用" : "禁用";
        },
        getOrderState: function (value, row, index) {
            return orderStatus(value);
        },
        goodsStatus: function (value, row, index) {
            if (value == 1) {
                return "售卖中";
            } else {
                return "已下架";
            }
        },
        getPayType: function (value, row, index) {
            if (value == 1) {
                return "支付宝";
            } else if (value == 2) {
                return "微信";
            } else if (value == 3) {
                return "银行卡";
            } else {
                return "未支付";
            }
        }
    }
})();

function orderStatus(value) {
    var status;
    if (value === 0) {
        status = "已下单";
    } else if (value === 1) {
        status = "已付款";
    } else if (value === 2) {
        status = "已截单";
    } else if (value === 3) {
        status = "已取货";
    } else if (value === 4) {
        status = "已取消";
    } else if (value === 5) {
        status = "已退款";
    } else if (value === 6) {
        status = "退款中";
    } else if (value === 7) {
        status = "已退货";
    } else {
        status = "未知";
    }
    return status;
}

function withdrawStatus(value) {
    var status;
    if (value === 0) {
        status = "待审核";
    } else if (value === 1) {
        status = "打款中";
    } else if (value === 2) {
        status = "已驳回";
    } else if (value === 3) {
        status = "已到账";
    } else if (value === 4) {
        status = "打款失败";
    }
    return status;
}

//每次隐藏modal 清理内容
$(".modal").on("hide.bs.modal", function () {
    $(this).removeData("bs.modal");
    $(".modal-body").children().remove();
});


//定义locale汉化插件
var locale = {
    "format": 'YYYY-MM-DD',
    "separator": " -222 ",
    "applyLabel": "确定",
    "cancelLabel": "取消",
    "fromLabel": "起始时间",
    "toLabel": "结束时间'",
    "customRangeLabel": "自定义",
    "weekLabel": "W",
    "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
    "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
    "firstDay": 1
};

function isEmpty(variable1) {
    if (variable1 !== null && variable1 !== undefined && variable1 !== '') {
        return true;
    }
}

//一位小数的正数
function limitInput(o) {
    var value = Number(o.value);
    var reg = new RegExp(/^\d{1,100}(\.\d)?$/);
    if (reg.test(value)) {
        return true;
    } else {
        alert("最多一位小数，不能为负数！");
        $(o).val('');
        $(o).focus();
        return false;
    }
}

//金额输入框
function numberCheck(obj) {
    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
    obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是字符          
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个.清除多余的       
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}

//手机号校验
function isPoneAvailable(poneInput) {
    var myreg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
    if (!myreg.test(poneInput.value)) {
        $(poneInput).val('');
        $(poneInput).focus();
        alertMsg("请输入正确的手机号", "danger");
        return false;
    } else {
        return true;
    }
}

//表单刷新
var list_ajax;
var date_ajax;
//当你需要多条件查询，你可以调用此方法，动态修改参数传给服务器
//查询添加的时候需要重写该方法，每个页面查询参数不一样，请参考user/list 葛永亮 2017-9-14
window.reloadTable = function (oTable, opt) {
    oTable.bootstrapTable('refresh', opt);
};

$(document).on('show.bs.modal', '.modal', function () {
    var zIndex = 1040 + (10 * $('.modal:visible').length);
    $(this).css('z-index', zIndex);
    setTimeout(function () {
        $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
    }, 0);
});

//建立一個可存取到該file的url
function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

/*
function onClickCheckbox(clickName, target) {
    var status = false;
    if (document.getElementById(clickName).checked) {
        status = true;
    }
    var list = document.getElementsByName(target);
    for (var i = 0; i < list.length; i++) {
        list[i].checked = status;
    }
}
*/

/*function iFrameHeight() {
    var ifm = document.getElementById("content");
    var subWeb = document.frames ? document.frames["content"].document : ifm.contentDocument;
    if (ifm != null && subWeb != null) {
        ifm.height = subWeb.body.scrollHeight;
    }
}*/

(function () {
    //全局ajax处理
    $.ajaxSetup({
        complete: function (jqXHR, textStatus) {
            if (textStatus == "parsererror") {
                if (confirm("提示信息:登陆超时！请重新登陆！")) {
                    window.location.href = 'login';
                }
            }
        },
        data: {},
        error: function (jqXHR, textStatus, errorThrown) {
        }
    });

    if ($.browser && $.browser.msie) {
        //ie 都不缓存
        $.ajaxSetup({
            cache: false
        });
    }

//不支持placeholder浏览器下对placeholder进行处理
    if (document.createElement('input').placeholder !== '') {
        $('[placeholder]').focus(function () {
            var input = $(this);
            if (input.val() == input.attr('placeholder')) {
                input.val('');
                input.removeClass('placeholder');
            }
        }).blur(function () {
            var input = $(this);
            if (input.val() == '' || input.val() == input.attr('placeholder')) {
                input.addClass('placeholder');
                input.val(input.attr('placeholder'));
            }
        }).blur().parents('form').submit(function () {
            $(this).find('[placeholder]').each(function () {
                var input = $(this);
                if (input.val() == input.attr('placeholder')) {
                    input.val('');
                }
            });
        });
    }

    var ajaxForm_list = $('form.js-ajax-form');
    if (ajaxForm_list.length) {
        var $btn;
        $('button.js-ajax-submit').on('click', function (e) {
            var btn = $(this);
            var form = btn.parents('form.js-ajax-form');
            $btn = btn;

            if (btn.data("loading")) {
                return;
            }

            //ie处理placeholder提交问题
            if ($.browser && $.browser.msie) {
                form.find('[placeholder]').each(function () {
                    var input = $(this);
                    if (input.val() == input.attr('placeholder')) {
                        input.val('');
                    }
                });
            }
        });

        ajaxForm_list.each(function () {
            $(this).validate({
                debug: true,
                //是否在获取焦点时验证
                //onfocusout : false,
                //当鼠标掉级时验证
                //onclick : false,
                //给未通过验证的元素加效果,闪烁等
                //highlight : false,
                onkeyup: function (element, event) {
                    return;
                },
                showErrors: function (errorMap, errorArr) {
                    if (parseInt(errorArr.length) > 0) {
                        $(errorArr[0].element).focus();
                        layer.msg(errorArr[0].message, {icon: 2});
                    }
                },
                submitHandler: function (form) {
                    var $form = $(form);
                    $form.ajaxSubmit({
                        url: $btn.data('action') ? $btn.data('action') : $form.attr('action'),
                        dataType: 'json',
                        beforeSubmit: function (arr, $form, options) {
                            $btn.data("loading", true);
                            var text = $btn.text();
                            $btn.text(text + '中...').prop('disabled', true).addClass('disabled');
                        },
                        success: function (data, statusText, xhr, $form) {
                            var text = $btn.text();
                            $btn.removeClass('disabled').prop('disabled', false).text(text.replace('中...', '')).parent().find('span').remove();
                            if (data.state === 'success') {
                                layer.msg(data.msg, {icon: 1}, function () {
                                    if (data.referer) {
                                        operaModel.redirect(data.referer);//返回带跳转地址
                                    } else {
                                        if (data.state === 'success') {
                                            operaModel.reloadPage(window);//刷新当前页
                                        }
                                    }
                                });
                            } else if (data.state === 'error') {
                                layer.msg(data.msg, {icon: 2});
                            }
                        },
                        error: function (xhr, e, statusText) {
                            console.log(statusText);
                            operaModel.reloadPage(window);//刷新当前页
                        },
                        complete: function () {
                            $btn.data("loading", false);
                        }
                    });
                }
            });
        });
    }
})
();