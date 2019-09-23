$(function () {

})
// var way = {1:1,2:3,3:5,4:7,5:9,6:11};
$(".on-off").click(function () {
    var groupOne;
    var groupTwo;
    var command1;
    var command2;
    var host = '192.168.10.251';
    var group = $(this).attr('alt');
    var src = $(this).attr('src');
    var topOneCheckboxState = $(".top-one-checkbox").prop('checked');
    var topTwoCheckboxState = $(".top-two-checkbox").prop('checked');
    console.log('topOneCheckboxState:',topOneCheckboxState,' topTwoCheckboxState:',topTwoCheckboxState,'group:',group,'src:',src);
    if (src == '/static/img/switch-off.png'){//关
        $(this).attr('src','/static/img/switch-on.png');
        groupOne = group;
        groupTwo = group + 1;
        if (group == 9){
            groupOne = 'A';
            groupTwo = 'B';
        }else if (group == 11){
            groupOne = 'C';
            groupTwo = 'D';
        }
        command1 = '770104160' + groupOne + '3232' + '66';
        command2 = '770104160' + groupTwo + '3232' + '66';
        if (!topOneCheckboxState && !topTwoCheckboxState){//没有故障
            $.post("/sendSocket6", {"command": command1, "host": host},function (data) {
                if (data.success == 'success'){
                    $.post("/sendSocket6", {"command": command2, "host": host},function (data) {
                        if (data.success == 'success'){
                            $(this).parent().parent().parent().next().find('img').attr('src','/static/img/red2.png');
                        }
                    });
                }
            });
        }else if (topOneCheckboxState && !topTwoCheckboxState){//第一段故障
            $.post("/sendSocket6", {"command": command1, "host": host},function (data) {
                if (data.success == 'success'){
                    $(this).parent().parent().parent().next().find('.one-100 img').attr('src','/static/img/red2.png');
                    $(".first-line").find('.one-100').addClass('active');
                }
            })
        }
    }else {
        $(this).attr('src','/static/img/switch-off.png');
    }

});