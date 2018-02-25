//打开字滑入效果
window.onload = function(){
	$(".connect p").eq(0).animate({"left":"0%"}, 600);
	$(".connect p").eq(1).animate({"left":"0%"}, 400);
};
//jquery.validate表单验证
$(document).ready(function(){
	//登陆表单验证
	$("#loginForm").validate({
		rules:{
			username:{
				required:true,//必填
				minlength:3, //最少6个字符
				maxlength:32,//最多20个字符
			},
			password:{
				required:true,
				minlength:3, 
				maxlength:32,
			},
		},
		//错误信息提示
		messages:{
			username:{
				required:"必须填写用户名",
				minlength:"用户名至少为3个字符",
				maxlength:"用户名至多为32个字符",
				remote: "用户名已存在",
			},
			password:{
				required:"必须填写密码",
				minlength:"密码至少为3个字符",
				maxlength:"密码至多为32个字符",
			},
		},

	});
	$("#login").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		/*$.post("http://localhost:8081/user/login",{"username":username,"password":password},function(result){
			var obj = JSON.parse(result);
			if(obj.code == "000000"){
				window.open("http://localhost:8081/index.html");
			}
		});*/

		/*$.post("http://localhost:8081/user/login",{"username":username,"password":password},function(result){
			var obj = JSON.parse(result);
			if(obj.code == "000000"){
				window.open("http://localhost:8081/index.html");
			}
		});*/
		var loginUrl = location.origin + "/user/login";
		$.ajax({
			method : 'POST',
			url: loginUrl,
			dataType:"json",
			data:{"username":username,"password":password},
			//async: false, //true:异步，false:同步
			//contentType: false,
			//processData: false,
			success: function (data) {
				if(data.code == "000000"){
					//登录成功，但是ajax发起的请求不能跳转页面 这里再请求一次，
					// 判断刚刚登录存得session存在，就跳转到index页面
					var url = location.origin + "/jumpToIndexPage";
					window.location.href = url;
				} else {
					alert(data.desc);
				}
			},
			error: function (err) {
				alert("error");

			}});
	});

	//添加自定义验证规则
	jQuery.validator.addMethod("phone_number", function(value, element) { 
		var length = value.length; 
		var phone_number = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/ 
		return this.optional(element) || (length == 11 && phone_number.test(value)); 
	}, "手机号码格式错误");

});
