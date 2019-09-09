$(function () {
    // var height1= $(window).height();
    // var partListHeight=$('.leftNav-list li').height();
    // console.log('partListHeight',partListHeight);
    //
    // var top2 = partListHeight+5 ;
    // $('.to-bottom-1').css('bottom','20px');
    // $('.to-bottom-2').css('bottom',top2);
    // console.log('top2',top2);
    // $('.data-show').hide();
    $('.updown-data .create-mesh-list').click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        $(this).parent('.updown-data ').toggleClass('active');
    })
    $('.left-timing-title li').click(function () {
        $(this).addClass('active').siblings('li').removeClass('active');
        $('.right-timing-detail ').addClass('active');
    });
    $('.leftNav2 .am-nav li').click(function () {
        $(this).addClass('active').siblings('li').removeClass('active');
    });
    // $('.modal-footer .btn+.btn, .modal-footer .btn').click(function () {
    //     $('.data-show-table table .am-dropdown-content').stopPropagation();
    // });
    // $("body").on('click','[data-am-dropdown-toggle]',function (e) {
    //     e.stopPropagation();
    //     e.stopPropagation();
    // });
})
