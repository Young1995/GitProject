/***
 * 用户头像上传及删除操作
 */
$(document).ready(function() {
	$('.change_profile').click(function() {
		$('#uploadImage').click();
	});
	
	$('#uploadImage').change(function() {
		console.log('user select the file');
		$.ajaxFileUpload({
			url: $('#upload_url').val(),
			secureuri: false,
			fileElementId: 'uploadImage',
			dataType: 'json',
			success: function(data, status) {
				if(status == 'error') {
					alert('error');
				} else{
					$('.user_profile').attr("src", data.path);
				}
			}
		});
	});
	
	$('.delete_profile').click(function() {
		easyDialog.open({
			container : 'delete_dialog',
			fixed : false
		});
	});
	
	$('.close_btn, .cancel').click(function() {
		easyDialog.close();
	});
	
	
	$('#confirm_delete_btn').click(function() {
		$.ajax({
			url: $('#delete_url').val(),
			type: 'GET',
			success: function(data) {
				if(data.status) {
					window.location.reload();
				}
			}
		});
	});
});