/**
 * Created by yuanjie.fang on 2019/6/24.
 */
$(function(){
    // var lightState=[[${lightState}]];
    // console.log(lightState);

    light()
        setInterval(function () {
            light()
        }, 2000)

})
function light(){
    $.ajax({
        type: "post",
        url: "/getMonitor3",
        dataType: "json",
        success: function (data) {
           
            var lightState = data.lightState;
            var placeLNumList = data.placeLNumList;
            var centerLNumList = data.centerLNumList;
            console.log(lightState);
            $.each(lightState,function(i,val){
                var index = i;
                var group = val.group;
                var status = val.status;
                var lname=val.lname;
                $('.box-part .lamp').not('.hide').each(function(){
                    if(lname==$(this).attr('title')){
                        if(status==0){
                            $(this).addClass('on')
                        }else if(status==1){
                            $(this).addClass('off')
                        }else if(status==null){
                            $(this).addClass('disconnected')
                        }
                    }
                })
                // if(group==4){
                //     $('.box-1 .box-part .lamp.four').each(function ( ) {
                //         if(lname==$(this).attr('title')){
                //             if(status==0){
                //                 $(this).addClass('on')
                //             }else if(status==1){
                //                 $(this).addClass('off')
                //             }else if(status==null){
                //                 $(this).addClass('disconnected')
                //             }
                //         }
                //     })
                // }
            })
            $.each(placeLNumList,function(i,val){
                console.log(placeLNumList)
            })
        }
    })
}
$('.on-off').click(function(){
    $(this).addClass('active').siblings().removeClass('active')
})