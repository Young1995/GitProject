$(document).ready(function() {
	// 修改
	$('.a_edit').click(function() {
		var row = $(this).closest('tr');
		var role_name = $(row).find('td:eq(0)').html();
		var role_code = $(row).find('td:eq(1)').html();
		var role_desc = $(row).find('td:eq(2)').html();
		var role_id = $(row).find('.role_id').val();
		
		// 写入表单
		$('.role_name').val(role_name);
		$('.role_code').val(role_code);
		$('.role_desc').text(role_desc);
		$('.role_id').val(role_id);
		
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
		});
	});

	// 添加
	$('#add').click(function() {
		// 重置表单
		$('#reset_form').click();
		$('.role_desc').text('');
		$('.role_id').val(0);
		
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
		});
	});
	
	// 更新 ajax提交
	$('#save_or_update').click(function() {
		// 禁用按钮
		$(this).attr("disabled", "disabled");
		
		var post_data = $('#save_or_update_form').serializeArray();
		
		$.ajax({
			url: $('#add_or_edit_url').val(),
			data: post_data,
			type: 'POST',
			success: function(data) {
				$('#op_msg').html(data.msg);
				if(data.status === true) {
					setTimeout(reloadPage, 1500);
				} else {
					$(this).removeAttr('disabled');
				}
			}
		});
	});
	
	function reloadPage() {
		window.location.reload();
	}
	
	// 删除角色
	$('.a_delete').click(function(e) {
		e.preventDefault();
		
		$('#delete_url').val($(this).attr('href'));
		easyDialog.open({
			container: 'confirm_dialog',
			fixed: false
		});
	});
	
	$('#confirm_delete').click(function() {
		window.location.href=$('#delete_url').val();
	});
	
	$('.cancel,.close_btn').click(function() {
		easyDialog.close();
	});
});