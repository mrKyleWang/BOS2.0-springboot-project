//go to top
$(document).ready(function($){
    var offset = 300,
        offset_opacity = 1200,
        scroll_top_duration = 700,
        $back_to_top = $('.cd-top');

    $(window).scroll(function(){
        ( $(this).scrollTop() > offset ) ? $back_to_top.addClass('cd-is-visible') : $back_to_top.removeClass('cd-is-visible cd-fade-out');
        if( $(this).scrollTop() > offset_opacity ) {
            $back_to_top.addClass('cd-fade-out');
        }
    });
    $back_to_top.on('click', function(event){
        event.preventDefault();
        $('body,html').animate({
                scrollTop: 0,
            }, scroll_top_duration
        );
    });
	
	// 控制哪个页面被选中
	var currentLocation = window.location.href ;
	if(currentLocation.indexOf("#/")!=-1){
		var key = currentLocation.substring(currentLocation.indexOf("#/"),currentLocation.length);
		$("#bs-example-navbar-collapse-1 ul li").removeClass("active");
		$("a[href='"+key+"']").parent().addClass("active");
	}
	
	$("#bs-example-navbar-collapse-1 ul li").click(function(){
		$("#bs-example-navbar-collapse-1 ul li").removeClass("active");
		$(this).addClass("active");
	});
	
	
});
