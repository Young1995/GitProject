$(document).ready(function() {
	$('#add').click(function() {
		clear_form();
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
			});
		
		if($('.editBox').attr('display') != 'none') {
			editor = KindEditor.create('textarea[name="details"]', {
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
			url: $('#pre_update_url').val() + '/' + id,
			type: "GET",
			success: function(data) {
				if(data) {
					console.log(data);
					$('.name').val(data.name);
					$('.details').text(data.details);
					$('.startDate').val(data.formatStartDate);
					$('.terminalDate').val(data.formatTerminalDate);
					$('.department option').each(function() {
						if($(this).val() == data.department.id) {
							$(this).attr('selected', 'selected');
						}
					});
					
					$('.marjor option').each(function() {
						if($(this).val() == data.major.id) {
							$(this).attr('selected', 'selected');
						}
					});

					
					$('.projectTypeId option').each(function() {
						if($(this).val() == data.projectType.id) {
							$(this).attr('selected', 'selected');
						}
					});

					
					$('.coCompanyId option').each(function() {
						if($(this).val() == data.coCompany.id) {
							$(this).attr('selected', 'selected');
						}
					});

					
					if(data.autoSign) {
						$('.enabled_open').attr('checked', 'checked');
					} else {
						$('.enabled_close').attr('checked', 'checked');
					}
					
					$('#op_id').val(data.id);
					
					if($('.editBox').attr('display') != 'none') {
						
						editor = KindEditor.create('textarea[name="details"]', {
						   resizeType: 1,
						   allowPreviewEmoticons: false,
						   allowImageUpload: false,
						   afterCreate: function() {
							   this.sync();
							   this.html($('.details').text());
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
		
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
		});
	});
	
	// 用户表单验证
	var form_validate = $('#operation_form').validate({
		rules : {
			name : {
				required : true
			},
			details : {
				required : true,
			}
		},
		messages : {
			name : {
				required : "项目名称不能为空"
			},
			details : {
				required : "项目详情不能为空"
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
		console.log(post_data);
		
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
			url: $('#pre_update_url').val() + '/' + id,
			type: "GET",
			success: function(data) {
				if(data) {
					$('.v_name').html(data.name);
					$('.v_department').html(data.department.name);
					$('.v_major').html(data.major.name);
					$('.v_projectType').html(data.projectType.settingName);
					$('.v_coCompany').html(data.coCompany.settingName);
					$('.v_details').html(data.details);
					
					if(data.autoSign) {
						$('.v_autosign').html('是').addClass('green');
					} else {
						$('.v_autosign').html('否').addClass('red');
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
			url: $('#delete_url').val() + '/' + $('#delete_id').val(),
			type: 'GET',
			success: function(data) {
				if(data.status) {
					reloadPage();
				}
			}
		});
	});
	
	// verify co-project
	$('.a_verify').click(function() {
		$('#update_id').val($(this).next('input[type="hidden"]').val());
		easyDialog.open({
			container : 'verify_dialog',
			fixed : false
		});
	});
	
	$('.update_status_btn').click(function() {
		var post_data = $('#update_status_form').serializeArray();
		console.log(post_data);
		$.ajax({
			url: $('#verify_url').val(),
			type: 'POST',
			data: post_data,
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
	
	$('.department').change(function() {
		var dep_id = $(this).val();
		load_major(dep_id);
	});
	
	// 首次加载
	var default_dep_id = $('.department option:first').val();
	load_major(default_dep_id);
	
	function load_major(dep_id) {
		// 加载所有的院系
		$.ajax({
			url: $('#load_major').val() + '?depId=' + dep_id,
			type: 'GET',
			success: function(data) {
				if(data) {
					$('.major').html('');
					deps_options = '';
					for(var i = 0; i < data.length; i++ ) {
						deps_options += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
					} 
					$('.major').html(deps_options);
				}
			}
		});
	}
});