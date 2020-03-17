<#macro menu>
    <aside class="main-sidebar">
        <section class="sidebar">
            <ul class="sidebar-menu" data-widget="tree">
                <li class="header">主导航</li>
                <#if menuList??>
                    <#list menuList as menu>
                        <li class="treeview active">
                            <a href="#">
                                <i class="fa ${menu.icon}"></i>
                                <span>${menu.name}</span>
                                <span class="pull-right-container">
                                <i class="fa fa-angle-left pull-right"></i>
                            </span>
                            </a>
                            <#if menu.child??>
                                <ul class="treeview-menu active">
                                    <#list menu.child as childMenu>
                                        <li class="treeview">
                                            <#if childMenu.child??>
                                                <a href="#">
                                                    <i class="fa ${childMenu.icon}"></i>
                                                    <span>${childMenu.name}</span>
                                                    <span class="pull-right-container">
                                                    <i class="fa fa-angle-left pull-right"></i>
                                                </span>
                                                </a>
                                                <ul class="treeview-menu">
                                                    <#list childMenu.child as child>
                                                        <li>
                                                            <a target="navTab" href="${child.url}">
                                                                <i class="fa fa-circle-o"></i>
                                                                <span>${child.name}</span>
                                                            </a>
                                                        </li>
                                                    </#list>
                                                </ul>
                                            <#else >
                                                <a target="navTab" href="${childMenu.url}">
                                                    <i class="fa fa-circle-o"></i>
                                                    <span>${childMenu.name}</span>
                                                </a>
                                            </#if>
                                        </li>
                                    </#list>
                                </ul>
                            </#if>
                        </li>
                    </#list>
                </#if>
            </ul>
        </section>
    </aside>
</#macro>

<#macro header>
    <header class="main-header">
        <a href="#" class="logo">
            <#--<span class="logo-mini"><b>易乎社区CMS</b></span>-->
            <span class="logo-lg"><b>营销策划</b></span>
        </a>
        <nav class="navbar navbar-static-top">
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">切换导航</span>
            </a>

            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li class="dropdown user user-menu">
                        <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
                            <span class="hidden-xs">${user.name!}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="user-header">
                                <img src="${user.avatar!'${request.contextPath}/adminlte/dist/img/ehulogo.png'}"
                                     class="img-circle">
                                <p>
                                    ${user.username!}
                                    <small>${user.createTime?string('yyyy-MM-dd HH:mm:ss')}加入</small>
                                </p>
                            </li>
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="system/user/updatePassword/${user.id}" target="modal"
                                       class="btn btn-default btn-flat">密码修改</a>
                                </div>
                                <div class="pull-right">
                                    <a href="/logout" class="btn btn-default btn-flat">安全退出</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#" data-toggle="control-sidebar">
                            <i class="fa fa-gears"></i>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
</#macro>

<#macro footer>
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 3.0.0
        </div>
        鹤壁忆时光文化传播有限公司
        <a href="http://beian.miit.gov.cn/" style="color:#f72b07" target="_blank">豫ICP备19030440号</a>
    </footer>
</#macro>

<#macro style>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="shortcut icon" type="image/x-icon" href="${request.contextPath}/adminlte/dist/img/ehulogo.png">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" type=text/css href="${request.contextPath}/adminlte/plugins/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" type=text/css
          href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" type=text/css href="${request.contextPath}/adminlte/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" type=text/css href="${request.contextPath}/adminlte/dist/css/skins/_all-skins.min.css">
    <!-- 以上为公共css -->

    <!-- iCheck for checkboxes and radio inputs -->
    <link rel="stylesheet" type=text/css href="${request.contextPath}/adminlte/plugins/iCheck/all.css">
    <!-- Bootstrap Color Picker -->
    <link rel="stylesheet" type=text/css
          href="${request.contextPath}/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <!-- Bootstrap time Picker -->
<#--<link rel="stylesheet" href="${request.contextPath}/adminlte/plugins/timepicker/bootstrap-timepicker.min.css">-->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/css/bootstrap-timepicker.min.css"
          type=text/css rel="stylesheet">
    <link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-daterangepicker/2.1.25/daterangepicker.css"
          type=text/css rel="stylesheet">
    <!-- Bootstrap select -->
    <link rel="stylesheet" type=text/css
          href="${request.contextPath}/adminlte/plugins/bootstrap-select/bootstrap-select.min.css">
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" type=text/css
          href="${request.contextPath}/adminlte/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" type=text/css href="${request.contextPath}/adminlte/plugins/iCheck/flat/blue.css">
    <!-- treeview-->
    <link rel="stylesheet" type=text/css
          href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min.css">

    <link rel="stylesheet" type=text/css
          href="${request.contextPath}/adminlte/plugins/jquery-treegrid-master/css/jquery.treegrid.css">
    <!-- bootstrap slider -->
    <link rel="stylesheet" type=text/css href="${request.contextPath}/adminlte/plugins/bootstrap-slider/slider.css">
    <!-- bootstrap-table -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.1/bootstrap-table.min.css">
<#--bootstrap-iconpicker-->
    <link rel="stylesheet" type=text/css
          href="${request.contextPath}/adminlte/plugins/bootstrap-iconpicker/bootstrap-iconpicker.min.css">
<#--ladda-->
    <link type=text/css href="https://cdnjs.cloudflare.com/ajax/libs/ladda-bootstrap/0.9.4/ladda.min.css"
          rel="stylesheet">
    <link type=text/css href="https://cdnjs.cloudflare.com/ajax/libs/ladda-bootstrap/0.9.4/ladda-themeless.min.css"
          rel="stylesheet">
<#--datepicker-->
    <link type=text/css
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.min.css"
          rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</#macro>

<#macro jsFile>
    <!-- jQuery 2.2.3 -->
<#--<script src="${request.contextPath}/adminlte/plugins/jQuery/jquery-2.2.3.min.js"></script>-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
    <!-- jQuery UI 1.11.bootstrap-select -->
    <script src="${request.contextPath}/adminlte/plugins/jQueryUI/jquery-ui.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="${request.contextPath}/adminlte/plugins/bootstrap/js/bootstrap.min.js"></script>
<#--<script src="${request.contextPath}/adminlte/plugins/fastclick/fastclick.js"></script>-->
    <!-- Slimscroll -->
    <script src="${request.contextPath}/adminlte/plugins/slimScroll/jquery.slimscroll.min.js"></script>
    <!-- AdminLTE App -->
    <script src="${request.contextPath}/adminlte/dist/js/common.js"></script>
    <script src="${request.contextPath}/adminlte/dist/js/AjaxFileUpload.js"></script>
    <script src="${request.contextPath}/adminlte/dist/js/app.js"></script>
    <!-- 以上JS为页面必须 -->

    <!-- Bootstrap WYSIHTML5 -->
    <script src="${request.contextPath}/adminlte/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
    <!-- iCheck -->
    <script src="${request.contextPath}/adminlte/plugins/iCheck/icheck.min.js"></script>
    <!-- InputMask -->
    <script src="${request.contextPath}/adminlte/plugins/input-mask/jquery.inputmask.js"></script>
    <script src="${request.contextPath}/adminlte/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
    <script src="${request.contextPath}/adminlte/plugins/input-mask/jquery.inputmask.extensions.js"></script>
    <!-- bootstrap daterangepicker -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-daterangepicker/2.1.25/moment.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-daterangepicker/2.1.25/daterangepicker.js"></script>
    <!-- bootstrap color picker -->
    <script src="${request.contextPath}/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
    <!-- bootstrap time picker -->
<#--<script src="${request.contextPath}/adminlte/plugins/timepicker/bootstrap-timepicker.min.js"></script>-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/js/bootstrap-timepicker.min.js"></script>
    <!-- bootstrap select -->
    <script src="${request.contextPath}/adminlte/plugins/bootstrap-select/bootstrap-select.min.js"></script>
    <script src="${request.contextPath}/adminlte/plugins/bootstrap-select/i18n/defaults-zh_CN.js"></script>
    <!-- Bootstrap slider -->
    <script src="${request.contextPath}/adminlte/plugins/bootstrap-slider/bootstrap-slider.js"></script>
    <!-- bootstrap-table -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.1/bootstrap-table.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.1/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.0/extensions/treegrid/bootstrap-table-treegrid.js"></script>
<#--table treegrid-->
    <script src="${request.contextPath}/adminlte/plugins/jquery-treegrid-master/js/jquery.treegrid.min.js"></script>
    <script src="${request.contextPath}/adminlte/plugins/jquery-treegrid-master/js/jquery.treegrid.bootstrap3.js"></script>
<#--bootstrap-iconpicker-->
    <script type="text/javascript"
            src="${request.contextPath}/adminlte/plugins/bootstrap-iconpicker/bootstrap-iconpicker-iconset-all.min.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/adminlte/plugins/bootstrap-iconpicker/bootstrap-iconpicker.min.js"></script>
    <!-- treeview 角色管理用-->
    <script type="text/javascript" src="${request.contextPath}/adminlte/plugins/tree/treeview.js"></script>z
    <script type="text/javascript"
            src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min.js"></script>
<#--datepicker-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    <!-- validate-->
    <script type="text/javascript" src="${request.contextPath}/adminlte/plugins/validate/jquery.validate.js"></script>
<#--防止按钮重复组建-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ladda-bootstrap/0.9.4/ladda.jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ladda-bootstrap/0.9.4/spin.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ladda-bootstrap/0.9.4/ladda.min.js"></script>
<#--表可编辑-->
    <script type="text/javascript"
            src="${request.contextPath}/adminlte/plugins/table-edit/bootstrap-table-editable.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/adminlte/plugins/table-edit/bootstrap-editable.min.js"></script>

<#-- 文本搜索-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-3-typeahead/4.0.2/bootstrap3-typeahead.min.js"></script>
</#macro>

<#macro setting>
    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Create the tabs -->
        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <!-- Home tab content -->
            <div class="tab-pane" id="control-sidebar-home-tab">
            </div>
        </div>
    </aside>
    <div class="modal fade" id="smModal">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">提示</h4>
                </div>
                <div class="modal-body">
                    <p>确认删除？</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary">确认</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="lgModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <#--<div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>-->
                <div class="modal-body">
                    <p>确认删除？</p>
                </div>
            </div>
        </div>
    </div>
    <div id="loading" class="loading-panel">
        <div class="box">
            <i class="fa fa-refresh fa-spin"></i> <span class="tip"> 正在加载 · · · </span>
        </div>
    </div>
</#macro>