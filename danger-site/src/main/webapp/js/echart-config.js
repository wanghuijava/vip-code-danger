/***
 * @description echarts配置
 */

var _option={
		title : {
	        text: '',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        orient : 'vertical',
	        x : 'left'
	    }
};
   
function getOption(title,alias,group,data,dom){
	var option=$.extend(null,_option);
		dom=dom||document;
	var ldata=[],_ldata=[],xdata=[],xtempobj={},series=[],isPie=false,value=[];
	for(var j in group){	
		var groupItem=group[j];
		if(group.hasOwnProperty(j)){
			ldata.push(groupItem.title);
		}
		var seriesItem={
			name:groupItem.title
			,type:groupItem.type
			,data:[]
			,radius : '55%'
	        ,center: ['50%', '50%']
	        ,markPoint:{
        		data:[
        		      {type:'max',name:'最大值'},
        		      {type:'min',name:'最小值'}
        		      ]
        	}
        	,markLine:{
        		data:[
        		      {type:'average',name:'平均值'}
        		      ]
        	}
		}
		switch(groupItem.type){
			case 'pie':
				for(var i =0;i<data.length;i++){
					var item = data[i];
					var _value=item[groupItem.value];
					isPie=true;
					_ldata.push(item[groupItem.name]);
					seriesItem.data.push({
						name:item[groupItem.name]
						,value:_value
					});
				}
				xtempobj[item[alias['name']]]={};
				option.tooltip.trigger="item";
				option.tooltip.formatter="{a} <br/>{b}:{c}";
				seriesItem.markPoint=null;
				seriesItem.markLine=null;
				if($(dom).width()<300){
					seriesItem.itemStyle={
						normal:{
							label:{show:false}
							,labelLine:{show:false}
						}
					}
				}
				break;
			case 'funnel':
				for(var i =0;i<data.length;i++){
					var item = data[i];
					var _value=item[groupItem.value];
					isPie=true;
					_ldata.push(item[groupItem.name]);
					seriesItem.data.push({
						name:item[groupItem.name]
						,value:_value
					});
				}
				xtempobj[item[alias['name']]]={};
				option.tooltip.trigger="item";
				option.tooltip.formatter="{a} <br/>{b}:{c}";
				seriesItem.markPoint=null;
				seriesItem.markLine=null;
				seriesItem.x="10%"
				seriesItem.width="80%"
				if($(dom).width()<300){
					seriesItem.itemStyle={
						normal:{
							label:{show:false}
							,labelLine:{show:false}
						}
					}
				}
				break;
			case 'radar':
				var polar = [];
				var value =[] ;
				var indicator = [] ; 
				for(var i =0;i<data.length;i++){
					var item = data[i];
					var _value=item[groupItem.value];
					_ldata.push(item[groupItem.name]);
					value.push(_value);
					indicator.push({
						text:item[groupItem.name]
						,max:100
					})
					xtempobj[item[alias['name']]]={};
				}
				seriesItem.data.push({
					name:groupItem.title
					,value:value
				});
				polar.push({
					indicator:indicator
				});
				option.polar =polar;
				seriesItem.markPoint=null;
				seriesItem.markLine=null;
				seriesItem.center =null;
				seriesItem.radius =null;
				break;
			case 'bar':
			default:
				for(var i =0;i<data.length;i++){
					var item = data[i];
					var _value=item[groupItem.value];
					if(_value){
						option.tooltip.trigger="item";
						option.tooltip.formatter="{a} <br/>{b}:{c}";
						seriesItem.data.push(_value);
						xtempobj[item[alias['name']]]={};
					}
				}
	   			option.xAxis=[{type:'category'}];
	    		option.yAxis=[{position:'top',type:'value'}];
				option.grid={
					x:40
					,x2:30
					,y2:30
				}
				break;
		}
		series.push(seriesItem);
	}
	for(var i in xtempobj){
		xdata.push(i);
	}
	if(isPie){ldata=_ldata;}
	option.title.text=title;
	option.legend.data=ldata;
	if(option.xAxis){
		option.xAxis[0].data=xdata
	}
	option.series=series;
	return option;
}