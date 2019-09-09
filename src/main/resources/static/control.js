/**
 * Created by nannan.li on 2019/9/5.
 */
$(function () {
    var groupId;
    var meshId;
    var panelId;
    var panelState;
    var match = /^[0-9A-Za-z\u4e00-\u9fa5]{2,8}$/;
    var text = "请输入2-8 位中文、字母、数字";
    //根据输入显示提示
    $(".am-form-field").on('input propertychange change', function () {
        var context = $(this).val();
        if (!(match).test(context)) {
            $(this).parent().prev().text(text);
        } else {
            $(this).parent().prev().text('');
        }
    });
    //组操作前置动作
    $(".group-operation").click(function () {
        groupId = $(this).prev().prev().val();
    });
    //面板前置操作
    $(".am-text-sm").on('click', '.first-rename', function () {
        // $(this).next().toggle();
        $(this).next('.rename-delete').toggle();
        $(this).parent().parent().siblings('.panel-show-detail').children('td').find('.rename-delete.panel-ope').hide();
        panelId = $(this).parent().next().text();
        panelState = $(this).parent().next().next().next().text();
    });
    //网络前置操作
    $('.mesh-ope').click(function () {
        // $('.rename-delete.mesh-ope').toggle();
        $(this).next('.rename-delete').toggle();
        // $(this).parent().parent().siblings().find('.rename-delete').hide();
        meshId = $(this).parent().next().text();
        var thisMesh = $(this);
        var otherMeshOpe = $('.mesh-ope').not(thisMesh).length;
        // var allMeshOpe = $('.mesh-ope').length;

    });
    //点击删除面板
    $(".am-text-sm").on('click', 'div.delete-panel', function () {
        if (panelState == '在线'){
            layer.open({
                content: '不可删除'
                ,skin: 'msg'
                ,time: 5 //2秒后自动关闭
            });
        }
    });
    //组操作
    $(".btn.btn-primary.yes").click(function () {
        var val = $(this).parent().prev().find('input[id="mesh-name"]').val();
        var title = $(this).parent().prev().prev().find('h4').text();
        var buttonThis = $(this);
        var hiddenTitle = $(this).parent().prev().prev().find('input').val();
        var hint = $(this).parent().prev().find('label').text();
        var hintEmpty = isEmpty(hint);
        if (hintEmpty){
            if (title == '创建网组') {//创建组
                $.post('/control/group', {'gname': val, 'type': 'create'}, function (data) {
                    var exitGroup = data.exitGroup;
                    if (exitGroup == 1) {//组名重复
                        $(buttonThis).parent().prev().find('label').text('已存在，请重新输入');

                    } else {
                        window.location.href = "/control/netWorkGroupConsole";
                        $(buttonThis).parent().prev().find('input[id="mesh-name"]').val("");
                    }
                });
            } else if (hiddenTitle == '重命名组') {
                $.post('/control/group', {'gname': val, 'id': groupId, 'type': 'rename'}, function (data) {
                    var exitGroup = data.exitGroup;
                    if (exitGroup == 1) {//组名重复
                        $(buttonThis).parent().prev().find('label').text('已存在，请重新输入');

                    } else {
                        window.location.href = "/control/netWorkGroupConsole";
                        $(buttonThis).parent().prev().find('input[id="mesh-name"]').val("");
                    }
                    console.log($(buttonThis))
                });
            }else if (hiddenTitle == '重命名网络'){
                $.post('/control/renameMesh',{"mname":val,"meshId":meshId},function (data) {
                    var exitMname = data.exitMname;
                    if (exitMname == 1) {//网络名重复
                        $(buttonThis).parent().prev().find('label').text('已存在，请重新输入');
                    } else {
                        window.location.href = "/control/netWorkGroupConsole";
                        $(buttonThis).parent().prev().find('input[id="mesh-name"]').val("");
                    }
                });
            }else if (hiddenTitle == '重命名面板'){
                $.post('/control/panelOperations',{"pname":val,"mac":panelId,"type":"rename"},function (data) {
                    var exitPname = data.exitPname;
                    if (exitPname == 1){//面板名称重复
                        $(buttonThis).parent().prev().find('label').text('已存在，请重新输入');
                    }else {
                        window.location.href = "/control/netWorkGroupConsole";
                        $(buttonThis).parent().prev().find('input[id="mesh-name"]').val("");
                    }
                });
            }
        }

        if (hiddenTitle == '删除组') {
            $.post('/control/group', {'id': groupId, 'type': 'delete'}, function (data) {
                var exitGroup = data.exitGroup;
                if (exitGroup == 0) {
                    window.location.href = "/control/netWorkGroupConsole";
                }
            });
        }else if (hiddenTitle == '删除面板'){
                $.post('/control/panelOperations',{"mac":panelId,"type":"delete"},function (data) {
                    var exitPname = data.exitPname;
                    if (exitPname == 0){//面板名称重复
                        window.location.href = "/control/netWorkGroupConsole";
                    }
                });
        }
    });
    //勾选主控
    $(":checkbox").click(function () {
        var type = $(this).prop('checked');
        if (type) {//勾选
            type = 1;
        } else {
            type = 0
        }
        var meshId = $(this).prev().val();
        $.post('/control/setMaster', {'meshId': meshId, 'type': type}, function (data) {
            if (data.success == 'success') {
                window.location.href = "/control/netWorkGroupConsole";
            }
        });
    });
    //点击创建组中的全部
    $(".all-group").click(function () {
        window.location.href = "/control/netWorkGroupConsole?gname=全部";
    });
    //点击创建组中的单个组
    $(".one-group").click(function () {
        window.location.href = "/control/netWorkGroupConsole?gname=" + $(this).text();
    });
    //选择组
    $(".select-group").click(function () {
        window.location.href = "/control/netWorkGroupConsole?gname=" + $(this).text() + "&meshId=" + $(this).prev().val();
    });

    //查看面板
    $('.panel-show-msg').click(function (e) {
        $(this).parent().parent().siblings('.panel-show-detail').hide();
        e.preventDefault();
        var status = $(this).attr('src');
        if (status.indexOf('open') != -1) {//开
            var reg = /open/g;
            status = status.replace(reg, 'close');
            $(this).attr('src', status);
            var meshId = $(this).parent().prev().prev().text();
            var tr = '';
            var thisMesh = $(this);
            $.post('/control/getPanels', {'meshId': meshId}, function (data) {
                var controlHosts = data.controlHosts;
                if (controlHosts.length > 0) {
                    var rows = controlHosts.length + 1;
                    tr += '<tr class="am-text-xs panel-show-detail"><th rowspan="' + rows + '" class="am-text-center"></th><th class="d-panel-msg am-text-center">面板名称</th> <th class="d-panel-msg am-text-center">面板MAC</th><th class="d-panel-msg am-text-center">版本型号</th><th class="d-panel-msg am-text-center">面板状态</th>';
                    tr += '<th rowspan="' + rows + '"></th>';
                    $.each(controlHosts, function (key, value) {
                        var deletePanel;
                        if (panelState == '在线'){
                            deletePanel = ' ';
                        }else {
                            deletePanel = '#deletePanel-modal';
                        }
                        tr += '<tr class="am-text-xs panel-show-detail"><td class="d-panel-msg p-r "><span>' + value.pname + '</span><img src="/static/poeConsole/img/dot.png" alt="" class=" p-a  tool first-rename" style="width: 1.7%"><div class="am-cf  rename-delete p-a left panel-ope"> <div class="am-fl am-center rename-panel" style="border-right: 1px solid #ccc;"data-toggle="modal" data-target="#renamePanel-modal">重命名</div><div class="am-fl am-center delete-panel" data-toggle="modal" data-target="'+deletePanel+'">删除</div></div></td><td class="d-panel-msg ">' + value.mac + '</td><td class="d-panel-msg ">版本(型号1)</td><td class="d-panel-msg ">' + value.state + '</td></tr>';
                    });
                    $(thisMesh).parent().parent().after(tr);
                }
                $('.rename-delete').hide();//隐藏重命名/删除
            });
        } else if (status.indexOf('close') != -1) {//关
            var reg = /close/g;
            status = status.replace(reg, 'open');
            $(".panel-show-detail").remove();
            $(this).attr('src', status);
        }
//            $(this).parent().parent().siblings('.panel-show-detail').toggle();
    });


})

function isEmpty(value) {
    if (value == null || value == "" || value == "undefined" || value == undefined) {
        return true;
    }
    else {
        value = value.replace(/\s/g, "");
        if (value == "") {
            return true;
        }
        return false;
    }
}