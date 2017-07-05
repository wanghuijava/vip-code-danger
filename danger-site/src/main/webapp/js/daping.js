
var nowdate=null;
$(document).ready(function(){
	nowdate=$("[name='nowDate']").val();
	//危险作业-当月计划
	//workPlan();
	//危险作业-今日录入;
	workExecute();
	setInterval('workExecute()',1800000);
	

	//试运投产-当月计划
	//producePlan()
	//试运投产-今日录入
	produceExecute();
	setInterval('produceExecute()',1800000);
	

	setInterval('window.location.reload();',10000);
})

function reloadPage(){
	window.location.reload();
}

function workPlan(){
	http.post({
		url:"/project/workPlan/find"
			,nomask:true
			,data:{page:JSON.stringify({'count':1000}),param:JSON.stringify({'thisMonth':'1'})}
			,success:function(res){
				tpl.render({
					tid:"js-workplan-tmpl"
						,hook:".js-workplan-hook"
						,data:res.response
				});


				var speed1=50;
				var demo = document.getElementById("workplan-demo");
				var demo1 = document.getElementById("workplan-demo1");
				var demo2 = document.getElementById("workplan-demo2");
				
				if($("#workplan-demo1").height()>200){
					demo2.innerHTML=demo1.innerHTML;
					
					function Marquee(){
						if(demo2.offsetTop-demo.scrollTop<=0){
							demo.scrollTop-=demo1.offsetHeight;
						}else{
							demo.scrollTop++
						}
					}
					var MyMar=setInterval(Marquee,speed1);
					demo.onmouseover=function() {clearInterval(MyMar);}
					demo.onmouseout=function() {MyMar=setInterval(Marquee,speed1);}
				}
			}
	});
}

function workExecute(){
	http.post({
		url:"/project/workExecute/find"
			,nomask:true
			,data:{page:JSON.stringify({'count':100}),param:JSON.stringify({'searchStartTimeStr':nowdate,'searchEndTimeStr':nowdate})}
			,success:function(res){
				tpl.render({
					tid:"js-workexecute-tmpl"
						,hook:".js-workexecute-hook"
						,data:res.response
				});

				var speed1=50;
				var demo = document.getElementById("workexecute-demo");
				var demo1 = document.getElementById("workexecute-demo1");
				var demo2 = document.getElementById("workexecute-demo2");
				if($("#workexecute-demo1").height()>200){
					demo2.innerHTML=demo1.innerHTML;
					
					function Marquee(){
						if(demo2.offsetTop-demo.scrollTop<=0){
							demo.scrollTop-=demo1.offsetHeight;
						}else{
							demo.scrollTop++
						}
					}
					var MyMar=setInterval(Marquee,speed1);
					demo.onmouseover=function() {clearInterval(MyMar);}
					demo.onmouseout=function() {MyMar=setInterval(Marquee,speed1);}
				}
			}
	});
}

function producePlan(){
	http.post({
		url:"/project/producePlan/find"
			,nomask:true
			,data:{page:JSON.stringify({'count':1000}),param:JSON.stringify({'thisMonth':'1'})}
			,success:function(res){
				tpl.render({
					tid:"js-produceplan-tmpl"
						,hook:".js-produceplan-hook"
						,data:res.response
				});

				var speed1=50;
				var demo = document.getElementById("produceplan-demo");
				var demo1 = document.getElementById("produceplan-demo1");
				var demo2 = document.getElementById("produceplan-demo2");
				if($("#produceplan-demo1").height()>200){
					demo2.innerHTML=demo1.innerHTML;
					
					function Marquee(){
						if(demo2.offsetTop-demo.scrollTop<=0){
							demo.scrollTop-=demo1.offsetHeight;
						}else{
							demo.scrollTop++
						}
					}
					var MyMar=setInterval(Marquee,speed1);
					demo.onmouseover=function() {clearInterval(MyMar);}
					demo.onmouseout=function() {MyMar=setInterval(Marquee,speed1);}
				}
			}
	});
}

function produceExecute(){
	http.post({
		url:"/project/produceExecute/find"
			,nomask:true
			,data:{page:JSON.stringify({'count':100}),param:JSON.stringify({'searchStartTimeStr':nowdate,'searchEndTimeStr':nowdate})}
			,success:function(res){
				tpl.render({
					tid:"js-produceexecute-tmpl"
						,hook:".js-produceexecute-hook"
						,data:res.response
				});

				var speed1=50;
				var demo = document.getElementById("produceexecute-demo");
				var demo1 = document.getElementById("produceexecute-demo1");
				var demo2 = document.getElementById("produceexecute-demo2");

				if($("#produceexecute-demo1").height()>200){
					demo2.innerHTML=demo1.innerHTML;
					
					function Marquee(){
						if(demo2.offsetTop-demo.scrollTop<=0){
							demo.scrollTop-=demo1.offsetHeight;
						}else{
							demo.scrollTop++
						}
					}
					var MyMar=setInterval(Marquee,speed1);
					demo.onmouseover=function() {clearInterval(MyMar);}
					demo.onmouseout=function() {MyMar=setInterval(Marquee,speed1);}
				}
			}
	});
}