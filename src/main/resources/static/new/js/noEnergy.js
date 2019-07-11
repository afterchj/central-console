/**
 * Created by yuanjie.fang on 2019/7/10.
 */
$(function () {
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
function init() {
    var pDefault;
    swiper('.light-hint-list .swiper-container', 3, 1, 'row')

    waterbubbleS('#floor-1', '30%', pDefault, pDefault, 26, 0.3, pDefault, '#FF4646', pDefault, pDefault)
    waterbubbleS('#floor-2', '30%', pDefault, pDefault, 26, 0.3, pDefault, '#FF4646', pDefault, pDefault)
    waterbubbleS('#floor-3', '30%', pDefault, pDefault, 26, 0.3, pDefault, '#FF4646', pDefault, pDefault)
    waterbubbleS('#floor-4', '30%', pDefault, pDefault, 26, 0.3, pDefault, '#FF4646', pDefault, pDefault)
    waterbubbleS('#floor-5', '30%', pDefault, pDefault, 26, 0.3, pDefault, '#FF4646', pDefault, pDefault)
    // waterbubbleS('#floor-6', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    // waterbubbleS('#floor-7', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    // waterbubbleS('#floor-8', '30%', pDefault, pDefault, 26, 0.3, pDefault, pDefault, pDefault, pDefault)
    // waterbubbleS('#floor-9', '60%', pDefault, pDefault, 26, 0.6, pDefault, pDefault, pDefault, pDefault)
    // waterbubbleS('#floor-10', '40%', pDefault, pDefault, 26, 0.4, pDefault, pDefault, pDefault, pDefault)
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
            operation(lightState, placeLNumList, centerLNumList, '1楼')
        }
    })
}

function operation(lightState, placeLNumList, centerLNumList, fmname) {
    $('.content').empty();
    $('.nave ul').empty();
    var leftNav = '';
    var leftIndex = '<li class="current active"><a href="/newIndex">首页</a></li>';
    $.each(lightState, function (i, item) {
        var centerLNum = item.centerLNum;
        var centerLNumTotal = item.centerLNumTotal;
        var mname = item.mname;
        var placeList = item.placeList;
        console.log('placeList', placeList);
        console.log('lightState', lightState);

        if (item.mname == fmname) {
            $.each(placeList, function (i, item2) {
                var groupList = item2.groupList;
                console.log('groupList3',groupList)
                //右侧数据展示

                var rightList = '';
                for (var j = 0; j < groupList.length; j++) {
                    var lightList = groupList[j].lightList;
                    var lightContent = '';
                    $.each(lightList, function (i, item3) {
                        // var status = item3.status = jsonIsEqual(lightList, 'status');
                        // var state = statusM(status).state;
                        var status = item3.status;

                        var state = statusM1(status).state;
                        var img = statusM1(status).img;
                        // var status = item3.status;
                        var warning = statusM1(status).warning;
                        // var img = statusM1(status, 'blue').img;
                        console.log('status',status,'state',state,'img',img);
                        var hint = '';
                        if (warning) {
                            hint = 'active';
                        } else {
                            hint = '';
                        }
                        lightContent += '<li class="clearfix"> <div class="f-l p-r r-min-line"><div class="middle p-a ">' + item3.lname + '</div></div><div class="f-l p-r r-min-line"><div class="middle p-a yellow ' + hint + '">' + item3.y + '</div></div>' +
                            ' <div class="f-l p-r"><div class="middle p-a"><img src="/static/new/img/on-off-black.png" alt=""></div></div></li>';
                    })

                    var status = groupList[j].groupState = jsonIsEqual(lightList, 'status');
                    var state = statusM(status).state;
                    var img = statusM(status, 'blue').img;
                    if(status==0 || status==1){
                        groupList[j].groupNum=groupList[j].groupTotal;
                    }else if(status == 12 || status==2){
                      var sumTotal=  sum(lightList,'status',12)+sum(lightList,'status',2);
                       groupList[j].groupNum=  parseInt(groupList[j].groupTotal)-sumTotal;
                    }

                    var title = '<div class="place-title"><div class="clearfix "><div class="f-l p-r r-line"><div class="middle p-a "><p class="max">组' + groupList[j].group + '</p>' +
                        '<p>(<span>' + groupList[j].groupNum + '</span>/ <span>' + groupList[j].groupTotal + '</span>)</p></div></div><div class="f-l p-r r-line"><div class="middle p-a"><p>' + img + '</p></div>' +
                        '</div><div class="f-l p-r"> <div class="middle p-a min"><p><img src="/static/new/img/on-off-white.png" alt=""></p> <p>开关</p></div></div></div></div>';

                    rightList += '<div class="place f-l swiper-slide">' + title + '<div class="place-content"><ul>' + lightContent + '</ul></div></div>';

                }
                var status = item2.placeLNumState = jsonIsEqual1(groupList, 'groupState');
                var state = statusM(status).state;
                var img = statusM(status, 'blue').img;
                if(status==0 || status==1){
                    item2.placeLNum=item2.placeLNumTotal;
                }else if(status == 12 || status==2){
                    var sumTotal=  sum(groupList,'status',12)+sum(groupList,'status',2);
                    item2.placeLNum=  parseInt(item2.placeLNumTotal)-sumTotal;
                }
                var right = ' <div class="light-list f-l "> <div class="swiper-container light-swiper"> <div class="swiper-wrapper clearfix ">' +
                    rightList + ' <div class="swiper-button-prev"></div><div class="swiper-button-next"></div></div></div></div>';


                var left = '<div class="on-off f-l"><div class="clearfix btn green "><div class="f-l p-r"><div class="pp-num middle p-a "><p class="mname">区域' + item2.place + '</p><p>(<span class="place-LNum1">' + item2.placeLNum + '</span>/ <span>' + item2.placeLNumTotal + '</span>)</p></div></div>' +
                    ' <div class="f-l"> <span>故障：</span><span class="error">'+sumTotal+'</span> </div> ' +
                    '<div class="f-l"> <div class="img"> <img src="/static/new/img/on-off-white.png" alt=""> ' +
                    '<div class="min-font">开关</div> </div> </div> </div> </div>';
                var content = '<div class="clearfix">' + left + right + '</div>';
                $('.content').append(content);
            })
            swiper('.light-swiper', 3, 1, 'row')
        }else{
            $.each(placeList, function (i, item2) {
                var groupList = item2.groupList;
                // console.log('groupList3',groupList)
                //右侧数据展示
                // var left = '<div class="on-off f-l"><div class="clearfix btn green "><div class="f-l p-r"><div class="pp-num middle p-a "><p class="mname">区域' + item2.place + '</p><p>(<span class="place-LNum1">' + item2.placeLNum + '</span>/ <span>' + item2.placeLNumTotal + '</span>)</p></div></div>' +
                //     ' <div class="f-l"> <span>故障：</span><span class="error">0</span> </div> ' +
                //     '<div class="f-l"> <div class="img"> <img src="/static/new/img/on-off-white.png" alt=""> ' +
                //     '<div class="min-font">开关</div> </div> </div> </div> </div>';
                // var rightList = '';
                for (var j = 0; j < groupList.length; j++) {
                    var lightList = groupList[j].lightList;
                    var lightContent = '';
                    $.each(lightList, function (i, item3) {
                        // var status = item3.status = jsonIsEqual(lightList, 'status');
                        // var state = statusM(status).state;
                        var status = item3.status;
                        var warning = statusM(status).warning;
                        var img = statusM(status, 'blue').img;
                        // var hint = '';
                        // if (warning) {
                        //     hint = 'active';
                        // } else {
                        //     hint = '';
                        // }
                        // lightContent += '<li class="clearfix"> <div class="f-l p-r r-min-line"><div class="middle p-a ">' + item3.lname + '</div></div><div class="f-l p-r r-min-line"><div class="middle p-a yellow ' + hint + '">' + item3.y + '</div></div>' +
                        //     ' <div class="f-l p-r"><div class="middle p-a"><img src="/static/new/img/on-off-black.png" alt=""></div></div></li>';
                    })
                    var status = groupList[j].groupState = jsonIsEqual1(lightList, 'status');
                    var state = statusM(status).state;
                    var img = statusM(status, 'blue').img;
                    if(status==0 || status==1){
                        groupList[j].groupNum=groupList[j].groupTotal;
                    }else if(status == 12 || status==2){
                        var sumTotal=  sum(lightList,'status',12)+sum(lightList,'status',2);
                        groupList[j].groupNum=  parseInt(groupList[j].groupTotal)-sumTotal;
                    }
                    // var title = '<div class="place-title"><div class="clearfix "><div class="f-l p-r r-line"><div class="middle p-a "><p class="max">组' + groupList[j].group + '</p>' +
                    //     '<p>(<span>' + groupList[j].groupNum + '</span>/ <span>' + groupList[j].groupTotal + '</span>)</p></div></div><div class="f-l p-r r-line"><div class="middle p-a"><p>' + img + '</p></div>' +
                    //     '</div><div class="f-l p-r"> <div class="middle p-a min"><p><img src="/static/new/img/on-off-white.png" alt=""></p> <p>开关</p></div></div></div></div>';
                    //
                    // rightList += '<div class="place f-l swiper-slide">' + title + '<div class="place-content"><ul>' + lightContent + '</ul></div></div>';

                }
                var status = item2.placeLNumState = jsonIsEqual1(groupList, 'groupState');
                var state = statusM(status).state;
                var img = statusM(status, 'blue').img;
                if(status==0 || status==1){
                    item2.placeLNum=item2.placeLNumTotal;
                }else if(status == 12 || status==2){
                    var sumTotal=  sum(groupList,'status',12)+sum(groupList,'status',2);
                    item2.placeLNum=  parseInt(item2.placeLNumTotal)-sumTotal;
                }
                // var right = ' <div class="light-list f-l "> <div class="swiper-container light-swiper"> <div class="swiper-wrapper clearfix ">' +
                //     rightList + ' <div class="swiper-button-prev"></div><div class="swiper-button-next"></div></div></div></div>';
                // var content = '<div class="clearfix">' + left + right + '</div>';
                // $('.content').append(content);
            })
            // swiper('.light-swiper', 3, 1, 'row')
        }
        //左侧导航
        var status = item.centerLNumState = jsonIsEqual1(placeList, 'placeLNumState')
        var state = statusM(status).state;
        var img = statusM(status).img;
        console.log(item.mname+img)
        if(status==0 || status==1){
            item.centerLNum=item.centerLNumTotal;
        }else if(status == 12 || status==2){
            var sumTotal=  sum(placeList,'placeLNumState',12)+sum(placeList,'placeLNumState',2);
            item.centerLNum=  parseInt(item.centerLNumTotal)-sumTotal;
        }
        leftNav += '<li><a href="/newIndex/noEnergy"><div class="clearfix"><div class="f-l p-r">' +
            '<div class="nav-l p-a"><div class="floor">实验室-<span>' + item.mname + '</span></div>' +
            '<div class="switch-hint">(<span class=" center-LNum">' + item.centerLNum + '</span> / <span class="">' + item.centerLNumTotal + '</span>)</div>' +
            '</div></div><div class="f-l p-r"><div class="nav-r p-a"><div class="left-img">' +
            img + '</div><div class="switch-hint">' + state + '</div></div></div></div></a></li>';

    })
    $('.nave ul').append(leftIndex + leftNav);

    console.log('placeLNumList44', placeLNumList);
    if (placeLNumList.length > 0) {
        $.each(placeLNumList, function (i, item) {
            if (item.mname == fmname) {
                var placeList = item.placeList;
                $('.content>div.clearfix').each(function () {
                    var that = $(this);
                    $.each(placeList,function (i,item2) {
                        if(item2.place==extractNum(that.find('.mname').text())){
                            that.find('.place-LNum1').text(item2.placeLNum);
                        }
                    })
                })
            }
        })
    } else {
        console.log('placeLNumList长度小于0')
    }
    console.log('现在的centerLNumList',centerLNumList);
    if (centerLNumList.length > 0) {
        $.each(centerLNumList, function (i, item) {
            var mname = item.mname;
            $('.nave li').each(function () {
                var floor = $(this).find('.floor span').text();
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