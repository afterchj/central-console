// $(function () {
//     // var height1= $(window).height();
//     // var partListHeight=$('.leftNav-list li').height();
//     // console.log('partListHeight',partListHeight);
//     //
//     // var top2 = partListHeight+5 ;
//     // $('.to-bottom-1').css('bottom','20px');
//     // $('.to-bottom-2').css('bottom',top2);
//     // console.log('top2',top2);
//     // $('.data-show').hide();
//     var meshId=getParam('meshId');
//     console.log(meshId);
//     // $('.updown-data .create-mesh-list').click(function (e) {
//     //     e.preventDefault();
//     //     e.stopPropagation();
//     //     $(this).parent('.updown-data ').toggleClass('active');
//     // })
$(function () {
    var height = $('.topNav ').height();
    console.log('height',height);
    $('.leftNav2').css('height',height);
    $('.left-timing-title li:first-child').addClass('active');
    $('.left-timing-title li').click(function () {
        $(this).addClass('active').siblings('li').removeClass('active');
        $('.right-timing-detail ').addClass('active');
    });
    $("input").focus(function(){
        $(" .modal.in .modal-dialog ").addClass('scroll');
    });
    $("input").blur(function(){
        $(" .modal.in .modal-dialog ").removeClass('scroll');
    });
})

//     $('.leftNav2 .am-nav li').click(function () {
//         $(this).addClass('active').siblings('li').removeClass('active');
//     });
//     $('.leftNav2 .am-nav li').each(function () {
//         var text=$(this).attr('alt');
//         console.log(text);
//         if(meshId==text){
//             $(this).parent('li').addClass('active');
//         }else{
//             $(this).parent('li').removeClass('active');
//         }
//     })
//     // $('.modal-footer .btn+.btn, .modal-footer .btn').click(function () {
//     //     $('.data-show-table table .am-dropdown-content').stopPropagation();
//     // });
//     // $("body").on('click','[data-am-dropdown-toggle]',function (e) {
//     //     e.stopPropagation();
//     //     e.stopPropagation();
//     // });
// })
/**
 * 获取指定的URL参数值
 * URL:http://www.quwan.com/index?name=tyler
 * 参数：paramName URL参数
 * 调用方法:getParam("name")
 * 返回值:tyler
 */
// function getParam(paramName) {
//     paramValue = "", isFound = !1;
//     if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
//         arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;
//         while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++
//     }
//     return paramValue == "" && (paramValue = null), paramValue
// }