/**
 * Created by yuanjie.fang on 2019/6/28.
 */
$(function () {
    light2();
    setInterval(function () {
        // light();
        light2();
    }, 500)
})

function light2() {
    $.ajax({
        type: "post",
        url: "/getMonitor3",
        dataType: "json",
        success: function (data) {
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
                    $(this).find('img').attr('src', 'g')
                    $(this).find('.on-off>div').removeClass('active')
                } else if (!isAllEqual(lightGroupPart) && lightGroupPart.indexOf(null) == -1) {
                    $(this).find('img').attr('src', '/static/img/1.png')
                    $(this).find('.on-off>div').removeClass('active')
                } else if (isAllEqual(lightGroupPart) && lightGroupPart.indexOf('0') != -1) {
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
            }else if(!isAllEqual(onOffList) && imgList.indexOf('/static/img/2.png') == -1 && imgList.indexOf('/static/img/1.png') == -1 ) {
                $('.switch-part.total img').attr('src', '/static/img/1.png')
                $('.switch-part.total .on-off>div').removeClass('active')
            }else if(isAllEqual(onOffList) && imgList.indexOf('/static/img/2.png') != -1){
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
        url: "/getMonitor3",
        dataType: "json",
        success: function (data) {
            var lightState = data.lightState;
            // var placeLNumList = data.placeLNumList;
            // var centerLNumList = data.centerLNumList;
            // if (centerLNumList.length > 0) {
            //     $('.switch-part.total .page>span:first-child').text(centerLNumList[0].centerLNum);
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
            $(".box.clearfix").find('.box-1.f-l.clearfix:eq(0)').find('div.lamp').not('.hide').each(function () {
                if ($(this).hasClass('on')){
                    on=1;
                }else if ($(this).hasClass('off')){
                    off=1;
                }else if ($(this).hasClass('disconnected')){
                    disconnected=1;
                }
                // console.log($(this).attr('class'))
            });
            if (disconnected==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(0)').find('img').attr('src', '')
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            }else if (on==1&&off==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            }else if (on==1&&off==0){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(2)').find('.on-off>div:first-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(0)').find('img').attr('src', '')
            }else if (on==0&&off==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(2)').find('.on-off>div:last-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(0)').find('.f-l:eq(0)').find('img').attr('src', '')
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
                }
            });

            if (twodisconnected==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(0)').find('img').attr('src', '')
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            }else if (twoOn==1&&twoOff==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            }else if (twoOn==1&&twoOff==0){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(2)').find('.on-off>div:first-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(0)').find('img').attr('src', '')
            }else if (twoOn==0&&twoOff==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(2)').find('.on-off>div:last-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(1)').find('.f-l:eq(0)').find('img').attr('src', '')
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
                }
            });
            if (thirddisconnected==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(0)').find('img').attr('src', '')
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            }else if (thirdOn==1&&thirdOff==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            }else if (thirdOn==1&&thirdOff==0){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(2)').find('.on-off>div:first-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(0)').find('img').attr('src', '')
            }else if (thirdOn==0&&thirdOff==1){
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(2)').find('.on-off>div:last-child').addClass('active').siblings().removeClass('active');
                $(".switch.clearfix").find('.clearfix.switch-part.f-l:eq(2)').find('.f-l:eq(0)').find('img').attr('src', '')
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
                }
            });
            if (thirddisconnected==1){
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(0)').find('img').attr('src', '');
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            }else if (fourOn==1&&fourOff==1){
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            }else if (fourOn==1&&fourOff==0){
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(2)').find('.on-off>div:first-child').addClass('active').siblings().removeClass('active');
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(0)').find('img').attr('src', '')
            }else if (fourOn==0&&fourOff==1){
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(2)').find('.on-off>div:last-child').addClass('active').siblings().removeClass('active');
                $(".f-r>.clearfix.switch-part ").find('.f-l:eq(0)').find('img').attr('src', '')
            }
            if (disconnected==1||twodisconnected==1||thirddisconnected==1||fourdisconnected==1){
                $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(0)').find('img').attr('src', '');
                $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            } else if ((on==1&&off==1)||(twoOn==1&&twoOff==1)||(thirdOn==1&&thirdOff==1)||(fourOn==1&&fourOff==1)){
                $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(0)').find('img').attr('src', '/static/img/1.png');
                $(".f-r>.clearfix.switch-part.total ").find('.f-l:eq(2)').find('.on-off>div').removeClass('active');
            }



            // $('.switch-part').not('.scene,.total').each(function () {
            //     var that = $(this);
            //     var groupName = parseInt($(this).find('.groupName').text());
            //     // $.each(placeLNumList, function (i, val) {
            //     //     var place = val.place;
            //     //     var placeLNum = val.placeLNum;
            //     //     if (place == groupName) {
            //     //         that.find('.page>span:first-child').text(placeLNum);
            //     //     }
            //     // })
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
    var host = '192.168.1.194';
    $.post("/sendSocket6", {
        "command": command,
        "host": host
    }, function () {
    })
})
$("select").change(function () {
    var command = $.trim($(this).val());
    var host = '192.168.1.194';
    $.post("/sendSocket5", {
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
