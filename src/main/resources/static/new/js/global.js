/**
 * Created by yuanjie.fang on 2019/7/18.
 */


$('.nave').on('mouseenter mouseleave','li',function(){
    var src=$(this).find('.left-img img').attr('src');
    if(src && src.indexOf('normal')!=-1){
        if(src=='/static/new/img/normal.png'){
            $(this).find('.left-img img').attr('src','/static/new/img/normal-white.png');
        }else{
            $(this).find('.left-img img').attr('src','/static/new/img/normal.png');
        }
    }
});

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

//提取字符串中的数字
function getNum(text){
    var value;
    if(isNaN(text)){
        value = text.replace(/[^0-9]/ig,"");
    }else{
        value=text;
    }
    return value;
}



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
//状态判断
function statusJudgement(exception,diff,active){
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
        if(active==''){
            statusImg = '<img src="/static/new/img/normal.png" alt="">';
        }else{
            statusImg = '<img src="/static/new/img/normal-white.png" alt="">';
        }

    }
    var obj={
        status:status,
        statusImg:statusImg
    }
    return obj;
}

//左侧数据初始化
function ajaxLeftNav() {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: '/new/getNewMonitor', // 此处为错误的 url
            type: 'POST',
            dataType: "json",
            success: function (res) {
                resolve(res)
                console.log(res);
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
                    var status=statusJudgement(exception,diff,active).status;
                    var statusImg=statusJudgement(exception,diff,active).statusImg;
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