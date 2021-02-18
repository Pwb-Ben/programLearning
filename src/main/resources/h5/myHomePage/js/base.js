/**　---------- nav：スマホ用 ----------　**/
$(function(){
    $("#toggle").click(function(){
        $("#navigation").slideToggle();
        return false;
    });
    $(window).resize(function(){
        var win = $(window).width();
        var p = 768;
        if(win > p){
            $("#navigation").show();
        } else {
            $("#navigation").hide();
        }
    });
});
/**　---------- /nav ----------　**/


/**　---------- nav：スクロールでメニュー固定 ----------　**/
$(function(){
    var box    = $("nav");
    var boxTop = box.offset().top;
    $(window).scroll(function () {
        if($(window).scrollTop() >= boxTop) {
            box.addClass("fixed");
            $("body").css("margin-top","50px");
        } else {
            box.removeClass("fixed");
            $("body").css("margin-top","0px");
        }
    });
});
/**　---------- /nav ----------　**/

/**　---------- 画像保護 ----------　**/
$(window).bind('load', function() {
    $('img.protect').protectImage();
});
/**　---------- /画像保護 ----------　**/


/**　---------- scrollTop ----------　**/
$(function(){
    $('.scroll').click(function(){
        var speed = 500;
        var href= $(this).attr("href");
        var target = $(href == "#" || href == "" ? 'html' : href);
        var position = target.offset().top;
        $("html, body").animate({scrollTop:position}, speed, "swing");
        return false;
    });
});
/**　---------- /scrollTop ----------　**/