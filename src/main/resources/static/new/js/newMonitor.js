/**
 * Created by yuanjie.fang on 2019/7/9.
 */
$(function () {
    $('.tabs').tabslet();
    run();
})
async function run() {
    const result1 = await init();
    console.log('巡检执行完毕');
    // const resylt2= await realTime();
    // console.log('动态数据更新执行完毕');
    const result2 = await setInterval(()=> {
        realTime();
        console.log('动态数据更新执行完毕');
    }, 1000);
}

//巡检
async function init() {
    const result1 = await myChart("main");
    const result2 = await swiper('.waterbubble', 3, 2, 'row')
    const result3 = await water();
    const result4 = await $.ajax({
        url: '/getNewMonitor',
        dataType: "json",
        async: true,
        type: "POST",
        success: function (data) {
            console.log('巡检', data);
            var centerLNumList = data.centerLNumList;
            var lightState = data.lightState;
            var placeLNumList = data.placeLNumList;
            var status = data.status;
            //排序
            // if(centerLNumList.length>0){
            //     centerLNumList = sort(centerLNumList, 'mname');
            // }
            if (placeLNumList.length > 0) {
                placeLNumList = sort(placeLNumList, 'mname');
            }
            if (lightState.length > 0) {
                lightState = sort(lightState, 'mname');

            }
            // console.log('lightState', lightState);
            //json数据格式转换调用方法
            lightState = lightStateM(lightState);
            placeLNumList = placeLNumListM(placeLNumList);
            // console.log('lightState', lightState);
            operation(lightState, placeLNumList, centerLNumList, status)
        }
    })
    // upLight("/getNewMonitor")
}


/**
 * @param {string} id - 参数id
 * @param {number} b=1 - 参数b默认值为1
 * @param {string} c=1 - 参数c有两种支持的取值
 * @returns {boolean} 返回值为true
 */
function myChart(id) {
    var myChart = echarts.init(document.getElementById(id));
// 指定图表的配置项和数据
    var option = {
        tooltip: {},
        legend: {
            data: ['能耗']
        },
        xAxis: {
            data: [0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24]
        },
        yAxis: {},
        series: [{
            name: 'KWh',
            type: 'line',
            data: [5, 20, 36, 10, 10, 20, 39, 49, 59, 70, 44, 34]
        }]
    };
    myChart.setOption(option);
}

function operation(lightState, placeLNumList, centerLNumList, status) {
    $('.content').empty();
    $('.nave ul').empty();
    var leftNav = '';
    var leftIndex = '<li class="current active"><a href="/newIndex">首页</a></li>';
    var allStatus = status.allStatus;
    var floorStatus = status.floorStatus;
    // var groupStatus=status.groupStatus;
    // var placeStatus=status.placeStatus;
    // console.log('lightState',lightState)
    // console.log('floorStatus',floorStatus)
    // console.log('groupStatus',groupStatus)
    // console.log('placeStatus',placeStatus)
    var allBtn;
    if (allStatus.length == 0) {
        allBtn = 'on';
    } else {
        allBtn = 'off';
    }
    $('.totalLight img').attr(statusM2(allBtn).imgBtn)
    $.each(lightState, function (i, item) {
        var rightList = '';
        var mname = item.mname;
        var placeList = item.placeList;
        console.log('lightState', lightState);
        $.each(placeList, function (i, item2) {
            var groupList = item2.groupList;
            // if(placeStatus.length==0){
            //     item2.placeLBtn='on';
            // }else{
            //     item2.placeLBtn='off';
            // }
            $.each(groupList, function (i, item3) {
                var lightList = item3.lightList;
                // var imgBtn;
                // var other;
                // if(groupStatus.length>0){
                //     $.each(groupStatus, function (i, item7) {
                //         if (extractNum(item7.mname) == item.mname && item7.place==item2.place) {
                //             other = item7.other;
                //         }else{
                //             other = '开';
                //         }
                //     })
                // }else{
                //     other = '开';
                // }
                // imgBtn = statusM3(other).imgBtn;
                $.each(lightList, function (i, item4) {
                    var status = item4.status;
                    // var state = statusM1(status).state;
                    // var img = statusM1(status).img;
                })
                var status = item3.groupState = jsonIsEqual(lightList, 'status');
                // var state = statusM(status).state;
                // var img = statusM(status).img;
                // var img = statusM(status, 'blue').img;
                item3.groupNum = item3.groupTotal - sum(lightList, 'status', null);
            })

            var status = item2.placeLNumState = jsonIsEqual1(groupList, 'groupState');
            var state = statusM(status).state;
            var img = statusM(status).img;
            item2.placeLNum = sum(groupList, 'groupNum', 1);
            rightList += '<div class="place f-l swiper-slide"> <div class="place-title"> ' +
                '<div>区域<span class="placeName">' + item2.place +
                '</span></div> <div>(<span class="p-num">' +
                item2.placeLNum + '</span><span>/</span><span>' +
                item2.placeLNumTotal + '</span>)</div></div> ' +
                '<div class="place-content"><div class="img-place">' + img +
                '</div><div><p class="p-status">' + state + '</p></div> </div></div>'

        })

        var status = item.centerLNumState = jsonIsEqual1(placeList, 'placeLNumState')
        var state = statusM(status).state;
        var img = statusM(status).img;
        item.centerLNum = sum(placeList, 'placeLNum', 1);
        var sumTotal = parseInt(item.centerLNumTotal) - sum(placeList, 'placeLNum', 1);
        var imgBtn;
        var other = '开';
        if (floorStatus.length > 0) {
            $.each(floorStatus, function (i, item6) {
                if (extractNum(item6.mname) == item.mname) {
                    other = item6.other;
                    return false;
                }
            })
        }
        imgBtn = statusM3(other).imgBtn;

        //右侧数据展示
        var left = '<div class="on-off f-l"><div class="clearfix btn"><div class="f-l mname">' + mname + '</div>' +
            ' <div class="f-l"> <span>故障：</span><span>' + sumTotal + '</span> </div> ' +
            '<div class="f-l"> <div class="img toggle-button centerL-btn click-btn" >' + imgBtn +
            '<div class="min-font">开关</div> </div> </div> </div> </div>';
        var right = '<div class="light-list f-l  "><div class="swiper-container light-swiper"><div class="swiper-wrapper clearfix ">' + rightList + '<div                          class="swiper-button-prev swiper-button-black"></div><div class="swiper-button-next swiper-button-black"></div></div></div></div>';
        var content = '<div class="clearfix">' + left + right + '</div>';
        $('.content').append(content);

        //左侧导航
        leftNav += '<li><a href="javascript:void(0);"><div class="clearfix"><div class="f-l p-r">' +
            '<div class="nav-l p-a"><div class="floor">实验室-<span>' + item.mname + '</span></div>' +
            '<div class="switch-hint">(<span class=" center-LNum">' + item.centerLNum + '</span> / <span class="">' + item.centerLNumTotal + '</span>)</div>' +
            '</div></div><div class="f-l p-r"><div class="nav-r p-a"><div class="left-img">' +
            img + '</div><div class="switch-hint">' + state + '</div></div></div></div></a></li>';
    })

    $('.nave ul').append(leftIndex + leftNav);
    swiper('.light-swiper', 4)

    if (placeLNumList.length > 0) {
        $.each(placeLNumList, function (i, item) {
            $('.content>div.clearfix').each(function () {
                var that = $(this);
                if (item.mname == $(this).find('.mname').text()) {
                    var placeList = item.placeList;
                    $.each(placeList, function (j, item2) {
                        that.find('.place').each(function () {
                            if (item2.place == $(this).find('.placeName').text()) {
                                $(this).find('.p-num').text(item2.placeLNum);
                            }
                        })
                    })
                }
            })
        })
    } else {
        console.log('placeLNumList长度小于0')
    }
// console.log('现在的lightState',lightState);
    if (centerLNumList.length > 0) {
        $.each(centerLNumList, function (i, item) {
            var mname = extractNum(item.mname);
            $('.nave li').each(function () {
                var floor = extractNum($(this).find('.floor').text());
                if (mname == floor) {
                    if (item.centerLNum) {
                        //初始化
                        $(this).find('.center-LNum').text(item.centerLNum);
                    } else {
                        //更新
                        var sum = 0;
                        $('.content>.clearfix').each(function () {
                            if ($(this).find('.mname').text() == item.mname) {
                                $(this).find('.place').each(function () {
                                    var num = parseInt($(this).find('.p-num').text());
                                    sum += num;

                                })
                            }
                        })
                        $(this).find('.center-LNum').text(sum);
                    }
                }
            })
        })
    } else {
        console.log('centerLNumList长度小于0')
    }
    var sum1 = 0;
    $('.content>.clearfix').each(function () {
        var onStatus = $(this).find('.centerL-btn img').attr('src');
        console.log('onStatus', onStatus);
        if (onStatus.indexOf('on') != -1) {
            sum1++;
        }
    })
    if (sum1 > 0) {
        var imgBtn = statusM3('开').imgBtn;
        $('.totalLight img').replaceWith(imgBtn);
    } else {
        var imgBtn = statusM3('关').imgBtn;
        $('.totalLight img').replaceWith(imgBtn);
    }
}

//更新开关状态方法
async function realTime() {
    const result1 = await $.ajax({
        url: '/getNewMonitorLightStatus',
        dataType: "json",
        async: true,
        type: "POST",
        success: function (data) {
            console.log('更新', data);
            if (data.lightDemo && data.lightDemo[0].other != null) {
                var lightDemo = data.lightDemo;
                operation2(data, lightDemo)
            }
        }
    })
}

function operation2(data, lightDemo) {
    var other = lightDemo[0].other;
    var sum = 0;
    if (other == 'group') {
        var mname = lightDemo[0].mname;
        var groupId = lightDemo[0].groupId;
        var status = lightDemo[0].status;
        var img = statusM1(status, 1).imgBtn;
        // if (extractNum(mname) == floor) {
        $('.content>.clearfix').each(function () {
            $(this).find('.place').each(function () {
                var txt = extractNum($(this).find('.max').text());
                if (txt == groupId) {
                    $(this).find('.place-content li').each(function () {
                        $(this).find('img').replaceWith(img);
                        if (status == 1 || status == null) {
                            $(this).find('.yellow').text('');
                            $(this).find('.yellow').addClass('off');
                        }
                    })
                }
            })
        })
    } else if (other == 'floor') {
        var mname = lightDemo[0].mname;
        // var groupId = lightDemo[0].groupId;
        var status = lightDemo[0].status;
        var imgBtn = statusM1(status).imgBtn;


        if (mname == 'all') {
            $('.content>.clearfix').each(function () {
                var floor = $(this).find('.on-off .mname').text();
                $('.totalLight img').replaceWith(imgBtn);
                $(this).find('.on-off img').replaceWith(imgBtn);
                $(this).find('.place').each(function () {
                    var length=$(this).find('.img-place').children('img').length;
                    if(length==2){
                        $(this).find('.img-place').children('img').each(function(){
                            var unlikeImg = $(this).attr('src');
                            if (unlikeImg.indexOf('switch-un')!=-1){
                                $(this).replaceWith('');
                            }
                        })
                        $(this).find('.p-status').text('异常');
                    }else if(length==1){
                        var unlikeImg = $(this).find('.img-place img').attr('src');
                        if(unlikeImg.indexOf('switch-un')!=-1){
                            var img = '<img src="/static/new/img/normal.png" alt="">';
                            $(this).find('.img-place img').replaceWith(img);
                            $(this).find('.p-status').text('正常');
                        }

                    }

                })
            })
        } else {

            $('.content>.clearfix').each(function () {
                var floor = $(this).find('.on-off .mname').text();
                if ((floor == mname)) {
                    $(this).find('.on-off img').replaceWith(imgBtn);
                    $(this).find('.place').each(function () {
                        var length=$(this).find('.img-place').children('img').length;
                        if(length==2){
                            $(this).find('.img-place').children('img').each(function(){
                               var unlikeImg = $(this).attr('src');
                                if (unlikeImg.indexOf('switch-un')!=-1){
                                    $(this).replaceWith('');
                                }
                            })
                            $(this).find('.p-status').text('异常');
                        }else if(length==1){
                            var unlikeImg = $(this).find('.img-place img').attr('src');
                            if(unlikeImg.indexOf('switch-un')!=-1){
                                var img = '<img src="/static/new/img/normal.png" alt="">';
                                $(this).find('.img-place img').replaceWith(img);
                                $(this).find('.p-status').text('正常');
                            }

                        }

                    })
                }
            })
        }


    }
    if (data.floorStatus) {
        var floorStatus = data.floorStatus;
        var floorMname = floorStatus.mname;
        $('.content>.clearfix').each(function () {
            var floor = $(this).find('.on-off .mname').text();
            if (extractNum(floorMname) == extractNum(floor)) {
                var floorOther = floorStatus.other;
                var imgBtn = statusM3(floorOther).imgBtn;
                console.log('floor', floor, floorMname, floorOther, imgBtn)
                $(this).find('.centerL-btn img').replaceWith(imgBtn);
            }
        })
    }

    $('.content>.clearfix').each(function () {
        var onStatus = $(this).find('.centerL-btn img').attr('src');
        console.log('onStatus', onStatus);
        if (onStatus.indexOf('on') != -1) {
            sum++;
        }
    })
    console.log('sum', sum);
    if (sum > 0) {
        var imgBtn = statusM3('开').imgBtn;
        $('.totalLight img').replaceWith(imgBtn);
    } else {
        var imgBtn = statusM3('关').imgBtn;
        $('.totalLight img').replaceWith(imgBtn);
    }
}

function water() {
    var pDefault;
    waterbubbleS('#floor-1', '30%', pDefault, pDefault, 22, 0.3, 2.5, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-2', '30%', pDefault, pDefault, 22, 0.3, 2.5, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-3', '30%', pDefault, pDefault, 22, 0.3, 2.5, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-4', '30%', pDefault, pDefault, 22, 0.3, 2.5, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-5', '30%', pDefault, pDefault, 22, 0.3, 2.5, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-6', '30%', pDefault, pDefault, 22, 0.3, 2.5, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-7', '30%', pDefault, pDefault, 22, 0.3, 2.5, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-8', '30%', pDefault, pDefault, 22, 0.3, 2.5, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-9', '60%', pDefault, pDefault, 22, 0.6, 2.5, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-10', '40%', pDefault, pDefault, 26, 0.4, 2.5, pDefault, pDefault, pDefault)
    waterbubbleS('#todayElectric', '136.05KWh', pDefault, pDefault, 40, 0.6, 3, pDefault, '12px arial', pDefault)
    waterbubbleS('#yesterdayElectric', '106.05KWh', pDefault, pDefault, 40, 0.45, 3, pDefault, '12px arial', pDefault)
}
//单楼层总开总关
$(".content").on('click', ".centerL-btn", function () {
    var src = $(this).children().attr('src');
    var state = $(this).children();
    src = src.substring(src.lastIndexOf("-") + 1, src.lastIndexOf("."));
    var centerOrder = $(this).parent().parent().parent().find('.mname').text();
    centerOrder = centerOrder.substring(0, 1);
    var host = getHostByFloor(centerOrder);

    if (src == "off") {
        var command = '77010315373766';
    } else if (src == "on") {
        var command = '77010315323266';
    }
    $.post("/sendSocket6", {
        "command": command,
        "host": host
    }, function (msg) {
        if (msg.success == 'success') {
            // console.log(state)
            if (src == "off") {
                $(state).attr('src', '/static/new/img/light-on.PNG');
            } else if (src == "on") {
                $(state).attr('src', '/static/new/img/light-off.PNG');
            }
        }
    })
});
//总楼层开关
$(".p-a.middle").on('click', ".pointer", function () {
    var src = $(this).children().attr('src');
    var state = $(this).children();
    src = src.substring(src.lastIndexOf("-") + 1, src.lastIndexOf("."));
    var host = 'all';
    if (src == "off") {
        var command = '77010315373766';
    } else if (src == "on") {
        var command = '77010315323266';
    }
    $.post("/sendSocket6", {
        "command": command,
        "host": host
    }, function (msg) {
        if (msg.success == 'success') {
            // console.log(state)
            if (src == "off") {
                $(state).attr('src', '/static/new/img/light-on.PNG');
            } else if (src == "on") {
                $(state).attr('src', '/static/new/img/light-off.PNG');
            }
        }
    })
})
