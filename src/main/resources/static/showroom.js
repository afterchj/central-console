/**
 * Created by yuanjie.fang on 2019/6/28.
 */
$(function () {
    // light2();
    // setInterval(function () {
    //     light();
    //     // light2();
    //     // console.log('light')
    // }, 500)
    //
    // setInterval(function () {
    //     // light();
    //     light2();
    //     console.log('light2')
    // }, 100000)
    let socket;
    $(function () {
        let url = location.host;
        if (typeof (WebSocket) == "undefined") {
            console.log("遗憾：您的浏览器不支持WebSocket");
        } else {
            socket = new WebSocket("ws://" + url + "/ws/webSocket");
            //连接打开事件
            socket.onopen = function () {
                console.log("Socket 已打开！");
            };
            //收到消息事件
            socket.onmessage = function (result) {
                console.log("type", typeof result.data, "receive", result.data);
                var data = JSON.parse(result.data);
                var ctype = data.ctype;
                var x = data.x;
                var y = data.y;
                var cid = data.cid;
                var scences = {1:"场景一",2:"场景二",3:"场景三",4:"场景四"};
                var xMap = {'32':'off','37':'on'};
                var onOffOrder = {'32':1,'37':0};
                switch (ctype) {
                    case "C0":
                        $('.box-part .lamp').not('.hide').removeClass('off on');
                        $(".clearfix.on-off.p-a").children().removeClass('active');
                        $('.box-part .lamp').not('.hide').addClass(''+xMap[x]+'');
                        $(".clearfix.on-off.p-a").find('div:eq('+onOffOrder[x]+')').addClass('active');
                        break;
                    case "42":
                        $("select").val(scences[cid]);
                        break;
                    case "CW":
                        $("div.box.clearfix [alt="+cid+"]").find('.box-part .lamp').not('.hide').removeClass('off on');
                        $("div.box.clearfix [alt="+cid+"]").find('.box-part .lamp').not('.hide').addClass(''+xMap[x]+'');
                        var onState = $(".clearfix.on-off.p-a").find(':first').not('[alt="total-on"]').is('.active');
                        var offState = $(".clearfix.on-off.p-a").find(':last').not('[alt="total-off"]').is('.active');
                        if (!onState){
                            $("div.f-l[alt='total-on']").removeClass('active')
                            $("div.f-l[alt='total-off']").removeClass('active').addClass('active');
                        }else if (!offState){
                            $("div.f-l[alt='total-off']").removeClass('active')
                            $("div.f-l[alt='total-on']").removeClass('active').addClass('active');
                        }
                        console.log(onState,offState)
                }
            }
        }
    });
})

function light2() {
    $.ajax({
        type: "post",
        url: "/getMonitor3",
        dataType: "json",
        success: function (data) {
            console.log(data)
            var lightState = data.lightState;
            var placeLNumList = data.placeLNumList;
            var centerLNumList = data.centerLNumList;
            if(centerLNumList.length > 0){
                $('.switch-part.total .page>span:first-child').text(centerLNumList[0].centerLNum);
            }else{
                $('.switch-part.total .page>span:first-child').text('0');
            }
            var lightGroup = Array_2(4, '');
            $('.box-part .lamp').not('.hide').removeClass('on off disconnected')
            $('.switch-part .on-off>div').removeClass('active')
            $('.switch-part img').attr('src', '')
            $('.switch-part .page>div:first-child').text('0')
            $.each(lightState, function (i, val) {
                var index = i;
                var group = val.group;
                var status = val.status;
                var lname = val.lname;
                if (lightGroup[group - 1]) {
                    lightGroup[group - 1].push(status);
                }
                $('.box-part .lamp').not('.hide').each(function () {
                    if (lname == $(this).attr('title')) {
                        if (status == 0) {
                            $(this).addClass('on')
                        } else if (status == 1) {
                            $(this).addClass('off')
                        } else if (status == null) {
                            $(this).addClass('disconnected')
                        }
                    }
                })
            })
            $('.switch-part').not('.scene,.total').each(function () {
                var that=$(this);
                var groupName = parseInt($(this).find('.groupName').text());
                $.each(placeLNumList, function (i, val) {
                    var place = val.place;
                    var placeLNum = val.placeLNum;
                    if (place == groupName) {
                        that.find('.page>span:first-child').text(placeLNum);
                    }
                })
                var lightGroupPart = lightGroup[parseInt(groupName) - 1];
                if (isAllEqual(lightGroupPart) && lightGroupPart.indexOf(null) != -1) {
                    $(this).find('img').attr('src', '')
                    $(this).find('.on-off>div').removeClass('active')
                    $(this).find('.page>span:first-child').text('0');
                } else if (!isAllEqual(lightGroupPart) && lightGroupPart.indexOf(null) != -1) {
                    $(this).find('img').attr('src', '')
                    $(this).find('.on-off>div').removeClass('active')
                }
                // else if (!isAllEqual(lightGroupPart) && lightGroupPart.indexOf(null) == -1) {
                //     $(this).find('img').attr('src', '/static/img/1.png')
                //     $(this).find('.on-off>div').removeClass('active')
                // }
                else if (isAllEqual(lightGroupPart) && lightGroupPart.indexOf('0') != -1) {
                    $(this).find('img').attr('src', '')
                    $(this).find('.on-off>div:first-child').addClass('active').siblings().removeClass('active')
                } else if (isAllEqual(lightGroupPart) && lightGroupPart.indexOf('1') != -1) {
                    $(this).find('img').attr('src', '')
                    $(this).find('.on-off>div:last-child').addClass('active').siblings().removeClass('active')
                }
            })
            var imgList = [];
            var onOffList = [];
            $('.switch-part').not('.scene,.total').each(function () {
                if ($(this).find('.on-off>div.active').text()) {
                    onOffList.push($(this).find('.on-off>div.active').text());
                } else {
                    onOffList.push('');
                }
                imgList.push($(this).find('img').attr('src'));
            })
            if (!isAllEqual(onOffList) && imgList.indexOf('/static/img/2.png') != -1) {
                $('.switch-part.total img').attr('src', '')
                $('.switch-part.total .on-off>div').removeClass('active')
            } else if (!isAllEqual(onOffList) && imgList.indexOf('/static/img/2.png') == -1 && imgList.indexOf('/static/img/1.png') != -1) {
                $('.switch-part.total img').attr('src', '/static/img/1.png')
                $('.switch-part.total .on-off>div').removeClass('active')
            } else if (isAllEqual(onOffList) && onOffList.indexOf('ON') != -1) {
                $('.switch-part.total img').attr('src', '')
                $('.switch-part.total .on-off>div:first-child').addClass('active').siblings().removeClass('active')
            } else if (isAllEqual(onOffList) && onOffList.indexOf('OFF') != -1) {
                $('.switch-part.total img').attr('src', '')
                $('.switch-part.total .on-off>div:last-child').addClass('active').siblings().removeClass('active')
            }
            // else if(!isAllEqual(onOffList) && imgList.indexOf('/static/img/2.png') == -1 && imgList.indexOf('/static/img/1.png') == -1 ) {
            //     $('.switch-part.total img').attr('src', '/static/img/1.png')
            //     $('.switch-part.total .on-off>div').removeClass('active')
            // }
            else if(isAllEqual(onOffList) && imgList.indexOf('/static/img/2.png') != -1){
                $('.switch-part.total img').attr('src', '')
                $('.switch-part.total .on-off>div').removeClass('active')
            }else if (isAllEqual(onOffList) && imgList.indexOf('/static/img/1.png') != -1){
                $('.switch-part.total img').attr('src', '/static/img/1.png')
                $('.switch-part.total .on-off>div').removeClass('active')
            }
        }
    })
}
function light() {
    $.ajax({
        type: "post",
        url: "/getLightOnOrOff",
        dataType: "json",
        success: function (data) {
            var lightState = data.lightState;
            // var placeLNumList = data.placeLNumList;
            // var centerLNumList = data.centerLNumList;
            // if (centerLNumList.length > 0) {
            //     $('.switch-part.total .page>span:first-child').text(centerLNumList);
            // } else {
            //     $('.switch-part.total .page>span:first-child').text('0');
            // }
            var lightGroup = Array_2(4, '');
            // $('.box-part .lamp').not('.hide').removeClass('on off disconnected ')
            // $('.box-part .lamp').not('.hide').removeClass('on off')
            $('.switch-part .on-off>div').removeClass('active')
            $('.switch-part img').attr('src', '')
            $('.switch-part .page>div:first-child').text('0')
            $.each(lightState, function (i, val) {
                var index = i;
                var group = val.group;
                var status = val.status;
                var lname = val.lname;
                if (lightGroup[group - 1]) {
                    lightGroup[group - 1].push(status);
                }
                $('.box-part .lamp').not('.hide').each(function () {
                    if (lname == $(this).attr('title')) {
                        if (status == 0) {
                            $(this).removeClass('off disconnected')
                            $(this).addClass('on');
                        } else if (status == 1) {
                            $(this).removeClass('on disconnected')
                            $(this).addClass('off');
                        }
                        // else if (status == null) {
                        //     $(this).addClass('disconnected')
                        // }
                    }
                })
            })
            //group01
            var on=0,off=0,disconnected=0;
            var totalStatus = new Array();
            var group1DisNum = new Array();
            var group2DisNum = new Array();
            var group3DisNum = new Array();
            var group4DisNum = new Array();
            $(".box.clearfix").find('.box-1.f-l.clearfix:eq(0)').find('div.lamp').not('.hide').each(function () {
                if ($(this).hasClass('on')){
                    on=1;
                }else if ($(this).hasClass('off')){
                    off=1;
                }else if ($(this).hasClass('disconnected')){
                    disconnected=1;
                    group1DisNum.push(1);
                }
                // console.log($(this).attr('class'))
            });
            if (disconnected==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(0)').find('img').attr('src', '')
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
                totalStatus.push('dis');
            }else if (on==1&&off==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
                totalStatus.push('diff');
            }else if (on==1&&off==0){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(2)').find('.on-off>div:first-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(0)').find('img').attr('src', '')
                totalStatus.push('on');
            }else if (on==0&&off==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(2)').find('.on-off>div:last-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(0)').find('img').attr('src', '')
                totalStatus.push('off');
            }
            //group02
            var twoOn=0,twoOff=0,twodisconnected=0;
            $(".box.clearfix").find('.box-1.f-l.clearfix:eq(1)').find('div.lamp').not('.hide').each(function () {
                if ($(this).hasClass('on')){
                    twoOn=1;
                }else if ($(this).hasClass('off')){
                    twoOff=1;
                }else if ($(this).hasClass('disconnected')){
                    twodisconnected=1;
                    group2DisNum.push(1);
                }
            });

            if (twodisconnected==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(0)').find('img').attr('src', '')
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
                totalStatus.push('dis');
            }else if (twoOn==1&&twoOff==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
                totalStatus.push('diff');
            }else if (twoOn==1&&twoOff==0){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(2)').find('.on-off>div:first-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(0)').find('img').attr('src', '')
                totalStatus.push('on');
            }else if (twoOn==0&&twoOff==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(2)').find('.on-off>div:last-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(0)').find('img').attr('src', '')
                totalStatus.push('off');
            }

            //group03
            var thirdOn=0,thirdOff=0,thirddisconnected=0;
            $(".box.clearfix").find('.box-1.f-l.clearfix:eq(2)').find('div.lamp').not('.hide').each(function () {
                if ($(this).hasClass('on')){
                    thirdOn=1;
                }else if ($(this).hasClass('off')){
                    thirdOff=1;
                }else if ($(this).hasClass('disconnected')){
                    thirddisconnected=1;
                    group3DisNum.push(1);
                }
            });
            if (thirddisconnected==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(0)').find('img').attr('src', '')
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
                totalStatus.push('dis');
            }else if (thirdOn==1&&thirdOff==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
                totalStatus.push('diff');
            }else if (thirdOn==1&&thirdOff==0){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(2)').find('.on-off>div:first-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(0)').find('img').attr('src', '')
                totalStatus.push('on');
            }else if (thirdOn==0&&thirdOff==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(2)').find('.on-off>div:last-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(0)').find('img').attr('src', '')
                totalStatus.push('off');
            }
            //group04
            var fourOn=0,fourOff=0,fourdisconnected=0;;
            $(".center-box.f-l").find('div.lamp').not('.hide').each(function () {
                if ($(this).hasClass('on')){
                    fourOn=1;
                }else if ($(this).hasClass('off')){
                    fourOff=1;
                }else if ($(this).hasClass('disconnected')){
                    fourdisconnected=1;
                    group4DisNum.push(1);
                }
            });
            if (thirddisconnected==1){
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(0)').find('img').attr('src', '');
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
                totalStatus.push('dis');
            }else if (fourOn==1&&fourOff==1){
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
                totalStatus.push('diff');
            }else if (fourOn==1&&fourOff==0){
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(2)').find('.on-off>div:first-child').addClass('active').siblings().removeClass('active');
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(0)').find('img').attr('src', '')
                totalStatus.push('on');
            }else if (fourOn==0&&fourOff==1){
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(2)').find('.on-off>div:last-child').addClass('active').siblings().removeClass('active');
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(0)').find('img').attr('src', '')
                totalStatus.push('off');
            }

            // if (disconnected==1||twodisconnected==1||thirddisconnected==1||fourdisconnected==1){
            //     $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(0)').find('img').attr('src', '');
            //     $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            // } else if ((on==1&&off==1)||(twoOn==1&&twoOff==1)||(thirdOn==1&&thirdOff==1)||(fourOn==1&&fourOff==1)){
            //     $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
            //     $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            // }
            var groupStatusOn = 'on';
            var groupStatusOff = 'off';
            $.each(totalStatus,function (key,value) {
                // console.log(value)
                if (value=='dis'){
                    $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(0)').find('img').attr('src', '');
                    $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
                    return false;
                }
                if (value=='diff'){
                    $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                    $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
                    return false;
                }
                if (value=='on'){
                    groupStatusOff = 'on';
                }
                if (value=='off'){
                    groupStatusOn = 'off';
                }
            });
            if (groupStatusOn == 'on' && groupStatusOff=='on'){
                $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(0)').find('img').attr('src', '');
                $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(2)').find('.on-off>div:first-child').addClass('active').siblings().removeClass('active');
            }else if (groupStatusOn == 'off' &&groupStatusOff=='off'){
                $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(0)').find('img').attr('src', '');
                $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(2)').find('.on-off>div:last-child').addClass('active').siblings().removeClass('active');
            }else if (groupStatusOn=='off'&&groupStatusOff=='on'){
                $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            }


            // $('.switch-part').not('.scene,.total').each(function () {
            //     var that = $(this);
            //     var groupName = parseInt($(this).find('.groupName').text());
            //     $.each(placeLNumList, function (i, val) {
            //         var place = val.place;
            //         var placeLNum = val.placeLNum;
                    // if (place == groupName){
                    //     switch (place){
                    //         case 1:
                    //             that.find('.page>span:first-child').text((placeLNum-group1DisNum.size()));
                    //             break;
                    //         case 2:
                    //             that.find('.page>span:first-child').text((placeLNum-group2DisNum.size()));
                    //             break;
                    //         case 3:
                    //             that.find('.page>span:first-child').text((placeLNum-group3DisNum.size()));
                    //             break;
                    //         case 4:
                    //             that.find('.page>span:first-child').text((placeLNum-group4DisNum.size()));
                    //             break;
                    //     }
                    // }

                    // if (place == groupName) {
                    //     that.find('.page>span:first-child').text(placeLNum);
                    // }
                // })
            // })
            //     // var lightGroupPart = lightGroup[parseInt(groupName) - 1];
            //     // if (isAllEqual(lightGroupPart) && lightGroupPart.indexOf(null) != -1) {
            //     //     // $(this).find('img').attr('src', '/static/img/2.png')
            //     //     $(this).find('.on-off>div').removeClass('active')
            //     //     $(this).find('.page>span:first-child').text('0');
            //     // } else if (!isAllEqual(lightGroupPart) && lightGroupPart.indexOf(null) != -1) {
            //     //     // $(this).find('img').attr('src', '/static/img/2.png')
            //     //     $(this).find('.on-off>div').removeClass('active')
            //     // } else if (!isAllEqual(lightGroupPart) && lightGroupPart.indexOf(null) == -1) {
            //     //     // $(this).find('img').attr('src', '/static/img/1.png')
            //     //     $(this).find('.on-off>div').removeClass('active')
            //     // } else if (isAllEqual(lightGroupPart) && lightGroupPart.indexOf('0') != -1) {
            //     //     // $(this).find('img').attr('src', '')
            //     //     $(this).find('.on-off>div:first-child').addClass('active').siblings().removeClass('active')
            //     // } else if (isAllEqual(lightGroupPart) && lightGroupPart.indexOf('1') != -1) {
            //     //     // $(this).find('img').attr('src', '')
            //     //     $(this).find('.on-off>div:last-child').addClass('active').siblings().removeClass('active')
            //     // }
            //
            // })
            // var imgList = [];
            // var onOffList = [];
            // $('.switch-part').not('.scene,.total').each(function () {
            //     if ($(this).find('.on-off>div.active').text()) {
            //         onOffList.push($(this).find('.on-off>div.active').text());
            //     } else {
            //         onOffList.push('');
            //     }
            //     // imgList.push($(this).find('img').attr('src'));
            // })
            // if (!isAllEqual(onOffList)) {
            //     $('.switch-part.total .on-off>div').removeClass('active')
            // } else if (isAllEqual(onOffList) && onOffList.indexOf('ON') != -1) {
            //     // $('.switch-part.total img').attr('src', '')
            //     $('.switch-part.total .on-off>div:first-child').addClass('active').siblings().removeClass('active')
            // } else if (isAllEqual(onOffList) && onOffList.indexOf('OFF') != -1) {
            //     // $('.switch-part.total img').attr('src', '')
            //     $('.switch-part.total .on-off>div:last-child').addClass('active').siblings().removeClass('active')
            // }
        }
    })
}
var host = 'showroom';
$('.on-off>div').click(function () {
    $(this).addClass('active').siblings().removeClass('active');
    var groupOrder = parseInt($(this).parent().parent().siblings('.groupOrder').find('.groupName').text());
    if ($(this).text() == 'ON') {
        var onOffOrder = '3737';
    } else if ($(this).text() == 'OFF') {
        var onOffOrder = '3232';
    }
    if (groupOrder <= 9) {
        var command = '770104160' + groupOrder + onOffOrder + '66';
    } else if (groupOrder == 10) {
        var command = '770104160A' + onOffOrder + '66';
    }
    if ($(this).attr("alt") == "total-on") {
        var command = '77010315373766';
    } else if ($(this).attr("alt") == "total-off") {
        var command = '77010315323266';
    }
    console.log('host:',host,'command:',command);
    $.post("/sendByMeshId", {"command": command, "host": host}, function () {
    })
})
$("select").change(function () {
    var command = $.trim($(this).val());
    $.post("/sendScenseByMeshId", {
        "command": command,
        "host": host
    }, function () {

    })
})
function Array_2(nRow, nColumn) {
    var array1 = new Array();
    for (i = 0; i < nRow; i++) {
        array1[i] = new Array();
        for (n = 0; n < nColumn; n++) {
            array1[i][n] = '';
        }
    }
    return array1;
}
function isAllEqual(array) {
    if (array.length > 0) {
        return !array.some(function (value, index) {
            return value !== array[0];
        });
    } else {
        return true;
    }
}
