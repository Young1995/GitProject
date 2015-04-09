$(document).ready(function() {
	$('#btn_excel').click(function() {
		easyDialog.open({
			container : 'excel_dialog',
			fixed : false
		});
	});

	$('#btn_add').click(function() {
		// 清空
		$('#add_user_form')[0].reset();
		$('.username').val('').show().prev('span').hide();
		$('#selected_dep').val('');
		$('.load_department option').each(function() {
			$(this).removeAttr('selected');
		});

		$('#add_or_update_user_id').val(0);
		$('.roles').each(function() {
			$(this).removeAttr('checked');
		});
		
		$('input[type="text"],input[type="password"]').removeClass('error').next('.err-wrap').html('');

		easyDialog.open({
			container : 'add_dialog',
			fixed : false
		});
	});

	$('#closeBtn_add, #closeBtn_excel, .cancel').click(function() {
		easyDialog.close();
	});
	
	// 打开修改dialog
	$('.a_edit').click(function() {
		var row = $(this).closest('tr');
		var id = $(row).find('input[type="checkbox"]').val();
		$.ajax({
			url: $('#pre_update_url').val() + '?id=' + id,
			type: 'GET',
			success: function(data) {
				// 填充表单
				$('.username').val(data.username).hide().prev('span').html(data.username).show();
				$('.name').val(data.name);
				if(data.gender) {
					$('.gender_male').attr('checked', 'checked');
				} else {
					$('.gender_female').attr('checked', 'checked');
				}
				$('.email').val(data.email);
				$('.phoneNumber').val(data.phoneNumber);
				console.log(data.department);
				$('#selected_dep').val(data.department);
				$('.load_department option').each(function() {
					if($(this).val() == data.department) {
						$(this).attr('selected', 'selected');
					}
				});
				$('#add_or_update_user_id').val(data.id);
				$('.roles').each(function() {
					var i = 0;
					for(; i <data.roles.length;i++) {
						if($(this).val() == data.roles[i].id) {
							$(this).attr('checked', 'checked');
						} else {
							$(this).removeAttr('checked');
						}

					}
				});
				
				easyDialog.open({
					container : 'add_dialog',
					fixed : false
				});
			}
		});
	});
	
	// 用户表单验证
	var add_uesr_form_validator = $('#add_user_form').validate({
		rules : {
			username : {
				required : true,
				minlength : 6,
				remote: {
					url: $('#verify_name_url').val()
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
			}
		},
		errorPlacement : function(error, element) {
			error.appendTo($(element).next('div')).addClass('font12');
			$(element).removeClass('error');
		}
	});
	
	
	// 添加或者修改用户
	$('.add_or_update_user').click(function() {
		console.log($('#add_or_update_user_id').val());
		if($('#add_or_update_user_id').val() == 0) {
			if(!add_uesr_form_validator.form()) {
				return;
			}
		}
		var post_data = $('#add_user_form').serializeArray();
		
		$.ajax({
			url: $('#add_or_edit_url').val(),
			data: post_data,
			type: 'POST',
			success: function(data) {
				if(data.status === true) {
					$('#op_msg').html(data.msg || "操作成功，正在跳转...");
					setTimeout(reloadPage, 1000);
				} else {
					$('#op_msg').html(data.msg || "操作失败！");
				}
			}
		});
	});
	
	function reloadPage() {
		window.location.reload();
	}
	
	// 启用选择用户
	$('#btn_enabled_users').click(function() {
		$('#enabled_msg').html('');
		$('#is_verified').val(1);
		
		var enabled_dialog = $('#enabled_dialog');
		enabled_dialog.find('.dl_title strong').html('启用所选用户');
		enabled_dialog.find('table tr:eq(1)').find('td').html('确定启用所选用户？');
		
		// 弹出dialog给用户确认
		easyDialog.open({
			container : 'enabled_dialog',
			fixed : false
		});
	});
	
	// 禁用所选用户
	$('#btn_disabled_users').click(function() {
		$('#enabled_msg').html('');
		$('#is_verified').val(0);
		
		var enabled_dialog = $('#enabled_dialog');
		enabled_dialog.find('.dl_title strong').html('禁用所选用户');
		enabled_dialog.find('table tr:eq(1)').find('td').html('确定禁用所选用户？');
		
		// 弹出dialog给用户确认
		easyDialog.open({
			container : 'enabled_dialog',
			fixed : false
		});
	});
	
	$('#confirm_enabled_btn').click(function() {
		// 获取表单数据
		var post_data = $('#user_list_form').serializeArray();
		
		$.ajax({
			url: $('#enabled_url').val(),
			data: post_data,
			type: 'POST',
			success: function(data) {
				if(data.status == true) {
					reloadPage();
				} else {
					$('#enabled_msg').html('请选择一个');
				}
			}
			
		});
	});
	
	// 删除用户
	$('#btn_delete_users').click(function() {
		easyDialog.open({
			container : 'delete_dialog',
			fixed : false
		});
	});
	
	// 删除一个用户
	$('.a_delete').click(function() {
		$('#delete_msg').html('');
		$(this).closest('tr').find('input[type="checkbox"]').attr("checked", "checked");
		easyDialog.open({
			container : 'delete_dialog',
			fixed : false
		});
	});
	
	$('#confirm_delete_btn').click(function() {
		var post_data = $('#user_list_form').serializeArray();
		
		$.ajax({
			url: $('#delete_url').val(),
			data: post_data,
			type: 'POST',
			success: function(data) {
				if(data.status == true) {
					reloadPage();
				} else {
					$('#delete_msg').html('请选择一个');
				}
			}
		});
	});
	
	// 搜索
	$('#search_btn').click(function() {
		var search_key = $('#search_key').val();
		window.location.href = $('#search_url').val() + '?search=' +search_key;
	});
	
	// 批量上传用户
	$('.select_upload_file').click(function() {
		$('#excel_file').click();
	});
	
	$('.upload_users').click(function() {
		$.ajaxFileUpload({
			url: $('#upload_users_url').val(),
			secureuri: false,
			fileElementId: 'excel_file',
			dataType: 'json',
			data: $('#upload_users_form').serializeArray(),
			success: function(data, status) {
				if(status == 'error') {
					$('#upload_users_form .ops_msg').html('添加用户失败!').css("color", "red");
				} else{
					if(data.status) {
						$('#upload_users_form .ops_msg').html('添加用户成功!').css("color", "red");
						setTimeout(reloadPage, 1000);
					} else {
						$('#upload_users_form .ops_msg').html(data.msg).css("color", "red");
					}
				}
			}
		});
	});
});