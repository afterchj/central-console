//77010416 01 00 32/37 66
$(function () {
    init()
    light()
    setInterval(function () {
        light2()
    }, 500);

})
$('.frame>span').click(function () {
    $(this).addClass('active').siblings('span').removeClass('active');
    var groupOrder = parseInt($(this).parent().siblings('label').find('span').text());
    if ($(this).text() == 'ON') {
        var onOffOrder = '0037';
    } else if ($(this).text() == 'OFF') {
        var onOffOrder = '0032';
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
    }, function () {})
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

function init() {
    var grouplist = [2, 3, 4, 5, 6, 7, 8, 9, 10];
    var selector;
    for (var i = 0; i < grouplist.length; i++) {
        var group = grouplist[i];
        (group > 9) ? selector = '.place': selector = '.place0';
        $(selector + group + '  .light-line').each(function () {
            $(this).find('span').not(".hide ").each(function () {
                var sIndex = $(this).attr('alt');
                var iIndex = $(this).index();
                var pastIndex;
                if (!isNaN(sIndex)) {
                    sIndex = parseInt(sIndex);
                    if ($(this).siblings("span[alt=" + (sIndex - 1) + "]")) {
                        $(this).siblings("span[alt=" + (sIndex - 1) + "]").after($(this));
                    }
                }
            })
        })
    }
    $('.place07 .light-line span').each(function () {
        if ($(this).attr('alt') == '空调1') {
            $(this).siblings("span[alt=51]").after($(this));
        } else if ($(this).attr('alt') == '空调2') {
            $(this).siblings("span[alt=60]").after($(this));
        }
    })
}

function light() {
    $.ajax({
        type: "post",
        url: "/getMonitor2",
        dataType: "json",
        success: function (data) {
            var groupLists = Array_2(10, '');
            var lightState = data.lightState;
            var placeLNumList = data.placeLNumList;
            var centerLNumList = data.centerLNumList;
            if (centerLNumList.length > 0) {
                var centerLNum = centerLNumList[0].centerLNum;
                $('.total-page').text(centerLNum);
            } else {
                $('.total-page').text("0");
            }
            $('.light-line>span').not(".hide .air").removeClass('disconnected off on')
            $('.total-frame>span').removeClass('active')
            $(this).find('.icon img').attr('src', '')
            $.each(lightState, function (i, val) {
                var index = i;
                var group = val.group;
                var status = val.status;
                if(groupLists[group - 1]){
                    groupLists[group - 1].push(status);
                }
                var selector;
                (group > 9) ? selector = '.place': selector = '.place0';
                $(selector + group + '  .light-line').each(function () {
                    $(this).find('span').not(".hide").each(function () {
                        var sIndex = $(this).attr('alt');
                        if (sIndex == index) {
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
            })
            $('.place-status').each(function () {
                var that=$(this);
                var place2 = $(this).find('.caption span').text();
                $.each(placeLNumList,function(i, val){
                    var place=val.place;
                    var placeLNum=val.placeLNum;
                    if(place2==place){
                        that.find('.page>span:first-child').text(placeLNum);
                    }
                })
                var grouppart = groupLists[parseInt(place2) - 1];
                    if(isAllEqual(grouppart) && grouppart.indexOf(null) != -1){
                        $(this).find('.icon img').attr('src', '/static/img/2.png')
                        $(this).find('.frame>span').removeClass('active');
                        $(this).find('.page>span:first-child').text('0');
                    }else if (!isAllEqual(grouppart) &&  grouppart.indexOf(null) != -1 ) {
                        $(this).find('.icon img').attr('src', '/static/img/2.png')
                        $(this).find('.frame>span').removeClass('active');
                    } else if (!isAllEqual(grouppart)) {
                        $(this).find('.icon img').attr('src', '/static/img/1.png')
                        $(this).find('.frame>span').removeClass('active');
                    } else if (isAllEqual(grouppart) && grouppart.indexOf('0') != -1) {
                        $(this).find('.frame>span:first-child').addClass('active').siblings('span').removeClass('active');
                        $(this).find('.icon img').attr('src', '')
                    } else if (isAllEqual(grouppart) && grouppart.indexOf('1') != -1) {
                        $(this).find('.frame>span:last-child').addClass('active').siblings('span').removeClass('active');
                        $(this).find('.icon img').attr('src', '')
                    }
            })

            var isTextArray = [];
            var isExistArray = [];
            $('.place-status').each(function () {
                var img = $(this).find('img').attr('src');
                var onOff = $(this).find('.frame>span.active').text();
                isExistArray.push(img)
                isTextArray.push(onOff)
            })

            if (isExistArray.indexOf('/static/img/2.png') != -1) {
                $('img.total-frame').attr('src', '/static/img/2.png');
            } else if (isExistArray.indexOf('/static/img/2.png') == -1 && isExistArray.indexOf('/static/img/1.png') != -1) {
                $('img.total-frame').attr('src', '/static/img/1.png');
            } else {
                $('img.total-frame').attr('src', '');
            }
            if (isAllEqual(isTextArray)) {
                if (isTextArray[0] == 'ON') {
                    $('.total-frame>span:first-child').addClass('active').siblings('span').removeClass('active');
                    $('img.total-frame').attr('src', '');
                } else if (isTextArray[0] == 'OFF') {
                    $('.total-frame>span:last-child').addClass('active').siblings('span').removeClass('active');
                    $('img.total-frame').attr('src', '');
                }
            }
        }
    })
}

function light2() {
    $.ajax({
        type: "post",
        url: "/getMonitor2LightStatus",
        dataType: "json",
        success: function (data) {
            var groupLists = Array_2(10, '');
            var lightState = data.lightState;
            var placeLNumList = data.placeLNumList;
            // var centerLNumList = data.centerLNumList;
            // if (centerLNumList.length > 0) {
            //     var centerLNum = centerLNumList[0].centerLNum;
            //     $('.total-page').text(centerLNum);
            // } else {
            //     $('.total-page').text("0");
            // }
            // $('.light-line>span').not(".hide .air").removeClass('disconnected off on')
            $('.total-frame>span').removeClass('active')
            $(this).find('.icon img').attr('src', '');
            $.each(lightState, function (i, val) {
                var index = i;
                var group = val.group;
                var status = val.status;
                var lname = val.lname;
                if(groupLists[group - 1]){
                    groupLists[group - 1].push(status);
                }
                // var selector;
                // (group > 9) ? selector = '.place': selector = '.place0';
                $('.light-line span').not(".hide").each(function () {
                    // $(this).find('span').not(".hide").each(function () {
                    //     $(this).attr('alt');
                        var weblname =$(this).attr('alt')
                        if (parseInt(lname) == (parseInt(weblname)+1)) {
                            if (status == 0) {
                                $(this).removeClass('off disconnected')
                                $(this).addClass('on')
                            } else if (status == 1) {
                                $(this).removeClass('on disconnected')
                                $(this).addClass('off')
                            }
                            // else if (status == null) {
                            //     $(this).addClass('disconnected')
                            // }
                        }
                    // })
                })
            })
            $('.place-status').each(function () {
                var that=$(this);
                var place2 = $(this).find('.caption span').text();

                $.each(placeLNumList,function(i, val){
                    var place=val.place;
                    var placeLNum=val.placeLNum;
                    if(place2==place){
                        that.find('.page>span:first-child').text("");
                        that.find('.page>span:first-child').text($.trim(parseInt(placeLNum)));
                    }
                })
                var grouppart = groupLists[parseInt(place2) - 1];
                if(isAllEqual(grouppart) && grouppart.indexOf(null) != -1){
                    $(this).find('.icon img').attr('src', '/static/img/2.png')
                    $(this).find('.frame>span').removeClass('active');
                    $(this).find('.page>span:first-child').text('0');
                }else if (!isAllEqual(grouppart) &&  grouppart.indexOf(null) != -1 ) {
                    $(this).find('.icon img').attr('src', '/static/img/2.png')
                    $(this).find('.frame>span').removeClass('active');
                } else if (!isAllEqual(grouppart)) {
                    $(this).find('.icon img').attr('src', '/static/img/1.png')
                    $(this).find('.frame>span').removeClass('active');
                } else if (isAllEqual(grouppart) && grouppart.indexOf('0') != -1) {
                    $(this).find('.frame>span:first-child').addClass('active').siblings('span').removeClass('active');
                    $(this).find('.icon img').attr('src', '')
                } else if (isAllEqual(grouppart) && grouppart.indexOf('1') != -1) {
                    $(this).find('.frame>span:last-child').addClass('active').siblings('span').removeClass('active');
                    $(this).find('.icon img').attr('src', '')
                }
            })

            var isTextArray = [];
            var isExistArray = [];
            $('.place-status').each(function () {
                var img = $(this).find('img').attr('src');
                var onOff = $(this).find('.frame>span.active').text();
                isExistArray.push(img)
                isTextArray.push(onOff)
            })

            if (isExistArray.indexOf('/static/img/2.png') != -1) {
                $('img.total-frame').attr('src', '/static/img/2.png');
            } else if (isExistArray.indexOf('/static/img/2.png') == -1 && isExistArray.indexOf('/static/img/1.png') != -1) {
                $('img.total-frame').attr('src', '/static/img/1.png');
            } else {
                $('img.total-frame').attr('src', '');
            }
            if (isAllEqual(isTextArray)) {
                if (isTextArray[0] == 'ON') {
                    $('.total-frame>span:first-child').addClass('active').siblings('span').removeClass('active');
                    $('img.total-frame').attr('src', '');
                } else if (isTextArray[0] == 'OFF') {
                    $('.total-frame>span:last-child').addClass('active').siblings('span').removeClass('active');
                    $('img.total-frame').attr('src', '');
                }
            }
        }
    })
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