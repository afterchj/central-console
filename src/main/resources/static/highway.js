$(function () {

})
// var way = {1:1,2:3,3:5,4:7,5:9,6:11};
$(".on-off").click(function () {
    var group = $(this).attr('alt');
    group = parseInt(group);
    var groupOne = group;
    var groupTwo = group + 1;
    groupOne = groupOne.toString();
    groupTwo = groupTwo.toString();
    if (group == 9){
        groupOne = 'A';
        groupTwo = 'B';
    }else if (group == 11){
        groupOne = 'C';
        groupTwo = 'D';
    }
    var command1;
    var command2;
    var host = '21007032';
    // var host = 'all';
    var src = $(this).attr('src');
    var onOffThis = $(this);
    var topOneCheckboxState = $(".top-one-checkbox").prop('checked');
    var topTwoCheckboxState = $(".top-two-checkbox").prop('checked');
    // console.log('topOneCheckboxState:',topOneCheckboxState,' topTwoCheckboxState:',topTwoCheckboxState,' group:',group,' groupOne:',groupOne,' groupTwo:',groupTwo);
    if (src == '/static/img/switch-off.png'){//开
        $(this).attr('src','/static/img/switch-on.png');
        command1 = '770104160' + groupOne + '3737' + '66';
        command2 = '770104160' + groupTwo + '3737' + '66';
        if ((!topOneCheckboxState && !topTwoCheckboxState)||(topOneCheckboxState && topTwoCheckboxState)){//都故障或都有故障
            $.post("/sendByMeshId", {"command": command1, "host": host},function (data) {
                if (data.success == 'success'){
                    $.post("/sendByMeshId", {"command": command2, "host": host},function (data) {
                        if (data.success == 'success'){
                            $(onOffThis).parent().parent().parent().next().find('img').attr('src','/static/img/red2.png');
                            if (topOneCheckboxState && topTwoCheckboxState){//都有故障
                                $('.first-line,.second-line').removeClass('active');
                                $('.first-line,.second-line').addClass('active');//整条路段故障
                            }
                        }
                    });
                }
            });
        }else if (topOneCheckboxState && !topTwoCheckboxState){//第一段故障
            $.post("/sendByMeshId", {"command": command1, "host": host},function (data) {
                if (data.success == 'success'){
                    $(onOffThis).parent().parent().parent().next().find('.one-100 img').attr('src','/static/img/red2.png');//故障侧灯亮
                    $('.first-line,.second-line').removeClass('active');
                    $(".first-line").addClass('active');//显示故障一侧
                }
            })
        }else if (!topOneCheckboxState && topTwoCheckboxState){//第二段故障
            $.post("/sendByMeshId", {"command": command2, "host": host},function (data) {
                if (data.success == 'success'){
                    $(onOffThis).parent().parent().parent().next().find('.two-100 img').attr('src','/static/img/red2.png');//故障侧灯亮
                    $('.first-line,.second-line').removeClass('active');
                    $(".second-line").addClass('active');//显示故障一侧
                }
            })
        }
    }else {//关
        command1 = '770104160' + groupOne + '3232' + '66';
        command2 = '770104160' + groupTwo + '3232' + '66';
        $.post("/sendByMeshId", {"command": command1, "host": host},function (data) {
            if (data.success == 'success'){
                $.post("/sendByMeshId", {"command": command2, "host": host},function (data) {
                    if (data.success == 'success'){
                        $(onOffThis).parent().parent().parent().next().find('img').attr('src','/static/img/gray.png');//关灯
                        $(onOffThis).attr('src','/static/img/switch-off.png');//开关按钮变关
                        var isOnState = $(".on-off").is('img[src="/static/img/switch-on.png"]');//其它车道是否有开灯的车道
                        // console.log('isOnState',isOnState);
                        if (!isOnState){//所有车道灯关闭
                            $('.first-line,.second-line').removeClass('active');
                        }
                    }
                });
            }
        });
    }

});

