var CalendarData = new Array(100);
var madd = new Array(12);
var tgString = "甲乙丙丁戊己庚辛壬癸";
var dzString = "子丑寅卯辰巳午未申酉戌亥";
var numString = "一二三四五六七八九十";
var monString = "正二三四五六七八九十冬腊";
var weekString = "日一二三四五六";
var sx = "鼠牛虎兔龙蛇马羊猴鸡狗猪";
var cYear, cMonth, cDay, TheDate;
CalendarData = new Array(0xA4B, 0x5164B, 0x6A5, 0x6D4, 0x415B5, 0x2B6, 0x957,
		0x2092F, 0x497, 0x60C96, 0xD4A, 0xEA5, 0x50DA9, 0x5AD, 0x2B6, 0x3126E,
		0x92E, 0x7192D, 0xC95, 0xD4A, 0x61B4A, 0xB55, 0x56A, 0x4155B, 0x25D,
		0x92D, 0x2192B, 0xA95, 0x71695, 0x6CA, 0xB55, 0x50AB5, 0x4DA, 0xA5B,
		0x30A57, 0x52B, 0x8152A, 0xE95, 0x6AA, 0x615AA, 0xAB5, 0x4B6, 0x414AE,
		0xA57, 0x526, 0x31D26, 0xD95, 0x70B55, 0x56A, 0x96D, 0x5095D, 0x4AD,
		0xA4D, 0x41A4D, 0xD25, 0x81AA5, 0xB54, 0xB6A, 0x612DA, 0x95B, 0x49B,
		0x41497, 0xA4B, 0xA164B, 0x6A5, 0x6D4, 0x615B4, 0xAB6, 0x957, 0x5092F,
		0x497, 0x64B, 0x30D4A, 0xEA5, 0x80D65, 0x5AC, 0xAB6, 0x5126D, 0x92E,
		0xC96, 0x41A95, 0xD4A, 0xDA5, 0x20B55, 0x56A, 0x7155B, 0x25D, 0x92D,
		0x5192B, 0xA95, 0xB4A, 0x416AA, 0xAD5, 0x90AB5, 0x4BA, 0xA5B, 0x60A57,
		0x52B, 0xA93, 0x40E95);
madd[0] = 0;
madd[1] = 31;
madd[2] = 59;
madd[3] = 90;
madd[4] = 120;
madd[5] = 151;
madd[6] = 181;
madd[7] = 212;
madd[8] = 243;
madd[9] = 273;
madd[10] = 304;
madd[11] = 334;

function GetBit(m, n) {
	return (m >> n) & 1;
}
function e2c() {
	TheDate = (arguments.length != 3) ? new Date() : new Date(arguments[0],
			arguments[1], arguments[2]);
	var total, m, n, k;
	var isEnd = false;
	var tmp = TheDate.getYear();
	if (tmp < 1900) {
		tmp += 1900;
	}
	total = (tmp - 1921) * 365 + Math.floor((tmp - 1921) / 4)
			+ madd[TheDate.getMonth()] + TheDate.getDate() - 38;

	if (TheDate.getYear() % 4 == 0 && TheDate.getMonth() > 1) {
		total++;
	}
	for (m = 0;; m++) {
		k = (CalendarData[m] < 0xfff) ? 11 : 12;
		for (n = k; n >= 0; n--) {
			if (total <= 29 + GetBit(CalendarData[m], n)) {
				isEnd = true;
				break;
			}
			total = total - 29 - GetBit(CalendarData[m], n);
		}
		if (isEnd)
			break;
	}
	cYear = 1921 + m;
	cMonth = k - n + 1;
	cDay = total;
	if (k == 12) {
		if (cMonth == Math.floor(CalendarData[m] / 0x10000) + 1) {
			cMonth = 1 - cMonth;
		}
		if (cMonth > Math.floor(CalendarData[m] / 0x10000) + 1) {
			cMonth--;
		}
	}
}

function GetcDateString() {
	var tmp = "";
	tmp += tgString.charAt((cYear - 4) % 10);
	tmp += dzString.charAt((cYear - 4) % 12);
	tmp += "(";
	tmp += sx.charAt((cYear - 4) % 12);
	tmp += ")年 ";
	if (cMonth < 1) {
		tmp += "(闰)";
		tmp += monString.charAt(-cMonth - 1);
	} else {
		tmp += monString.charAt(cMonth - 1);
	}
	tmp += "月";
	tmp += (cDay < 11) ? "初" : ((cDay < 20) ? "十" : ((cDay < 30) ? "廿" : "三十"));
	if (cDay % 10 != 0 || cDay == 10) {
		tmp += numString.charAt((cDay - 1) % 10);
	}
	return tmp;
}

function GetLunarDay(solarYear, solarMonth, solarDay) {
	//solarYear = solarYear<1900?(1900+solarYear):solarYear;
	if (solarYear < 1921 || solarYear > 2020) {
		return "";
	} else {
		solarMonth = (parseInt(solarMonth) > 0) ? (solarMonth - 1) : 11;
		e2c(solarYear, solarMonth, solarDay);
		return GetcDateString();
	}
}

var D = new Date();
var yy = D.getFullYear();
var mm = D.getMonth() + 1;
var dd = D.getDate();
var ww = D.getDay();
var ss = parseInt(D.getTime() / 1000);
if (yy < 100)
	yy = "19" + yy;
function GetCNDate() {
	return GetLunarDay(yy, mm, dd);
}

function Lunar2Obj(){
	var tmp = (cDay < 11) ? "初" : ((cDay < 20) ? "十" : ((cDay < 30) ? "廿" : "三十"));
	if (cDay % 10 != 0 || cDay == 10) {
		tmp += numString.charAt((cDay - 1) % 10);
	}
	return {
		year:cYear
		,month:cMonth
		,date:tmp
	}
}

var tempDate=null
	,tempDom=null
	,_timer=null;

function getLunarObj(date){
	e2c(date.getFullYear(), date.getMonth()+1, date.getDate());
	return Lunar2Obj();
}


/**
 * 弹出显示日历上的气泡
 * */

function showPopup(date,dom){
	tempDate=date,tempDom=dom;
	var $popup=$(".duty-popup");
	var dateStr=dateFactory.format(date,"YYYY-MM-DD");
	$popup.find("#dutyDateStr").val(dateStr);
	
	var $dom=$(dom);
	$popup.removeClass("aright");
	var $par=$dom.closest("table");
	var position=$dom.position();
	var top=position.top;
	var left=position.left+$dom.width();
	$popup.find(".tran-outer,.tran-inner").css({top:30})
	if(left>$par.width()-$dom.width()-20){
		left=position.left-$popup.width();
		$popup.addClass("aright");
	}
	if(top+$popup.height()>$par.height()){
		top=$par.height()-$popup.height();
		$popup.find(".tran-outer,.tran-inner").css({top:position.top-top+30})
	}
	$popup.show().css({
		top:top
		,left:left
	})
	
	$(".duty-popup .popup-close").off("click").on("click",function(){
		$popup.hide();
	})
	var _days=["日","一","二","三","四","五","六"];
	var _date=date;
	var _day=_date.getDay();
	var o={
		year:_date.getFullYear()
		,month:_date.getMonth()+1
		,date:_date.getDate()
		,day:_days[_day]
	}
	
	try{
		tpl.render({
			tid:"calendar-tmpl"
			,hook:"#calendar-body"
			,data:o
		})
	}catch(e){}
	
	$("[poshy-tip]").poshytip('destroy');
}

/**
 * 关闭日历上的气泡
 * */

function closePopup(){
	tempDate=null
	,tempDom=null;
	$(".duty-popup").hide()
}

$(window).resize(function(){
	clearTimeout(_timer);
	_timer=setTimeout(function(){
		if(tempDom&&tempDom){
			showPopup(tempDate,tempDom)
		}
	},200)
})

/**
 * 增加农历
 */

function addLunar(){
    $("#CalendarBody > tr > td").each(function(){
        var $this=$(this);
        var date=new Date($this.attr("date"));
        var holidayDate=getLunarObj(date);
        if(holidayDate){
            //$this.addClass("holiday");
            if($this.find(".lunar-label").size()==0){
                $this.find(".DateLabel").append("<span class='lunar-label'>"+holidayDate.date+"</span>")
            }
        }
    })
}

/**
 * 查询是否节假日
 * @param {} dateIn
 */
function searchHoliday(dateIn){
    var date=dateIn;
    var year=date.getFullYear();
    var month=date.getMonth()+1;

	//var startDate=$("#CalendarBody > tr > td:first").attr("date");
	//var endDate=$("#CalendarBody > tr > td:last").attr("date");
    //查询假日管理
    http.post({
    		url:"/duty/dutyHoliday/find"
    		,data:{year:year,month:month}
    	})
        .then(function(result){
            var data=result.response;
            if(data.length==0) return false;
            for(var i =0;i<data.length;i++){
            	var _item=data[i];
            	var _currentDate=new Date(_item.year,_item.month-1,_item.day);
            	setHoliday(_currentDate,_item.holidaytypeId,_item.id);
            }
        });
}

/***
 * 设置节假日样式
 * @param {} dateIn
 * @param {} flag
 * @param {} tid
 */

function setHoliday(dateIn,flag,tid){
    var dateStr=dateFactory.format(dateIn,"MM/DD/YYYY",true);
    var $dom=$("[date='"+dateStr+"']");
    	$dom.attr("data-tid",tid);
    if(!flag){//如果是周末,则安排上班
		$dom.addClass("workday").removeClass("holiday");
    	if($dom.find(".work-label").size()==0){
    		$dom.find(".DateLabel").append("<div class='work-label'>班</div>")
        }else{
        	$dom.find(".work-label").text("班");
        }
    }else{//平时安排休息
		$dom.removeClass("workday").addClass("holiday");;
        if($dom.find(".work-label").size()==0){
        	$dom.find(".DateLabel").append("<div class='work-label'>休</div>")
        }else{
        	$dom.find(".work-label").text("休");
        }
    }
}

function commonCal(date){
	closePopup();
	searchHoliday(date);
	addLunar();
}

function formatInfo(list) {
    var _list=[];
    var i=1;
	for(var j=0;j<list.length;j++){
		var item=list[j];
		var titleStr="";
        var dutys=item.dutyPlanTypeVOs;
		for(var k=0;k<dutys.length;k++){
			var _item=dutys[k];
			var persons=_item.orgUsers;
            var personArr=[];
            if(persons){
				for(var l=0;l<persons.length;l++){
					var person=persons[l];
					personArr.push(person.username);
				}
            }
            titleStr+="<dl class='duty-list group'><dt>"+_item.dutyType.dutyTypeName+"：</dt><dd>"+personArr.join(",")+"</dd></dl>"
		}
        _list.push({
            EventID:i
            ,StartDateTime:item.dutyDateStr
            ,Title:titleStr
            ,URL:"javascript:;"
        })
        i++;
	}
    return _list;
}
$(document).on("click",function(){
	$("[poshy-tip]").poshytip('destroy');
})
