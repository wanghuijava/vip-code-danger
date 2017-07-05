/*shim for ie8*/
document.createElement("mark");

String.prototype.trim=function(){
		return this.replace(/(^\s*)|(\s*$)/g, "");
}

var http={
	/***
	 * post请求
	 * @param {} param
	 * @return {}
	 */
	post:function(param){
		var _param=$.extend({type:"post"},param);
		return $.ajax(_param)
	}
	/**
	 * get请求
	 * @param {} param
	 * @return {}
	 */
	,get:function(param){
		var _param=$.extend({type:"get"},param);
		return $.ajax(_param)
	}
}

top.currentUser=null;

//用户服务
var userFactory={
	/**
	 * 登录
	 * @param {} param 登录参数
	 * @return {}
	 */
	login:function(param){
		var defered=$.Deferred();
		return http.post({
			url:"/basedata/user/login"
			,data:param
			,mask:false
			,dataType:"json"
			,success:function(data){
				defered.resolve(data);
			}
			,error:function(data){
				defered.reject(data);
			}
		})
	}
	/**
	 * 登出
	 * @return {}
	 */
	,logout:function(){
		this.removeUser();
		top.location=siteUrl+"/logout";
	}
	/**
	 * 设置用户
	 * @param {} data
	 */
	,setUser:function(data){
		if(!data) return;
		//设置token值
		storageProvider.set("token",data.token);
	}
	/**
	 * 取用户
	 */
	,getUser:function(){
		var user=null;
		if(!top.currentUser){
			http.post({
				url:"/basedata/user/getUser"
				,async:false
				,success:function(res){
					user=res.response;
					top.currentUser=res.response;
				}
			})
		}else{
			user=top.currentUser;
		}
		return user;
	}
	/**
	 * 删除用户
	 */
	,removeUser:function(){
		storageProvider.remove("User");
	}
}

//日期格式化服务
var dateFactory={
		format:function(date,format,flag){
            var str=format;
            str=str.replace("YYYY",date.getFullYear());
            str=str.replace("MM",flag||date.getMonth()+1>9?date.getMonth()+1:'0'+(date.getMonth()+1));
            str=str.replace("DD",flag||date.getDate()>9?date.getDate():'0'+date.getDate());
            str=str.replace("hh",flag||date.getHours()>9?date.getHours():'0'+date.getHours());
            str=str.replace("mm",flag||date.getMinutes()>9?date.getMinutes():'0'+date.getMinutes());
            str=str.replace("ss",flag||date.getSeconds()>9?date.getSeconds():'0'+date.getSeconds());
            return str;
        }
        ,getDate:function(str,split){
        	if(str){
        		split=split?split:"-";
	        	var strs=str.split(split);
	        	for(var i =0;i<strs.length;i++){
	        		strs[i]=parseFloat(strs[i]);
	        		strs[i]=isNaN(strs[i])?0:strs[i];
	        	}
	        	return new Date(strs[0],strs[1]-1,strs[2])
        	}else{
        		return new Date();
        	}
        }
		,getDateJson:function(date){
			if(date){
			}else{
				date = new Date();
			}
			var _days=["日","一","二","三","四","五","六"];
			var _date=date;
			var _day=_date.getDay();
			var dateJson={
				year:_date.getFullYear()
				,month:_date.getMonth()+1
				,date:_date.getDate()
				,day:_days[_day]
			}
			return dateJson;
			
		}
}

/*数学操作服务*/
var mathFactory={
	fixed:function(num,n){
		num=parseFloat(num);
		if(isNaN(num)) return 0;
		n=parseInt(n);
		n=isNaN(n)?0:n;
		return parseInt(num*Math.pow(10,n))/Math.pow(10,n);
	}
	,formatNum:function(num,n){
		num = new String(num);
		if(!num){
			return "";
		}
		if(num.length<4){
			return num;
		}
		var delimiter=",";
		var a = num.split(".");
		var zhengshu = a[0];
		var xiaoshu="";
		if(a.length==2){
			xiaoshu = a[1];
		}
		var numArr=[];
		var strNum=zhengshu;
		while(strNum.length>3){
			var nn = strNum.substr(strNum.length-3);
			numArr.unshift(nn);
			strNum = strNum.substr(0,strNum.length-3);
		}
		if(strNum.length>0){
			numArr.unshift(strNum);
		}
		strNum = numArr.join(",");
		if(xiaoshu.length>0){
			strNum = strNum + "."+xiaoshu
		}
		return strNum;
	}
}

function Cookie(){
	this.get=function(key){
		var cookieStr=document.cookie;
		var cs=cookieStr.split(";");
		var val="null";
		for(var i =0;i<cs.length;i++){
			var _cs=cs[i].split("=");
			if(_cs[0].trim()==key){
				val=unescape(_cs[1]);
				break; 
			}
		}
		return val;
	}
	this.set=function(key,val){
		document.cookie=key+"="+escape(val);
	}
	this.remove=function(key){
		document.cookie=key+"= ";
	}
}

//存储服务
var storageProvider={
	/***
	 * 
	 * 获取本地存储对象,如果有localStorage则使用,否则使用cookie
	 */
	getProvider:function(){
		return new Cookie();
	}
	/***
	 * 
	 * @param {String} key 键
	 * @param {String}|{Object} value 值
	 */
	,set:function(key,value){
		var storage=this.getProvider();
		if(typeof value != "string"){
			storage.set(key,JSON.stringify(value));
		}else{
			storage.set(key,value);
		}
	}
	/***
	 * 
	 * @param {String} key 键
	 */
	,get:function(key){
		var storage=this.getProvider();
		var value=null;
		try{
			value=JSON.parse(storage.get(key));
		}catch(e){
			value=storage.get(key);
		}
		return value;
	}
	/***
	 * 
	 * @param {String} key 键
	 */
	,remove:function(key){
		var storage=this.getProvider();
		storage.remove(key);
	}
}


//模板编译服务
var tpl={
	/***
	 * @option {Object} 模板编译配置
	 * @option.tid {String} artTemplate模板id
	 * @option.tempalteUrl {String} artTemplate模板的url 优先级高于tid
	 * @option.hook {String}||{DOM}||{jQuery} 模板对应填充的容器,选择符,原生dom,jQuery对象
	 * @option.data {Ojbect} 模板编译的数据源
	 * */
	render:function(option){
		var id=option.tid;
		var hook=option.hook;
		var data=option.data;
			data=data?data:{};
		var templateUrl=option.templateUrl;
		var nempty=option.nempty;
		var html="";
		template.helper("parseInt",function(data){
			return parseInt(data);
		})
		template.helper("ceil",function(data){
			return Math.ceil(data);
		})
		template.helper("parseJSON",function(data){
			return JSON.parse(data);
		})
		template.helper("stringify",function(data){
			var o=null;
			if(data){o=data;}
			return JSON.stringify(o);
		})
		template.helper("format",function(data){
			return data?data:'-';
		})
		template.helper("formatNum",function(data, n){
			return data?mathFactory.formatNum(data, n):'';
		})
		template.helper("indexOf",function(data, instr){
			return data?data.indexOf(instr):-1;
		})
		template.helper("substring",function(data, n){
			return data?(data.length>n?data.substring(0,n)+"...":data):'';
		})
		template.helper("isExpertPhoneExist",function(data){
			if(1==data){
				return siteUrl+"/img/photo_male.png";
			}else if(2==data){
				return siteUrl+"/img/photo_female.png";
			}
			return siteUrl+"/img/photo.png";
		})
		template.helper("contact",function(data){
			var name=""
			var num="";
			var tempory=[];
			//依次获取负责人联系人电话
			for(var i =0;i<data.length;i++){
				var validPerson=data[i];
				if(validPerson){
					name=validPerson.name;
					tempory.push(name);
					var nums=validPerson.phonenums;
					for(var j=0;j<nums.length;j++){
						if(nums[j]){
							num=nums[j];
							break;
						}
					}
				}
				if(name!=""&&num!=""){
					break;
				}
			}
			//如果负责人和联系人电话都为空
			if(num==""){
				name=tempory[0];
			}
			//防止负责人和联系人都是空
			name=name?name:"";
			return name+" "+num;
		})
		if(templateUrl){
			$.ajax({
				url:templateUrl
				,async:false
				,success:function(tmpl){
					var render=template.compile(tmpl);
					html=render(data)
				}
			})
		}else{
			if(!document.getElementById(id)) return false;
			html=template(id,data);
		}
		if(!nempty){
			$(hook).empty()
		}
		$(hook).append(html);
		  //加载组件
	    var ctrl=new customControll();
		ctrl.scan();
		//属性对应
		$(hook).find("[data-rel]").each(function(){
			var $this=$(this);
			var _key=$this.attr("data-rel");
			var _val=$(hook).data(_key);
			$this.val(_val);
		})
		
		$(hook).find("[type=checkbox]").trigger("init.checkbox");
	}
}

function stringify(obj){
	var str = "";
	var array=[];
	for(var i in obj){
		var _str=i+"="
		if(typeof obj =="object"){//如果是对象则转化为字符串
	        _str+=JSON.stringify(obj[i]);
	    }else{
	        _str+=obj;
	    }
	    array.push(_str);
	}
    str = encodeURI(array.join("&"));
    return str;
}

/***
 * 
 * @param {String}|{DOM}|{jQuery} $par 选择符,原生dom,jQuery对象,form表单主体
 * @param {} option
 * 			option.url {String}请求的action
 * 			option.content {String}|{DOM}|{jQuery} 表单渲染主体
 * 			option.tid {String} artTemplate 模板id
 * @param {} successFn 查询成功回调
 * @param {} failureFn 查询失败回调
 */
function former($par,option,successFn,failureFn){
	var that=this;
	$par=$($par);
	var url=option.url;
	var data=option.data;
	var action=$par.attr("action");
	if(!url){
		url=action;
	}
	var	conent=$par.find(option.content);
	var pager=option.pager?option.pager:{};
	pager.templateUrl=pager.templateUrl?pager.templateUrl:"/views/inc/pagenation.html";
	var pagerCase=$par.find(pager.hook?pager.hook:"[x-pager]");
		if(pagerCase.find(".pager-case").size()==0){
		var count=pagerCase.data("count");
		var start=pagerCase.data("start");
			start=start?start:0;
			pagerCase.append('<div class="pager-case"></div>');
			pagerCase.append('<input type="hidden" data-rel="count" name="page.count" value="'+count+'"/>\
							<input type="hidden" name="page.start" value="'+start+'"/>');
		}
		//阻止当前分页提交,如果有初始值,则使用初始值
		if(!start>0){
			pagerCase.find("[name='page.start']").attr("disabled",true);
			start=0;
		}
		pagerCase.on("click.pager",".pager a",function(){
			var $this=$(this);
			var $pagerCase=$this.closest("[x-pager]");
			//传递页码
			$pagerCase.find("[name='page.start']").removeAttr("disabled");
			var $form=$this.closest("[data-form]").size>0?$this.closest("[data-form]"):$this.closest("form");
			var _start=$this.data("start")
			$pagerCase.find('[name="page.start"]').val(_start)
			$pagerCase.attr("data-start",_start)
			$form.trigger("former.search")
		}).on("keydown.pager",".pager .ipt",function(e){
			var $this=$(this);
			var $pagerCase=$this.closest("[x-pager]");
			var $form=$this.closest("form");
			var _val=parseInt($this.val());
			var _total=$this.closest(".pager").data("total");
			if(isNaN(_val)){
				$this.val("");
				_val=1;
			}
			var _start=_val;
			_start=_start?_start:1;
			_start=_start<_total?_start:_total;
			_start=_start-1;
			$pagerCase.find('[name="page.start"]').val(_start)
			$pagerCase.attr("data-start",_start)
			if(e.which==13){
				//传递页码
				pagerCase.find("[type='hidden']").removeAttr("disabled");
			}
		}).on("click.pager",".pager .btn",function(e){
			e.preventDefault();
			var $this=$(this);
			var $form=$this.closest("form");
			var $pagerCase=$this.closest("[x-pager]");
			var $ipt=$pagerCase.find(".ipt");
			var _val=parseInt($ipt.val());
			var _total=$this.closest(".pager").data("total");
			if(isNaN(_val)){
				$ipt.val("");
				_val=1;
			}
			var _start=_val;
			_start=_start?_start:1;
			_start=_start<_total?_start:_total;
			_start=_start-1;
			$pagerCase.find('[name="page.start"]').val(_start);
			$pagerCase.attr("data-start",_start);
			//传递页码
			pagerCase.find("[type='hidden']").removeAttr("disabled");
			$form.trigger("former.search");
		})
	var	pagerDom=pagerCase.find(".pager-case");
		
	that.timer=null;
	
	$par.on("submit",function(e){
		e.preventDefault();
		//表单校验
		var flag=$par.fval("validate");
		if(flag){
			$(this).trigger("former.search");
		}
		return flag;
	})
	
	$par.off("former.search").on("former.search",function(e){
		$("[poshy-tip]").poshytip('destroy')
		if(that.timer){//如果有请求,放弃之前的
			clearTimeout(that.timer)
			that.timer=null;
		}
		e.stopPropagation();
		if(!url){return false;}
		var params=$par.getParam();
		
		params=$.extend(data,params);
		
		that.timer=setTimeout(function(){
			var xhr=http.post({
				url:url
				,data:stringify(params)
				,success:function(result){
					if (option.tid) {
						tpl.render({
							tid: option.tid,
							hook: conent,
							data: result.response
						});
						tpl.render({
							templateUrl: pager.templateUrl,
							hook: pagerDom,
							data: result.response
						});
						var page = result.response.page;
						var start = 0;
						if (page) {
							start = page.start;
						}
						//阻止当前分页提交
						pagerCase.find("[name='page.start']").val(start).attr("disabled", true);
					}
					that.success&&that.success(result,$par,xhr);
					successFn&&successFn(result,$par,xhr);
				}
				,fail:function(result){
					failureFn&&failureFn(result,$par,xhr);
				}
			})
		},200)
	})
	
	this.submit=function(){
		//表单校验
		var flag=$par.fval("validate");
		if(flag){
			$par.trigger("former.search");
		}
		return flag;
	}
}

var customControll=function(){
	
	var controlls={
		"[x-time]":function(){
			var elems=$('[x-time]');
			elems.each(function(){
				var $this=$(this);
				var id="time-"+parseInt(Math.random()*100000);
				$this.attr("id")?id=$this.attr("id"):$this.attr("id",id);
				$this.after("<em class='calenar-sub'><i class='ibm icon-cal'></i></em>");
				var istime=$this.data("istime");
				var maxStr=$this.data("max");
				var minStr=$this.data("min");
				var format=$this.data("format");
					format=format?format:"YYYY-MM-DD";
				if(!$this.data("init")){
					$this.attr("readonly",true);
					$this.next(".calenar-sub").click(function(){
						$this.trigger("click").trigger("focus");
					})
					if(maxStr && minStr){
						laydate({elem: "#" + id,istime:istime,format:format,max:maxStr,min:minStr,choose:function(datas){
							$this.val(datas);
	                    }});
					}else{
						laydate({elem: "#" + id,istime:istime,format:format,choose:function(datas){
							$this.val(datas);
	                    }});
					}
				}
                $this.data("init",true);
			})
		}
	    ,"[x-time-group]":function(){
			var elems=$('[x-time-group]');
			elems.each(function(){
				var $this=$(this);
				var $start=$(this).find("[x-time-start]");
				var $end=$(this).find("[x-time-end]");
				var sid="time-"+parseInt(Math.random()*100000);
				var eid="time-"+parseInt(Math.random()*100000);
				$start.attr("id")?id=$start.attr("id"):$start.attr("id",sid);
				$end.attr("id")?id=$end.attr("id"):$end.attr("id",eid);
				var istime=$this.data("istime");
				var format=$this.data("format");
					format=format?format:"YYYY-MM-DD";
					
				var startOption={elem: "#" + sid,istime:istime,format:format,max:$end.val(),choose:function(datas){
					endOption.min=datas;
					//$("#"+sid).trigger("click");
					//$this.val(datas);
	            }}
				var endOption={elem: "#" + eid,istime:istime,format:format,min:$start.val(),choose:function(datas){
					startOption.max=datas;
					//$("#"+eid).trigger("click");
					//$this.val(datas);
	            }}
				if(!$this.data("init")){
					$start.attr("readonly",true);
					$end.attr("readonly",true);
					if($start.parent().hasClass("ipt-case"))
					$start.after("<em class='calenar-sub'><i class='ibm icon-cal'></i></em>");
					if($end.parent().hasClass("ipt-case"))
					$end.after("<em class='calenar-sub'><i class='ibm icon-cal'></i></em>");
					$start.next(".calenar-sub").click(function(){
						$start.trigger("click").trigger("focus");
					})
					$end.next(".calenar-sub").click(function(){
						$end.trigger("click").trigger("focus");
					})
					laydate(startOption);
					laydate(endOption);
				}
	            $this.data("init",true);
			})
		}
		,"[x-upload]":function(){
			var elems=$("[x-upload]");
			elems.each(function(){
				var $this=$(this);
				var id="upload-"+parseInt(Math.random()*100000);
				var name=$this.data("name");name=name?name:"";
				var accept=$this.data("accept");
					accept=accept?accept:'*.jpg;*.gif;*.bmp;*.png;'
				$this.attr("id")?id=$this.attr("id"):$this.attr("id",id);
				var maxLength=$this.data("maxlength")?$this.data("maxlength"):99;
				if(!$this.data("init")){
					$this.after("<input type='hidden' name='"+name+"' value='"+$this.val()+"'/>");
					$this.upload({
		                buttonImage:siteUrl+"/img/upload_btn.png"
						,button_image_url:siteUrl+"/img/upload_btn.png"
		                ,'uploader' :SERVICECONTEXT+'/attach/uploadfile'
		                ,'width':96
		                ,'id':id
		                ,'height':30
		                ,'fileTypeDesc':'文件'
            			,'fileTypeExts':accept
						,'urlPre':fileUrl
		                ,'file_upload_limit':maxLength
		            });
				}
	            $this.data("init",true);
			})
			
		}
		,"[x-upload-office]":function(){
			var elems=$("[x-upload-office]");
			elems.each(function(){
				var $this=$(this);
				var id="upload-"+parseInt(Math.random()*100000);
				var name=$this.data("name");name=name?name:"";
				var url=$this.data("url");
				$this.attr("id")?id=$this.attr("id"):$this.attr("id",id);
				var accept=$this.data("accept");
					accept=accept?accept:"*.doc;*.docx;*.xls;*.xlsx*;*.ppt;*.pptx;*.pdf;"
				var maxLength=$this.data("maxlength")?$this.data("maxlength"):99;
				if(!$this.data("init")){
					$this.after("<input type='hidden' name='"+name+"' value='"+$this.val()+"'/>");
					$this.upload({
		                buttonImage:siteUrl+"/img/upload_btn.png"
						,button_image_url:siteUrl+"/img/upload_btn.png"
		                ,'uploader' :url?url:SERVICECONTEXT+'/eppManage/eppManage/uploadFile'
		                ,'width':96
		                ,'id':id
		                ,'height':30
		                ,'fileTypeDesc':'文档'
            			,'fileTypeExts':accept
						,'urlPre':officeUrl
            			,'alias':{
		                	'id':'id'
		                	,'name':'name'
		                	,'path':'attachPath'
		                }
            			,'file_size_limit':10*1024*1024
		                ,'file_upload_limit':maxLength
		            });
				}
	            $this.data("init",true);
			})
			
		}
		,"[x-map]":function(){
			$("[x-map]").each(function(){
				var $this=$(this);
				var typeName=$this.data("type-name");
				var typeValue=$this.data("type-value");
					typeValue=typeValue?typeValue:'';
					typeValue=typeValue.toLowerCase();
				var stringName=$this.data("string-name");
				var stringValue=$this.data("string-value");
				var isView=$this.data("view");
				var _dataRule=$this.data("rule");
					_dataRule=_dataRule?_dataRule:"";
				$this.empty().append('<iframe style="width: 100%;height: 240px;border: none;vertical-align:middle;" frameborder="0" scrolling="0" src="'+siteUrl+'/gis/map-control/Gsafety.GIS.ResourceLocationTestPage.html?time='+Date.parse(new Date())+'"></iframe>\
							<input type="hidden" name="'+typeName+'" value="'+typeValue+'" />\
							<input type="hidden" data-rule="'+_dataRule+'" data-label="地理信息" name="'+stringName+'" value="'+stringValue+'"/>');
				$(document).off("maploaded").on("maploaded",function(){
					var that=$this.find("iframe").get(0).contentWindow;
					that.Draw(typeValue,stringValue);
					if(isView){
						that.GetVisibility();
					}
				}).off("featurechanged").on("featurechanged",function(e,data){
					if(data){
						var arr=data.split("&");
						var type=arr[0];
						var string=arr[1];					
						$this.find("[name='"+typeName+"']").val(type);
						$this.find("[name='"+stringName+"']").val(string);
					}
				}).off("pointchanged").on("pointchanged",function(){
					var win=$this.find("iframe").get(0).contentWindow;
					var lon=$('[name="param.longitude"]').val();
						lon=isNaN(parseFloat(lon))?0:parseFloat(lon);
					var lat=$('[name="param.latitude"]').val();
						lat=isNaN(parseFloat(lat))?0:parseFloat(lat);
					win.Draw('point',lon+","+lat);
				});

			})
		}
		,"[data-source-id]":function(){
			//为基础数据添加交互dom
		    $("[type='hidden'][data-source-id]").each(function(){
		    	var $this=$(this)
		    	if(!$this.data("init")){
		    		var id=$this.data("source-id");
					var _tid=id+parseInt(Math.random()*10000000000);
						$this.attr("id",_tid)
					var name=$this.data("name");
						name=name?name:"";
					var type=$this.data("source-type");
						type=type?type:"list";
					var showname=$this.attr("data-show-name");
						showname=showname?showname:"";
					var filter=$this.attr("data-filter");
					if($this.next("[data-source-id='"+id+"']").size()==0){
						$this.after("<input class='ipt shipt'  name='"+name+"' data-for='"+_tid+"' data-source-id='"+id+"' data-source-type='"+type+"' data-filter='"+filter+"' value='"+showname+"'/>");
					}
					$this.after("<em class='calenar-sub'><i class='ibm tran-down'></i></em>");
					var $par=$this.parent();
					var $sipt=$par.find(".shipt");
					initDown($sipt);
					var $con=$par.find(".ipt-auto-case");
					$par.find(".calenar-sub").off("click").on("click",function(){
						$sipt.trigger("focus");
					})
					$sipt.on("focus click",function(){
						$con.show();
					})
		    	}
		    	$this.data("init",true);
		    })
		}
		,"[x-scrollable]":function(){
			//自定义滚动
			$("[x-scrollable]").mCustomScrollbar({
		        theme: "dark"
		        ,scrollInertia:0
		        ,callbacks:{
		        	whileScrolling:function(){
		        		setTimeout(function(){
		        			$("[poshy-tip]").each(function(){
				    			$(this).poshytip("refresh");
				    		})
		        		},500)
		        	}
		        }
		    });
		}
		,"[x-scrollable-x]":function(){
			//自定义滚动
			$("[x-scrollable-x]").mCustomScrollbar({
		        theme: "dark"
				,axis:"x"
		        ,scrollInertia:0
		    });
		}
		,"[x-authority]":function(){
			//为用户权限添加交互dom
			$("[x-authority]").each(function(){
				var $this=$(this);
				$this.hide();
				
				var $entities=$this.find('[href]');
				var url = $entities.attr("href");
				url = url.replace(siteUrl, "");
				var userAuthority = userFactory.getUser().authority;
				var fuse = 0;
				do{
					var patternStr =  "\""+url+"\"" ;
					if(userAuthority.indexOf(patternStr) > 0){
						$this.show();
						return;
					}
					fuse = fuse + 1;
					url = url.substring(0, url.lastIndexOf("/"));
				}while((url.indexOf("/") > -1 && fuse < 10));
				$this.remove();
			});
		}
		,"select":function(){
			$("select").each(function(){
				var options=this.options;
				var $this=$(this);
				var _val=$this.val();
				var _text="";
				var $hipt,$sipt,$dom,$par;
					$par = $this.parent()
				var $con=$par.find(".ipt-auto-case");
				var data = [];
				$.each(options, function(index, item){
					var $item = $(item);
					data.push({
						name: $item.text(),
						id: $item.val()
					})
					if(item.selected){
						_text=$item.text();
						_val=$item.val();
					}else if($item.val()==_val){_text=$item.text()}
				})
				if (!$this.data("init")) {
					var name = $this.attr("name");
					var datarule = $this.attr("data-rule");
					var datalabel = $this.attr("data-label");
					$this.removeAttr("name").removeAttr("data-rule").hide();
					$this.after("<input type='hidden'  name='" + name + "' data-rule="+ datarule + " data-label="+ datalabel + " value='"+_val+"'/><input class='ipt shipt' value='"+_text+"'/>");
					if($con.size()==0){
						$this.after("<div class='ipt-auto-case' style='display:none'/>");
						$con=$this.next(".ipt-auto-case");
						$con.mCustomScrollbar({
					        theme: "dark"
					        ,scrollInertia:0
					    });
						$con.find(".mCSB_container").append("<ul class='csul zlist'></ul><iframe src='about:blank;'></iframe>")
					}
					$this.after("<em class='calenar-sub'><i class='ibm tran-down'></i></em>");
					$this.data("init", true);
				}
					$hipt=$par.find("[type=hidden]");
					$sipt=$par.find(".shipt");
					$sipt.attr("readonly",true);
					$hipt.val(_val);
					$sipt.val(_text);
					$dom=$con.find(".csul").removeClass("dir-up");
					$par.find(".calenar-sub").off("click").on("click", function(){
						$sipt.trigger("focus");
					})
					$sipt.on("focus click",function(){
						$con.show();
					})
				generateList($dom,$sipt,$hipt,data);
			})
		}
		,"textarea":function(){
			var txts=$("textarea");
			txts.each(function(){
				var $this=$(this);
				var $par=$this.closest(".ipt-case");
				var val=$this.val();
				var max=$this.attr("maxlength");
				if(max){
					if($par.find('.text-des').size()==0){
						$par.append("<em class='text-des'><i>"+val.length+"</i>/"+max+"</em>");
						$this.off("keyup.autocal").on("keyup.autocal",function(){
							var $count=$par.find('.text-des i');
							$count.text($(this).val().length);
						})
					}
				}
			})
		}
	}
	
	this.scan=function(){
		for(var i in controlls){
			if(controlls.hasOwnProperty(i)){
				controlls[i]();
			}
		}
	}
}

function generateList($dom,$sipt,$hipt,data){
	if(data.length==0) return;
	var str="";
	var $case=$dom.closest(".ipt-auto-case");
	var $pp=$case.closest(".ipt-case");
	for(var i = 0;i<data.length;i++){
		str+="<li data-id='"+data[i].id+"' data-lbl='"+data[i].name+"'>"+data[i].name+"</li>"
	}
	$dom.empty().append(str);
	if($hipt.val()!=""){
		$dom.find("li").removeClass("active").end().find("[data-id='"+$hipt.val()+"']").addClass("active");
	}
	//绑定交互
	$sipt.on("focus click",function(){
		$case.show();
		$case.removeClass("dir-up");
		if(($case.offset().top+$case.height())>$(document).height()){
			$case.addClass("dir-up");
		}
	})
	$dom.off("click").on("click","li",function(){
		var $this=$(this);
		var value=$this.attr("data-id");
		var title=$this.attr("data-lbl");
		$sipt.val(title);
		$hipt.val(value);
		$sipt.trigger("change");
		$hipt.trigger("change");
		$this.siblings().add($this).removeClass("active");
		$this.addClass("active");
		$case.hide();
	});
	
	var _timer=null;
	$(document).on("click",function(e){
		var $tar=$(e.target);
		var _$pp=$tar.closest(".ipt-case");
		if(!_$pp.is($pp)){
			$case.hide();
		}
	})
	$sipt.off("keyup").on("keyup",function(){
		$hipt.val("");
	})
	$pp.off("mouseleave").on("mouseleave",function(){
		_timer=setTimeout(function(){
			//$case.hide();
		},300)
	})
	$case.off("mouseenter").on("mouseenter",function(){
		clearTimeout(_timer)
		_timer=null;
	}).off("mouseleave").on("mouseleave",function(){
		_timer=setTimeout(function(){
			//$case.hide();
		},300)
	})
}

function generateTree($dom,$sipt,$hipt,data){
	var $case=$dom.closest(".ipt-auto-case");
	var $pp=$case.closest(".ipt-case");
	var isMulti=$hipt.data("mode")=="multi"
	var hval=$hipt.val(),hvals=[];
	var cbk={
            onClick: function(event, treeId, treeNode, clickFlag) {
                var that=this;
                var value=treeNode.id;
                var title=treeNode.name;
                $sipt.val(title);
                $hipt.val(value);
                $sipt.trigger("change");
        		$hipt.trigger("change");
                $case.hide();
            }
        }
	if(isMulti){
		hvals=hval.split(",");
		$sipt.attr("readonly",true);
		$dom.after("<div id='selNum' style='height:20px;margin-right:10px;margin-bottom:2px;color:#ad1818; text-align:right;'>已选择<span>0</span>个节点</div>");
		cbk={
			onCheck:function(event, treeId, treeNode){
				var _tree=$.fn.zTree.getZTreeObj(treeId);
				var ids=[];
				var nms=[];
				var nodes=_tree.getNodesByFilter(function(node){
					if(node.checked&&node.check_Child_State!=1){
						ids.push(node.id);
						nms.push(node.name);
					}
					return node.checked;
				})
				$sipt.val(nms.join(","));
                $hipt.val(ids.join(","));
                $("#selNum span").text(ids==null?0:ids.length);
                $sipt.trigger("change");
        		$hipt.trigger("change");
                //$case.hide();
			}
		}
	}
	var setting = {
		data: {
			simpleData: {
				idKey: "id",
				pIdKey: "parentid",
				rootPId: 0
			}
		},
		check:{
			enable:isMulti
		},
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        callback: cbk
    };
	var _tree=$.fn.zTree.init($dom,setting,data);
	if(hvals.length>1){
		for(var i =0;i<hvals.length;i++){
			var nd=_tree.getNodeByParam("id",hvals[i]);
			_tree.checkNode(nd,true,true,true);
		}
	}
	_generateTree();
    
   	$sipt.on("focus click",function(){
		$case.show();
		$case.removeClass("dir-up");
		if(($pp.offset().top+$case.height())>$(document).height()){
			$case.addClass("dir-up");
		}
	})

	var _timer=null;
	$(document).on("click",function(e){
		var $tar=$(e.target);
		var _$pp=$tar.closest(".ipt-case");
		if(!_$pp.is($pp)){
			$case.hide();
		}
	})
	$sipt.off("keydown").on("keydown",function(e){
		if(e.which==13){
			e.preventDefault();
		}
	}).off("keyup").on("keyup",function(e){
		$hipt.val("");
		$case.show();
		_generateTree(true,e);
	})
	$sipt.off("blur").on("blur",function(){
		_timer=setTimeout(function(){
			//$case.hide();
		},300)
	})
	$case.off("mouseenter").on("mouseenter",function(){
		clearTimeout(_timer)
		_timer=null;
	}).off("mouseleave").on("mouseleave",function(){
		_timer=setTimeout(function(){
			//$case.hide();
		},300)
	})
	
	function _generateTree(flag,event){
		if(!isMulti){
			var keyword=$sipt.val();
				keyword=$.trim(keyword);
			var nodes=data;
			var tNode=[];
			if(keyword!=""&&event){
				_tree=$.fn.zTree.init($dom,setting,data);
				var __nodes=_tree.transformToArray(_tree.getNodes());
				for(var k=0;k<__nodes.length;k++){
					var _item=__nodes[k];
						_item.children=[];
					if(_item.name.indexOf(keyword)>-1){
						tNode.push(_item);
					}
				}
				nodes=_tree.transformTozTreeNodes(tNode);
				//nodes=_tree.getNodesByParamFuzzy("name",keyword);
				//_tree.destroy();
			}
			var newTree=$.fn.zTree.init($dom,setting,nodes);
			var _nodes=newTree.getNodes();
			if(flag&&event.which==13){
				var node=_nodes[0];
				if(node){
					var tid=node.tId;
					newTree.selectNode(node);
					$("#"+tid+"_a").trigger("click");
				}
			}
		}
	}
	
}

function initDown($this){
	//基础数据类型下拉列表和树
	var url="/common/tree/findTree";
	var id=$this.attr("data-source-id");
	var tid=$this.data("for");
	var type=$this.attr("data-source-type");
	var keyword=$this.val();
	var filter=$this.data("filter");
	
	var $con=$this.next(".ipt-auto-case");
	if($con.size()==0){
		$this.after("<div class='ipt-auto-case tab-case' style='display:none;'/>");
		$con=$this.next(".ipt-auto-case");
		if(type=="tree" && filter && filter.indexOf("select distinct")>0){
			$con.append("<div class='layout-tab-handler'><ul><li class='item active'><a href='javascript:;'>常用</a></li><li class='item'><a href='javascript:;'>全部</a></li></ul></div>")
			$con.append("<div class='layout-tab-content' style='min-height:150px;max-height:200px;'><div class='item active'></div><div class='item'></div></div>")
			$con = $con.find(".layout-tab-content");
		}
		$con.mCustomScrollbar({
	        theme: "dark"
	        ,scrollInertia:0
	    });
		if(type=="list"){
			$con.find(".mCSB_container").append("<ul class='csul zlist'></ul><iframe src='about:blank;'></iframe>")
		}else if(type=="tree" && filter && filter.indexOf("select distinct")>0){
			$con.find(".item").eq(0).append("<ul class='csul zlist' id='"+id+parseInt(Math.random()*1000)+"'></ul><iframe style='display:none;' src='about:blank;'></iframe>");
			$con.find(".item").eq(1).append("<ul class='csul ztree' id='"+id+parseInt(Math.random()*1000)+"'></ul><iframe style='display:none;' src='about:blank;'></iframe>");
		}else{
			$con.find(".mCSB_container").append("<ul class='csul ztree' id='"+id+parseInt(Math.random()*1000)+"'></ul><iframe style='display:none;' src='about:blank;'></iframe>")
		}
	}
	
	var isLoaded=$this.data("loaded");
	if(!isLoaded){
		if(type=="tree" && filter && filter.indexOf("select distinct")>0){
			http.post({
				url:url
				,data:{
					treeid:id
					,type:type
					,filter:filter
				}
				,nomask:true
			}).then(function(result){
				var $hipt=$("#"+tid);
				var $dom=$con.find(".csul.zlist").removeClass("dir-up");
				generateTree($dom,$this,$hipt,result.response);
				if(result.response.length==0){
					$con.prev().find(".item")[1].click();
				}
			});

			http.post({
				url:url
				,data:{
					treeid:id
					,type:type
					,filter:''
				}
				,nomask:true
			}).then(function(result){
				$this.data("loaded",true);
				var $hipt=$("#"+tid);
				var $dom=$con.find(".csul.ztree").removeClass("dir-up");
				generateTree($dom,$this,$hipt,result.response);
			});
		}else{
			http.post({
				url:url
				,data:{
					treeid:id
					,type:type
					,filter:filter=='undefined'?'':filter
				}
				,nomask:true
			}).then(function(result){
				$this.data("loaded",true);
				var $hipt=$("#"+tid);
				var $dom=$con.find(".csul").removeClass("dir-up");
				if(type=="list"){
					generateList($dom,$this,$hipt,result.response);
				}else{
					generateTree($dom,$this,$hipt,result.response);
				}
			})
		}
	}
}
