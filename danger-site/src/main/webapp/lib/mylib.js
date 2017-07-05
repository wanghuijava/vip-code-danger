(function($){
    /**
     * scroller
     */
    var _scrollerTimer=null;
    $.fn.scroller=function(settings){
        settings=$.extend({
            nBtn:null
            ,pBtn:null
            ,child:""
            ,index:1
            ,direction:"lr"//tb
            ,autoPlay:false
            ,interval:100
            ,delay:1000
            ,endless:false
        },settings);
        return this.each(function(){
            $.fn.scroller.init($(this),settings);
        })
    };

    $.fn.scroller.scroll=function(dir,option){

        switch(option.direction){
            case 'lr':
                this.scrollHorizontal(dir,option);
                break;
            case 'tb':
                this.scrollVertical(dir,option);
                break;
        }
    };

    $.fn.scroller.render=function(option){
        var _index=option.index-option.size-1;
        $('.scroller-index li').removeClass("active").eq(_index).addClass("active");
    };

    $.fn.scroller.scrollVertical=function(index,option){
        var that=this;
        var $this=option.parent;
        var _top=option.startRect.top;
        var _count=option.size;
        var _unit=option.iHeight;
        var _dir=index-option.index;
        var __scrollHeight=(index-1)*_unit;
        $this.animate({
            scrollTop:__scrollHeight
        },option.interval,function(){
            if(_dir>0){
                //right
                if(option.endless){
                    if(option.index<_count*2){
                        option.index++;
                    }else{
                        option.index=_count+1;
                    }
                }else{
                    if(option.index<_count){
                        option.index++;
                    }
                }
            }else{
                //left
                if(option.endless){
                    if(option.index>1+_count){
                        option.index--;
                    }else{
                        option.index=2*_count;
                    }
                }else{
                    if(option.index>1){
                        option.index--;
                    }
                }

            }
            that.render(option);
        });
    };

    $.fn.scroller.scrollHorizontal=function(index,option){
        var that=this;
        var $this=option.parent;
        var _left=option.startRect.left;
        var _count=option.size;
        var _unit=option.iWidth;
        var _dir=index-option.index;
        var __scrollWidth=(index-1)*_unit;
        $this.animate({
            scrollLeft:__scrollWidth
        },option.interval,function(){
            if(_dir>0){
                //right
                if(option.endless){
                    if(option.index<_count*2){
                        option.index++;
                    }else{
                        option.index=_count+1;
                    }
                }else{
                    if(option.index<_count){
                        option.index++;
                    }
                }
            }else{
                //left
                if(option.endless){
                    if(option.index>1+_count){
                        option.index--;
                    }else{
                        option.index=2*_count;
                    }
                }else{
                    if(option.index>1){
                        option.index--;
                    }
                }

            }
            that.render(option);
        });

    };

    $.fn.scroller.start=function(_option,settings){
        var _this=this;
        _this.render(_option);
        _scrollerTimer=setInterval(function(){
            _this.scroll(_option.index+1,_option);
        },settings.delay)
    }

    $.fn.scroller.stop=function(_option,settings){
        clearInterval(_scrollerTimer);
        _scrollerTimer=null;
    }

    $.fn.scroller.init=function($this,settings){
        //inital tier;
        var _child=settings.child!=""?settings.child:".scroller-item";
        this.timer=null;
        var _this=this;
        var _scrollerItem=$this.find(_child);
        var _count=_scrollerItem.size();
        settings.index=settings.index>_count?1:settings.index;
        var _tempHtml=$this.html();
        var _startRect={
            left:0
            ,top:0
        }

        var _scrollerItemWidth=_scrollerItem.outerWidth();
        var _scrollerItemHeight=_scrollerItem.height();
        if(settings.endless){
            settings.index+=_count;
            _startRect={
                left:_count*_scrollerItemWidth
                ,top:_count*_scrollerItemHeight
            };
            $this.append(_tempHtml);
            $this.prepend(_tempHtml);
        }
        var _option={
            parent:$this
            ,size:_count
            ,startRect:_startRect
            ,pWidth:_count*_scrollerItemWidth
            ,pHeight:_count*_scrollerItemHeight
            ,iWidth:_scrollerItemWidth
            ,iHeight:_scrollerItemHeight
            ,direction:settings.direction
            ,interval:settings.interval
            ,endless:settings.endless
            ,index:settings.index
        };

        if(settings.direction=="lr"){
            $this.css("white-space","nowrap");
            $this.scrollLeft(_startRect.left)
        }else{
            $this.css("white-space","normal");
            $this.scrollTop(_startRect.top);
        }

        if(settings.nBtn&&settings.pBtn){
            $("body").on("click",settings.nBtn,function(){
                _this.stop();
                _this.scroll(_option.index+1,_option);
            }).on("click",settings.pBtn,function(){
                _this.stop();
                _this.scroll(_option.index-1,_option);
            })
        }

        $(document).on("mouseenter",".scroller-index",function(){
            _this.stop();
        }).on("click",".scroller-index li",function(){
            if($(this).hasClass("active")){return false;}
            var __index=$(this).index()+_option.size-1;
            _this.scroll(__index,_option);
        })

        var _size=$('.scroller-index li').size();
        var liTmpl="<li></li>";
        var str="";
        if(_size==0){
            for(var i =0;i<_option.size;i++){
                str+=liTmpl;
            }
            $('.scroller-index').append(str);
        }
        if(settings.autoPlay){
            $this.on("mouseenter",function(){
                _this.stop();
            }).on("mouseleave",function(){
                _this.start(_option,settings);
            })
            _this.start(_option,settings);
        }
    };

    /*
     * mailInput
     * */
    $.fn.mailInput=function(options){
        var setting=$.extend({
            url:""
        },options);
        return this.each(function(){
            return init($(this),setting);
        })
    };

    function init($this,setting){
        $this.addClass("mi-case");
        $this.append('<span class="mi-grp"><input type="text" class="mi-ipt"/></span>');
        bindEvent($this,setting);
    }

    function bindEvent($this,setting){
        var $par=$this;
        var $cuript=null;
        $(document).on("keydown",".mi-ipt",function(e){
            e.stopPropagation();
            var $this=$(this);
            $cuript=$this;
            var _val=$.trim($this.val());
            var val='';
            resetWidth($this);
            var keycode=e.which;
            switch(keycode){
                case 8:
                    //backspace
                    if(_val==""){
                        var prevSpan=$this.parent().prev();
                        deleteItem(prevSpan);
                    }
                    break;
                case 40:
                    //↓
                    var i=$(".mi-suggest-lst .hover").index();
                    i++;
                    if(i>=$(".mi-suggest-lst li").size()){i=0;}
                    $(".mi-suggest-lst li").removeClass("hover").eq(i).addClass("hover");
                    break;
                case 38:
                    //↑
                    var i=$(".mi-suggest-lst .hover").index();
                    i--;
                    if(i<0){i=$(".mi-suggest-lst li").size()-1;}
                    $(".mi-suggest-lst li").removeClass("hover").eq(i).addClass("hover");
                    break;
                case 13:
                    //enter
                    var $li=$(".mi-suggest-lst .hover");
                    var _lbl=$li.data("lbl");
                    var _val=$li.data("val");
                    var _type=$li.data("type");
                    appendItem($this,_lbl,_val,_type);
                    break;
            }
        }).on("keyup",".mi-ipt",function(e){
            var ajaxTimer=null;
            e.stopPropagation();
            var $this=$(this);
            $cuript=$this;
            var _val=$.trim($this.val());
            var val=_val;
            resetWidth($this);
            var keycode=e.which;
            clearTimeout(ajaxTimer);
            switch(keycode){
                case 186:
                case 59:
                    val=_val.split(";")[0];
                    //console&&console.log?console.log(val):"";
                    if($.trim(val)=="") {
                        $this.val("");
                        return false;
                    }
                    $.ajax({
                        url:setting.url
                        ,data:{name:val}
                        ,dataType:"json"
                        ,nomask:true
                        ,success:function(data){
                            if(data.length>0){
                                var obj=data[0];
                                if(obj.name.indexOf(val)!=-1){
                                    appendItem($this,obj.name,obj.id,obj.type);
                                }else{
                                    $this.val("");
                                }
                            }else{
                                $this.val("");
                            }
                            resetWidth($this);
                            destorySuggest();
                        }
                    });
                    break;
                case 46:
                    var nextSpan=$this.parent();
                    var nextIpt=$this.parent().next().find(".mi-ipt");
                    if(nextIpt.size()!=0){
                        deleteItem(nextSpan);
                        nextIpt.focus();
                    }
                    break;
                case 40:
                case 38:
                    break;
                default:
                    if(val!=""){
                        ajaxTimer=setTimeout(function(){
                            //console&&console.log?console.log(val):"";
                            $.ajax({
                                url:setting.url
                                ,data:{name:val}
                                ,dataType:"json"
                                ,nomask:true
                                ,success:function(data){
                                    var str="";
                                    for(var i=0;i<data.length;i++){
                                        var obj=data[i];
                                        var orgStr="";
                                        if(obj.type=="1"){
                                            orgStr="&lt;<em>"+obj.orgName+"</em>&gt;";
                                        }
                                        str+="<li data-val='"+obj.id+"' data-lbl='"+obj.name+"' data-type='"+obj.type+"'>"+obj.name+orgStr+"</li>"
                                    }
                                    $(".mi-suggest-lst").empty().append(str);
                                    showSuggest($par);
                                }
                                ,error:function(){
                                    //console&&console.log?console.log(val):"";
                                }
                            })
                        },1000)
                    }
                    break;
            }
        }).on("focus click",".mi-ipt",function(e){
            e.stopPropagation();
            $cuript=$this;
        }).on("click",".mi-case",function(){
            var $this=$(this);
            var lastIpt=$this.find(".mi-ipt:last").focus();
        }).on("click",".mi-lbl em",function(){
            var $this=$(this);
            var $span=$this.closest(".mi-grp");
            var $ipt=$span.prev();
            deleteItem($span)
        }).on("click",".mi-suggest-lst li",function(){
            var $li=$(this);
            var _lbl=$li.data("lbl");
            var _val=$li.data("val");
            var _type=$li.data("type");
            appendItem($cuript,_lbl,_val,_type);
        })
    }



    function appendItem($this,str,val,type){
        var $par=$this.closest(".mi-case");
        str=$.trim(str);
        val=$.trim(val+"");
        if(str!=""&&$par.find("[data-val='"+val+"'][data-type='"+type+"']").size()<=0){
            var template='<span class="mi-grp" data-lbl="'+str+'" data-val="'+val+'" data-type="'+type+'">';
            if(type==1){
                template+='<input type="hidden" name="usercodes" value="'+val+'"/>';
            }else{
                template+='<input type="hidden" name="orgcodes" value="'+val+'"/>';
            }
            template+='<input type="text" class="mi-ipt"/>';
            template+='<span class="mi-lbl">';
            template+='	<strong>'+str+'</strong><em>X</em>';
            template+='</span></span>';
            $this.parent().before(template);
        }
        $this.val("");
        resetWidth($this);
        destorySuggest();
    }

    function deleteItem($dom){
        $dom.remove();
    }

    function resetWidth($ipt){
        $("body").prepend("<span id='_mi-word-holder' style='white-space:nowrap;position:absolute;left:-999em;'>"+$ipt.val()+"</span>")
        var _width=$("#_mi-word-holder").width();
        $("#_mi-word-holder").remove();
        _width=_width?_width:4;
        $ipt.width(_width);
    }

    function showSuggest($this){
        $(".mi-suggest-lst").css({
            top:$this.offset().top+$this.outerHeight()
            ,left:$this.offset().left
            ,width:$this.outerWidth()-2
        }).show()
    }

    function destorySuggest(){
        $(".mi-suggest-lst").hide();
        $(".mi-suggest-lst li").remove();
    }

    /*
     * fval
     * */
    $.fn.fval=function(settings){
        settings=settings?settings:{};
        var that=this;
        var base=$.extend({
            "notnull":function($this,val){
                if(val==""){
                    options.tip($this,"不能为空!");
                    return false;
                }
            }
            ,"ip":function($this,val){
                if(val!=""&&base["notnull"]($this,val)!==false&&!/^[0-255].[0-255].[0-255].[0-255]$/.test(val)){
                    options.tip($this,"无效!<br/>正确格式为:192.168.0.0");
                    return false;
                }
            }
            ,"date":function($this,val){
                if(val!=""&&base["notnull"]($this,val)&&!/^[0-9.]{1,20}$/.exec(val)){
                    options.tip($this,"无效!<br/>正确格式为:2015-09-10");
                    return false;
                }
            }
            ,"email":function($this,val){
                if(val!=""&&!/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(val)){
                    options.tip($this,"无效!<br/>正确格式为:admin@domian.com");
                    return false;
                }
            }
            ,"url":function($this,val){
                if(val!=""&&!/^(http)|(https):\/\/(\w+(-\w+)*)(\.(\w+(-\w+)*))*(\?\S*)?$/.test(val)){
                    options.tip($this,"无效!<br/>正确格式为:http://www.abc.com");
                    return false;
                }
            }
            ,"number":function($this,val){
                if(val!=""&&!/^[0-9]{1,20}(.[0-9]{1,20})?$/.test(val)){
                    options.tip($this,"不为数字!");
                    return false;
                }
            }
            ,"int":function($this,val){
                if(val!=""&&!/^(-)?[0-9]{1,20}$/.exec(val)){
                    options.tip($this,"不为整数!");
                    return false;
                }
            }
            ,"float":function($this,val){
                if(val!=""&&!/^(-)?[0-9.]{1,20}$/.exec(val)){
                    options.tip($this,"不为小数!");
                    return false;
                }
            }
            ,"idcard":function($this,val){
                if(!/^[0-9.]{1,20}$/.exec(val)){
                    options.tip($this,"不为身份证号码!");
                    return false;
                }
            }
            ,"telphone":function($this,val){
				var regmb=/^((86)|(\+86))?1\d{10}$/.test(val);
				var regtl=/^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}(-\d{1,4})?$/.test(val)
                if(val!=""&&!(regmb||regtl)){
                    options.tip($this,"无效!<br/>正确格式为:11位手机号或电话号码");
                    return false;
                }
            }
            ,"fax":function($this,val){
                if(val!=""&&!/^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$/.test(val)){
                    options.tip($this,"不为传真号码!");
                    return false;
                }
            }
            ,"mobilephone":function($this,val){
                if(val!=""&&!/^((86)|(\+86))?1\d{10}$/.test(val)){
                    options.tip($this,"无效!<br/>正确格式为:11位手机号");
                    return false;
                }
            }
            /*此两项检测需要将两项数据放入同一个组内(daga-group)才能进行检测,否则无效*/
            ,"vstart":function($this,val){
                if(val!=""&&!/^[0-9.]{1,20}$/.exec(val)){
                    options.tip($this,"IP格式错误!");
                    return false;
                }
            }
            ,"vend":function($this,val){
                if(val!=""&&!/^[0-9.]{1,20}$/.exec(val)){
                    options.tip($this,"IP格式错误!");
                    return false;
                }
            }

        },settings.base);
        var options=$.extend({
            rule:{
				"param.longitude":function($this,val){
					var maxlong=maxlong?maxlong:180,minlong=minlong?minlong:-180
					if(val!=""&&!isNaN(parseInt(val))&&!(val>=minlong&&val<=maxlong)){
	                    options.tip($this,"经度有误!");
	                    return false;
	                }
				}
				,"param.latitude":function($this,val){
					var maxlat=maxlat?maxlat:90,minlat=minlat?minlat:-90
					if(val!=""&&!isNaN(parseInt(val))&&!(val>=minlat&&val<=maxlat)){
	                    options.tip($this,"纬度有误!");
	                    return false;
	                }
				}
			}
            ,base:base
            ,success:function($this){
                //Console("form validate success");
                //alert("表单正在提交")
            }
            ,error:function($this){
                //Console("form validate error");
                //alert("验证失败")
            },tip:function($this,str){
                str=str?str:"sth wrong";
                var $scrollbar=$this.closest(".mCustomScrollbar");
                showIptTip($this,str);
            	if($scrollbar.size()>0){
            		$scrollbar.mCustomScrollbar("scrollTo",$this.parent());
	        	}
            }
        },settings);

        this.fval.init(this,options);
        if(typeof settings=="string"){
            if(typeof this.fval[settings]=="function"){
                return this.fval[settings]($(this));
            }
        }
        /*return that.each(function(){
            var $this=$(this);
            $this.fval.init($this,options);
        })*/
    }
    $.fn.fval.init=function($this,options){
        this.options=options;
        this.$this=$this;
    }

    $.fn.fval.validate=function($this){
        var that=this;
        var options=this.options;
        if(!options) return false;
        var _rule=options.rule;
        var base=options.base;
        var _flag=true;
        var _fields=$this.find("input,select,textarea").not("[disabled],[type=button],[type=submit],[type=reset]");
        _fields.each(function(i){
            var _$this=$(this);
            var _$par=_$this.parent();
            var _iflds=_$this.attr("name");
            var _baserule=_$this.data("rule");
            if(_baserule == 'undefined'){_baserule="";}
            var _basearr=_baserule?_baserule.split(","):[];
            var _val=$.trim(_$this.val());
            var _fn=_rule[_iflds];
            if(_basearr.length>0){
                for(var j=0;j<_basearr.length;j++){
                    _flag=base[_basearr[j]](_$this,_val);
                    if(_flag===false){
                        break;
                    }
                }
                if(_flag===false){
                    return false;
                }
            }
            if(_fn){
                _flag=_fn(_$this,_val);
            }
            _$par.removeClass("focus error pass");
            if(_flag===false){
                _$par.addClass("focus error");
                _$this.off("change.validate").on("change.validate",function(){
                    _$par.removeClass("error");
                }).off("click.validate").on("click.validate",function(){
                    _$this.trigger("change.validate");
                }).off("keyup.validate").on("keyup.validate",function(){
                    _$this.trigger("change.validate");
                })
                return false;
            }else{
                _$par.addClass("pass");
            }
        })
        if(_flag===false){options.error($this);return false;}else{_flag=true}
        options.success($this);
        if(_flag===false){}
        return _flag;
    }

    $.fn.jTextarea=function(options){
        options=$.extend({
            height:$(this).height(),
            tab:false
        },options);
        return this.each(function(){
            jTextarea($(this),options);
        })
    };

    function jTextarea($this,options){
        calculate($this,options);
        if(options.tab){
            $this.off("keydown.jTextarea").on("keydown.jTextarea",function(e){
                if(e.which==9){
                    e.preventDefault();
                    $this.val($this.val()+"    ");
                }
            });
        }
        $this.off("keyup.jTextarea").on("keyup.jTextarea",function(e){
            calculate($this,options);
        })
    }

    function calculate($this,options){
        $this.css("overflow","hidden");
        var textarea=$this.get(0);
        var height=options.height;
        var textHeight=0;
        var textRange=null;
        textHeight=textarea.scrollHeight;
        if(textHeight>=height){
            $this.height(textHeight);
        }
    }


    /*
     * 封装uploadify插件
     * */

    $.fn.upload=function(options){
        options=options?options:{};
        var that=this;
        var uploadLimit=options['file_upload_limit']?options['file_upload_limit']:0;
        options['file_upload_limit']=0;
        var fileSizeLimit=options['file_size_limit']?options['file_size_limit']:1048576*10;//默认10M
        var limit = Math.round(fileSizeLimit / 1024);
        var suffix   = 'KB';
        if (limit >= 1024) {
            limit = Math.round(limit / 1024);
            suffix   = 'MB';
        }
        var tip = limit+suffix;

        var settings=$.extend({
            'swf'      :siteUrl+'/lib/uploadify/uploadify.swf',
            'uploader' :'upload/uploadFile',
            'auto'	   : false,
            'buttonImage':'img/upload_photo_btn.png',
            'width':97,
            'height':75,
            'fileObjName':'upfile',
            'buttonText':'上传图片',
            'fileTypeDesc':'图片',
            'fileTypeExts':'*.jpg;*.gif;*.bmp;*.png;',
            'file_queued_handler':file_queued_handler,
            'upload_success_handler':upload_success_handler,
            'onDialogOpen':function(){
                // Load the swfupload settings
                var settings = this.settings;

                // Reset some queue info
                this.queueData.errorMsg="操作有误";
                this.queueData.filesReplaced  = 0;
                this.queueData.filesCancelled = 0;
            }
        },options);

        return that.each(function(){
        	var $this=$(this);
        	var val=$this.val();
        		val=val!='null'&&val!='undefined'?val:"";
        		$this.val(val);
        	$this.hide();
        	var view=$this.data("action");
			var $btn=$('#' + settings.id)
        	var $con=$btn.parent();
        	$con.closest(".ipt-case").addClass("noborder")
        	$con.on("click",".upload-delete",function(){
        		var item=$(this).closest(".uploaded-img-case");
				item.remove();
				var ids=[];
            	$con.parent().find(".uploaded-img-case").each(function(){
            		var id=$(this).attr("data-id");
            		if(id){ids.push(id);}
            	})
            	$con.parent().find("[type='hidden']").val(ids.join());
    			if($con.find(".uploaded-img-case").size()<uploadLimit){
    				that.uploadify(settings);
            	}
        	})
        	
        	if(val!=""&&val!="undefined"&&val!="null"){
        		var str="";
        		var files = JSON.parse(val);
        		if(!(files instanceof Array)){
        			files=[files];
        		}
        		for(var i=0;i<files.length;i++){
        			str+=compileTemplate(files[i],settings);
        		}
        		$this.before(str);
        		var ids=[];
            	$con.parent().find(".uploaded-img-case").each(function(){
            		var id=$(this).attr("data-id");
            		if(id){ids.push(id);}
            	})
            	$con.parent().find("[type='hidden']").val(ids.join());
        	}
        	
        	if(view=="view"){
        		$con.find(".upload-delete").remove();
        	}
        	
            if(that.uploadify&&view!='view'){
            	if($con.find(".uploaded-img-case").size()<uploadLimit){
    				that.show();
    				that.data("init",true);
            		return that.uploadify(settings);
            	}
            	//$this.show();
                //return that.uploadify(settings);
            }
        });
        
		function isImage(str){
			var images={"gif":true,"jpeg":true,"jpg":true,"bmp":true,"png":true}
			return images[str];
		}
		
        function compileTemplate(file,settings){
			var urlPre=settings.urlPre?settings.urlPre:"";
        	var alias=$.extend({
            		'id':'id'
                	,'name':'name'
                	,'path':'attachPath'
					,'ext':'suffix'
					,'tPath':'thumbnailPath'
                },settings.alias);
            
        	// Create the file data object
            var itemData = {
                'fileID'     : file[alias.id],
                'instanceID' : settings.id,
                'fileName'   : file[alias.name],
				'fileTPath'  : urlPre+file[alias.tPath],
                'filePath'   : urlPre+file[alias.path]
            };
             // Create the file item template
			 if (!settings.itemTemplate) {
			 	settings.itemTemplate = '<div class="uploaded-img-case" title="${fileName}" data-id=${fileID}>\
								<a href="${filePath}" target="_blank">${fileName}</a> &nbsp; \
								<a class="upload-delete" title="删除" href="javascript:;">删除</a>\
							</div>';
			 }
			// Replace the item data in the template
	        var itemHTML = settings.itemTemplate;
			 if (isImage(file[alias.ext])) {
				 itemHTML = '<div class="uploaded-img-case" title="${fileName}" data-id=${fileID}>\
							<a href="${filePath}" target="_blank"><img src="${fileTPath}"/></a> &nbsp; \
							<a class="upload-delete" title="删除" href="javascript:;">删除</a>\
						</div>';
		 	}
          	
            for (var d in itemData) {
                itemHTML = itemHTML.replace(new RegExp('\\$\\{' + d + '\\}', 'g'), itemData[d]);
            }
            return itemHTML
        }
        
        //重写uploadify插件
        function file_queued_handler(file) {
    	 var $con=$(this.movieElement).parents(".uploadify").parent();
            // Load the swfupload settings
            var settings = this.settings;
            // Check if a file with the same name exists in the queue
            var queuedFile = {};
            for (var n in this.queueData.files) {
                queuedFile = this.queueData.files[n];
                if (queuedFile.uploaded != true && queuedFile.name == file.name) {
                    var replaceQueueItem = true//confirm('The file named "' + file.name + '" is already in the queue.\nDo you want to replace the existing item in the queue?');
                    if (!replaceQueueItem) {
                        this.cancelUpload(file.id);
                        this.queueData.filesCancelled++;
                        return false;
                    } else {
                        $('#' + queuedFile.id).remove();
                        this.cancelUpload(queuedFile.id);
                        this.queueData.filesReplaced++;
                    }
                }
            }
			
            // Get the size of the file
            var fileSize = Math.round(file.size / 1024);
            var suffix   = 'KB';
            if (fileSize > 1000) {
                fileSize = Math.round(fileSize / 1000);
                suffix   = 'MB';
            }
            var fileSizeParts = fileSize.toString().split('.');
            fileSize = fileSizeParts[0];
            if (fileSizeParts.length > 1) {
                fileSize += '.' + fileSizeParts[1].substr(0,2);
            }
            fileSize += suffix;

            // Truncate the filename if it's too long
            var fileName = file.name;
            if (fileName.length > 25) {
                fileName = fileName.substr(0,25) + '...';
            }
            
            

            this.queueData.queueSize += file.size;
            this.queueData.files[file.id] = file;
            var fileslength=0;
            for(var i in this.queueData.files){
                fileslength++;
            }
            // Call the user-defined event handler
            if(file.size>fileSizeLimit){
                alert("超出文件大小"+tip+"的限制！");
                for (var n in this.queueData.files) {
                    queuedFile = this.queueData.files[n];
                    this.cancelUpload(queuedFile.id);
                }
                return false;
            }
            if(uploadLimit!=0&&(uploadLimit<=$(this.wrapper).parent().find(".uploaded-img-case").size()||uploadLimit<fileslength)){
                alert("超出上传数量限制!");
                for (var n in this.queueData.files) {
                    queuedFile = this.queueData.files[n];
                    this.cancelUpload(queuedFile.id);
                }
            }else{
                $('#' + settings.id).uploadify('upload', '*');
            }
			$con.find(".uploadify-queue").eq(0).text("文件上传中")
            if (settings.onSelect) settings.onSelect.apply(this, arguments);
        }

        function upload_success_handler(file,data,response){
            var localData=JSON.parse(data);
            var res=localData.response;
			var $btn=$(this.movieElement).parents(".uploadify")
            var $con=$btn.parent();
            if(localData.code==200){
				$con.find(".uploadify-queue").eq(0).text("")
            	itemHTML=compileTemplate(res,this.settings);
            	$btn.before(itemHTML);
            	if($con.find(".uploaded-img-case").size()==uploadLimit){
            		$('#' + settings.id).uploadify("destroy");
            		that.data("init",false);
            		setTimeout(function(){
            			//flash销毁需要时间,在一段时间后隐藏组件防止IE8报错
            			$('#' + settings.id).hide();
            		},400)
            	}
            	var ids=[];
            	$con.parent().find(".uploaded-img-case").each(function(){
            		var id=$(this).attr("data-id");
            		if(id){ids.push(id);}
            	})
            	$con.parent().find("[type='hidden']").val(ids.join());
            	//$("#"+id).before("<img src='"+localData.response.filePath+"'/>")
                    //files.push(localData.response);
            }else{
                tips("上传失败")
            }
        }

    }

    function Console(str){
    	try{
	        if(console&&console.log){
	            console.log(str);
	        }
    	}catch(e){}
    }

})(jQuery);


(function($){
    function jTextarea($this,options){
        calculate($this,options);
        if(options.tab){
            $this.off("keydown.jTextarea").on("keydown.jTextarea",function(e){
                if(e.which==9){
                    e.preventDefault();
                    $this.val($this.val()+"    ");
                }
            });
        }
        $this.off("keyup.jTextarea").on("keyup.jTextarea",function(e){
            calculate($this,options);
        })
    }

    function calculate($this,options){
        $this.css("overflow","hidden");
        var textarea=$this.get(0);
        var height=options.height;
        var textHeight=0;
        var textRange=null;
        $this.height(0)
        textHeight=textarea.scrollHeight;
        if(textHeight>=height){
            $this.height(textHeight);
        }else{
            $this.height(height);
        }

    }

    $.fn.jTextarea=function(options){
        options=$.extend({
            height:$(this).height(),
            tab:false
        },options);
        return this.each(function(){
            jTextarea($(this),options);
        })
    }
})(jQuery);

(function($){
	
	$.fn.dialog=function(settins){
		settings=settings?settings:{};
		this.dialog.init(this,options);
		if(typeof settings=="string"){
            if(typeof this.dialog[settings]=="function"){
                return this.dialog[settings]($(this));
            }
        }
	}
	
	$.fn.dialog.init=function($this,options){
		this.options=options;
        this.$this=$this;
	}
	
	
	
	
	
})(jQuery);

(function($){
	
	$.fn.getParam=function(){
        var params={};
		var _tparam=$(this).serializeArray();
		for(var i =0 ;i< _tparam.length;i++){
			var _name=_tparam[i].name
			if(_name&&_name!="undefined"){
				var dicts=_name.split(".");
				if(dicts.length>1){
					var j=0;
					generateNode(params,_name,_tparam[i].value)
				}else{
					params[_name]=_tparam[i].value;
				}
			}
		}
		return params
	}
	
	function splitNode(pNode,node,dicts,i,value){
		pNode=pNode?pNode:{}
		if(dicts[i]){
			if(i==dicts.length-1){
				if(value!=""){
					pNode[dicts[i]]=value;
				}else{
					delete pNode[dicts[i]];
				}
			}else{
				pNode[dicts[i]]=pNode[dicts[i]]?pNode[dicts[i]]:{}
			}
			arguments.callee(pNode[dicts[i]],null,dicts,++i,value);
		}
	}
	
	function generateNode(pNode,_name,value){
		var dicts=_name.split(".");
		var i=0;
		if(dicts.length>1){
			splitNode(pNode,null,dicts,i,value);
		}else{
			pNode[_name[i]]={};
		}
		return pNode;
	}
	
})(jQuery);
