$(function () {

})
// var way = {1:1,2:3,3:5,4:7,5:9,6:11};
$(".on-off").click(function () {
    console.log($(this));
    var src = $(this).attr('src');
    var topOneCheckboxState = $("top-one-checkbox").prop('checked');
    var topTwoCheckboxState = $("top-two-checkbox").prop('checked');

    if (src == '/static/img/switch-off.png'){
        $(this).attr('src','/static/img/switch-on.png');
    }else {
        $(this).attr('src','/static/img/switch-off.png');
    }
});