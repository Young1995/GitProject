$(document).ready(function() {
	// 修改
	$('.a_edit').click(function() {
		var row = $(this).closest('tr');
		var name = $(row).find('td:eq(1)').html();
		var id = $(this).next('input[type="hidden"]').val();
		
		// 写入表单
		$('.name').val(name);
		$('.qid').val(id);
		
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
		});
	});

	// 添加
	$('#add').click(function() {
		// 重置表单
		$('#reset_form').click();
		$('.qid').val(0);
		
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
				
				if(data.status === true) {
					$('#op_msg').html('操作成功');
					setTimeout(reloadPage, 1500);
				} else {
					$('#op_msg').html('操作失败');
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
		
		$('#delete_id').val($(this).next('input[type="hidden"]').val());
		easyDialog.open({
			container: 'confirm_dialog',
			fixed: false
		});
	});
	
	$('#confirm_delete').click(function() {
		$.ajax({
			url: $('#delete_url').val() + '?id=' + $('#delete_id').val(),
			type: 'GET',
			success: function(data) {
				if(data.status === true) {
					$('#d_op_msg').html('操作成功');
					setTimeout(reloadPage, 1500);
				} else {
					$('#d_op_msg').html('操作失败');
				}
			}
		});
	});
	
	$('.cancel,.close_btn').click(function() {
		easyDialog.close();
	});
});