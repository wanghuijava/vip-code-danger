if(!Array.prototype.map)
Array.prototype.map = function(fn,scope) {
  var result = [],ri = 0;
  for (var i = 0,n = this.length; i < n; i++){
	if(i in this){
	  result[ri++]  = fn.call(scope ,this[i],i,this);
	}
  }
return result;
};
var getWindowSize = function(){
return ["Height","Width"].map(function(name){
  return window["inner"+name] ||
	document.compatMode === "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ]
});
}
window.onload = function (){
	if(!+"\v1" && !document.querySelector) { // for IE6 IE7
	  document.body.onresize = resize;
	} else { 
	  window.onresize = resize;
	}
	function resize() {
		wSize();
		return false;
	}
}

function wSize(){
	//这是一字符串
	var str=getWindowSize();
	var strs= new Array(); //定义一数组
	strs=str.toString().split(","); //字符分割
	var heights = strs[0]-150,Body = $('body');$('#rightMain').height(heights);   
	//iframe.height = strs[0]-46;
	if(strs[1]<980){
		$('.header').css('width',980+'px');
		$('#content').css('width',980+'px');
		Body.attr('scroll','');
		Body.removeClass('objbody');
	}else{
		$('.header').css('width','auto');
		$('#content').css('width','auto');
		Body.attr('scroll','no');
		Body.addClass('objbody');
	}
	
	var openClose = $("#rightMain").height()+39;
	$('#center_frame').height(openClose+9);
	$("#openClose").height(openClose+30);	
	$("#Scroll").height(openClose-20);
	windowW();
}
wSize();
function windowW(){
	if($('#Scroll').height()<$("#leftMain").height()){
		$(".scroll").show();
	}else{
		$(".scroll").hide();
	}
}
windowW();

(function(){
    var addEvent = (function(){
             if (window.addEventListener) {
                return function(el, sType, fn, capture) {
                    el.addEventListener(sType, fn, (capture));
                };
            } else if (window.attachEvent) {
                return function(el, sType, fn, capture) {
                    el.attachEvent("on" + sType, fn);
                };
            } else {
                return function(){};
            }
        })(),
    Scroll = document.getElementById('Scroll');
    // IE6/IE7/IE8/Opera 10+/Safari5+
    addEvent(Scroll, 'mousewheel', function(event){
        event = window.event || event ;  
		if(event.wheelDelta <= 0 || event.detail > 0) {
				Scroll.scrollTop = Scroll.scrollTop + 29;
			} else {
				Scroll.scrollTop = Scroll.scrollTop - 29;
		}
    }, false);

    // Firefox 3.5+
    addEvent(Scroll, 'DOMMouseScroll',  function(event){
        event = window.event || event ;
		if(event.wheelDelta <= 0 || event.detail > 0) {
				Scroll.scrollTop = Scroll.scrollTop + 29;
			} else {
				Scroll.scrollTop = Scroll.scrollTop - 29;
		}
    }, false);
	
})();

function menuScroll(num){
	var Scroll = document.getElementById('Scroll');
	if(num==1){
		Scroll.scrollTop = Scroll.scrollTop - 60;
	}else{
		Scroll.scrollTop = Scroll.scrollTop + 60;
	}
}

//左侧开关
$("#openClose").click(function(){
	if($(this).data('clicknum')==1) {
		$("html").removeClass("on");
		$(".left_menu").removeClass("left_menu_on");
		$(this).removeClass("close");
		$(this).data('clicknum', 0);
		$(".scroll").show();
	} else {
		$(".left_menu").addClass("left_menu_on");
		$(this).addClass("close");
		$("html").addClass("on");
		$(this).data('clicknum', 1);
		$(".scroll").hide();
	}
	return false;
});

$(".top_menu").on("click", function(){
	var param = $(this).attr("data");
	$.ajax({
		url:"/menu/" + param,
		type:"GET",
		success:function(rsp){
			$("#leftMain").html(rsp);
			$("#leftMain ul:first li:first a").click();
		}			
	})
	$(".top_menu").removeClass("on");
	$(this).addClass("on");
	$("#current_pos").html($(this).text() + " ");
	
	if($("#openClose").data('clicknum')==1){
		$("#openClose").click();
	}
});

$(".switchs").live("click", function(){
	var ul = $(this).parent().next();
	ul.toggle();
	if(ul.is(":hidden")){
		$(this).removeClass("on");
	}
	else{
		$(this).addClass("on");
	}
});

$("#leftMain a").live("click",function(e){
	e.preventDefault();
	$(this).parent().siblings().removeClass("on");
	$(this).parent().addClass("on");
	var href=$("#rightMain").attr("src");
		$("#rightMain").attr("src", $(this).attr("href"));
	var subTitle = $(this).parent().parent().prev().text();
	var clickItemName = $(this).text();
	var topTitle = $("#current_pos").html().split('&gt;')[0];
	$("#current_pos").html(topTitle + " &gt; " + subTitle + " &gt; " + clickItemName );
});

function closeDialog(dialogId){
	var dialog = art.dialog.get(dialogId);
	dialog.close();
}

$.ajaxSetup({
	beforeSend:function(xhr, settings){
		var url = settings.url;
		settings.url= adminUrl + url;
	}
});

//Init
$(document).ready(function(){
	$(".top_menu:first").click();
	
	$(".sub_menu").on("click",function(){
		
		
	})
	
});