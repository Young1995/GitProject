$(document).ready(function() {
	$("#add").click(function() {
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
		});
		
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
		}
	});
	
	
	$('.a_edit').click(function() {
		// 获取数据
		var id = $(this).next('input[type="hidden"]').val();
		$.ajax({
			url: $('#query_url').val() + '/' + id,
			type: "GET",
			success: function(data) {
				if(data) {
					$('.title').val(data.title);
					$('#op_id').val(data.id);
					$('textarea[name="content"]').text(data.content);
				}
				
				easyDialog.open({
					container : 'edit_dialog',
					fixed : false
				});
				
				window.content_editor = KindEditor.create('textarea[name="content"]', {
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
