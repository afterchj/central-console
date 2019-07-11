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
    slidesPerView ? slidesPerView : slidesPerView=1;
    slidesPerColumn ? slidesPerColumn : slidesPerColumn=1;
    slidesPerColumnFill ? slidesPerColumnFill :slidesPerColumnFill= 'row';
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
    txt? txt : txt='0%';
    textColor ? textColor : textColor='#333';
    wave ? wave :wave= true;
    radius ? radius : radius=26;
    data ? data : data=0;
    lineWidth ? lineWidth : lineWidth=2;
    waterColor ? waterColor :waterColor= '#64D1C4';
    font ? font :font= '14px arial';
    animation ? animation : animation=true;
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
function unique(array){
    var r = [];
    for(var i = 0, l = array.length; i<l; i++){
        for(var j = i + 1; j < l; j++)
            if(array[i] == array[j]) j == ++i;
        r.push(array[i]);
    }
    return r;

}

function sort(json,field){
    json.sort(function (a, b) {
        return extractNum(a[field])- extractNum(b[field]);
    });
    return json;
}
function extractNum(str){
    var result= str.replace(/[^0-9]/ig,"");
    return result;
}
function jsonIsEqual(json,field){
    var array=[];
    // var result;
    $.each(json,function (i,item) {
        array.push(item[field]);
    })
    if(array.indexOf(null)!=-1 && isAllEqual(array)){
        //异常
        return "2"
    }else if(array.indexOf(null)==-1 && isAllEqual(array)){
        //正常且开关灯一致
        return "0"
    }else if(array.indexOf(null)!=-1 && !isAllEqual(array)){
        //异常常且开关灯不一致
        return "12"
    }else if(array.indexOf(null)==-1 && !isAllEqual(array)){
        //开关灯不一致
        return "1"
    }
}
function jsonIsEqual1(json,field){
    var array=[];
    $.each(json,function (i,item) {
        array.push(item[field]);
    })
    if(array.indexOf('0')!=-1 && isAllEqual(array)){
        return '0';
    }else if(array.indexOf('2')!=-1 && isAllEqual(array)){
        return '2';
    }else if(array.indexOf('12')!=-1 && isAllEqual(array)){
        return "12"
    }else if(array.indexOf("1")==-1 && !isAllEqual(array)){
        return "1"
    }
}
function sum(json,field,m) {
    var sum=0;
    var array=[];
    $.each(json,function (i,item) {
        array.push(item[field]);
    }) 
    for(var i=0;i<array.length;i++){
        if(array[i]==m){
            sum++;
        }
    }
    console.log('sum',sum);
    return sum;
}
$(".nave li").click(function () {
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
//lightState灯状态数据转换
function lightStateM(lightState) {
    var floorName = [], groupName = [], placeName = [],lightName=[], obj1 = {};
    $.each(lightState, function (i, item) {
        var mname = item.mname;
        var group = item.group;
        var place = item.place;
        var status = item.status;
        var lname=item.lname;
        var lmac=item.lmac;
        var y=item.y;
        if (!obj1[mname]) {
            var floorList = {
                mname: mname,
                centerLNum: 0,
                centerLNumTotal: 48,
                centerLNumState: "2",
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
            group: group,
            // groupTotal: 4,
            // groupState: "2",
            lightList:[]
        }
        groupName.push(groupList)
        var lightList={
            mname:mname,
            place: place,
            group: group,
            lname:lname,
            lmac:lmac,
            status: status,
            y:y
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
                        if (!obj3[item2.group]) {
                            var newGroup = {
                                mname: item2.mname,
                                place: item2.place,
                                group: item2.group,
                                groupNum: 0,
                                groupTotal: 4,
                                groupState: "2",
                                lightList:[]
                            }
                            placeArr[n].groupList.push(newGroup)
                            obj3[item2.group] = true;
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
                        var groupArr=placeArr[n].groupList;

                        for(var m=0;m<groupArr.length;m++){
                            if (groupArr[m].group == item2.group) {
                                if (!obj4[item2.lname]) {
                                    var newLight= {
                                        mname: item2.mname,
                                        place: item2.place,
                                        group: item2.group,
                                        lname:item2.lname,
                                        lmac:item2.lmac,
                                        status: item2.status,
                                        y:item2.y,
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
function statusM(status,blue) {
    var img, state,warning=false;
    if (status == 0) {
        //正常
        state = '正常';
        if(blue=='blue'){
            img = '<img src="/static/new/img/normal-white.png" alt="">';
        }else{
            img = '<img src="/static/new/img/normal.png" alt="">';
        }
        warning=false;
    } else if (status == 1) {
        //开关灯状态不一致
        state = '开关灯不一致';
        img = '<img src="/static/new/img/switch-un.png" alt="">';
        warning=false;
    } else if (status == 2) {
        //异常
        state = '异常';
        img = '<img src="/static/new/img/switch-abnormal.png" alt="">';
        warning=true;
    } else if (status == 12) {
        //开关灯状态不一致且异常
        state = '开关灯状态不一致异常状态（故障）';
        img = '<img src="/static/new/img/switch-un.png" alt=""><img src="/static/new/img/switch-abnormal.png" alt="">';
        warning=true;
    }
    return obj = {
        img: img,
        state: state,
        warning:warning
    }
}
function statusM1(status,blue) {
    var img, state,warning=false;
    if(status == 0){
        state = '开';
        if(blue=='blue'){
            img = '<img src="/static/new/img/normal-white.png" alt="">';
        }else{
            img = '<img src="/static/new/img/normal.png" alt="">';
        }
        warning=false;
    }else if(status == 1){
        state = '关';
        img = '<img src="/static/new/img/switch-un.png" alt="">';
        warning=false;
    }else if(status == null){
        state = '异常';
        img = '<img src="/static/new/img/switch-abnormal.png" alt="">';
        warning=false;
    }
    // if (status == 0) {
    //     //正常
    //     state = '正常';
    //     if(blue=='blue'){
    //         img = '<img src="/static/new/img/normal-white.png" alt="">';
    //     }else{
    //         img = '<img src="/static/new/img/normal.png" alt="">';
    //     }
    //     warning=false;
    // } else if (status == 1) {
    //     //开关灯状态不一致
    //     state = '开关灯不一致';
    //     img = '<img src="/static/new/img/switch-un.png" alt="">';
    //     warning=false;
    // } else if (status == 2) {
    //     //异常
    //     state = '异常';
    //     img = '<img src="/static/new/img/switch-abnormal.png" alt="">';
    //     warning=true;
    // } else if (status == 12) {
    //     //开关灯状态不一致且异常
    //     state = '开关灯状态不一致异常状态（故障）';
    //     img = '<img src="/static/new/img/switch-un.png" alt=""><img src="/static/new/img/switch-abnormal.png" alt="">';
    //     warning=true;
    // }
    return obj = {
        img: img,
        state: state,
        warning:warning
    }
}