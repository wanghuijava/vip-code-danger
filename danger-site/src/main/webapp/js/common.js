window.SERVICECONTEXT=siteUrl;
window.IMAGECONTEXT="";
(function (config) {
    config['lock'] = true;
    config['fixed'] = true;
    config['padding'] = '0 0 0 0'; 
    config['top'] = '50%'; 
    config['background']='#eee';
})(art.dialog.defaults);

//hotfix for ie8
var _console={
	assert:function(str){}
	,clear:function(str){}
	,count:function(str){}
	,debug:function(str){}
	,dir:function(str){}
	,dirxml:function(str){}
	,error:function(str){}
	,group:function(str){}
	,groupCollapsed:function(str){}
	,groupEnd:function(str){}
	,info:function(str){}
	,log:function(str){}
	,profile:function(str){}
	,profileEnd:function(str){}
	,table:function(str){}
	,time:function(str){}
	,timeEnd:function(str){}
	,timeStamp:function(str){}
	,trace:function(str){}
	,warn:function(str){}
}
window.console=window.console?window.console:_console

var _mask=null;
$.ajaxSetup({
	beforeSend:function(xhr,b,c){
		this.url=siteUrl+this.url;
		var token=storageProvider.get("token");
		xhr.setRequestHeader("token",token);
		if(!this.nomask){
			_mask=top.showMask()
		}
	}
	,cache:false
	,complete:function(response){
		if(response.status=="200"){
			_mask&&_mask.close()&&(_mask=null);
		}else if(response.status=="500"){
			top.tips("服务器出错!",function(){
				//top.location="/login";
				_mask&&_mask.close()&&(_mask=null);
			})
		}
	}
})



$(document).ready(function(){
	
	//获取用户,如果用户为空则返回登录页
	if(!userFactory.getUser()){
		//userFactory.logout();
	}
	
	//自定义滚动
//	$("[x-scrollable]").mCustomScrollbar({
//        theme: "dark"
//        ,scrollInertia:0
//        ,callbacks:{
//        	whileScrolling:function(){
//        		setTimeout(function(){
//        			$("[poshy-tip]").each(function(){
//		    			$(this).poshytip("refresh");
//		    		})
//        		},500)
//        	}
//        }
//    });
	
	//自定义滚动
//	$("[x-scrollable-x]").mCustomScrollbar({
//        theme: "dark"
//		,axis:"x"
//        ,scrollInertia:0
//    });

//    if($.browser.msie&&$.browser.version<=7){
//		var scollFn=ie7Scroll();
//		scollFn();
//		$(window).resize(function(){
//			scollFn();
//		})
//	}
    
	
    //分类检索子系统排序默认初始值
    $(".sort-group label").each(function(){
    	var $this=$(this);
    	var $par=$this.closest(".sort-group");
    	var _name=$par.find('[name="page.orderBy"]').val();
    	var _value=$par.find('[name="page.dir"]').val();
    	var name=$this.data("sort-name");
    	var value=$this.data("sort-dir");
    	if(_name==name){
    		$par.find('[name="page.dir"]').val(value);
    		$this.addClass(_value);
    		return false;
    	}
    })
    
    //为表头增加描述
    $("thead td,thead th").each(function(){
    	var $this=$(this);
    	$this.attr("title",$this.text());
    })
    
    //加载组件
    var ctrl=new customControll();
	ctrl.scan();
    
	//为IE6,7,8增加placeholder
	if(!("placeholder" in document.createElement("input"))){
		$('[placeholder]!=""').each(function(){
			var $this=$(this);
			var $par=$this.parent();
			if($par.css("position")=="static"){$par.css("position","relative")}
			var isAdded=$par.data("added");
			var _val=$.trim($this.val());
			var _short="";
			if(_val!=""){_short='style="display:none;"'}
			var _str='<span class="placeholder" '+_short+' title="'+$this.attr("placeholder")+'" style="width:'+$this.width()+'px">'+$this.attr("placeholder")+'</span>';
			$par.data("added",true);
			if(!isAdded){
				$this.after(_str);
			}
		})
	}
	
}).on("keydown",function(e){
	if(e.which==8){
		var ta=e.target;
		var $ta=$(ta);
		if($ta.attr("readonly")||$ta.attr("disabled")){return false;}
		if(ta.tagName.toLowerCase()=="input"){
			if($ta.is("[type='checkbox']")||$ta.is("[type='radio']")){
				return false;
			}
		}else if(!$ta.is("textarea")&&!$ta.is('[contenteditable]')){
			return false;
		}
	}
}).on("click focus",".placeholder",function(){
	var $this=$(this);
	var _par=$this.parent();
	var _ipt=_par.find("[type=text],[type=password],textarea");
	_ipt.click().focus();
	$this.hide();
}).on("change","[type=text],[type=password],textarea",function(){
	var $this=$(this).removeClass("focus");;
	var _par=$this.parent();
	var _holder=_par.find(".placeholder");
	var _val=$.trim($this.val());
	if(_val==""){
		_holder.show();
	}
}).on("blur","[type=text],[type=password],textarea",function(){
	var $this=$(this).removeClass("focus");;
	var _par=$this.parent();
	var _holder=_par.find(".placeholder");
	var _val=$.trim($this.val());
	if(_val==""){
		_holder.show();
	}
}).on("focus","[type=text],[type=password],textarea",function(){
	var $this=$(this).addClass("focus");
	var _par=$this.parent();
	var _holder=_par.find(".placeholder");
		_holder.hide();
}).on("click","[data-top-href]",function(){
	var href=$(this).data("top-href");
	top.location=href;
}).on("click","[data-href]",function(){
	var href=$(this).data("href");
	location=href;
}).on("click",".layout-js-side .expander",function(){
	$(this).closest(".layout-full").toggleClass("collapsed");
}).on("click",".filter-list dd li",function(){
	var $this=$(this);
	if ($this.hasClass("active")) {
		return false;
	}
	var $par=$this.closest("dd");
	$par.find("li").removeClass("active");
	$this.addClass("active");
	$par.find("[type=hidden]").remove();
	var str="";
	var labels=""
	var _name=$this.parents("ul").data("name");
	var _guides=[];
	$par.find(".active").each(function(){
		var _$this=$(this);
		var id=_$this.attr("data-id");
		_guides.push(id)
		if(_$this.data("id"))
		labels+="<li data-name='"+_name+"' data-id='"+id+"'><span>"+$this.text()+"<a href='javascript:;'>×</a></span></li>"
	})

	str+="<input type='hidden' tabindex='-1' name='"+_name+"' value='"+_guides.join(",")+"'/>"
	$par.append(str);
	$this.closest("form").trigger("submit");
	var $set=$(".filter-set ul");
	var $currItem=$set.find("[data-name='"+_name+"']");
	if($currItem.size()>0){
		$currItem.remove();
	}
	$set.append(labels);
	
}).on("click",".filter-set ul li a",function(){
	var $li=$(this).closest("li");
	var name=$li.data("name");
	var id=$li.attr("data-id");
	var $ul=$('ul[data-name="'+name+'"]');
	var _$li=$ul.find('[data-id="'+id+'"]');
	_$li.removeClass("active");
	$ul.next("[type=hidden]").remove();
	var str="";
	var labels=""
	var _name=name;
	var _guides=[];
	$ul.find(".active").each(function(){
		var _$this=$(this);
		var id=_$this.attr("data-id");
		_guides.push(id)
		if(_$this.data("id"))
		labels+="<li data-name='"+_name+"' data-id='"+id+"'><span>"+_$this.text()+"<a href='javascript:;'>×</a></span></li>"
	})

	str+="<input type='hidden' tabindex='-1' name='"+_name+"' value='"+_guides.join(",")+"'/>"
	$ul.after(str);
	$ul.closest("form").trigger("submit");
	var $set=$(".filter-set ul");
	var $currItem=$set.find("[data-name='"+_name+"']");
	if($currItem.size()>0){
		$currItem.remove();
	}
	$set.append(labels);
	$li.remove();
}).on("click",".sort-group label",function(){
	var $this=$(this);
	var $par=$this.parent();
	$this.removeClass("desc asc");
	$this.siblings().removeClass("desc asc");
	var _name=$this.attr("data-sort-name");
	var _dir=$this.attr("data-sort-dir");
	if(_dir=="asc"){
		_dir="desc";
		$this.addClass("desc");
		$this.attr("data-sort-dir","desc");
	}else{
		_dir="asc";
		$this.addClass("asc");
		$this.attr("data-sort-dir","asc");
	}
	$par.find("[name='page.orderBy']").val(_name);
	$par.find("[name='page.dir']").val(_dir);
	$par.closest("form").trigger("former.search");
}).on("click","[data-role='site-map']",function(){
	$(this).toggleClass("active");
	$(".site-map").toggle();
}).on("click","[data-post-url]",function(e){
	var $this=$(this);
	var str=$this.data("confirm");
		str=str?str:"是否删除该信息?"
	top.confirm(str,function(){
		e.preventDefault();
		var url=$this.data("post-url");
		var param=$this.data("post-param");
			http.post({
				url:url
				,data:param
				,success:function(res){
					tips(res.message,function(){
						$this.closest("form").trigger("submit");
					});
				}
			})
	})
}).on("click",".layout-search .expander",function(){
	$(this).toggleClass("active").closest(".layout-search").find(".search-filter-content").toggleClass("expanded");
}).on("click",".form-need-active .form-need-handler li",function(){
	var $this=$(this);
	if($this.hasClass("disabled")){return false;}
	var $par=$(".form-need-active");
	var index=$this.index();
	$par.removeClass("active").eq(index).addClass("active");
}).on("click","[x-checkgrp] [x-checkall]",function(){
	var $this=$(this);
	var $par=$this.closest("[x-checkgrp]");
	var _grp=$this.data("group");
	var $ipts=$par.find("[type=checkbox]:not([x-checkall])");
		if(_grp){
			$ipts=$par.find("[type=checkbox]:not([x-checkall])[data-group='"+_grp+"']");
		}
	if($this.is(":checked")){
		$ipts.attr("checked",true);
		$this.parent().addClass("checked");
		$ipts.parent().addClass("checked");
	}else{
		$ipts.removeAttr("checked");
		$this.parent().removeClass("checked");
		$ipts.parent().removeClass("checked");
	}
}).on("click","[x-checkgrp] [type=checkbox]:not([x-checkall])",function(){
	var $this=$(this);
	var $par=$this.closest("[x-checkgrp]");
	var _grp=$this.data("group");
	var $ipta=$par.find("[x-checkall]");
	var flag=true;
	var $ipts=$par.find('[type=checkbox]:not([x-checkall])');
	if(_grp){
		$ipta=$par.find("[x-checkall][data-group='"+_grp+"']");
		$ipts=$par.find("[type=checkbox]:not([x-checkall])[data-group='"+_grp+"']");
	}
	$ipts.each(function(){
		var _$this=$(this);
		if(!_$this.is(":checked")){
			flag=false;
			return false;
		}else{
		}
	});
	if(flag){
		$ipta.attr("checked",true)
		$ipta.parent().addClass("checked");
	}else{
		$ipta.removeAttr("checked")
		$ipta.parent().removeClass("checked");
	}
}).on("change init.checkbox","[type=checkbox]",function(){
	var _$this=$(this);
	var hasValue=_$this.val()==""?false:true;
	if(!_$this.is(":checked")){
		_$this.parent().removeClass("checked");
		if(!hasValue){_$this.val(false);}
	}else{
		_$this.parent().addClass("checked");
		if(!hasValue){_$this.val(true);}
	}
}).on("click","#add-material-btn",function(){  //应急资源中的 添加和编辑
//	var emResourceTypeId = $("#emResourceTypeId").val();
//	var healthId = $("#materialPlaceId").val();
//	var placeName =$("#materialPlaceName").val();
//	var _param = {title:"",id:'',placeId:healthId,placeName:placeName,emResourceTypeId:emResourceTypeId};
//	_param.id = "000";
//	_param.title = "新增物资与装备";
//	//保存方法.来自于common.js
//	materialSaveOrUpdate(_param);
}).on("click",".m-action-edit",function(){ //应急资源中的 添加和编辑
//	var emResourceTypeId = $("#emResourceTypeId").val();
//	var healthId = $("#materialPlaceId").val();
//	var placeName =$("#materialPlaceName").val();
//	var _param = {title:"",id:'',placeId:healthId,placeName:placeName,emResourceTypeId:emResourceTypeId};
//	_param.id = $(this).attr("data-value");
//	_param.title = "编辑物资与装备";
//	materialSaveOrUpdate(_param);
}).on("focus",".ipt-case input,.ipt-case textarea",function(){
	$(this).closest(".ipt-case").addClass("focus");
}).on("blur",".ipt-case input,.ipt-case textarea",function(){
	$(this).closest(".ipt-case").removeClass("focus");
}).on("click",".search-filter-content label",function(){
	var $this=$(this);
	var $dd=$this.closest("dd");
	$dd.find("label").removeClass("active");
	$this.addClass("active")
	$dd.find("[x-time-group] input").val("");
}).on("click",".search-filter-content button",function(){
	var $this=$(this);
	var $dd=$this.closest("dd");
	$dd.find("label").removeClass("active");
}).on("click","[data-role='logout']",function(){
	confirm("是否注销?",function(){
		userFactory.logout();
	})
}).on("click",".table .chandler",function(){
	$(this).closest("tbody").toggleClass("collapsed");
}).on("click","[data-role='dialog']",function(e){
	e.preventDefault();
	var $this=$(this);
	var url=$this.attr("href");
	var title=$this.data("title");
	var _w=$(document).width()*0.8;
		_w=_w>1024?_w:1024;
	openDialog(url,{
		width:_w
		,title:title
		,height:'80%'
	});
}).on("change","[maxlength]",function(e){
	var $this=$(this);
	var count=$this.attr("maxlength");
	var val=$this.val();
	var s=val.substring(0,count);
		$this.val(s);
	if(val.length>count){
		e.preventDefault();
	}
}).on("click",".layout-tab-handler .item",function(){
	var $this=$(this);
	var i=$this.index();
	var $p=$this.closest(".tab-case");
	$this.siblings().add($this).removeClass("active").eq(i).addClass("active");
	$p.find(".layout-tab-content .item").removeClass("active").eq(i).addClass("active");
}).on("click",function(){
	$("[poshy-tip]").poshytip('destroy');
});



function ie7Scroll(){
	var ie7timer=null;
	return function(){
		if(ie7timer){
			clearTimeout(ie7timer);
		}
		ie7timer=setTimeout(function(){
			$(".mCSB_container").each(function(){
				var $this=$(this);
				var $par=$this.parent().parent();
				$this.parent().height($par.height());
				$par.mCustomScrollbar("update")
			})
		},100);
	}
}


function showMask(str){
	//str=str?str:"查询中";
	var tips = function (content, time) {
	    return art.dialog({
	        id: 'Tips',
	        title: false,
	        cancel: false,
	        fixed: true,
	        esc:false,
	        top:'50%',
	        padding:'20px',
	        lock: true,
	        init:function(){
	        	this._lockMask.unbind("dblclick");
	        	$(this.DOM.wrap[0]).find(".aui_border").addClass("nobg");
	        }
	    })
	    .content('<img src="'+siteUrl+'/img/loading.gif"/>')
	    .time(time || 1);
	};
	return tips(str,100000000);
}

/**
 * @string {String} 消息提示的字符串
 * @callback {Function} 提示完毕后的回调
 * */
window.alert=function(string,callback){
    return artDialog({
        id: 'Alert',
        icon: 'warning',
        fixed: true,
        lock: true,
        content: string.toString(),
        padding:'0 20px 0 0',
        ok: true,
        init:function(){
        	this._lockMask.unbind("dblclick");
	    },
        close: callback
    });
}

/**
 * @string {String} 消息提示的字符串
 * @ok {Function} 确认后的回调
 * @cancel {Function} 确认后的回调
 * */
window.confirm=function(string,ok,cancel){
	 return artDialog({
        id: 'Confirm',
        icon: 'question',
        fixed: true,
        lock: true,
        opacity: .4,
        padding:'0 20px 0 0',
        content: string.toString(),
        init:function(){
        	this._lockMask.unbind("dblclick");
	    },
        ok: function (context) {
            return ok.call(this, context);
        },
        cancel: function (context) {
            return cancel && cancel.call(this, context);
        }
    });
}

/**
 * @string {String} 消息提示的字符串
 * @callback {Function} 提示完毕后的回调
 * */
window.tips=function(string,callback){
	var _tips=art.dialog({
	        id: 'tips',
	        title: false,
	        cancel: false,
	        fixed: true,
	        esc:false,
	        top:'50%',
			init:function(){
	        	$(this.DOM.wrap[0]).find(".aui_border").removeClass("nobg");
	        },
	        close:callback
	    })
	    .content('<div style="padding: 1em;">' + string.toString() + '</div>');
	//$(_tips.DOM).animate({top:"-1px"},1000);
	return _tips.slideUp();
}

/**
 * artDialog的关闭方式
 * */
artDialog.fn.slideUp = function (){
    var that=this,
    	style=this.DOM.wrap[0].style,
    	height=$(this.DOM.wrap[0]).height(),
    	style_top=parseInt(style.top),
    	fx = function () {
    		var ___top=parseInt(style.top);
    		if(___top){
    			style.top = ___top-2+"px";
	            if (style_top-___top >= 30) {
	                clearInterval(timerId);
	                that.close();
	            };
    		}else{
    			clearInterval(timerId);
                that.close();
    		}
        };
    timerId = setInterval(fx, 50);
    return this;
};

/**
 * @url {String} 弹框的路径
 * @option {Ojbect} 配置,同artDialog的option
 * */
function openDialog(url,option,styleflag){
	option=option?option:{}
	var resizeFn=function(a,b,c){
		var that=this;
		if(!styleflag){
			that._lockMask.unbind("dblclick");
			that._lockMask.unbind("click");
			that.iframe.contentWindow.$("body").addClass("dialog-body")
		}
		
		var _height=0;
		var timer=setInterval(function(){
			if(that.iframe.contentWindow.$){
				var width=that.iframe.contentWindow.$(".show-body").outerWidth();
				var height=that.iframe.contentWindow.$(".show-body").outerHeight();
				that.size(width,height);
				that.position("50%","50%");
				if(_height==height){
					clearInterval(timer);
				}
				_height=height;
			}else{
				clearInterval(timer);
			}
		},100)
	}
	var initFn=function(){};
	if(option&&option.init){
		initFn=option.init;
	}
	var mergeFn=function(){
		if(initFn){
			initFn.apply(this,arguments);
		}
		resizeFn.apply(this,arguments);
	}
	option.init=mergeFn;
	option.padding="0 20px 0 0";
	art.dialog.open(url,option);
}

/**
 * @obj {String}||{DOM}||{jQuery} 选择器,原生dom元素,jQuery对象
 * @string {String} 提示信息
 * @option {Object} 配置,同phosyTip的option
 * */
function showIptTip(obj,string,option){
	$("[poshy-tip]").poshytip('destroy')
	var $obj=$(obj);
	$obj.off("blur.poshytip mouseenter.poshytip").on("blur.poshytip mouseenter.poshytip",function(){
		$(this).poshytip('destroy')
	});
	$obj.poshytip('destroy');
	var label=$obj.data("label");
		label=label?label:"";
	var _option=$.extend({
		className: 'tip-yellowsimple',
		showOn: 'none',
		alignTo: 'target',
		alignX: 'inner-left',
		alignY: 'bottom',
		content:label+string,
		offsetX: 0,
		offsetY: 7
	},option);
	var _obj=$obj;
	if(!$obj.is(":visible")){
		_obj=$obj.parent();
	}
	_obj.poshytip(_option).poshytip('show');
	_obj.attr("poshy-tip",true);
}

/**
 * @str {String}地理坐标串
 * */
function getFeature(str){
	$(document).trigger("featurechanged",str).on("change",'[name="param.longitude"],[name="param.latitude"]',function(){
		$(this).trigger("pointchanged");
	});
	var geos=str.split("&");
	var type=geos[0];
	var stri=geos[1];
	var lonlat=stri.split(",");
	var longitude=lonlat[0];
	var latitude=lonlat[1];
	if(type=='point'){
		longitude=mathFactory.fixed(longitude,8);
		latitude=mathFactory.fixed(latitude,8);
		$('[name="param.longitude"]').val(longitude);
		$('[name="param.latitude"]').val(latitude);
	}
}
 
function mapLoaded(){
	$(document).trigger("maploaded")
}
 
function calFrameHeight(){
	var iframe=window.frameElement;
	$(iframe).height(0);
	$(iframe).height($("form").height())
}