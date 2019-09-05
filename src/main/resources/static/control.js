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
    $(".am-modal-btn").click(function () {
        var val = $(this).parent().prev().find('input[id="mesh-name"]').val();
        var title = $(this).parent().prev().prev().find('span').text();
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
    //勾选中控

    //点击创建组中的全部
    $(".all-group").click(function () {
        window.location.href="/control/netWorkGroupConsole?gname=全部";
    });
    //点击创建组中的单个组
    $(".one-group").click(function () {
        window.location.href="/control/netWorkGroupConsole?gname="+$(this).text();
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