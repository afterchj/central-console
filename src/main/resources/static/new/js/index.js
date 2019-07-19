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
        // let result3 = await ajaxGet(result2.url);
        console.log('result1 ---> ', result1);
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
