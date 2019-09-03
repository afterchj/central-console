$(function () {
    var height1= $(window).height();
    var height2=$(document).height();
    var height3=$(document.body).height();
    var height4=$(document.body).outerHeight(true);
    console.log('height1',$(window).height()); //浏览器当前窗口可视区域高度
    console.log('height2',$(document).height()); //浏览器当前窗口文档的高度
    console.log('height3',$(document.body).height());//浏览器当前窗口文档body的高度
    console.log('height4',$(document.body).outerHeight(true));//浏览器当前窗口文档body的总高度 包括border padding margin

    var partListHeight=$('.leftNav-list li').height();
    console.log('partListHeight',partListHeight);
    var top1 = height1 - partListHeight;
    var top2 = height1 - 2*partListHeight;
    $('.to-bottom-1').css('top',top1);
    $('.to-bottom-2').css('top',top2);
    $('.left-timing-title li').click(function () {
        $(this).addClass('active').siblings('li').removeClass('active');
        $('.right-timing-detail ').addClass('active');
    });
    $('.leftNav2 .am-nav li').click(function () {
        $(this).addClass('active').siblings('li').removeClass('active');
    })
})
