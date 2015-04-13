$(function() {
	$('.box').Tabs({
		event : 'click',
		callback: function() {
			$('#student_register,#teacher_register').find('.err-wrap').html('');
		}
	});
	/** *************** 注册信息验证 ***************** */
	
	var verify_name_url = $('#verify_name_url').val();
	// 验证学生注册信息
	$('#student_register').validate({
		rules : {
			username : {
				required : true,
				minlength : 6,
				remote: {
					url: verify_name_url
				}
			},
			password : {
				required : true,
				minlength : 6
			},
			cpassword : {
				required : true,
				minlength : 6,
				equalTo : "#password"
			},
			name : {
				required : true
			},
			positionNo : {
				required : true,
				minlength : 6,
				maxlength : 12
			}
		},
		messages : {
			username : {
				required : "用户名不能为空",
				minlength : "用户名至少为6位字母、数字、下划线等",
				remote: "用户名已存在"
			},
			password : {
				required : "密码不能为空",
				minlength : "密码至少为6位字母、数字、下划线等"
			},
			cpassword : {
				required : "确认密码不能为空",
				minlength : "密码至少为6位字母、数字、下划线等",
				equalTo : "两次输入密码不一致"
			},
			name : {
				required : "姓名不能为空"
			},
			positionNo : {
				required : "学号不能为空",
				minlength : "学号至少为6位",
				maxlength : "学号最多为12位"
			}
		},
		errorPlacement : function(error, element) {
			error.appendTo($(element).next('div')).addClass('font12');
			$(element).removeClass('error');
		}
	});
	// 验证教师注册信息
	$('#register_teacher').validate({
		rules : {
			username : {
				required : true,
				minlength : 6,
				remote: {
					url: verify_name_url
				}
			},
			password : {
				required : true,
				minlength : 6
			},
			cpassword : {
				required : true,
				minlength : 6,
				equalTo : "#t_password"
			},
			name : {
				required : true
			},
			positionNo : {
				required : true,
				minlength : 6,
				maxlength : 12
			}
		},
		messages : {
			username : {
				required : "用户名不能为空",
				minlength : "用户名至少为6位字母、数字、下划线等",
				remote: "用户名已存在"
			},
			password : {
				required : "密码不能为空",
				minlength : "密码至少为6位字母、数字、下划线等"
			},
			cpassword : {
				required : "确认密码不能为空",
				minlength : "密码至少为6位字母、数字、下划线等",
				equalTo : "两次输入密码不一致"
			},
			name : {
				required : "姓名不能为空"
			},
			positionNo : {
				required : "工号不能为空",
				minlength : "工号至少为6位",
				maxlength : "工号最多为12位"
			}
		},
		errorPlacement : function(error, element) {
			error.appendTo($(element).next('div')).addClass('font12');
		}
	});
	/** *************** 注册信息验证 ***************** */
});