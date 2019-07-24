/**
 * Created by yuanjie.fang on 2019/7/18.
 */
$(function () {
    $('.tabs').tabslet();
    water();
    swiper('.waterbubble', 3, 2, 'row')
    getInit();
})

async function getInit() {
    try {
        let result1 = await ajaxIndex('1');
    } catch (err) {
        console.log(err);
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

function ajaxIndex(type) {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: '/new/getNewMonitor',
            type: 'POST',
            dataType: "json",
            data:{"floor":"index","type":type} ,
            success: function (res) {
                resolve(res);
                $('.nave ul').empty();
                $('.content').empty();
                $('.totalFloor').empty();
                console.log('res',res);
                //左侧导航
                var leftFloors = res.leftFloors;
                var leftNav = '';
                $.each(leftFloors, function (i, item) {
                    var mname=item.mname;
                    var centerLNum=item.centerLNum;
                    var exception = item.exception;
                    var diff = item.diff;
                    var floor=getNum(mname);
                    var active='';
                    var urlFloor= getUrlParams('floor');
                    if(urlFloor && urlFloor!=null){
                        if(urlFloor==floor){
                            active='active';
                        }
                    }
                    var status=statusFloorJudgement(exception,diff,active).status;
                    var statusImg=statusFloorJudgement(exception,diff,active).statusImg;
                    leftNav +=` <li class="${active}">
                                    <a href="/newIndex/noEnergy?floor=${floor}">
                                        <div class="clearfix">
                                            <div class="f-l p-r">
                                                <div class="nav-l p-a">
                                                    <div class="floor">实验室-<span> ${mname} </span></div>
                                                    <div class="switch-hint">(<span class=" center-LNum">${centerLNum}</span> / <span
                                                            class="">48</span>)
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="f-l p-r">
                                                <div class="nav-r p-a">
                                                    <div class="left-img">
                                                        ${statusImg}
                                                    </div>
                                                    <div class="switch-hint">${status}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </li>`;
                });
                var indexActive='';
                var url=window.location.href;
                if(url.indexOf('floor')==-1){
                    indexActive='active';
                }
                var leftIndex = `<li class="current ${indexActive}"><a href="/newIndex">首页</a></li>`;
                $('.nave ul').append(leftIndex + leftNav);
                
                //右侧
                var rightSwiper = '';
                var rightContent='';
                var indexFloorList = res.indexFloorStatus.floor;
                var allFloorStatus=res.indexFloorStatus.status;
                var switchImg=switchAllFloorJudgement(allFloorStatus);
                var AllFloor=` <div class=" totalLight clearfix">
                                        <div class="f-l p-r long-line">
                                            <div class="p-a middle">
                                                <p>实验室</p>
                                                <p>（全部楼层）</p>
                                            </div>
                                        </div>
                                        <div class="f-l p-r switch">
                                            <div class="p-a middle">
                                                <p class="pointer ">${switchImg}</p>
                                                <p>开关</p>
                                            </div>
                                        </div>                   
                                </div>`;
                $.each(indexFloorList, function (i, floorTtem) {
                    var mname = floorTtem.mname;
                    var exception = floorTtem.exception;
                    // var diff = floorTtem.diff;
                    var placeList = floorTtem.placeList;
                    var on = floorTtem.on;
                    var switchImg = switchFloorJudgement(on);
                    var rightList = '';
                    var leftFloor = `<div class="on-off f-l">
                                          <div class="clearfix btn">
                                               <div class="f-l mname"> ${mname}</div>
                                                    <div class="f-l">
                                                        <span>故障：</span>
                                                        <span> ${exception}</span>
                                                    </div>
                                                    <div class="f-l">
                                                        <div class="img toggle-button centerL-btn click-btn">
                                                            ${switchImg}
                                                            <div class="min-font">
                                                                开关
                                                            </div>
                                                        </div>
                                                    </div>
                                               </div>
                                      </div>`;

                    $.each(placeList, function (i, placeItem) {
                        var place = placeItem.place;
                        var exception = placeItem.exception;
                        var normal = 12 - parseInt(exception);
                        var diff = placeItem.diff;
                        var status = statusPlaceJudgement(exception, diff, '').status;
                        var statusImg = statusPlaceJudgement(exception, diff, '').statusImg;
                        rightList += `<div class="place f-l swiper-slide">
                                        <div class="place-title">
                                            <div>区域<span class="placeName">${place}</span></div>
                                            <div>
                                                (<span class="p-num">${normal}</span><span> /</span><span>12</span> )
                                            </div>
                                        </div>
                                        <div class="place-content">
                                            <div class="img-place">
                                                ${statusImg}
                                            </div>
                                            <div>
                                                <p class="p-status">
                                                   ${status}
                                                </p>
                                            </div>
                                        </div>
                                    </div>`;
                    });
                    
                    rightSwiper = `<div class="light-list f-l">
                                       <div class="swiper-container light-swiper">
                                           <div class="swiper-wrapper clearfix ">
                                                ${rightList}
                                               <div class="swiper-button-prev swiper-button-black"></div>
                                                <div class="swiper-button-next swiper-button-black"></div>
                                            </div>
                                        </div>
                                   </div>`;
                    rightContent+=leftFloor+rightSwiper;
                    
                });
                var content = `<div class="clearfix">${rightContent}</div>`;
                $('.content').append(content)
                $('.totalFloor').append(AllFloor);
                swiper('.light-swiper', 4)
            },
            error: function (err) {
                reject('请求失败')
            }
        })
    })
};

//单楼层总开总关
$(".content").on('click', ".centerL-btn img", function () {
    var src = $(this).attr('src');
    var that=$(this);
    src = src.substring(src.lastIndexOf("-") + 1, src.lastIndexOf("."));
    var centerOrder = $(this).parent().parent().siblings('.mname').text();
    centerOrder = extractNum(centerOrder);
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
            if (src=='off' ) {
                that.attr('src', '/static/new/img/light-on.PNG');
            } else if (src=='on') {
               
                that.attr('src', '/static/new/img/light-off.PNG');
            }
        }
    })
});
//总楼层开关
$(".totalFloor ").on('click', ".switch", function () {
    var src = $(this).find('img').attr('src');
    var that=$(this);
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
        console.log('msg',msg);
        if (msg.success == 'success') {
            if (src == "off") {
                that.find('img').attr('src', '/static/new/img/light-on.PNG');
            } else if (src == "on") {
                that.find('img').attr('src', '/static/new/img/light-off.PNG');
            }
        }
    })
})