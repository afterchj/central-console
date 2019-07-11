/**
 * Created by yuanjie.fang on 2019/7/9.
 */
$(function () {
    $('.tabs').tabslet();
    init()
    // setInterval(function () {
    //     realTime()
    // }, 500)
})

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
function init() {
    var pDefault;
    myChart("main");
    swiper('.waterbubble', 3, 2, 'row')
    waterbubbleS('#floor-1', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-2', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-3', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-4', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-5', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-6', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-7', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-8', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-9', '60%', pDefault, pDefault, 26, 0.6, pDefault, pDefault, pDefault, pDefault)
    waterbubbleS('#floor-10', '40%', pDefault, pDefault, 26, 0.4, pDefault, pDefault, pDefault, pDefault)
    upLight("/getNewMonitor")
}
function realTime() {
    upLight("/getNewMonitorLightStatus")
}

function upLight(url) {
    $.ajax({
        url: url,
        dataType: "json",
        async: true,
        type: "POST",
        success: function (data) {
            console.log('data1', data);
            var centerLNumList = data.centerLNumList;
            var lightState = data.lightState;
            var placeLNumList = data.placeLNumList;
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

            //json数据格式转换调用方法
            lightState = lightStateM(lightState);
            placeLNumList = placeLNumListM(placeLNumList);
            operation(lightState, placeLNumList, centerLNumList)
        }
    })
}

function operation(lightState, placeLNumList, centerLNumList) {
    $('.content').empty();
    $('.nave ul').empty();
    var leftNav = '';
    var leftIndex = '<li class="current active"><a href="/newIndex">首页</a></li>';
    console.log('lightState44', lightState)
    $.each(lightState, function (i, item) {
        var centerLNum = item.centerLNum;
        var centerLNumTotal = item.centerLNumTotal;
        var mname = item.mname;
        var placeList = item.placeList;

        //右侧数据展示
        var left = '<div class="on-off f-l"><div class="clearfix btn"><div class="f-l mname">' + mname + '</div>' +
            ' <div class="f-l"> <span>故障：</span><span>0</span> </div> ' +
            '<div class="f-l"> <div class="img"> <img src="/static/new/img/on-off-black.png" alt=""> ' +
            '<div class="min-font">开关</div> </div> </div> </div> </div>';
        var rightList = '';
        for (var j = 0; j < placeList.length; j++) {
            var groupList = placeList[j].groupList;
            for (var k = 0; k < groupList.length; k++) {
                var lightList = groupList[k].lightList;
                $.each(lightList, function (i, item3) {
                    var status = item3.status;
                    var state = statusM1(status).state;
                    var img = statusM1(status).img;
                })
                var status = groupList[k].groupState = jsonIsEqual(lightList, 'status');
                var state = statusM(status).state;
                if(status==0 || status==1){
                    groupList[k].groupNum=groupList[k].groupTotal;
                }else if(status == 12 || status==2){
                    var sumTotal=  sum(lightList,'status',12)+sum(lightList,'status',2);
                    groupList[k].groupNum=  parseInt(groupList[k].groupTotal)-sumTotal;
                }
            }

        var status = placeList[j].placeLNumState = jsonIsEqual1(groupList, 'groupState');
        var state = statusM(status).state;
        var img = statusM(status).img;
            if(status==0 || status==1){
                placeList[j].placeLNum=placeList[j].placeLNumTotal;
            }else if(status == 12 || status==2){
                var sumTotal=  sum(groupList,'status',12)+sum(groupList,'status',2);
                placeList[j].placeLNum=  parseInt(placeList[j].placeLNumTotal)-sumTotal;
            }
        rightList += '<div class="place f-l swiper-slide"> <div class="place-title"> ' +
            '<div>区域<span class="placeName">' + placeList[j].place + '</span></div> <div>(<span class="p-num">' + placeList[j].placeLNum + '</span><span>/</span><span>' + placeList[j].placeLNumTotal + '</span>)</div></div> ' +
            '<div class="place-content"><div class="img-place">' + img + '</div><div><p class="p-status">' + state + '</p></div> </div></div>'

        }
        // console.log(' rightList', rightList);
        var status = item.centerLNumState = jsonIsEqual1(placeList, 'placeLNumState')
        var state = statusM(status).state;
        var img = statusM(status).img;
        if(status==0 || status==1){
            item.centerLNum=item.centerLNumTotal;
        }else if(status == 12 || status==2){
            var sumTotal=  sum(placeList,'placeLNumState',12)+sum(placeList,'placeLNumState',2);
            item.centerLNum=  parseInt(item.centerLNumTotal)-sumTotal;
        }
    var right = '<div class="light-list f-l  "><div class="swiper-container light-swiper"><div class="swiper-wrapper clearfix ">' + rightList + '<div class="swiper-button-prev"></div><div class="swiper-button-next"></div></div></div></div>';
    var content = '<div class="clearfix">' + left + right + '</div>';
    $('.content').append(content);

    //左侧导航
    // var status = item.centerLNumState = jsonIsEqual1(placeList, 'placeLNumState')
    // var state = statusM(status).state;
    // var img = statusM(status).img;

    leftNav += '<li><a href="/newIndex/noEnergy"><div class="clearfix"><div class="f-l p-r">' +
        '<div class="nav-l p-a"><div class="floor">实验室-' + mname + '</div>' +
        '<div class="switch-hint">(<span class=" center-LNum">' + centerLNum + '</span> / <span class="">' + centerLNumTotal + '</span>)</div>' +
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
                    console.log('初始化')
                    $(this).find('.center-LNum').text(item.centerLNum);
                } else {
                    //更新
                    console.log('更新')
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
}


