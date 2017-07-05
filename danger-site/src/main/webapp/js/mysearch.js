//选择时间
function changeTime(str, valNum){
    if(str){
    	$("[name='x-startTime']").val('');
    	$("[name='x-endTime']").val('');
        var startTime="";
        var endTime="";
        //固定时间
        switch (str){
            case 'month':
                //本月第一天到最后一天
                var date=new Date();
                var year=date.getFullYear();
                var month=date.getMonth()+1;
                var temp=new Date(year,month,0);
                var lastDate=temp.getDate();
                startTime=dateFactory.format(new Date(year,month-1,1),"YYYY-MM-DD");
                endTime=dateFactory.format(new Date(year,month-1,lastDate),"YYYY-MM-DD");
                break;
            case 'quarter':  //  add by raomengwen at 2015-4-27
                //本季度第一天到最后一天
        	   var date=new Date();
               var year=date.getFullYear();
               var month=date.getMonth()+1;
               var quarterMonthStart = 0 ;  // 本季度开始月份
               var quarterMonthEnd = 0 ;  // 本季度开始月份
               var spring = 1 ; 
               var summer =4;
               var fall = 7;
               var winter = 10 ;
               if(month<4){
            	   quarterMonthStart = spring; 
               }else if(month<7){
            	   quarterMonthStart = summer; 
               }else if(month<10){
            	   quarterMonthStart = fall; 
               }else {
            	   quarterMonthStart = winter; 
               }
               quarterMonthEnd = quarterMonthStart+2;
               var temp=new Date(year,quarterMonthEnd,0);
               var lastDate=temp.getDate();
               startTime=dateFactory.format(new Date(year,quarterMonthStart-1,1),"YYYY-MM-DD");
               endTime=dateFactory.format(new Date(year,quarterMonthEnd-1,lastDate),"YYYY-MM-DD");
               break;
            case 'year'://  add by raomengwen at 2015-4-27
                //本年度第一天到最后一天
                var date=new Date();
                var year=date.getFullYear();
                var temp=new Date(year,11,0);
                var lastDate=temp.getDate();
                startTime=dateFactory.format(new Date(year,0,1),"YYYY-MM-DD");
                endTime=dateFactory.format(new Date(year,11,lastDate),"YYYY-MM-DD");
                break;
            case 'years'://  add by wanghui at 2015-5-19
                //年度第一天到最后一天
                startTime=dateFactory.format(new Date(valNum,0,1),"YYYY-MM-DD");
                endTime=dateFactory.format(new Date(valNum,11,31),"YYYY-MM-DD");
                break;
            case 'week':
                //本周第一天到最后一天,从周一到周日
                var date=new Date();
                var year=date.getFullYear();
                var month=date.getMonth()+1;
                var day=date.getDay();
                var nowDate=date.getDate();
                startTime=dateFactory.format(new Date(year,month-1,nowDate-day+1),"YYYY-MM-DD");
                endTime=dateFactory.format(new Date(year,month-1,nowDate-day+7),"YYYY-MM-DD");
                break;
            case 'today':
                startTime=dateFactory.format(new Date(),"YYYY-MM-DD");
                endTime=dateFactory.format(new Date(),"YYYY-MM-DD");
                break;
            case 'all':
                break;
        }
        $("#search_startTime").val(startTime);
        $("#search_endTime").val(endTime);
    }else{
        //手动选择时间
        $("#search_startTime").val($("[x-time-start]").val());
        $("#search_endTime").val($("[x-time-end]").val());
    }
}

//状态类条件
function changeState(state){
	$("#search_status").val(state);
}

//代码类条件
function changeCode(code){
	$("#search_code").val(code);
}