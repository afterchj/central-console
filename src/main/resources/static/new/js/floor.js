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
    waterbubbleS('#floor-1', '30%', pDefault, pDefault, 24, 0.3, pDefault, '#FE4C40', pDefault, pDefault)
    waterbubbleS('#floor-2', '30%', pDefault, pDefault, 24, 0.3, pDefault, '#FE4C40', pDefault, pDefault)
    waterbubbleS('#floor-3', '30%', pDefault, pDefault, 24, 0.3, pDefault, '#FE4C40', pDefault, pDefault)
    waterbubbleS('#floor-4', '30%', pDefault, pDefault, 24, 0.3, pDefault, '#FE4C40', pDefault, pDefault)
    waterbubbleS('#floor-5', '30%', pDefault, pDefault, 24, 0.3, pDefault, '#FE4C40', pDefault, pDefault)
}