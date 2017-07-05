$(document).ready(function(){
	
}).on("click","#importData",function(){
	var _form=null;
	var that=null;
	var resourcetype=$(this).data("resourcetype");
	var importUrl=$(this).data("importurl");
	openDialog(siteUrl+"/resource/importPage/"+resourcetype,{
		title:"导入计划"
		,id:"messageDeal"
		,init:function(){
			that=this;
		}
		,button:[
	          {
	        	  name:"<span class='btn-otr'><span class='btn-inr'><i class='icon icon-save'></i>导入</span></span>"
	        	,className:"btn btn-green"
	        	,callback:function(){
	        		var _flag=null;
	        		
	        		var $form=that.iframe.contentWindow.$("#import-form");
	        			_flag=$form.fval("validate");
	        			if(_flag){
	        				var param=$form.getParam();
	        				//要验证文件格式

	        				http.post({
	                			url:importUrl
	                			,data:$form.serialize()
	                			,success:function(result){
	                				if(result.code==200){
	                					that.close();
	                					tips("导入成功！");
	                        			//父表单提交
	                        			tableform.submit();
	                				}else{
	                					$form.find("#showMessage").html(result.message);
	                				}
	                			}
	                		})
	        			}
	        		return false;
	        	}
	          }
      ]
	})
})
