var showAlert = function(msg,style,title,callback){
	var style=typeof(style)=='undefined'?0:style;
	var title=typeof(title)=='undefined'?'提示信息':title;
	var template;
	switch(style){
	case 0:
		template='<div id="alertTmp" class="alert fade in">' + 
					 '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
					 '<span>'+msg+'</span></div>';
		$(document.body).prepend(template);
		$("#alertTmp").alert();
		setTimeout(function(){$("#alertTmp").alert("close")}, 2000);
		break;
	case 1:
		template='<div id="alertTmp" class="modal hide fade">'+
				 '<div class="modal-header">'+
				 '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
				 '<h3>'+title+'</h3>'+
				 '</div>'+
				 '<div class="modal-body">'+
				 '<p>'+msg+'</p>'+
				 '</div>'+
				 '<div class="modal-footer">'+
				 '<button class="btn" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i> 关闭</button>'+
				 '</div></div>';
		$(document.body).prepend(template);
		$("#alertTmp").modal('show');
		$('#alertTmp').on('hidden', function () {
			$('#alertTmp').remove();
		})
		break;
	case 2:
		template='<div id="alertTmp" class="modal hide fade">'+
				 '<div class="modal-header">'+
				 '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
				 '<h3>'+title+'</h3>'+
				 '</div>'+
				 '<div class="modal-body">'+
				 '<p>'+msg+'</p>'+
				 '</div>'+
				 '<div class="modal-footer">'+
				 '<button id="confirm" class="btn btn-success" ><i class="icon-ok icon-white"></i> 确认</button>'+
				 '<button class="btn" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i> 关闭</button>'+
				 '</div></div>';
		$(document.body).prepend(template);
		$("#alertTmp").modal('show');
		$('#confirm').on('click',function(){
			if(callback){callback()}
			$("#alertTmp").modal('hide');
		});
		$('#alertTmp').on('hidden', function () {
			$('#alertTmp').remove();
		});
		break;
	}
};

//Rewrite window alert
window.alert=showAlert;