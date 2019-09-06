/**
 * Created by nannan.li on 2019/9/5.
 */
$(function () {
    var groupId;
    var meshId;
    var match = /^[0-9A-Za-z\u4e00-\u9fa5]{2,8}$/;
    var text = "请输入2-8 位中文、字母、数字";
    var hit = $('.am-form-label.am-text-danger.am-text-left');
    // $(".am-form-field").bind(
    //     "change",
    //     {hint:hit,context:".am-form-field",text:text,match:match},
    //     matchInput);
    //组操作前置动作
    $(".group-operation").click(function () {
        groupId = $(this).prev().prev().val();
    });
    //网络操作前置操作
    $(".rename-mesh").click(function () {
        meshId = $(this).parent().next().text();
    });
    //创建组
    $(".btn.btn-primary.yes").click(function () {
        var val = $(this).parent().prev().find('input[id="mesh-name"]').val();
        var title = $(this).parent().prev().prev().find('h4').text();
        if (title == '创建网组'){//创建组
            $.post('/control/group',{'gname':val,'type':'create'},function (data) {
                var exitGroup = data.exitGroup;
                if (exitGroup == 1){//组名重复
                    $(this).parent().find('label').text('已存在，请重新出入');

                }else {
                    window.location.href="/control/netWorkGroupConsole";
                    $(this).parent().find('label').text('请输入 2-8 位汉字、字母、数字');
                    $(this).parent().prev().find('input[id="mesh-name"]').val("");
                }
            });
        }
        console.log("title",title);
    });
    //组重命名
    $('#renameGroup-modal').on('open:modal:amui', function(){
        $('.am-modal-btn').click(function () {
            var val = $(this).parent().prev().find('input[id="mesh-name"]').val();
            $.post('/control/group',{'gname':val,'type':'rename','id':groupId},function (data) {
                var exitGroup = data.exitGroup;
                if (exitGroup == 1){//组名重复
                    $(this).parent().find('label').text('已存在，请重新出入');

                }else {
                    window.location.href="/control/netWorkGroupConsole";
                    $(this).parent().find('label').text('请输入 2-8 位汉字、字母、数字');
                    $(this).parent().prev().find('input[id="mesh-name"]').val("");
                }
            })
        });
    });
    //删除组
    $('#deleteGroup-modal').on('open:modal:amui', function(){
        $('.am-modal-btn').click(function () {
            $.post('/control/group',{'type':'delete','id':groupId},function (data) {
                var exitGroup = data.exitGroup;
                if (exitGroup == 0){//组名重复
                    window.location.href="/control/netWorkGroupConsole";
                }
            })
        });
    });
    //重命名网络
    $('#renameMesh-modal').on('open:modal:amui', function(){
        $('.am-modal-btn').click(function () {
            var val = $(this).parent().prev().find('input[id="mesh-name"]').val();
            $.post('/control/renameMesh',{'mname':val,'meshId':meshId},function (data) {
                var exitMname = data.exitMname;
                if (exitMname ==1){//网络名重复
                    $(this).parent().find('label').text('已存在，请重新出入');
                }else {
                    window.location.href="/control/netWorkGroupConsole";
                    $(this).parent().find('label').text('请输入 2-8 位汉字、字母、数字');
                    $(this).parent().prev().find('input[id="mesh-name"]').val("");
                }
            });
        })
    });
    //勾选主控
    $(":checkbox").click(function () {
        var type = $(this).prop('checked');
        console.log("type",type);
        if (type){
            type=1
        }else{
            type=0
        }
        var meshId = $(this).prev().val();
        $.post('/control/setMaster',{'meshId':meshId,'type':type},function (data) {
            if (data.success == 'success'){
                window.location.href="/control/netWorkGroupConsole";
            }
        });
    });
    //点击创建组中的全部
    $(".all-group").click(function () {
        window.location.href="/control/netWorkGroupConsole?gname=全部";
    });
    //点击创建组中的单个组
    $(".one-group").click(function () {
        window.location.href="/control/netWorkGroupConsole?gname="+$(this).text();
    });
    //选择组
    $(".select-group").click(function () {
        window.location.href="/control/netWorkGroupConsole?gname="+$(this).text()+"&meshId="+$(this).prev().val();
    });

    //查看面板
    $('.panel-show-msg').click(function (e) {
        e.preventDefault();
        var status = $(this).attr('src');
        if (status.indexOf('open') != -1) {//开
            var reg = /open/g;
            status = status.replace(reg, 'close');
            $(this).attr('src', status);
            var meshId = $(this).parent().prev().prev().text();
            var tr='';
            var thisMesh = $(this);
            $.post('/control/getPanels',{'meshId':meshId},function (data) {
                var controlHosts = data.controlHosts;
                if (controlHosts.length>0){
                    tr += '<tr class="am-text-xs panel-show-detail"><th rowspan="3" class="am-text-center"></th><th class="d-panel-msg am-text-center">面板名称</th> <th class="d-panel-msg am-text-center">面板MAC</th><th class="d-panel-msg am-text-center">版本型号</th><th class="d-panel-msg am-text-center">面板状态</th>';
                    var rows = controlHosts.length + 1;
                    tr += '<th rowspan="'+rows+'"></th>';
                    $.each(controlHosts,function (key,value) {
                        tr += '<tr class="am-text-xs panel-show-detail"><td class="d-panel-msg p-r "><span>'+value.pname+'</span><img src="/static/poeConsole/img/dot.png" alt="" class=" p-a  tool first-rename" style="width: 1.7%"><div class="am-cf  rename-delete p-a left"> <div class="am-fl am-center" style="border-right: 1px solid #ccc;"data-toggle="modal" data-target="#renamePanel-modal">重命名</div><div class="am-fl am-center"   data-toggle="modal" data-target="#deletePanel-modal">删除</div></div></td><td class="d-panel-msg ">'+value.mac+'</td><td class="d-panel-msg ">版本(型号1)</td><td class="d-panel-msg ">'+value.state+'</td></tr>';
                    });
                    $(thisMesh).parent().parent().after(tr);
                }
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

function matchInput(event) {
    var context = $(event.data.context).val();
    if (!(event.data.match).test(context)){
        $(event.data.hint).text('');
        $(event.data.hint).addClass('active').text(event.data.text);
    }else {
        $(event.data.hint).removeClass('active').text('');
    }
}