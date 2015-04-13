$(document).ready(function() {
	$("#add").click(function() {
		
		$('input[type="reset"]').click();
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
		});
		
		// datepicker
		$('.startDate,.endDate').datepicker({
			dateFormat: 'yy-mm-dd', 
			dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
			monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
		});
	});
	
	
	$('.a_edit').click(function() {
		var id = $(this).next('input[type="hidden"]').val();
		$.ajax({
			url: $('#query_url').val() + '/' + id,
			type: "GET",
			success: function(data) {
				if(data) {
					$('.title').val(data.title);
					$('#op_id').val(data.id);
					$('.startDate').val(new Date(data.startDate).format('yyyy-MM-dd'));
					$('.endDate').val(new Date(data.endDate).format('yyyy-MM-dd'));
					
					$('select[name="courseId"] option').each(function() {
						if(data.onlineCouse && $(this).val() == data.onlineCourse.id) {
							$(this).attr('selected', 'selected');
						}
					});
					
					$('select[name="keypointId"] option').each(function() {
						if(data.keypoint && $(this).val() == data.keypoint.id) {
							$(this).attr('selected', 'selected');
						}
					});
				}
				
				// datepicker
				$('.startDate,.endDate').datepicker({
					dateFormat: 'yy-mm-dd', 
					dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
					monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
				});
				
				easyDialog.open({
					container : 'edit_dialog',
					fixed : false
				});
			}
		});
	});
	
	$('#saveorupdate').click(function() {
		var post_data = $('#operation_form').serializeArray();
		console.log(post_data);
		
		$.ajax({
			url: $('#create_url').val(),
			type: 'POST', 
			data: post_data,
			success: function(data) {
				console.log(data);
				if(data.status) {
					reloadPage();
				}
			}
		});
	});
	
	$('.a_delete').click(function() {
		// 获取数据
		$('#delete_id').val($(this).next('input[type="hidden"]').val());
		
		easyDialog.open({
			container : 'delete_dialog',
			fixed : false
		});
	});
	
	$('#delete_button').click(function() {
		var id = $('#delete_id').val();
		
		$.ajax({
			url: $('#delete_url').val() + '/' + id,
			type: "GET",
			success: function(data) {
				if(data.status) {
					reloadPage();
				}
			}
		});
	});
	
	$('.close_btn, .cancel').click(function() {
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
};