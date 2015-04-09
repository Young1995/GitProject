$(document).ready(function() {
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
	
	function reloadPage() {
		window.location.reload();
	}
	
	$('.pubHomework').click(function() {
		$('#pub_id').val($(this).next('input[type="hidden"]').val());
		easyDialog.open({
			container : 'pub_homework_box',
			fixed : false
		});
	});
	
	$('#pub_button').click(function() {
		$.ajax({
			url: $('#pub_url').val() + '/' + $('#pub_id').val(),
			type: 'GET',
			success: function(data) {
				if(data.status) {
					$('#op_msg').html('发布成功');
					setTimeout(reloadPage, 1000);
				} else {
					$('#op_msg').html('发布失败');
				}
			}
		});
	});
});
