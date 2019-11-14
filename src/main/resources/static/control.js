/**
 * Created by nannan.li on 2019/9/5.
 */
$(function () {
    var groupId;
    var meshId;
    var meshState;//网络状态
    var panelId;//mac
    var panelState;//面板状态
    var panelNameLabel;//面板名称标签
    var pState;//面板总状态
    var pNum;
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
        panelId = $(this).prev().attr('alt');
        panelState = $(this).parent().next().next().next().text();
        meshId = $(this).parent();
        panelNameLabel = $(this).prev();
    });
    //网络前置操作
    $('.mesh-ope').click(function () {
        // $('.rename-delete.mesh-ope').toggle();
        // $('.panel-show-detail').hide();
        $('.data-show').hide();
        /*   $('.group-data').hide();*/
        $(this).next('.rename-delete').toggle();
        $(this).parent().parent().siblings().find('.rename-delete').hide();
        meshId = $(this).parent().next().text();
        meshState = $(this).parent().next().next().text();
        pNum = $(this).parent().next().next().next().find(":eq(0)").text();
        pState = $(this).parent().next().next().next().find(":eq(1)").text();
        pState = pState.indexOf("离线") == -1 ? true : false;
        console.log('pNum:', pNum, ' pState:', pState)
        if (meshState == '网络在线' || (pNum > 0 && pState)) {
            $(this).next().find(".delete-mesh").attr('data-target', '#');
        }
    });
    $(".delete-mesh").click(function () {
        if (meshState == '网络在线' || (pNum > 0 && pState)) {
            layer.open({
                content: '不可删除'
                , skin: 'msg'
                , time: 3 //3秒后自动关闭
            });
        }
    });
    //点击删除面板
    $(".am-text-sm").on('click', 'div.delete-panel', function () {
        if (panelState == '在线') {
            layer.open({
                content: '不可删除'
                , skin: 'msg'
                , time: 3 //3秒后自动关闭
            });
        }
    });
    //点击重命名
    $(".am-text-sm").on('click', 'div.rename-mesh,div.rename-group,div.rename-panel,button.create-group', function () {
        $('label.am-form-label.am-text-danger.am-text-left').text('');
        $('input.am-form-field').val('');
    });
    $(".btn.btn-primary.yes").click(function () {
        var val = $(this).parent().prev().find('input[id="mesh-name"]').val();
        var title = $(this).parent().prev().prev().find('h4').text();
        var buttonThis = $(this);
        var hiddenTitle = $(this).parent().prev().prev().find('input').val();
        var valEmpty = isEmpty(val);
        if (valEmpty && hiddenTitle.indexOf("删除") == -1) {//输入框不为空&&不是删除操作
            $(buttonThis).parent().prev().find('label').text(text);
        }
        var hint = $(buttonThis).parent().prev().find('label').text();
        var hintEmpty = isEmpty(hint);
        if (hintEmpty) {//没有提示
            if (title == '创建网组') {//创建组
                $.post('/central-console/control/group', {'gname': val, 'type': 'create'}, function (data) {
                    var exitGroup = data.exitGroup;
                    if (exitGroup == 1) {//组名重复
                        $(buttonThis).parent().prev().find('label').text('已存在，请重新输入');

                    } else {
                        window.location.href = "/central-console/control/netWorkGroupConsole";
                        $(buttonThis).parent().prev().find('input[id="mesh-name"]').val("");
                    }
                });
            } else if (hiddenTitle == '重命名组') {
                $.post('/central-console/control/group', {'gname': val, 'id': groupId, 'type': 'rename'}, function (data) {
                    var exitGroup = data.exitGroup;
                    if (exitGroup == 1) {//组名重复
                        $(buttonThis).parent().prev().find('label').text('已存在，请重新输入');

                    } else {
                        window.location.href = "/central-console/control/netWorkGroupConsole";
                        $(buttonThis).parent().prev().find('input[id="mesh-name"]').val("");
                    }
                    console.log($(buttonThis))
                });
            } else if (hiddenTitle == '重命名网络') {
                $.post('/central-console/control/renameMesh', {"mname": val, "meshId": meshId}, function (data) {
                    var exitMname = data.exitMname;
                    if (exitMname == 1) {//网络名重复
                        $(buttonThis).parent().prev().find('label').text('已存在，请重新输入');
                    } else {
                        window.location.href = "/central-console/control/netWorkGroupConsole";
                        $(buttonThis).parent().prev().find('input[id="mesh-name"]').val("");
                    }
                });
            } else if (hiddenTitle == '重命名面板') {
                $.post('/central-console/control/panelOperations', {"pname": val, "id": panelId, "type": "rename"}, function (data) {
                    var exitPname = data.exitPname;
                    if (exitPname == 1) {//面板名称重复
                        $(buttonThis).parent().prev().find('label').text('已存在，请重新输入');
                    } else {
                        // window.location.href = "/central-console/control/netWorkGroupConsole";
                        $(buttonThis).parent().prev().find('input[id="mesh-name"]').val("");
                        $("#renamePanel-modal").modal('hide');
                        $('.rename-delete').hide();//隐藏重命名/删除
                        $(panelNameLabel).text(val);
                    }
                });
            }
        }

        if (hiddenTitle == '删除组') {
            $.post('/central-console/control/group', {'id': groupId, 'type': 'delete'}, function (data) {
                var exitGroup = data.exitGroup;
                if (exitGroup == 0) {
                    window.location.href = "/central-console/control/netWorkGroupConsole";
                }
            });
        } else if (hiddenTitle == '删除面板') {
            $.post('/central-console/control/panelOperations', {"id": panelId, "type": "delete"}, function (data) {
                var exitPname = data.exitPname;
                if (exitPname == 0) {//面板名称重复
                    window.location.href = "/central-console/control/netWorkGroupConsole";
                }
            });
        } else if (hiddenTitle == '删除网络') {
            $.post('/central-console/control/renameMesh', {"meshId": meshId}, function (data) {
                var exitMname = data.exitMname;
                if (exitMname == 0) {
                    window.location.href = "/central-console/control/netWorkGroupConsole";
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
        $.post('/central-console/control/setMaster', {'meshId': meshId, 'type': type}, function (data) {
            if (data.success == 'success') {
                window.location.href = "/central-console/control/netWorkGroupConsole";
            }
        });
    });
    //点击创建组中的全部
    $(".all-group").click(function () {
        setAndSubmitForm(null, null);
    });
    //点击创建组中的单个组
    $(".one-group").click(function () {
        var gid = $(this).prev().val();
        setAndSubmitForm(gid, null);
    });
    //选择组
    $(".select-group").click(function () {
        var gid = $(this).attr('alt');
        var meshId = $(this).parent().siblings("input").val();
        setAndSubmitForm(gid, meshId);
    });

    //查看面板
    $('.panel-show-msg').click(function (e) {
        // e.stopPropagation();
        // $('.rename-delete').hide();
        $('.data-show').hide();
        // $('.group-data').hide();
        $(this).parent().parent().siblings('.panel-show-detail').hide();
        // e.preventDefault();

        var status = $(this).find('img').attr('src');
        if (status.indexOf('open') != -1) {//开
            var reg = /open/g;
            status = status.replace(reg, 'close');
            $(this).find('img').attr('src', status);
            var meshId = $(this).parent().prev().prev().text();
            var tr = '';
            var thisMesh = $(this);
            $.post('/central-console/control/getPanels', {'meshId': meshId}, function (data) {
                var controlHosts = data.controlHosts;
                if (controlHosts.length > 0) {
                    // console.log("meshAndPOEStatus",data.meshAndPOEStatus);
                    // console.log("meshId",data.meshAndPOEStatus.meshId);
                    $(thisMesh).prev().text(data.meshAndPOEStatus.pState);//面板数量状态实时更新
                    $(thisMesh).parent().prev().text(data.meshAndPOEStatus.mState);//网络状态实时更新
                    console.log($(thisMesh).prev().prev().text(), data.meshAndPOEStatus.pCount);
                    $(thisMesh).prev().prev().text(data.meshAndPOEStatus.pCount);

                    var rows = controlHosts.length + 1;
                    tr += '<tr class="am-text-xs panel-show-detail"><th rowspan="' + rows + '" ></th><th class="d-panel-msg ">面板名称</th> <th class="d-panel-msg ">面板MAC</th><th class="d-panel-msg ">版本型号</th><th class="d-panel-msg ">面板状态</th>';
                    tr += '<th rowspan="' + rows + '"></th>';
                    $.each(controlHosts, function (key, value) {
                        meshId = data.meshId;
                        var deletePanel;
                        if (value.state == '在线') {
                            deletePanel = ' ';
                        } else {
                            deletePanel = '#deletePanel-modal';
                        }
                        tr += '<tr class="am-text-xs panel-show-detail"><td class="d-panel-msg p-r "><span alt="' + value.id + '">' + value.pname + '</span ><span class=" p-a  tool first-rename area"><img src="/central-console/static/poeConsole/img/dot.png" alt=""  style="width:.25rem;"></span><div class="am-cf  rename-delete p-a left panel-ope"> <div class="am-fl am-center rename-panel" style="border-right: 1px solid #ccc;"data-toggle="modal" data-target="#renamePanel-modal">重命名</div><div class="am-fl am-center delete-panel" data-toggle="modal" data-target="' + deletePanel + '">删除</div></div></td><td class="d-panel-msg ">' + value.mac + '</td><td class="d-panel-msg ">' + value.productType + '</td><td' +
                            ' class="d-panel-msg ">' + value.state + '</td></tr>';
                    });
                    $(thisMesh).parent().parent().after(tr);
                }
                $('.rename-delete').hide();//隐藏重命名/删除
            });
        } else if (status.indexOf('close') != -1) {//关
            var reg = /close/g;
            status = status.replace(reg, 'open');
            $(".panel-show-detail").remove();
            $(this).find('img').attr('src', status);
        }
        $(this).parent().parent().siblings().find('.panel-show-msg').find('img').attr('src', '/central-console/static/poeConsole/img/open.png');
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

function setAndSubmitForm(gid, meshId) {
    var thisForm = $("form[name='groupOpe");
    $(thisForm)[0].reset();
    $(thisForm).find('[name="gid"]').val(gid);
    $(thisForm).find('[name="meshId"]').val(meshId);
    $(thisForm).submit();
}