
$(function () {

    var fiveOnePlaceImg;
    var fiveImg;
    //5楼区域1
    fiveOnePlaceImg = $("#five-oneplace").find("img").attr("src");
    fiveImg = $("#five-oneplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#five-oneplace-state").empty();
            $("#five-oneplace-state").append('<img src="img/2.png" >');
            $("#five-oneplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#five-oneplace-state").empty();
            $("#five-oneplace").removeClass("warning");
            $("#five-oneplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#five-oneplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#five-oneplace-state").empty();
            $("#five-oneplace").removeClass("warning");
            $("#five-oneplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#five-oneplace-state").empty();
            $("#five-oneplace").removeClass("warning");
            $("#five-oneplace-state").text("OFF")
        }
    }

    //5楼区域2
    fiveOnePlaceImg = $("#five-twoplace").find("img").attr("src");
    fiveImg = $("#five-twoplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#five-twoplace-state").empty();
            $("#five-twoplace-state").append('<img src="img/2.png" >');
            $("#five-twoplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#five-twoplace-state").empty();
            $("#five-twoplace").removeClass("warning");
            $("#five-twoplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#five-twoplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#five-twoplace-state").empty();
            $("#five-twoplace").removeClass("warning");
            $("#five-twoplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#five-twoplace-state").empty();
            $("#five-twoplace").removeClass("warning");
            $("#five-twoplace-state").text("OFF")
        }
    }
    //5楼区域3
    fiveOnePlaceImg = $("#five-thirdplace").find("img").attr("src");
    fiveImg = $("#five-thirdplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#five-thirdplace-state").empty();
            $("#five-thirdplace-state").append('<img src="img/2.png" >');
            $("#five-thirdplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#five-thirdplace-state").empty();
            $("#five-thirdplace").removeClass("warning");
            $("#five-thirdplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#five-thirdplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#five-thirdplace-state").empty();
            $("#five-thirdplace").removeClass("warning");
            $("#five-thirdplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#five-thirdplace-state").empty();
            $("#five-thirdplace").removeClass("warning");
            $("#five-thirdplace-state").text("OFF")
        }
    }
    //5楼区域4
    fiveOnePlaceImg = $("#five-fourplace").find("img").attr("src");
    fiveImg = $("#five-fourplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#five-fourplace-state").empty();
            $("#five-fourplace-state").append('<img src="img/2.png" >');
            $("#five-fourplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#five-fourplace-state").empty();
            $("#five-fourplace").removeClass("warning");
            $("#five-fourplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#five-fourplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#five-fourplace-state").empty();
            $("#five-fourplace").removeClass("warning");
            $("#five-fourplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#five-fourplace-state").empty();
            $("#five-fourplace").removeClass("warning");
            $("#five-fourplace-state").text("OFF")
        }
    }
    //4楼区域1
    fiveOnePlaceImg = $("#four-oneplace").find("img").attr("src");
    fiveImg = $("#four-oneplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#four-oneplace-state").empty();
            $("#four-oneplace-state").append('<img src="img/2.png" >');
            $("#four-oneplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#four-oneplace-state").empty();
            $("#four-oneplace").removeClass("warning");
            $("#four-oneplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#four-oneplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#four-oneplace-state").empty();
            $("#four-oneplace").removeClass("warning");
            $("#four-oneplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#four-oneplace-state").empty();
            $("#four-oneplace").removeClass("warning");
            $("#four-oneplace-state").text("OFF")
        }
    }

    //4楼区域2
    fiveOnePlaceImg = $("#four-twoplace").find("img").attr("src");
    fiveImg = $("#four-twoplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#four-twoplace-state").empty();
            $("#four-twoplace-state").append('<img src="img/2.png" >');
            $("#four-twoplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#four-twoplace-state").empty();
            $("#four-twoplace").removeClass("warning");
            $("#four-twoplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#four-twoplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#four-twoplace-state").empty();
            $("#four-twoplace").removeClass("warning");
            $("#four-twoplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#four-twoplace-state").empty();
            $("#four-twoplace").removeClass("warning");
            $("#four-twoplace-state").text("OFF")
        }
    }
    //4楼区域3
    fiveOnePlaceImg = $("#four-thirdplace").find("img").attr("src");
    fiveImg = $("#four-thirdplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#four-thirdplace-state").empty();
            $("#four-thirdplace-state").append('<img src="img/2.png" >');
            $("#four-thirdplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#four-thirdplace-state").empty();
            $("#four-thirdplace").removeClass("warning");
            $("#four-thirdplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#four-thirdplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#four-thirdplace-state").empty();
            $("#four-thirdplace").removeClass("warning");
            $("#four-thirdplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#four-thirdplace-state").empty();
            $("#four-thirdplace").removeClass("warning");
            $("#four-thirdplace-state").text("OFF")
        }
    }
    //4楼区域4
    fiveOnePlaceImg = $("#four-fourplace").find("img").attr("src");
    fiveImg = $("#four-fourplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#four-fourplace-state").empty();
            $("#four-fourplace-state").append('<img src="img/2.png" >');
            $("#four-fourplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#four-fourplace-state").empty();
            $("#four-fourplace").removeClass("warning");
            $("#four-fourplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#four-fourplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#four-fourplace-state").empty();
            $("#four-fourplace").removeClass("warning");
            $("#four-fourplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#four-fourplace-state").empty();
            $("#four-fourplace").removeClass("warning");
            $("#four-fourplace-state").text("OFF")
        }
    }
    //3楼区域1
    fiveOnePlaceImg = $("#third-oneplace").find("img").attr("src");
    fiveImg = $("#third-oneplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#third-oneplace-state").empty();
            $("#third-oneplace-state").append('<img src="img/2.png" >');
            $("#third-oneplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#third-oneplace-state").empty();
            $("#third-oneplace").removeClass("warning");
            $("#third-oneplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#third-oneplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#third-oneplace-state").empty();
            $("#third-oneplace").removeClass("warning");
            $("#third-oneplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#third-oneplace-state").empty();
            $("#third-oneplace").removeClass("warning");
            $("#third-oneplace-state").text("OFF")
        }
    }

    //3楼区域2
    fiveOnePlaceImg = $("#third-twoplace").find("img").attr("src");
    fiveImg = $("#third-twoplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#third-twoplace-state").empty();
            $("#third-twoplace-state").append('<img src="img/2.png" >');
            $("#third-twoplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#third-twoplace-state").empty();
            $("#third-twoplace").removeClass("warning");
            $("#third-twoplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#third-twoplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#third-twoplace-state").empty();
            $("#third-twoplace").removeClass("warning");
            $("#third-twoplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#third-twoplace-state").empty();
            $("#third-twoplace").removeClass("warning");
            $("#third-twoplace-state").text("OFF")
        }
    }
    //3楼区域3
    fiveOnePlaceImg = $("#third-thirdplace").find("img").attr("src");
    fiveImg = $("#third-thirdplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#third-thirdplace-state").empty();
            $("#third-thirdplace-state").append('<img src="img/2.png" >');
            $("#third-thirdplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#third-thirdplace-state").empty();
            $("#third-thirdplace").removeClass("warning");
            $("#third-thirdplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#third-thirdplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#third-thirdplace-state").empty();
            $("#third-thirdplace").removeClass("warning");
            $("#third-thirdplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#third-thirdplace-state").empty();
            $("#third-thirdplace").removeClass("warning");
            $("#third-thirdplace-state").text("OFF")
        }
    }
    //3楼区域4
    fiveOnePlaceImg = $("#third-fourplace").find("img").attr("src");
    fiveImg = $("#third-fourplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#third-fourplace-state").empty();
            $("#third-fourplace-state").append('<img src="img/2.png" >');
            $("#third-fourplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#third-fourplace-state").empty();
            $("#third-fourplace").removeClass("warning");
            $("#third-fourplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#third-fourplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#third-fourplace-state").empty();
            $("#third-fourplace").removeClass("warning");
            $("#third-fourplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#third-fourplace-state").empty();
            $("#third-fourplace").removeClass("warning");
            $("#third-fourplace-state").text("OFF")
        }
    }

    //2楼区域1
    fiveOnePlaceImg = $("#two-oneplace").find("img").attr("src");
    fiveImg = $("#two-oneplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#two-oneplace-state").empty();
            $("#two-oneplace-state").append('<img src="img/2.png" >');
            $("#two-oneplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#two-oneplace-state").empty();
            $("#two-oneplace").removeClass("warning");
            $("#two-oneplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#two-oneplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#two-oneplace-state").empty();
            $("#two-oneplace").removeClass("warning");
            $("#two-oneplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#two-oneplace-state").empty();
            $("#two-oneplace").removeClass("warning");
            $("#two-oneplace-state").text("OFF")
        }
    }

    //2楼区域2
    fiveOnePlaceImg = $("#two-twoplace").find("img").attr("src");
    fiveImg = $("#two-twoplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#two-twoplace-state").empty();
            $("#two-twoplace-state").append('<img src="img/2.png" >');
            $("#two-twoplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#two-twoplace-state").empty();
            $("#two-twoplace").removeClass("warning");
            $("#two-twoplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#two-twoplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#two-twoplace-state").empty();
            $("#two-twoplace").removeClass("warning");
            $("#two-twoplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#two-twoplace-state").empty();
            $("#two-twoplace").removeClass("warning");
            $("#two-twoplace-state").text("OFF")
        }
    }
    //2楼区域3
    fiveOnePlaceImg = $("#two-thirdplace").find("img").attr("src");
    fiveImg = $("#two-thirdplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#two-thirdplace-state").empty();
            $("#two-thirdplace-state").append('<img src="img/2.png" >');
            $("#two-thirdplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#two-thirdplace-state").empty();
            $("#two-thirdplace").removeClass("warning");
            $("#two-thirdplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#two-thirdplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#two-thirdplace-state").empty();
            $("#two-thirdplace").removeClass("warning");
            $("#two-thirdplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#two-thirdplace-state").empty();
            $("#two-thirdplace").removeClass("warning");
            $("#two-thirdplace-state").text("OFF")
        }
    }
    //2楼区域4
    fiveOnePlaceImg = $("#two-fourplace").find("img").attr("src");
    fiveImg = $("#two-fourplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#two-fourplace-state").empty();
            $("#two-fourplace-state").append('<img src="img/2.png" >');
            $("#two-fourplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#two-fourplace-state").empty();
            $("#two-fourplace").removeClass("warning");
            $("#two-fourplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#two-fourplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#two-fourplace-state").empty();
            $("#two-fourplace").removeClass("warning");
            $("#two-fourplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#two-fourplace-state").empty();
            $("#two-fourplace").removeClass("warning");
            $("#two-fourplace-state").text("OFF")
        }
    }
    //1楼区域1
    fiveOnePlaceImg = $("#one-oneplace").find("img").attr("src");
    fiveImg = $("#one-oneplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#one-oneplace-state").empty();
            $("#one-oneplace-state").append('<img src="img/2.png" >');
            $("#one-oneplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#one-oneplace-state").empty();
            $("#one-oneplace").removeClass("warning");
            $("#one-oneplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#one-oneplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#one-oneplace-state").empty();
            $("#one-oneplace").removeClass("warning");
            $("#one-oneplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#one-oneplace-state").empty();
            $("#one-oneplace").removeClass("warning");
            $("#one-oneplace-state").text("OFF")
        }
    }

    //1楼区域2
    fiveOnePlaceImg = $("#one-twoplace").find("img").attr("src");
    fiveImg = $("#one-twoplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#one-twoplace-state").empty();
            $("#one-twoplace-state").append('<img src="img/2.png" >');
            $("#one-twoplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#one-twoplace-state").empty();
            $("#one-twoplace").removeClass("warning");
            $("#one-twoplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#one-twoplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#one-twoplace-state").empty();
            $("#one-twoplace").removeClass("warning");
            $("#one-twoplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#one-twoplace-state").empty();
            $("#one-twoplace").removeClass("warning");
            $("#one-twoplace-state").text("OFF")
        }
    }
    //1楼区域3
    fiveOnePlaceImg = $("#one-thirdplace").find("img").attr("src");
    fiveImg = $("#one-thirdplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#one-thirdplace-state").empty();
            $("#one-thirdplace-state").append('<img src="img/2.png" >');
            $("#one-thirdplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#one-thirdplace-state").empty();
            $("#one-thirdplace").removeClass("warning");
            $("#one-thirdplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#one-thirdplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#one-thirdplace-state").empty();
            $("#one-thirdplace").removeClass("warning");
            $("#one-thirdplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#one-thirdplace-state").empty();
            $("#one-thirdplace").removeClass("warning");
            $("#one-thirdplace-state").text("OFF")
        }
    }
    //1楼区域4
    fiveOnePlaceImg = $("#one-fourplace").find("img").attr("src");
    fiveImg = $("#one-fourplace").find("img");
    $(fiveImg).each(function (key,value) {
        var img = $(value).attr("src");
        if (img=='img/5.png'){
            //区域灯异常
            $("#one-fourplace-state").empty();
            $("#one-fourplace-state").append('<img src="img/2.png" >');
            $("#one-fourplace").addClass("warning");
            return false;
        }
        if (fiveOnePlaceImg!=img){
            //区域灯状态不一致
            $("#one-fourplace-state").empty();
            $("#one-fourplace").removeClass("warning");
            $("#one-fourplace-state").append('<img src="img/1.png" >');
        }
    });
    if ($("#one-fourplace-state img").length<=0){
        //区域灯状态一致
        if (fiveOnePlaceImg=='img/3.PNG'){
            //灯亮
            $("#one-fourplace-state").empty();
            $("#one-fourplace").removeClass("warning");
            $("#one-fourplace-state").text("ON")
        }else if (fiveOnePlaceImg=='img/4.PNG'){
            //灯关
            $("#one-fourplace-state").empty();
            $("#one-fourplace").removeClass("warning");
            $("#one-fourplace-state").text("OFF")
        }
    }

    //每层灯状态
    var fiveOnePlace = $("#five-num").text();
    var fiveOnePlaceTotal = $("#fiv-num-total").text();

    if(fiveOnePlace!=fiveOnePlaceTotal){
        $("#five-hinit img:eq(0)").css("display","none");
        $("#five-hinit img:eq(1)").css("display","none");
        $("#five-hinit img:eq(0)").css("display","inline");
    }else if ($("#five-oneplace-state img").attr("src")=='img/1.png'||$("#five-twoplace-state img").attr("src")=='img/1.png'||$("#five-thirdplace-state img").attr("src")=='img/1.png'||$("#five-fourplace-state img").attr("src")=='img/1.png'){
        $("#five-hinit img:eq(0)").css("display","none");
        $("#five-hinit img:eq(1)").css("display","none");
        $("#five-hinit img:eq(1)").css("display","inline");
    }else {
        $("#five-hinit img:eq(0)").css("display","none");
        $("#five-hinit img:eq(1)").css("display","none");
    }


    if(($("#four-num").text())!=($("#four-num-total").text())){
        $("#four-hinit img:eq(0)").css("display","none");
        $("#four-hinit img:eq(1)").css("display","none");
        $("#four-hinit img:eq(0)").css("display","inline");
    }else if ($("#four-oneplace-state img").attr("src")=='img/1.png'||$("#four-twoplace-state img").attr("src")=='img/1.png'||$("#four-thirdplace-state img").attr("src")=='img/1.png'||$("#four-fourplace-state img").attr("src")=='img/1.png'){
        $("#four-hinit img:eq(0)").css("display","none");
        $("#four-hinit img:eq(1)").css("display","none");
        $("#four-hinit img:eq(1)").css("display","inline");
    } else {
        $("#four-hinit img:eq(0)").css("display","none");
        $("#four-hinit img:eq(1)").css("display","none");
    }


    if(($("#third-num").text())!=($("#third-num-total").text())){
        $("#third-hinit img:eq(0)").css("display","none");
        $("#third-hinit img:eq(1)").css("display","none");
        $("#third-hinit img:eq(0)").css("display","inline");
    }else if ($("#third-oneplace-state img").attr("src")=='img/1.png'||$("#third-twoplace-state img").attr("src")=='img/1.png'||$("#third-thirdplace-state img").attr("src")=='img/1.png'||$("#third-fourplace-state img").attr("src")=='img/1.png'){
        $("#third-hinit img:eq(0)").css("display","none");
        $("#third-hinit img:eq(1)").css("display","none");
        $("#third-hinit img:eq(1)").css("display","inline");
    }else {
        $("#third-hinit img:eq(0)").css("display","none");
        $("#third-hinit img:eq(1)").css("display","none");
    }

    if(($("#two-num").text())!=($("#two-num-total").text())){

        $("#two-hinit img:eq(0)").css("display","none");
        $("#two-hinit img:eq(1)").css("display","none");
        $("#two-hinit img:eq(0)").css("display","inline");
    }else if ($("#two-oneplace-state img").attr("src")=='img/1.png'||$("#two-twoplace-state img").attr("src")=='img/1.png'||$("#two-thirdplace-state img").attr("src")=='img/1.png'||$("#five-fourplace-state img").attr("src")=='img/1.png'){
        $("#two-hinit img:eq(0)").css("display","none");
        $("#two-hinit img:eq(1)").css("display","none");
        $("#two-hinit img:eq(1)").css("display","inline");
    }else {
        $("#two-hinit img:eq(0)").css("display","none");
        $("#two-hinit img:eq(1)").css("display","none");
    }

    if(($("#one-num").text())!=($("#one-num-total").text())){
        $("#one-hinit img:eq(0)").css("display","none");
        $("#one-hinit img:eq(1)").css("display","none");
        $("#one-hinit img:eq(0)").css("display","inline");
    }else if ($("#one-oneplace-state img").attr("src")=='img/1.png'||$("#one-twoplace-state img").attr("src")=='img/1.png'||$("#one-thirdplace-state img").attr("src")=='img/1.png'||$("#one-fourplace-state img").attr("src")=='img/1.png'){
        $("#one-hinit img:eq(0)").css("display","none");
        $("#one-hinit img:eq(1)").css("display","none");
        $("#one-hinit img:eq(1)").css("display","inline");
    }else {
        $("#one-hinit img:eq(0)").css("display","none");
        $("#one-hinit img:eq(1)").css("display","none");
    }

});