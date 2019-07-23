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
        let result1 = await ajaxLeftNav(); // 执行到这里报错，直接跳至下面 catch() 语句
        // let result2 = await ajaxGet(result1.url);
        let result2 = await ajaxIndex();
        // let result3 = await ajaxGet(result2.url);
        // console.log('result1 ---> ', result1);
        // console.log('result2 ---> ', result2);
        // console.log('result3 ---> ', result3);
    } catch (err) {
        console.log(err) // ReferenceError: url111 is not defined
    }
};

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

function ajaxIndex() {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: '/new/getNewMonitor',
            type: 'POST',
            dataType: "json",
            data: {'floor': 'index'},
            success: function (res) {
                resolve(res);
                console.log('res',res);
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
                                        <div class="f-l p-r">
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
                    })
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
                })
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