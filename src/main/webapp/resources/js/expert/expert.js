$(document).ready(function() {
	$('#add').click(function() {
		KindEditor.remove('textarea[name="description"]');
		clear_form();
		$('#op_id').val(0);
		$('.description').text('');
		$('.load_department option').each(function() {
			$(this).removeAttr('selected');
		});
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
			});
		
		if($('.editBox').attr('display') != 'none') {
			editor = KindEditor.create('textarea[name="description"]', {
			   resizeType: 1,
			   allowPreviewEmoticons: false,
			   allowImageUpload: false,
			   afterCreate: function() {
				   this.sync();
				   this.html('');
			   },
			   afterChange: function() {
				   this.sync();
			   },
			   afterBlur: function() {
				   this.sync();
			   },
			   items: [
			         'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			         'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			         'insertunorderedlist', '|', 'emoticons', 'link']
			});
		}

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
					KindEditor.remove('textarea[name="description"]');
					$('.departmentName').val(data.departmentName);
					$('.expertName').val(data.expertName);
					$('.description').text(data.description);
					$('.load_department option').each(function() {
						if($(this).val() == data.deparmentId) {
							$(this).attr('selected', 'selected');
						}
					});

					if(data.enabled) {
						$('.enabled_open').attr('checked', 'checked');
					} else {
						$('.enabled_close').attr('checked', 'checked');
					}
					$('#op_id').val(data.id);
					

					easyDialog.open({
						container : 'edit_dialog',
						fixed : false
					});

					
					if($('.editBox').attr('display') != 'none') {
						editor = KindEditor.create('textarea[name="description"]', {
						   resizeType: 1,
						   allowPreviewEmoticons: false,
						   allowImageUpload: false,
						   afterCreate: function() {
							   this.sync();
							   this.html($('.description').text());
						   },
						   afterChange: function() {
							   this.sync();
						   },
						   afterBlur: function() {
							   this.sync();
						   },
						   items: [
						         'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						         'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						         'insertunorderedlist', '|', 'emoticons', 'link']
						});
					}
				}
			}
		});
	});
	var form_validate = $('#operation_form').validate({
		rules: {
			departmentName: {
				required: true
			},
			expertName: {
				required: true
			}
		}, 
		messages: {
			departmentName: {
				required: '名称不能为空'
			},
			expertName: {
				required: '专家姓名不能为空'
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
					$('.v_departmentName').html(data.departmentName);
					$('.v_expertName').html(data.expertName);
					$('.v_description').html(data.description);
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