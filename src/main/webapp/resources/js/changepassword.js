$(document).ready(function() {
	/***********修改密码验证************/
	$('#changePsw').validate({
		rules: {
			oldPassword: {
				required: true,
				minlength: 6
			},
			newPassword: {
				required: true,
				minlength: 6
			},
			rePassword: {
				required: true,
				minlength: 6,
				equalTo: "#newPassword" 
			}
		},
		messages: {
			oldPassword: {
				required: "旧密码不能为空",
				minlength: "旧密码至少为6位"
			},
			newPassword: {
				required: "新密码不能为空",
				minlength: "新密码至少为6位"
			},
			rePassword: {
				required: "确认新密码不能为空",
				minlength: "确认新密码至少为6位",
				equalTo: "确认新密码必须和新密码一致"
			}
		},
		errorPlacement : function(error, element) {
			error.appendTo($(element).next('div')).addClass('font12');
			$(element).removeClass('error');
		}
	});
});