/**
 * Created by yuanjie.fang on 2019/7/10.
 */
$(function () {
     run()
    // setInterval(function () {
    //     realTime();
    // },500)
    // setInterval(function () {
    //     init();
    // }, 100000)
    // setTimeout(function () {
    //     realTime();
    // }, 500)
})
// async function run(){
//     const resylt1= await init();
//     console.log('巡检执行完毕');
//     const resylt2= await realTime();
//     console.log('动态数据更新执行完毕');
//     // const resylt2= await setInterval(()=>{
//     //     realTime();
//     // console.log('动态数据更新执行完毕');
// // },1000);
// }
async function run(){
    const resylt1= await init();
    console.log('巡检执行完毕');
    // const resylt2= await realTime();
    // console.log('动态数据更新执行完毕');
    // const resylt2= await setInterval(()=>{
    // realTime();
    // console.log('动态数据更新执行完毕');
// },1000);
}
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
    upLight("/getNewMonitor")
}
// function lightAlter(other, status) {
//     $.each(lightState, function (i, item1) {
//         var placeList = item1.placeList;
//         $('.content>.clearfix').each(function () {
//             var that = $(this);
//             if (item1.mname == floor) {
//                 $.each(placeList, function (i, item2) {
//                     var groupList = item2.groupList;
//                     if (item2.place == extractNum(that.find('.mname').text())) {
//                         $.each(groupList, function (i, item3) {
//                             var lightList = item3.lightList;
//
//                             that.find('.place').each(function () {
//                                 var that2 = $(this);
//                                 var status = item3.groupState = jsonIsEqual(lightList, 'status');
//                                 var state = statusM(status).state;
//                                 var img = statusM(status, 'blue').img;
//                                 item3.groupNum = parseInt(item3.groupTotal) - sum(lightList, 'status', null);
//                                 if (item3.groupId == extractNum($(this).find('.max').text())) {
//                                     $.each(lightList, function (i, item4) {
//                                         // var status = item4.status;
//                                         var y = item4.y;
//                                         item4.status = statusAll;
//                                         that2.find('.place-content>ul>li').each(function () {
//                                             if (item4.lname == extractNum($(this).find('.light-name').text())) {
//                                                 $(this).find('.yellow').text(item4.y);
//                                             }
//                                         })
//                                     })
//                                 }
//                             })
//                         })
//                     }
//                 })
//             }
//         })
//     })
// }
//更新开关状态方法
function realTime() {
    $.ajax({
        url: '/getNewMonitorLightStatus',
        dataType: "json",
        async: true,
        type: "POST",
        success: function (data) {
            console.log('更新', data);
            if (data.lightDemo) {
                if (data.lightDemo.other != null) {
                    var lightDemo = data.lightDemo;
                    var other = data.lightDemo.other;
                    // lightState = sort(lightState, 'mname');
                    // lightState=sort(lightState, 'lname');
                    // lightState = lightStateM(lightState);
                    var floor = getUrlParams('floor');
                    // console.log('floor',floor);
                    operation2(lightDemo, other, floor)
                }
            }
        }
    })
}
function operation2(lightDemo, other, floor) {
    if (other == 'all') {
        var status = lightDemo.status;
        var img = statusM1(status, 1).imgBtn;
        $('.content>.clearfix').each(function () {
            $(this).find('.place').each(function () {
                $(this).find('.place-content li').each(function () {
                    $(this).find('img').replaceWith(img);
                    if (status == 1 || status == null) {
                        $(this).find('.yellow').text('');
                        $(this).find('.yellow').addClass('off');
                    }
                })
            })
        })
    } else if (other == 'group') {
        var mname = lightDemo.mname;
        var groupId = lightDemo.groupId;
        var status = lightDemo.status;
        var img = statusM1(status, 1).imgBtn;
        if (extractNum(mname) == floor) {
            $('.content>.clearfix').each(function () {
                $(this).find('.place').each(function () {
                    var txt = extractNum($(this).find('.max').text());
                    if (txt == groupId) {
                        $(this).find('.group-btn img').replaceWith(img);
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
        }
    }
}
function upLight(url) {
    $.ajax({
        url: url,
        dataType: "json",
        async: true,
        type: "POST",
        success: function (data) {
            console.log('data原始', data);
            var centerLNumList = data.centerLNumList;
            var lightState = data.lightState;
            var placeLNumList = data.placeLNumList;
            var status=data.status;
            // var allStatus=status.allStatus;
            // var floorStatus=status.floorStatus;
            // var groupStatus=status.groupStatus;
            // var placeStatus=status.placeStatus;


            //排序
            // if(centerLNumList.length>0){
            //     centerLNumList = sort(centerLNumList, 'mname');
            // }
            if (placeLNumList.length > 0) {
                placeLNumList = sort(placeLNumList, 'mname');
            }
            if (lightState.length > 0) {
                lightState = sort(lightState, 'mname');
                // lightState=sort(lightState, 'lname');
            }

            var floor = getUrlParams('floor');
            console.log('floor', floor);
            //json数据格式转换调用方法
            lightState = lightStateM(lightState);
            placeLNumList = placeLNumListM(placeLNumList);
            // console.log('data原始lightState ', lightState);
            // console.log('data原始placeLNumList ', placeLNumList);
            operation(lightState, placeLNumList, centerLNumList, floor,status)
        }
    })
}

function operation(lightState, placeLNumList, centerLNumList, fmname,status) {
    $('.content').empty();
    $('.nave ul').empty();
    var allStatus=status.allStatus;
    var floorStatus=status.floorStatus;
    var groupStatus=status.groupStatus;
    // groupStatus=treeData(groupStatus);
    var placeStatus=status.placeStatus;
    var nowFloorNum;
    var nowFloorNumTotal;
    var nowFloorState;
    console.log('lightState',lightState);
    console.log('allStatus',allStatus);
    console.log('floorStatus',floorStatus);
    console.log('groupStatus',groupStatus);
    console.log('placeStatus',placeStatus);
    var leftNav = '';
    var leftIndex = '<li class="current active"><a href="/newIndex">首页</a></li>';
    $.each(lightState, function (i, item) {

        var mname = item.mname;
        var placeList = item.placeList;
        if (extractNum(item.mname) == fmname) {
            $.each(placeList, function (i, item2) {
                var groupList = item2.groupList;
                //右侧数据展示
                var rightList = '';
                $.each(groupList, function (i, item3) {
                    var lightList = item3.lightList;
                    var lightContent = '';
                    $.each(lightList, function (i, item4) {
                        var status = item4.status;
                        var img = statusM1(status).imgBtn;
                        var y = item4.y == "-60%" ? '' : item4.y;

                        if (status == 1 || status == null) {
                            item4.lightBtn = 'off';
                        } else {
                            item4.lightBtn = 'on';
                        }
                        var hint = '';
                        if (status == null) {
                            hint = 'hint';
                        } else {
                            hint = '';
                        }
                        var off = '';
                        if (status == 1) {
                            off = 'off';
                        } else {
                            off = '';
                        }
                        lightContent += '<li class="clearfix"> <div class="f-l p-r r-min-line"><div class="middle p-a light-name">灯' + item4.lname + '</div></div><div class="f-l p-r r-min-line"><div class="middle p-a yellow ' + hint + off + '">' + y + '</div></div>' +
                            ' <div class="f-l p-r"><div class="middle p-a light-btn click-btn" >' + img + '</div></div></li>';
                    })

                    var status = item3.groupState = jsonIsEqual(lightList, 'status');
                    var state = statusM(status).state;
                    var img = statusM(status, 'blue').img;
                    item3.groupNum = parseInt(item3.groupTotal) - sum(lightList, 'status', null);
                    var imgBtn;
                    var other;
                    $.each(groupStatus, function (i, item4) {
                        if (item4.groupId == item3.groupId) {
                            other = item4.other;
                        }else{
                            other = '开';
                        }
                    })
                    imgBtn = statusM3(other).imgBtn;
                    var title = '<div class="place-title"><div class="clearfix "><div class="f-l p-r r-line"><div class="middle p-a "><p class="max">组' + item3.groupId + '</p>' +
                        '<p>(<span>' + item3.groupNum + '</span>/ <span>' + item3.groupTotal + '</span>)</p></div></div><div class="f-l p-r r-line"><div class="middle p-a"><p>' + img + '</p></div>' +
                        '</div><div class="f-l p-r"> <div class="middle p-a min"><p class="group-btn click-btn" alt="' + state + '">' + imgBtn + '</p> <p>开关</p></div></div></div></div>';

                    rightList += '<div class="place f-l swiper-slide">' + title + '<div class="place-content"><ul>' + lightContent + '</ul></div></div>';

                })
                var status = item2.placeLNumState = jsonIsEqual1(groupList, 'groupState');
                var img = statusM(status, 'blue').img;
                item2.placeLNum = sum(groupList, 'groupNum', 1);
                var sumTotal = parseInt(item2.placeLNumTotal) - sum(groupList, 'groupNum', 1);
                var imgBtn;
                var other;
                $.each(placeStatus, function (i, item5) {
                    if (extractNum(item5.mname) == fmname && item5.place == item2.place) {
                         other = item5.other;

                    }else{
                        other = '开';
                    }
                })
                imgBtn = statusM3(other).imgBtn;
                    var right = ' <div class="light-list f-l "> <div class="swiper-container light-swiper"> <div class="swiper-wrapper clearfix ">' +
                        rightList + ' <div class="swiper-button-prev"></div><div class="swiper-button-next"></div></div></div></div>';


                        var left = '<div class="on-off f-l"><div class="clearfix btn green "><div class="f-l p-r"><div class="pp-num middle p-a "><p class="mname">区域' + item2.place + '</p><p>(<span class="place-LNum1">' + item2.placeLNum + '</span>/ <span>' + item2.placeLNumTotal + '</span>)</p></div></div>' +
                            ' <div class="f-l"> <span>故障：</span><span class="error">' + sumTotal + '</span> </div> ' +
                            '<div class="f-l"> <div class="img place-btn click-btn">  ' +
                            imgBtn +
                            '<div class="min-font">开关</div> </div> </div> </div> </div>';
                        var content = '<div class="clearfix">' + left + right + '</div>';
                        $('.content').append(content);
                swiper('.light-swiper', 3)
            })


        }else{
            $.each(placeList, function (i, item2) {
                var groupList = item2.groupList;
                $.each(groupList, function (i, item3) {
                    var lightList = item3.lightList;
                    $.each(lightList, function (i, item4) {
                        item4.status;
                    })
                    item3.groupState = jsonIsEqual(lightList, 'status');
                    item3.groupNum = parseInt(item3.groupTotal) - sum(lightList, 'status', null);
                })
                item2.placeLNumState = jsonIsEqual1(groupList, 'groupState');
                item2.placeLNum = sum(groupList, 'groupNum', 1);
            })
        }
        $.each(floorStatus, function (i, item6) {
            var other;
            if (extractNum(item6.mname) == fmname) {
                other = item6.other;
            }else{
                other = '开';
            }
            var imgBtn = statusM3(other).imgBtn;
            $('.search .nowFloor-on-of').replaceWith(imgBtn);

        })

        //左侧导航
        var status = item.centerLNumState = jsonIsEqual1(placeList, 'placeLNumState');
        // console.log('左侧导航',status)
        var state = statusM(status).state;
        var img = statusM(status).img;
        var active="";
        if(extractNum(item.mname) == fmname){
            nowFloorState=img;
            active="active";
            nowFloorNumTotal=item.centerLNumTotal;
        }
        item.centerLNum = sum(placeList, 'placeLNum', 1);
                leftNav += '<li class="'+active+'"><a  href="javascript:void(0); " ><div class="clearfix"><div class="f-l p-r">' +
                    '<div class="nav-l p-a"><div class="floor">实验室-<span>' + item.mname + '</span></div>' +
                    '<div class="switch-hint">(<span class=" center-LNum">' + item.centerLNum + '</span> / <span class="">' + item.centerLNumTotal + '</span>)</div>' +
                    '</div></div><div class="f-l p-r"><div class="nav-r p-a"><div class="left-img">' +
                    img + '</div><div class="switch-hint">' + state + '</div></div></div></div></a></li>';
    })

    $('.nave ul').append(leftIndex + leftNav);

    if (placeLNumList.length > 0) {
        $.each(placeLNumList, function (i, item) {
            if (item.mname == fmname) {
                var placeList = item.placeList;
                $('.content>div.clearfix').each(function () {
                    var that = $(this);
                    $.each(placeList, function (i, item2) {
                        if (item2.place == extractNum(that.find('.mname').text())) {
                            that.find('.place-LNum1').text(item2.placeLNum);
                        }
                    })
                })
            }
        })
    } else {
        console.log('placeLNumList长度小于0')
    }
    if (centerLNumList.length > 0) {
        $.each(centerLNumList, function (i, item) {
            var mname = item.mname;
            if(extractNum(mname)==fmname){
                nowFloorNum=item.centerLNum;
            }
            $('.nave li').each(function () {
                var floor = $(this).find('.floor span').text();
                if (extractNum(mname) == extractNum(floor)) {
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
    $('.search .nowFloor .floor').text('实验室-'+fmname+'层');
    $('.search .nowState img').replaceWith(nowFloorState);
    $('.search .nowFloor .font-color span:first-child').text(nowFloorNum);
    $('.search .nowFloor .font-color span:last-child').text(nowFloorNumTotal);
}
//点击单组
$(".content").on('click', ".group-btn", function () {
    var src = $(this).children().attr('src');
    var state = $(this).children();
    src = src.substring(src.lastIndexOf("-")+1,src.lastIndexOf("."))
    // console.log(src)
    var groupOrder = extractNum($(this).parent().parent().siblings().find('.max').text());
    if (src=="off"){
        var onOffOrder = '3232';
    }else if (src=="on"){
        var onOffOrder = '3737';
    }
    // if (groupOrder>0){
    groupOrder =  parseInt(groupOrder).toString(16).toUpperCase()
    var command = '770104160' + groupOrder + onOffOrder + '66';
    // }
    var floor = getUrlParams('floor');
    var host = getHostByFloor(floor);

    $.post("/sendSocket6", {
        "command": command,
        "host": host
    }, function (msg) {
        // console.log(msg)
        if (msg.success=='success'){
            // console.log(state)
            if (src=="off"){
                $(state).attr('src','/static/new/img/light-on.PNG');
            }else if (src=="on"){
                $(state).attr('src','/static/new/img/light-off.PNG');
            }
        }
    })
})
//区域开关
$(".content").on('click', ".place-btn", function () {
    var src = $(this).children().attr('src');
    var state = $(this).children();
    src = src.substring(src.lastIndexOf("-")+1,src.lastIndexOf("."));
    if (src=="off"){
        var onOffOrder = '3232';
    }else if (src=="on"){
        var onOffOrder = '3737';
    }
    var placeOrder = $(this).parent().parent().parent().next().find('.max');
    var groupOrder;
    var commandArr = [];
    var command;
    var floor = getUrlParams('floor');
    var host = getHostByFloor(floor);
    placeOrder.each(function (key,value) {
        // console.log("placeOrdr",$(this).text())
        groupOrder = extractNum($(this).text());
        groupOrder =  parseInt(groupOrder).toString(16).toUpperCase();
        command = '770104160' + groupOrder + onOffOrder + '66';
        commandArr[key]=command;
    });
    // console.log(commandArr)
    $.ajax({
        type:"POST",
        url:"/sendSocket7",
        dataType:"json",
        // contentType:"application/json",
        // processData: false,
        data:"commands="+commandArr+"&host="+host,
        success:function (msg) {
            if (msg.success=='success'){
                // console.log(state)
                if (src=="off"){
                    $(state).attr('src','/static/new/img/light-on.PNG');
                }else if (src=="on"){
                    $(state).attr('src','/static/new/img/light-off.PNG');
                }
            }
        }
    })
});

//单楼层总开总关
$(".middle.p-a").on('click', "p", function () {
    var src = $(this).children().attr('src');
    var state = $(this).children();
    src = src.substring(src.lastIndexOf("-")+1,src.lastIndexOf("."));
    var floor = getUrlParams('floor');
    var host = getHostByFloor(floor);

    if (src=="off"){
        var command = '77010315323266';
    }else if (src=="on"){
        var command = '77010315373766';
    }
    $.post("/sendSocket6", {
        "command": command,
        "host": host
    }, function (msg) {
        if (msg.success=='success'){
            // console.log(state)
            if (src=="off"){
                $(state).attr('src','/static/new/img/light-on.PNG');
            }else if (src=="on"){
                $(state).attr('src','/static/new/img/light-off.PNG');
            }
        }
    })
});
$(".status.card").on('click', "button", function () {
    var command = $.trim($("select").val());
    var floor = getUrlParams('floor');
    var host = getHostByFloor(floor);
    $.post("/sendSocket5", {
        "command": command,
        "host": host
    }, function (msg) {
        if (msg.success=='success'){
            $(".img-levels .f-l:eq(1) p[class='font-color']").text(command)
        }
    })
});
