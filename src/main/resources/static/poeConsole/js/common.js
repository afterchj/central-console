$(function () {
    var height1= $(window).height();
    var partListHeight=$('.leftNav-list li').height();
    console.log('partListHeight',partListHeight);
    var top2 = partListHeight+15 ;
    $('.to-bottom-1').css('bottom','15px');
    $('.to-bottom-2').css('bottom',top2 );
    console.log('top2',top2);
    $('.left-timing-title li').click(function () {
        $(this).addClass('active').siblings('li').removeClass('active');
        $('.right-timing-detail ').addClass('active');
    });
    $('.leftNav2 .am-nav li').click(function () {
        $(this).addClass('active').siblings('li').removeClass('active');
    })
})
