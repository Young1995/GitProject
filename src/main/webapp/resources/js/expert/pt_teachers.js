$(document).ready(function() {
	$('#add').click(function() {
		clear_form();
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
			});
	});
	
	$('.a_edit').click(function() {
		clear_form();
		// 获取数据
		var id = $(this).next('input[type="hidden"]').val();
		$.ajax({
			url: $('#pre_update_url').val() + '?id=' + id,
			type: "GET",
			success: function(data) {
				if(data) {
					$('.companyName').val(data.companyName);
					$('.username').val(data.username);
					$('.name').val(data.name);
					$('.email').val(data.email);
					$('.phoneNumber').val(data.phoneNumber);
					
					if(data.gender) {
						$('.gender_male').attr('checked', 'checked');
					} else {
						$('.gender_female').attr('checked', 'checked');
					}

					
					if(data.enabled) {
						$('.enabled_open').attr('checked', 'checked');
					} else {
						$('.enabled_close').attr('checked', 'checked');
					}
					
					$('#op_id').val(data.id);
				}
			}
		});
		
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
		});
	});
	
	// 用户表单验证
	var form_validate = $('#operation_form').validate({
		rules : {
			username : {
				required : true,
				minlength : 6
			},
			name : {
				required : true
			},
			phoneNumber : {
				required : true,
				minlength : 8,
				maxlength : 11
			},
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			username : {
				required : "用户名不能为空",
				minlength : "用户名至少为6位字母、数字、下划线等"
			},
			name : {
				required : "姓名不能为空"
			},
			phoneNumber : {
				required : "电话不能为空",
				minlength : "电话至少为8位",
				maxlength : "电话最多为11位"
			},
			email : {
				required : "邮箱不能为空",
				email : "邮箱格式不正确"
			}
		},
		errorPlacement : function(error, element) {
			error.appendTo($(element).next('div')).addClass('font12');
			$(element).removeClass('error');
		}
	});
	
	// 添加、更新数据
	$('.add_or_update_btn').click(function() {
		if(!form_validate.form()) {
			return;
		}
		
		$(this).attr('disabled', 'disabled');
		var post_data = $('#operation_form').serializeArray();
		
		$.ajax({
			url: $('#add_or_edit_url').val(),
			data: post_data,
			type: 'POST',
			success: function(data) {
				if(data.status === true) {
					$('#op_msg').html('操作成功');
					setTimeout(reloadPage, 1000);
				} else {
					$(this).removeAttr('disabled');
					$('#op_msg').html('操作失败');
				}
			}
		});
	});
	
	// 查看
	$('.view_btn').click(function() {
		// 获取数据
		var id = $(this).next('input[type="hidden"]').val();
		$.ajax({
			url: $('#pre_update_url').val() + '?id=' + id,
			type: "GET",
			success: function(data) {
				if(data) {
					$('.v_companyName').html(data.companyName);
					$('.v_name').html(data.name);
					$('.v_username').html(data.username);
					$('.v_email').html(data.email);
					$('.v_phoneNumber').html(data.phoneNumber);
					
					if(data.gender) {
						$('.v_gender').html('男');
					} else {
						$('.v_gender').html('女');
					}
					
					if(data.enabled) {
						$('.v_enabled').html('启用').addClass('green');
					} else {
						$('.v_enabled').html('禁用').addClass('red');
					}
				}
			}
		});
		
		easyDialog.open({
			container : 'view_dialog',
			fixed : false
		});

	});
	
	// 删除
	$('.a_delete').click(function() {
		var id = $(this).next('input[type="hidden"]').val();
		$('#delete_id').val(id);
		easyDialog.open({
			container : 'delete_dialog',
			fixed : false
		});
	});
	
	$('.delete_btn').click(function() {
		$.ajax({
			url: $('#delete_url').val() + '?id=' + $('#delete_id').val(),
			type: 'GET',
			success: function(data) {
				if(data.status) {
					reloadPage();
				}
			}
		});
	});
	
	$('#closeBtn, .cancel').click(function() {
		easyDialog.close();
	});
	
	// 清空数据
	function clear_form() {
		$('#operation_form input[type="reset"]').click();
	}
	
	function reloadPage() {
		window.location.reload();
	}
	
});