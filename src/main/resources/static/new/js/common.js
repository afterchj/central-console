/**
 * Created by yuanjie.fang on 2019/7/9.
 */
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
        autoplay: true,
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
    waterColor ? waterColor : waterColor = '#64D1C4';
    font ? font : font = '14px arial';
    animation ? animation : animation = true;
    // console.log(id, txt, textColor, wave, radius, data, lineWidth, waterColor, font, animation)
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
/**
 * @param {string} id - 参数数组-数组内元素是否一致
 */
function isAllEqual(array) {
    if (array.length > 0) {
        return !array.some(function (value, index) {
            return value !== array[0];
        });
    } else {
        return true;
    }
}
/**
 * @param {array} 生成二维数组
 */
function Array2(nRow, nColumn) {
    var array1 = new Array();
    for (i = 0; i < nRow; i++) {
        array1[i] = new Array();
        for (n = 0; n < nColumn; n++) {
            array1[i][n] = '';
        }
    }
    return array1;
}
/**
 * @param {array} 数组去重
 */
function unique(array) {
    var r = [];
    for (var i = 0, l = array.length; i < l; i++) {
        for (var j = i + 1; j < l; j++)
            if (array[i] == array[j]) j == ++i;
        r.push(array[i]);
    }
    return r;

}

function sort(json, field) {
    json.sort(function (a, b) {
        return extractNum(a[field]) - extractNum(b[field]);
    });
    return json;
}
function extractNum(str) {
    if (isNaN(str)) {
        var result = str.replace(/[^0-9]/ig, "");
        return result;
    }else{
        return str;
    }
}

function sum(json, field, m) {
    var array = [];
    $.each(json, function (i, item) {
        array.push(item[field]);
    })
    if (m == null) {
        return timesSum(array, null);
    } else {
        return sumTotal(array)
    }

}
function sumTotal(arr) {
    if (arr.length > 0) {
        return arr.reduce(function (prev, curr, idx, arr) {
            return prev + curr;
        });
    }
}


function timesSum(arr, m) {
    times = 0;
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == m) {
            times++;
        }
    }
    return times;
}


// $(".nave li").click(function () {
//
// })
$(".nave").on('click', "li", function () {
    $(this).addClass('active').siblings().removeClass('active');
    var imgUrl = $(this).find('img').attr('src');
    if (imgUrl) {
        if (imgUrl.indexOf('normal') != -1) {
            if ($(this).find('img').attr('src') == '/static/new/img/normal.png') {
                $(this).find('img').attr('src', '/static/new/img/normal-white.png')
            } else {
                $(this).find('img').attr('src', '/static/new/img/normal.png')
            }
        }
    }
    var floor = extractNum($(this).find('.floor span').text());
    if (floor) {
        location.href = "/newIndex/noEnergy?floor=" + floor;
    }
})
$(".nave li").hover(function () {
    $(this).addClass('active').siblings().removeClass('active');
    var imgUrl = $(this).find('img').attr('src');
    if (imgUrl) {
        if (imgUrl.indexOf('normal') != -1) {
            if ($(this).find('img').attr('src') == '/static/new/img/normal.png') {
                $(this).find('img').attr('src', '/static/new/img/normal-white.png')
            } else {
                $(this).find('img').attr('src', '/static/new/img/normal.png')
            }
        }
    }
})

function getUrlParams(name) { // 不传name返回所有值，否则返回对应值
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

//lightState灯状态数据转换
function lightStateM(lightState) {
    // var floorName = [{allBtn: 'on',}];
    var floorName = [], groupName = [], placeName = [], lightName = [], obj1 = {};
    $.each(lightState, function (i, item) {
        var mname = item.mname;
        var groupId = item.groupId;
        var place = item.place;
        var status = item.status;
        var lname = item.lname;
        var lmac = item.lmac;
        var y = item.y;
        if (!obj1[mname]) {
            var floorList = {
                mname: mname,
                centerLNum: 0,
                centerLNumTotal: 48,
                centerLNumState: "2",
                centerLBtn: "on",
                placeList: []
            };
            floorName.push(floorList);
            obj1[mname] = true;
        }
        var placeList = {
            mname: mname,
            place: place,
            // placeLNumTotal: 12,
            // placeLNumState: "2",
            groupList: []
        }
        placeName.push(placeList);
        var groupList = {
            mname: mname,
            place: place,
            groupId: groupId,
            // groupTotal: 4,
            // groupState: "2",
            lightList: []
        }
        groupName.push(groupList)

        var lightList = {
            mname: mname,
            place: place,
            groupId: groupId,
            lname: lname,
            lmac: lmac,
            status: status,
            y: y
        }
        lightName.push(lightList)

    })
    $.each(floorName, function (i, item1) {
        var obj2 = {};
        $.each(placeName, function (j, item2) {
            if (item1.mname == item2.mname) {
                if (!obj2[item2.place]) {
                    var newPlace = {
                        place: item2.place,
                        placeLNum: 0,
                        placeLNumTotal: 12,
                        placeLNumState: "2",
                        placeLBtn: "on",
                        groupList: item2.groupList
                    }

                    item1.placeList.push(newPlace)
                    obj2[item2.place] = true;
                }
            }
        })
    })
    $.each(floorName, function (i, item1) {
        var obj3 = {};
        $.each(groupName, function (j, item2) {
            if (item1.mname == item2.mname) {
                var placeArr = item1.placeList;

                for (var n = 0; n < placeArr.length; n++) {
                    if (placeArr[n].place == item2.place) {
                        if (!obj3[item2.groupId]) {
                            var newGroup = {
                                mname: item2.mname,
                                place: item2.place,
                                groupId: item2.groupId,
                                groupNum: 0,
                                groupTotal: 4,
                                groupState: "2",
                                groupBtn: "on",
                                lightList: []
                            }
                            placeArr[n].groupList.push(newGroup)
                            obj3[item2.groupId] = true;
                        }
                    }
                }
            }
        })
    })
    $.each(floorName, function (i, item1) {
        var obj4 = {};
        $.each(lightName, function (j, item2) {
            if (item1.mname == item2.mname) {
                var placeArr = item1.placeList;
                // var obj3 = {};
                for (var n = 0; n < placeArr.length; n++) {
                    if (placeArr[n].place == item2.place) {
                        var groupArr = placeArr[n].groupList;

                        for (var m = 0; m < groupArr.length; m++) {
                            if (groupArr[m].groupId == item2.groupId) {
                                if (!obj4[item2.lname]) {
                                    var lightBtn;
                                    if (item2.status == 0 || item2.status == null) {
                                        lightBtn:'off';
                                    } else {
                                        lightBtn:'on';
                                    }
                                    var newLight = {
                                        mname: item2.mname,
                                        place: item2.place,
                                        groupId: item2.groupId,
                                        lname: item2.lname,
                                        lmac: item2.lmac,
                                        status: item2.status,
                                        lightBtn: lightBtn,
                                        y: item2.y,
                                    }
                                    groupArr[m].lightList.push(newLight)
                                    obj4[item2.lname] = true;
                                }
                            }
                        }
                    }
                }
            }
        })
    })
    return floorName;
}
//placeLNumList灯数量数据转换
function placeLNumListM(placeLNumList) {
    var floorName = [], placeName = [], obj1 = {};
    $.each(placeLNumList, function (i, item) {
        var mname = item.mname;
        var place = item.place;
        var placeLNum = item.placeLNum;
        if (!obj1[mname]) {
            floorName.push({
                mname: mname,
                placeList: []
            });
            obj1[mname] = true;
        }
        var placeList = {
            mname: mname,
            place: place,
            placeLNum: placeLNum
        }
        placeName.push(placeList);
    })
    $.each(floorName, function (i, item1) {
        var obj2 = {};
        $.each(placeName, function (j, item2) {
            if (item1.mname == item2.mname) {
                if (!obj2[item2.place]) {
                    var newPlace = {
                        place: item2.place,
                        placeLNum: item2.placeLNum
                    }
                    item1.placeList.push(newPlace)
                    obj2[item2.place] = true;
                }
            }
        })
    })
    return floorName;
}

function jsonIsEqual(json, field) {
    var array = [];
    // var result;
    $.each(json, function (i, item) {
        array.push(item[field]);
    })

    if (array.indexOf(null) != -1) {
        //异常
        if (isAllEqual(filterArr(array, null))) {
            //除了异常是否一致,一致,则只有异常
            return "2"
        } else {
            //异常不一致
            return "12"
        }
    } else if (isAllEqual(array)) {
        //正常
        return "0"
    } else {
        //开关灯不一致
        return "1"
    }
}
function filterArr(array, field) {
    var newArr = array.filter(function (item) {
        return item != field;
    });
    return newArr;
}
function jsonIsEqual1(json, field) {
    var array = [];
    $.each(json, function (i, item) {
        array.push(item[field]);
    })
    if (array.indexOf('12') != -1) {
        //异常开关灯不一致
        return "12"
    } else if (array.indexOf('2') != -1) {
        if (filterArr(array, '2').indexOf('1') == -1) {
            //除了异常是否一致,一致,则只有异常
            return "2"
        } else {
            return "12"
        }
    } else if (array.indexOf('1') != -1) {
        //开关灯不一致
        return "1"
    } else if (array.indexOf('0') != -1) {
        //正常
        return "0"
    }
}
function statusM(status, blue) {
    var img, state, warning = false;
    if (status == 0) {
        //正常
        state = '正常';
        if (blue == 'blue') {
            img = '<img src="/static/new/img/normal-white.png" alt="">';
        } else {
            img = '<img src="/static/new/img/normal.png" alt="">';
        }
        warning = false;
    } else if (status == 1) {
        //开关灯状态不一致
        state = '开关灯不一致';
        img = '<img src="/static/new/img/switch-un.png" alt="">';
        warning = false;
    } else if (status == 2) {
        //异常
        state = '异常';
        img = '<img src="/static/new/img/switch-abnormal.png" alt="">';
        warning = true;
    } else if (status == 12) {
        //开关灯状态不一致且异常
        state = '开关灯状态不一致异常状态（故障）';
        img = '<img src="/static/new/img/switch-un.png" alt=""><img src="/static/new/img/switch-abnormal.png" alt="">';
        warning = true;
    }
    return obj = {
        img: img,
        state: state,
        warning: warning
    }
}
function statusM1(status, blue) {
    var img, imgBtn, state;
    if (status == 0) {
        state = '开';
        imgBtn = '<img src="/static/new/img/light-on.PNG" alt="">';
        if (blue == 'blue') {
            img = '<img src="/static/new/img/normal-white.png" alt="">';
        } else {
            img = '<img src="/static/new/img/normal.png" alt="">';
        }
    } else if (status == 1) {
        state = '关';
        imgBtn = '<img src="/static/new/img/light-off.PNG" alt="">';
        img = '<img src="/static/new/img/normal.png" alt="">';
      
    } else if (status == null) {
        state = '异常';
        imgBtn = '<img src="/static/new/img/light-off.PNG" alt="">';
        img = '<img src="/static/new/img/switch-abnormal.png" alt="">';
     
    }
    return obj = {
        img: img,
        state: state,
        imgBtn: imgBtn
    }
}
function statusM2(btn) {
    var img, imgBtn, state;
    //全异常 null,全关 1
    if (btn == 'off') {
        state = '关';
        imgBtn = '<img src="/static/new/img/light-off.PNG" alt="">';
    } else {
        state = '开';
        imgBtn = '<img src="/static/new/img/light-on.PNG" alt="">';
    }
    return obj = {
        state: state,
        imgBtn: imgBtn
    }
}
function statusM3(btn) {
    var img, imgBtn, state;
    //全异常 null,全关 1
    if (btn == '关') {
        state = '关';
        imgBtn = '<img src="/static/new/img/light-off.PNG" alt="">';
    } else {
        state = '开';
        imgBtn = '<img src="/static/new/img/light-on.PNG" alt="">';
    }
    return obj = {
        state: state,
        imgBtn: imgBtn
    }
}

// $(".content").on('click', ".btn", function () {
//
// })

//json格式数据

function treeData(resData) {
    var floor = [];
    var place = [];
    var group = [];
    if (resData.length != 0) {
        var obj1 = {};
        for (var i = 0; i < resData.length; i++) {
            if (!obj1[resData[i].mname]) {
                var obj = {
                    mname: resData[i].mname,
                    placeList: []
                };
                floor.push(obj);
                obj1[resData[i].mname] = true;
            }
            var placeList = {
                mname: resData[i].mname,
                place:resData[i].place,
                groupList: []
            };
            place.push(placeList);
            var groupList = {
                mname: resData[i].mname,
                place:resData[i].place,
                groupId: resData[i].groupId,
                status:resData[i].status,
                other:resData[i].other,
            };
            group.push(groupList);
        }
        // $.each(resData, function (i, item) {
            $.each(floor, function (i, item1) {
                var obj2 = {};
                $.each(place, function (i, item2) {
                    if (item1.mname == item2.mname) {
                        if (!obj2[item2.place]) {
                            var obj = {
                                mname: item2.mname,
                                place: item2.place,
                                groupList: []
                            };
                            item1.placeList.push(obj);
                            obj2[item2.place] = true;
                        }
                    }
                })
            })
        // })
        $.each(floor, function (i, item1){
            $.each(group, function (i, item2){
                if (item1.mname == item2.mname) {
                    var placeList=item1.placeList;
                    var obj3 = {};
                    $.each(placeList,function(i, item3){
                        if (item2.place == item3.place) {
                            if (!obj3[item2.groupId]) {
                                var obj = {
                                    mname: item2.mname,
                                    place: item2.place,
                                    groupId: item2.groupId,
                                    status:item2.status,
                                    other:item2.other,
                                };
                                item3.groupList.push(obj);
                                obj3[item2.groupId] = true;
                            }
                        }
                    })
                }
            })
        })
        // $.each(resData, function (i, item) {
        //     $.each(floor, function (i, item1) {
        //         var obj3 = {};
        //             $.each(place, function (i, item2) {
        //                 if (item1.mname == item2.mname) {
        //                     $.each(item1.placeList, function (i, item3) {
        //                         if(item1.place==item3.place ) {
        //                             if (!obj3[item3.groupId]) {
        //                                 var obj = {
        //                                     mname: item3.mname,
        //                                     place: item2.place,
        //                                     groupId: item3.groupId,
        //                                 };
        //                                 item1.groupList.push(obj);
        //                                 obj3[item3.groupId] = true;
        //                             }
        //                         }
        //                     })
        //                 }
        //             })
        //
        //     })
        // })

        // $.each(resData, function (i, item) {
        //     $.each(floor, function (i, item2) {
        //         if (item.mname == item2.mname) {
        //             $.each(place, function (i, item3) {
        //                 if(item.place==item3.place){
        //                     $.each(group, function (i, item4) {
        //                         if(item.groupId==item4.groupId){
        //                             if (!obj4[item4.groupId]) {
        //                                 var obj = {
        //                                     mname: item2.mname,
        //                                     place: item3.place,
        //                                     groupId: item4.groupId,
        //                                 };
        //                                 item4.groupList.push(obj);
        //                                 obj4[item4.mname] = true;
        //                             }
        //                         }
        //                     })
        //                 }
        //             })
        //         }
        //     })
        // })
        return floor;
    }

    // var obj1={};
    // if(resData.length!=0){
    //     for(var i=0;i<resData.length;i++){
    //         if(!obj1[resData[i][m]]){
    //             var obj2={
    //                 m:resData[i].m,
    //                 children:[]
    //             };
    //             tree.push(obj2);
    //             obj1[resData[i][m]] = true;
    //         }
    //     }
    // }


    // function run(chiArr,m ,n) {
    //     if (resData.length !== 0) {
    //         for (var i = 0; i < chiArr.length; i++) {
    //             for (var j = 0; j < resData.length; i++) {
    //                 if (chiArr[i][m] === resData[j][m]) {
    //
    //                     var obj = {
    //                         n: resData[j][n],
    //                         children: []
    //                     };
    //                     chiArr[i].children.push(obj);
    //                 }
    //             }
    //             run(chiArr[i].children);
    //         }
    //     }
    // }
    // console.log('tree',tree);
    // return tree;

}
// function data(treeData,resData,m){
//     var tree;
//     var obj={};
//     if(treeData.length>0){
//         tree=treeData;
//     }else{
//         tree=[];
//     }
//     console.log('tree11',tree);
//     if(resData.length!=0){
//         for(var i=0;i<resData.length;i++){
//             if(!obj[resData[i][m]]){
//                 var obj1={
//                     m:resData[i].m,
//                     children:[]
//                 };
//                 tree.push(obj1);
//                 obj[resData[i][m]] = true;
//             }
//         }
//     }
//     return tree;
// }

// function transData(a,idStr,pidStr,childrenStr) {
//     var r=[],hash={},id=idStr,pid=pidStr,children=childrenStr,i=0,j=0,len=a.length;
//     for(;i<len;i++){
//         hash[a[i][id]]=a[i];
//     }
//     for(;j<len;j++){
//       var aVal=a[j], hashVP= hash[aVal[pid]];
//       if(hashVP){
//           !hashVP[children] && (hashVP[children]=[]);
//           hashVP[children].push(aVal);
//       }else{
//           r.push(aVal);
//       }
//     }
//     return r;
// }

// function toTree(data){
//     data.forEach(function(item){
//         delete  item.children;
//     });
//     var map={};
//     data.forEach(function(item){
//         map[item.mname]==item;
//     });
//     var val=[];
//     data.forEach(function(item){
//         var parent=map[item.place];
//         if(parent){
//             (parent.children || (parent.children=[])).push(item);
//         }else{
//             val.push(item);
//         }
//     });
//     return val;
// }

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