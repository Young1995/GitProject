$(document).ready(function() {
	$('#add').click(function() {
		clear_form();
		$('textarea[name="content"]').text('');
		$('.warning').text('');
		$('.keyPoint').text('');
		$('.finishDate').val('');
		$('.pubDate').val('');
		$('#op_id').val(0);
		$('.taskfilename').html('');
		$('#taskFileName').val('');
		$('#taskFileURL').val('');
		$('.pubUser').val('');
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
		});
		
		// datepicker
		$('.pubDate,.finishDate').datepicker({
			dateFormat: 'yy-mm-dd', 
			dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
			monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
		});
		
		if(KindEditor && KindEditor.instances) {
			for(var i = 0; i < KindEditor.instances.length; i++) {
				KindEditor.instances[i].remove();
			}
		}
		
		if($('.editBox').attr('display') != 'none') {
			window.content_editor = KindEditor.create('textarea[name="content"]', {
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
			
			window.keyPoint_editor = KindEditor.create('textarea[name="keyPoint"]', {
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
			
			window.warning_editor = KindEditor.create('textarea[name="warning"]', {
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
					$('.type option').each(function() {
						if($(this).val() == data.type) {
							$(this).attr('selected', 'selected');
						}
					})
					$('.name').val(data.name);
					$('textarea[name="content"]').text(data.content);
					$('.warning').text(data.warning);
					$('.keyPoint').text(data.keyPoint);
					$('.finishDate').val(new Date(data.finishDate).format('yyyy-MM-dd'));
					$('.pubDate').val(new Date(data.pubDate).format('yyyy-MM-dd'));
					$('.pubUser').val(data.pubUser);
					
					if(data.taskFileName) {
						$('.taskfilename').html(data.taskFileName);
						$('#taskFileName').val(data.taskFileName);
						$('#taskFileURL').val(data.taskFileURL);
					}
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
					
					$('#op_id').val(data.id);
					
					if(KindEditor && KindEditor.instances) {
						for(var i = 0; i < KindEditor.instances.length; i++) {
							KindEditor.instances[i].remove();
						}
					}
					
					if($('.editBox').attr('display') != 'none') {
						
						KindEditor.create('textarea[name="content"]', {
						   resizeType: 1,
						   allowPreviewEmoticons: false,
						   allowImageUpload: false,
						   afterCreate: function() {
							   this.sync();
							   this.html($('textarea[name="content"]').text());
							   console.log($('textarea[name="content"]').text());
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
						
						KindEditor.create('textarea[name="warning"]', {
							   resizeType: 1,
							   allowPreviewEmoticons: false,
							   allowImageUpload: false,
							   afterCreate: function() {
								   this.sync();
								   this.html($('.warning').text());
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
						
						KindEditor.create('textarea[name="keyPoint"]', {
							   resizeType: 1,
							   allowPreviewEmoticons: false,
							   allowImageUpload: false,
							   afterCreate: function() {
								   this.sync();
								   this.html($('.keyPoint').text());
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
		
		// datepicker
		$('.pubDate,.finishDate').datepicker({
			dateFormat: 'yy-mm-dd', 
			dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
			monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
		});
		
		
	});
	
	
	
	// 添加、更新数据
	$('.add_or_update_btn').click(function() {
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
					$('.v_type').html(data.type == 1 ? '临时任务' : '常规任务');
					$('.v_name').html(data.name);
					$('.v_content').html(data.content);
					$('.v_warning').html(data.warning);
					$('.v_keyPoint').html(data.keyPoint);
					$('.v_finishDate').html(new Date(data.finishDate).format('yyyy-MM-dd'));
					$('.v_dep_major').html(data.department.name + '学院 ' + data.major.name + '专业');
					$('.v_pubDate').html(new Date(data.pubDate).format('yyyy-MM-dd'));
					$('.v_file_url').attr('href', data.taskFileURL);
					$('.v_taskfile').html(data.taskFileName);
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
	
	// 搜索
	$('.search_btn').click(function() {
		window.location.href=$('#search_url').val() + '?name=' + $('.search_name').val().trim();
	});
	
	// 上传文件
	$('input[name="taskfile"]').change(function() {
		$.ajaxFileUpload({
			url: $('#upload_url').val(),
			secureuri: false,
			fileElementId: 'taskfile',
			dataType: 'json',
			success: function(data, status) {
				if(status == 'error') {
					alert('error');
				} else{
					$('.taskfilename').html(data.file_name);
					$('#taskFileName').val(data.file_name);
					$('#taskFileURL').val(data.file_url);
				}
			}
		});
	});
});

Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1,  
        "d+": this.getDate(),  
        "h+": this.getHours(),  
        "m+": this.getMinutes(),  
        "s+": this.getSeconds(),  
        "q+": Math.floor((this.getMonth() + 3) / 3),  
        "S": this.getMilliseconds()  
    }  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    }  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}  