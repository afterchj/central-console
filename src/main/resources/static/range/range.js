// $.fn.RangeSlider = function(cfg){
//     this.sliderCfg = {
//         min: cfg && !isNaN(parseFloat(cfg.min)) ? Number(cfg.min) : null,
//         max: cfg && !isNaN(parseFloat(cfg.max)) ? Number(cfg.max) : null,
//         step: cfg && Number(cfg.step) ? cfg.step : 1,
//         callback: cfg && cfg.callback ? cfg.callback : null
//     };
//
//     var $input = $(this);
//     var min = this.sliderCfg.min;
//     var max = this.sliderCfg.max;
//     var step = this.sliderCfg.step;
//     var callback = this.sliderCfg.callback;
//     var left=0;
//     $input.attr('min', min)
//         .attr('max', max)
//         .attr('step', step);
//
//     $input.bind("input", function(e){
//         $input.attr('value', this.value);
//         $input.css( 'background-size', this.value + '% 100%' );
//         left=this.value - 10;
//         $input.prev().css('left',this.value + '%');
//         $input.prev().text(this.value + '%');
//         if ($.isFunction(callback)) {
//             callback(this);
//         }
//     });
// };

//滑动时的样式
// $.fn.RangeSlider = function(cfg){
//     this.sliderCfg = {
//         min: cfg && !isNaN(parseFloat(cfg.min)) ? Number(cfg.min) : null,
//         max: cfg && !isNaN(parseFloat(cfg.max)) ? Number(cfg.max) : null,
//         step: cfg && Number(cfg.step) ? cfg.step : 1,
//         callback: cfg && cfg.callback ? cfg.callback : null
//     };
//
//     var $input = $(this);
//     var min = this.sliderCfg.min;
//     var max = this.sliderCfg.max;
//     var step = this.sliderCfg.step;
//     var callback = this.sliderCfg.callback;
//
//     $input.attr('min', min)
//         .attr('max', max)
//         .attr('step', step);
//
//     $input.bind("input", function(e){
//         $input.attr('value', this.value);
//         $input.css( 'background-size', this.value + '% 100%' );
//
//         if ($.isFunction(callback)) {
//             callback(this);
//         }
//     });
// };
$.fn.RangeSlider = function(cfg){
    this.sliderCfg = {
        min: cfg && !isNaN(parseFloat(cfg.min)) ? Number(cfg.min) : null,
        max: cfg && !isNaN(parseFloat(cfg.max)) ? Number(cfg.max) : null,
        step: cfg && Number(cfg.step) ? cfg.step : 1,
        callback: cfg && cfg.callback ? cfg.callback : null
    };
    var $input = $(this);
    var min = this.sliderCfg.min;
    var max = this.sliderCfg.max;
    var step = this.sliderCfg.step;
    var callback = this.sliderCfg.callback;
    $input.attr('min', min)
        .attr('max', max)
        .attr('step', step);

    $input.bind("input", function(e){
        $input.attr('value', this.value);
        $input.css( 'background-size', this.value + '% 100%' );
        $input.prev('.show-hint').css('left',this.value + '%');
        $input.prev('.show-hint').text(this.value + '%');
        // console.log($input.siblings('.show-hint').text())
        if ($.isFunction(callback)) {
            callback(this);
        }
    });
};