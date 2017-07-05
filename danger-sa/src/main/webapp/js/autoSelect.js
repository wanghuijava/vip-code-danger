document.write('<script type="text/javascript" src="'+adminUrl+'/lib/jquery-ui-1.9.0/ui/jquery.ui.widget.js"></script>');
document.write('<script type="text/javascript" src="'+adminUrl+'/lib/jquery-ui-1.9.0/ui/jquery.ui.core.js"></script>');
document.write('<script type="text/javascript" src="'+adminUrl+'/lib/jquery-ui-1.9.0/ui/jquery.ui.position.js"></script>');
document.write('<script type="text/javascript" src="'+adminUrl+'/lib/jquery-ui-1.9.0/ui/jquery.ui.menu.js"></script>');
document.write('<script type="text/javascript" src="'+adminUrl+'/lib/jquery-ui-1.9.0/ui/jquery.ui.autocomplete.js"></script>');
//下拉框自动完成公共方法
var autoSelect = function(id,url){
	$("#"+id).autocomplete({
		source: function(request, response){
			$.ajax({
				url:url, 
				dataType: "json",
				data: {
					maxRows: 10,
					keyword: request.term
				},
				success: function(data) {
					response(
						$.each(data.response,function(item){
							return {
								label: item,
								value: item
							}
						})		
					);
				}
			});
		}
	});
}


