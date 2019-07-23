/**
 * Created by yuanjie.fang on 2019/7/18.
 */
$(function () {
    swiper('.light-hint-list .swiper-container', 3, 1, 'row');
    water();
    getInit();

})
async function getInit() {
    console.log('开始')
    try {
        let floor=getUrlParams('floor')+'楼';
        let result1 = await ajaxLeftNav();
        let result2 = await ajaxFloor(floor);
        console.log('floor',floor);
        // let result3 = await ajaxGet(result2.url);
        // console.log('result1 ---> ', result1);
        // console.log('result2 ---> ', result2);
        // console.log('result3 ---> ', result3);

    } catch (err) {
        console.log(err) // ReferenceError: url111 is not defined
    }
};
function ajaxFloor(floor) {
    return new Promise(function (resolve, reject) {
        $.ajax({
                url: '/new/getNewMonitor',
                type: 'POST',
                dataType: "json",
                data: {'floor': floor},
                success: function (res) {
                    resolve(res);
                    console.log('floorres', res);
                    var placeContent = '';
                    var floorStatus=res.floorStatus;
                    var placeList=floorStatus.placeList;
                    $.each(placeList, function (i, placeItem) {
                        var place = placeItem.place;
                        var groupList=placeItem.groupList;
                        var exception = placeItem.exception;
                        var normal = 12 - parseInt(exception);
                        var on = placeItem.on;
                        var switchImg=switchFloorJudgement(on);
                        var group = '';
                        var placeTitle = `<div class="on-off f-l">
                                <div class="clearfix btn green ">
                                    <div class="f-l p-r">
                                        <div class="pp-num middle p-a ">
                                            <p class="mname">
                                                区域${place}
                                            </p>
                                            <p class="font12">
                                                (<span class="place-LNum1">
                                                     ${normal}
                                                </span>/<span>12</span>)
                                            </p>
                                        </div>
                                    </div>
                                    <div class="f-l">
                                        <span>故障：</span>
                                        <span class="error">${exception}</span>
                                    </div>
                                    <div class="f-l">
                                        <div class="img place-btn click-btn">
                                            ${switchImg}
                                            <div class="min-font">
                                                开关
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
                        $.each(groupList, function (i, groupItem) {
                            var groupId = groupItem.groupId;
                            var exception = groupItem.exception;
                            var normal = 4 - parseInt(exception);
                            var diff = groupItem.diff;
                            var statusImg=statusFloorJudgement(exception,diff,'blue').statusImg;
                            var switchImg=switchAllFloorJudgement(status);
                            var light = '';
                            var lightList=groupItem.lightList;
                            var groupTitle = `<div class="place-title">
                                            <div class="clearfix ">
                                                <div class="f-l p-r r-line">
                                                    <div class="middle p-a ">
                                                        <p class="max">组${groupId}</p>
                                                        <p>( <span> ${normal}</span> /<span>4 </span>)</p>
                                                    </div>
                                                </div>
                                                <div class="f-l p-r r-line">
                                                    <div class="middle p-a">
                                                        <p class="group-btn-s">
                                                            ${statusImg}
                                                        </p>
                                                    </div>
                                                </div>
                                                <div class="f-l p-r">
                                                    <div class="middle p-a min">
                                                        <p class="group-btn click-btn">
                                                            ${switchImg}
                                                        </p>
                                                        <p>
                                                            开关
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>`;

                            $.each(lightList, function (i, lightItem) {
                                var lname=lightItem.lname;
                                var status=lightItem.status;
                                var y = lightItem.y == "-60%" ? 'null' : lightItem.y;
                                var switchImg=switchLightJudgement(status).switchImg;
                                var className=switchLightJudgement(status).className;
                                light += `<li class="clearfix">
                                                    <div class="f-l p-r r-min-line">
                                                        <div class="middle p-a light-name">
                                                            灯${lname}
                                                        </div>
                                                    </div>
                                                    <div class="f-l p-r r-min-line">
                                                        <div class="middle p-a yellow ${className}">
                                                            ${y}
                                                        </div>
                                                    </div>
                                                    <div class="f-l p-r">
                                                        <div class="middle p-a light-btn click-btn off" alt="">
                                                            ${switchImg}
                                                        </div>
                                                    </div>
                                                </li>`;
                            });
                            group += `<div class="place f-l swiper-slide">${groupTitle}<div class="place-content"><ul>${light}</ul></div></div>`;
                        });
                        var groupRight = `<div class="light-list f-l ">
                                                <div class="swiper-container light-swiper">
                                                    <div class="swiper-wrapper clearfix ">
                                                        ${group}
                                                        <div class="swiper-button-prev swiper-button-black">
                                                        </div>
                                                        <div class="swiper-button-next swiper-button-black">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>`;
                        placeContent += `<div class="clearfix">${placeTitle}${groupRight}</div>`;
                    })
                    $('.content').append(placeContent);
                    swiper('.light-swiper', 3)
                },
                error: function (err) {
                    reject('请求失败')
                }
            }
        )
    })
}
;
function water() {
    var pDefault;
    waterbubbleS('#floor-1', '30%', pDefault, pDefault, 24, 0.3, pDefault, '#FE4C40', pDefault, pDefault)
    waterbubbleS('#floor-2', '30%', pDefault, pDefault, 24, 0.3, pDefault, '#FE4C40', pDefault, pDefault)
    waterbubbleS('#floor-3', '30%', pDefault, pDefault, 24, 0.3, pDefault, '#FE4C40', pDefault, pDefault)
    waterbubbleS('#floor-4', '30%', pDefault, pDefault, 24, 0.3, pDefault, '#FE4C40', pDefault, pDefault)
    waterbubbleS('#floor-5', '30%', pDefault, pDefault, 24, 0.3, pDefault, '#FE4C40', pDefault, pDefault)
}