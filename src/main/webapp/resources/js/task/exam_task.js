$(document).ready(function() {
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
	$('.a_view').click(function() {
		if(KindEditor && KindEditor.instances) {
			for(var i = 0; i < KindEditor.instances.length; i++) {
				KindEditor.instances[i].remove();
			}
		}
		// 获取数据
		var id = $(this).next('input[type="hidden"]').val();
		$.ajax({
			url: $('#pre_update_url').val() + '/' + id,
			type: "GET",
			success: function(data) {
				if(data) {
					$('.type').html(data.type == 1 ? '临时任务' : '常规任务');
					$('.name').html(data.name);
					$('.content_v').html(data.content);
					$('.warning').html(data.warning);
					$('.keyPoint').html(data.keyPoint);
					$('.finishDate').html(new Date(data.finishDate).format('yyyy-MM-dd'));
					$('.dep_major').html(data.department.name + '学院 ' + data.major.name + '专业');
					$('.pubDate').html(new Date(data.pubDate).format('yyyy-MM-dd'));
					$('.executeFeedback').html(data.executeFeedback);
					$('textarea[name="examFeedback"]').text(data.examFeedback);
					if(data.taskFileName) {
						$('.taskfile').html(data.taskFileName);
						$('.taskfile_link').attr('href', data.taskFileURL);
					}
					
					if(data.executeFileName) {
						$('.execute_file_name').html(data.executeFileName);
						$('.execute_file_url').attr('href', data.executeFileURL);
					}
					
					if(data.examFileName) {
						$('.examfilename').html(data.examFileName);
					}
					
					$('#op_id').val(data.id);
					
					$('.score option').each(function() {
						if($(this).val() == data.examScore) {
							$(this).attr('selected', 'selected');
						}
					});
				}
			}
		});
		
		easyDialog.open({
			container : 'view_dialog',
			fixed : false
		});
		
		
		KindEditor.create('textarea[name="examFeedback"]', {
		   resizeType: 1,
		   allowPreviewEmoticons: false,
		   allowImageUpload: false,
		   afterCreate: function() {
			   this.sync();
			   this.html($('textarea[name="examFeedback"]').text() + '');
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

	});
	
	// 查看
	$('.a_result').click(function() {
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
					$('.v_executeFeedback').html(data.executeFeedback);
					$('.v_examFeedback').html(data.examFeedback);
					$('.v_score').html(data.examScore + '分');
					
					if(data.taskFileName) {
						$('.v_taskfile').html(data.taskFileName);
						$('.v_taskfile_link').attr('href', data.taskFileURL);
					}
					
					if(data.executeFileName) {
						$('.v_execute_file_name').html(data.executeFileName);
						$('.v_execute_file_url').attr('href', data.executeFileURL);
					}
					
					if(data.examFileName) {
						$('.v_exam_file_name').html(data.examFileName);
						$('.v_exam_file_url').attr('href', data.examFileURL);
					}
				}
			}
		});
		
		easyDialog.open({
			container : 'result_dialog',
			fixed : false
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
					$('.examfilename').html(data.file_name);
					$('#examFileName').val(data.file_name);
					$('#examFileURL').val(data.file_url);
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
    };  
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