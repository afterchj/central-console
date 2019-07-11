/**
 * Created by yuanjie.fang on 2019/7/10.
 */
$(function () {
    init();
    realTime();
    // setInterval(function () {
    //     realTime()
    // }, 500)
    // setInterval(function () {
    //     init();
    // }, 100000)
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
    upLight("/getNewMonitor")
}
function lightAlter(other,status){
    $.each(lightState,function(i,item1){
        var placeList = item1.placeList;
        $('.content>.clearfix').each(function(){
            var that=$(this);
            if(item1.mname==floor){
                $.each(placeList,function(i,item2){
                    var groupList = item2.groupList;
                    if(item2.place==extractNum(that.find('.mname').text())){
                        $.each(groupList,function(i,item3){
                            var lightList = item3.lightList;

                            that.find('.place').each(function(){
                                var that2=$(this);
                                var status = item3.groupState = jsonIsEqual(lightList, 'status');
                                var state = statusM(status).state;
                                var img = statusM(status, 'blue').img;
                                item3.groupNum=parseInt(item3.groupTotal)-sum(lightList,'status',null);
                                if(item3.groupId==extractNum($(this).find('.max').text())){
                                    $.each(lightList,function(i,item4){
                                        // var status = item4.status;
                                        var y=item4.y;
                                        item4.status=statusAll;
                                        that2.find('.place-content>ul>li').each(function(){
                                            if(item4.lname==extractNum($(this).find('.light-name').text())){
                                                $(this).find('.yellow').text(item4.y);
                                            }
                                        })
                                    })
                                }
                            })
                        })
                    }
                })
            }
        })
    })
}
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
                if(data.lightDemo.other != null){
                    var lightDemo = data.lightDemo;
                    var other = data.lightDemo.other;
                    // lightState = sort(lightState, 'mname');
                    // lightState=sort(lightState, 'lname');
                    // lightState = lightStateM(lightState);
                    var floor =getUrlParams('floor');
                    console.log('floor',floor);
                    operation2( lightDemo, other, floor)
                }
            }
        }
    })
}
function operation2(lightDemo, other, floor) {
    if (other == 'all') {
        var statusAll = lightDemo.status;
        // console.log('statusAll', statusAll);
        $('.content>.clearfix .place').each(function () {
            var src= $(this).find('.img-place').attr('src',src);
            // console.log('src',src);
            var img= statusM1(statusAll,1).img;
            $(this).find('.img-place').attr('src',img);
        })
    }else if(other=='groupId'){
        var mname=lightDemo.mname;
        var groupId=lightDemo.groupId;
        var status=lightDemo.status;
        var img= statusM1(status,1).img;
        console.log(mname,groupId,status,img)
        if(extractNum(mname)==floor){
            console.log('楼层',mname);
            $('.content .place:contains("组"+groupId)').parent().parent().next().find('img').replaceWith(img);
            // $('.content .place:contains(is)').each(function () {
            //     console.log($(this).find('.max').text());
            //    if(group==extractNum($(this).find('.max').text())){
            //
            //    }
            // })
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
            //排序
            // if(centerLNumList.length>0){
            //     centerLNumList = sort(centerLNumList, 'mname');
            // }
            if (placeLNumList.length > 0) {
                placeLNumList = sort(placeLNumList, 'mname');
            }
            if (lightState.length > 0) {
                lightState = sort(lightState, 'mname');
                lightState=sort(lightState, 'lname');
            }

            var floor =getUrlParams('floor');
            console.log('floor',floor);
            //json数据格式转换调用方法
            lightState = lightStateM(lightState);
            placeLNumList = placeLNumListM(placeLNumList);
            console.log('data原始lightState ', lightState);
            console.log('data原始placeLNumList ', placeLNumList);
            operation(lightState, placeLNumList, centerLNumList, floor)
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
        if (extractNum(item.mname) == fmname) {
            $.each(placeList, function (i, item2) {
                var groupList = item2.groupList;
                console.log('groupList3',groupList)
                //右侧数据展示

                var rightList = '';
                $.each(groupList,function(i, item3){
                    var lightList = item3.lightList;
                    var lightContent = '';
                    $.each(lightList, function (i, item4) {
                        var status = item4.status;
                        var state = statusM1(status).state;
                        var img = statusM1(status).img;
                        var warning = statusM1(status).warning;
                        var hint = '';
                        if (warning) {
                            hint = 'active';
                        } else {
                            hint = '';
                        }
                        lightContent += '<li class="clearfix"> <div class="f-l p-r r-min-line"><div class="middle p-a light-name">灯' + item4.lname + '</div></div><div class="f-l p-r r-min-line"><div class="middle p-a yellow ' + hint + '">' + item4.y + '</div></div>' +
                            ' <div class="f-l p-r"><div class="middle p-a light-btn click-btn" >'+img+'</div></div></li>';
                    })

                    var status = item3.groupState = jsonIsEqual(lightList, 'status');
                    var state = statusM(status).state;
                    var img = statusM(status, 'blue').img;
                    item3.groupNum=parseInt(item3.groupTotal)-sum(lightList,'status',null);

                    var title = '<div class="place-title"><div class="clearfix "><div class="f-l p-r r-line"><div class="middle p-a "><p class="max">组' + item3.groupId + '</p>' +
                        '<p>(<span>' + item3.groupNum + '</span>/ <span>' + item3.groupTotal + '</span>)</p></div></div><div class="f-l p-r r-line"><div class="middle p-a"><p>' + img + '</p></div>' +
                        '</div><div class="f-l p-r"> <div class="middle p-a min"><p><img src="/static/new/img/on-off-white.png" alt="" class="group-btn click-btn"></p> <p>开关</p></div></div></div></div>';

                    rightList += '<div class="place f-l swiper-slide">' + title + '<div class="place-content"><ul>' + lightContent + '</ul></div></div>';
                })
                var status = item2.placeLNumState = jsonIsEqual1(groupList, 'groupState');
                var state = statusM(status).state;
                var img = statusM(status, 'blue').img;
                item2.placeLNum=sum(groupList,'groupNum',1);
                var sumTotal=parseInt(item2.placeLNumTotal)-sum(groupList,'groupNum',1);
                var right = ' <div class="light-list f-l "> <div class="swiper-container light-swiper"> <div class="swiper-wrapper clearfix ">' +
                    rightList + ' <div class="swiper-button-prev"></div><div class="swiper-button-next"></div></div></div></div>';


                var left = '<div class="on-off f-l"><div class="clearfix btn green "><div class="f-l p-r"><div class="pp-num middle p-a "><p class="mname">区域' + item2.place + '</p><p>(<span class="place-LNum1">' + item2.placeLNum + '</span>/ <span>' + item2.placeLNumTotal + '</span>)</p></div></div>' +
                    ' <div class="f-l"> <span>故障：</span><span class="error">'+sumTotal+'</span> </div> ' +
                    '<div class="f-l"> <div class="img"> <img src="/static/new/img/on-off-white.png" alt="" class="place-btn click-btn"> ' +
                    '<div class="min-font">开关</div> </div> </div> </div> </div>';
                var content = '<div class="clearfix">' + left + right + '</div>';
                $('.content').append(content);
            })
            swiper('.light-swiper', 3, 1, 'row')
        }else{
            $.each(placeList, function (i, item2) {
                var groupList = item2.groupList;
                $.each(groupList,function(i, item3) {
                    var lightList = item3.lightList;
                    $.each(lightList, function (i, item4) {
                        var status = item4.status;
                        var state = statusM1(status).state;
                        var img = statusM1(status).img;
                        var warning = statusM1(status).warning;
                    })
                    var status =  item3.groupState = jsonIsEqual(lightList, 'status');
                    var state = statusM(status).state;
                    var img = statusM(status, 'blue').img;
                    item3.groupNum=item3.groupTotal-sum(lightList,'status',null);
                })
                var status = item2.placeLNumState = jsonIsEqual1(groupList, 'groupState');
                var state = statusM(status).state;
                var img = statusM(status, 'blue').img;
                item2.placeLNum=sum(groupList,'groupNum',1);
                var sumTotal=parseInt(item2.placeLNumTotal)-sum(groupList,'groupNum',1);

            })

        }
        var status = item.centerLNumState = jsonIsEqual1(placeList, 'placeLNumState')
        var state = statusM(status).state;
        var img = statusM(status).img;
        item.centerLNum=sum(placeList,'placeLNum',1);
        //左侧导航

        leftNav += '<li><a  href="javascript:void(0); "><div class="clearfix"><div class="f-l p-r">' +
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
    if (centerLNumList.length > 0) {
        $.each(centerLNumList, function (i, item) {
            var mname = item.mname;
            $('.nave li').each(function () {
                var floor = $(this).find('.floor span').text();
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
}
$(".content").on('click', ".toggle-button", function(){
    var src=$(this).attr('src');
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
