/**
 * Created by yuanjie.fang on 2019/7/18.
 */

//左侧导航栏鼠标悬浮
$('.nave').on('mouseenter mouseleave', 'li', function () {
    var src = $(this).find('.left-img img').attr('src');
    if (src && src.indexOf('normal') != -1) {
        if (src == '/static/new/img/normal.png') {
            $(this).find('.left-img img').attr('src', '/static/new/img/normal-white.png');
        } else if (src == '/static/new/img/normal-white.png') {
            $(this).find('.left-img img').attr('src', '/static/new/img/normal.png');
        }
    }
});

//监听select场景选择
// $('.select-levels select').change(function(){
//     var selectVal=$(this).children('option:selected').val();
//     console.log('selectVal',selectVal);
//     const actions = () => {
//         const functionA = () => {
//             /*do sth*/
//
//         }
//         const functionB = () => {
//             /*do sth*/
//
//         }
//         return new Map([
//             [/\S/, functionA],
//             [/^guest_5$/, functionB],
//             //...
//         ])
//     }
//
//     const onButtonClick = (identity, status) => {
//         let action = [...actions()].filter(([key, value]) => (key.test(`${identity}_${status}`)))
//         action.forEach(([key, value]) => value.call(this))
//     }
// })


/**
 * @param {string} classaName - 参数classaName
 * @param {number} slidesPerView - 参数默认值为1
 * @param {number} slidesPerColumn - 参数b默认值为1
 * @param {string} slidesPerColumnFill - 参数b默认值为row
 */
function swiper(classaName, slidesPerView, slidesPerColumn, slidesPerColumnFill) {
    slidesPerView ? slidesPerView : slidesPerView = 1;
    slidesPerColumn ? slidesPerColumn : slidesPerColumn = 1;
    slidesPerColumnFill ? slidesPerColumnFill : slidesPerColumnFill = 'row';
    new Swiper(classaName, {
        autoplay: false,
        direction: 'horizontal',
        slidesPerView: slidesPerView,
        slidesPerColumn: slidesPerColumn,
        slidesPerColumnFill: slidesPerColumnFill,
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        }
    })
}

/**
 * @param {string} id - 参数id
 * @param {string} txt - 参数默认值为0%
 * @param {bool} wave- 参数默认值为true
 * @param {number} radius - 参数b默认值为26
 * @param {number} data - 参数b默认值为0
 * @param {number} lineWidth- 参数b默认值为2
 * @param {waterColor} lineWidth- 参数b默认值为#64D1C4
 * @param {string}  font- 参数b默认值为'14px arial'
 * @param {bool} animation- 参数b默认值为true
 */
function waterbubbleS(id, txt, textColor, wave, radius, data, lineWidth, waterColor, font, animation) {
    txt ? txt : txt = '0%';
    textColor ? textColor : textColor = '#333';
    wave ? wave : wave = true;
    radius ? radius : radius = 26;
    data ? data : data = 0;
    lineWidth ? lineWidth : lineWidth = 2;
    waterColor ? waterColor : waterColor = '#68d8d8';
    font ? font : font = '14px arial';
    animation ? animation : animation = true;
    $(id).waterbubble({
        txt: txt,
        textColor: textColor,
        wave: wave,
        radius: radius,
        data: data,
        lineWidth: lineWidth,
        waterColor: waterColor,
        font: font,
        animation: animation
    })
}

//提取字符串中的数字
function getNum(text) {
    var value;
    if (isNaN(text)) {
        value = text.replace(/[^0-9]/ig, "");
    } else {
        value = text;
    }
    return value;
}


function extractNum(str) {
    if (isNaN(str)) {
        var result = str.replace(/[^0-9]/ig, "");
        return result;
    } else {
        return str;
    }
}

//获取url中的参数的值
function getUrlParams(name) {
    var url = window.location.search;
    if (url.indexOf('?') == 1) {
        return false;
    }
    url = url.substr(1);
    url = url.split('&');
    var name = name || '';
    var nameres;
    // 获取全部参数及其值
    for (var i = 0; i < url.length; i++) {
        var info = url[i].split('=');
        var obj = {};
        obj[info[0]] = decodeURI(info[1]);
        url[i] = obj;
    }
    // 如果传入一个参数名称，就匹配其值
    if (name) {
        for (var i = 0; i < url.length; i++) {
            for (const key in url[i]) {
                if (key == name) {
                    nameres = url[i][key];
                }
            }
        }
    } else {
        nameres = url;
    }
    // 返回结果
    return nameres;
}

//状态判断(左侧楼层,楼层页楼层状态判断)
function statusFloorJudgement(exception, diff, active) {
    var status;
    var statusImg;
    if (exception == 1 && diff == 1) {
        status = '开关不一致异常状态（故障）';
        statusImg = '<img src="/static/new/img/switch-un.png" alt=""><img src="/static/new/img/switch-abnormal.png" alt="">';
    } else if (exception == 1 && diff != 1) {
        status = '异常';
        statusImg = '<img src="/static/new/img/switch-abnormal.png" alt="">';
    } else if (exception == 0 && diff == 1) {
        status = '开关不一致';
        statusImg = '<img src="/static/new/img/switch-un.png" alt="">';
    } else if (exception == 0 && diff == 0) {
        status = '正常';
        if (active == '') {
            statusImg = '<img src="/static/new/img/normal.png" alt="">';
        } else {
            statusImg = '<img src="/static/new/img/normal-white.png" alt="">';
        }
    }
    var obj = {
        status: status,
        statusImg: statusImg
    }
    return obj;
}

//状态判断(右侧首页区域,楼层页组状态)
function statusPlaceJudgement(exception, diff, active) {
    var status;
    var statusImg;
    if (exception > 0 && diff == 1) {
        status = '<p>开关不一致</p><p>异常状态（故障）</p>';
        statusImg = '<img src="/static/new/img/switch-un.png" alt=""><img src="/static/new/img/switch-abnormal.png" alt="">';
    } else if (exception > 0 && diff == 0) {
        status = '<p>异常</p>';
        statusImg = '<img src="/static/new/img/switch-abnormal.png" alt="">';
    } else if (exception == 0 && diff == 1) {
        status = '<p>开关不一致</p>';
        statusImg = '<img src="/static/new/img/switch-un.png" alt="">';
    } else if (exception == 0 && diff == 0) {
        status = '<p>正常</p>';
        if (active == '') {
            statusImg = '<img src="/static/new/img/normal.png" alt="">';
        } else {
            statusImg = '<img src="/static/new/img/normal-white.png" alt="">';
        }
    }
    var obj = {
        status: status,
        statusImg: statusImg
    }
    return obj;
}


//开关判断(首页楼层单个,楼层页区域)
function switchFloorJudgement(on) {
    var switchImg;
    if (on == 0) {
        switchImg = '<img src="/static/new/img/light-off.PNG" alt="">';
    } else if (on == 1) {
        switchImg = '<img src="/static/new/img/light-on.PNG" alt="">';
    }
    return switchImg;
}


//开关判断(楼层总)
function switchAllFloorJudgement(allFloorStatus) {
    var switchImg;
    if (allFloorStatus == 0) {
        switchImg = '<img src="/static/new/img/light-on.PNG" alt="">';
    } else if (allFloorStatus == 1) {
        switchImg = '<img src="/static/new/img/light-off.PNG" alt="">';
    }
    return switchImg;
}


//开关判断(灯)
function switchLightJudgement(status) {
    var className = '';
    var switchImg;
    if (status == 0) {
        switchImg = '<img src="/static/new/img/light-on.PNG" alt="">';
    } else if (status == 1) {
        switchImg = '<img src="/static/new/img/light-off.PNG" alt="">';
        className = 'off';
    } else if (status == null) {
        switchImg = '<img src="/static/new/img/light-off.PNG" alt="">';
        className = 'off hint';
    }
    var obj = {
        className: className,
        switchImg: switchImg
    }
    return obj;
}
var eventDriven = function (host, floor) {
    let hosStr = host.substring(host.length - 1, host.length);
    console.log('hosStr', hosStr);
    const actions = new Map([
        [{
            hosStr: hosStr,
            floor: floor
        }, () => {
            update(host, floor)
        }],
        [{
            hosStr: hosStr,
            floor: floor
        }, () => {
        }],
    ])

    function update(host, floor) {
        $.ajax({
            url: host,
            type: 'POST',
            dataType: "json",
            data: {'floor': floor + '楼', "type": "1"},
            success: function (res) {
                console.log(res)
            }
        })
    }

    const onButtonClick = (hosStr, floor) => {
        //下面代码使用了数组解构  [key,value] = cuurrentValue
        let action = [...actions].filter(([key, value]) => (key.hosStr == hosStr && key.floor == floor && key.hosStr == key.floor ))
        action.forEach(([key, value]) => value.call(this))
    }
}

//根据楼层回去ip
function getHostByFloor(floor) {
    var host;
    switch (floor) {
        case "1":
            host = "192.168.10.11";
            break;
        case "2":
            host = "192.168.10.12";
            break;
        case "3":
            host = '192.168.10.13';
            break;
        case "4":
            host = '192.168.10.14';
            break;
        case "5":
            host = '192.168.10.15';
            break;
        case "6":
            host = "192.168.10.16";
            break;
        case "7":
            host = "192.168.10.17";
            break;
        case "8":
            host = "192.168.10.18";
            break;
        case "9":
            host = "192.168.10.19";
            break;
        case "10":
            host = "192.168.10.20";
            break;
    }
    return host;
}

function getFloorByHost(host) {
    var floor;
    switch (host) {
        case "192.168.10.11":
            floor = "1";
            break;
        case "192.168.10.12":
            floor = "2";
            break;
        case "192.168.10.13":
            floor = '3';
            break;
        case "192.168.10.14":
            floor = '4';
            break;
        case "192.168.10.15":
            floor = '5';
            break;
        case "192.168.10.16":
            floor = "6";
            break;
        case "192.168.10.17":
            floor = "7";
            break;
        case "192.168.10.18":
            floor = "8";
            break;
        case "192.168.10.19":
            floor = "9";
            break;
        case "192.168.10.20":
            floor = "10";
            break;
    }
    return floor;
}
// function update(host, floor) {
//     var hosStr = host.substring(host.length - 1, host.length);
//     if (hosStr == floor) {
//         $.ajax({
//             url: '/new/getNewMonitor',
//             type: 'POST',
//             dataType: "json",
//             data: {'floor': floor + '楼', "type": "1"},
//             success: function (res) {
//                 console.log('333',res)
//             }
//         })
//     }
// }
// function sc(host,floor){
//     let hosStr = host.substring(host.length - 1, host.length);
//     const actions = new Map([
//         [{
//             hosStr: hosStr,
//             floor: floor
//         },()=> {
//             update(host, floor)}
//         ],
//         [{
//             hosStr: hosStr,
//             floor: floor
//         },()=>{}
//         ],
//     ])
// }
//
//
// function update(host, floor) {
//     $.ajax({
//         url: host,
//         type: 'POST',
//         dataType: "json",
//         data: {'floor': floor + '楼', "type": "1"},
//         success: function (res) {
//             console.log(res)
//         }
//     })
// }
//
// const onButtonClick = (hosStr, floor) =>{
//     //下面代码使用了数组解构  [key,value] = cuurrentValue
//     let action = [...actions].filter(([key, value])=>(key.hosStr == hosStr && key.floor == floor && key.hosStr == key.floor ));
//     action.forEach(([key, value])=> value.call(this));
// }
//左侧数据初始化
function ajaxLeftNav() {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: '/new/getLeft',
            type: 'POST',
            dataType: "json",
            data:{"type":"1"},
            success: function (res) {
                $('.nave ul').empty();
                resolve(res);
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
            },
            error: function (err) {
                reject('请求失败')
            }
        })
    })
};