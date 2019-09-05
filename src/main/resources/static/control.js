/**
 * Created by nannan.li on 2019/9/5.
 */
$(function () {

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
                }
            });
        }
        console.log("title",title);
    });
    //点击创建组中的全部
    $(".all-group").click(function () {
        window.location.href="/control/netWorkGroupConsole?gname=全部";
    });
    //点击创建组中的单个组
    $(".one-group").click(function () {
        window.location.href="/control/netWorkGroupConsole?gname="+$(this).text();
    });
})