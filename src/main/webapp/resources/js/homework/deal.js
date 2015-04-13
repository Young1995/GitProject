$(document).ready(function() {
	$('.judge_question').each(function() {
		var question = $(this).html(),
			input_template = '<input type="text" name="answer"/>';
		
		question = question.replace('$$', input_template);
		$(this).html(question);
		
	});
	
	$('input[type="button"]').click(function() {
		var datas = [];
		$('form').each(function() {
			var json = {},
				data = $(this).serializeArray();
			$(data).each(function() {
				json[this.name] = this.value;
			});
			
			datas.push(json);
		});
		var post_data = JSON.stringify(datas);
		$.ajax({
			url: $('#post_url').val(),
			type: 'POST',
			data: {'answers': post_data},
			success: function(data){
				console.log(data);
			}
		});
	});
});