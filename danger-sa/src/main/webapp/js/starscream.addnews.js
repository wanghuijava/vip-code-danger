$(document).ready(function() {
//init Editor 
//some config
$.extend(window.UEDITOR_CONFIG,{
	UEDITOR_HOME_URL:"../../js/libs/ueditor/"
	//image upload url
	,focus:false
	,initialContent:$("#content").val()
	,imageUrl:"../../Editer/imgUpload/"
	,imagePath:""
	,imageManagerUrl:"../../Editer/imgView/"
})
var content_editer = UE.getEditor('content_editer');
content_editer.addListener("contentChange",function(){
	$("#content").val(content_editer.getContent());
}) ; 
}).on("click",".fixed-but .button .news-add",function(){
	$("#.myform").submit()
});