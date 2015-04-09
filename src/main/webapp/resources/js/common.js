$(document).ready(function() {
	var department_id = $('#selected_dep').val();
	// 加载所有的院系
	$.ajax({
		url: $('#load_department').val(),
		type: 'GET',
		success: function(data) {
			if(data) {
				$('.load_department').html('');
				deps_options = '';
				for(var i = 0; i < data.length; i++ ) {
					if(data[i].id == department_id) {
						deps_options += '<option value="' + data[i].id + '" selected="selected">' + data[i].name + '</option>';
					} else {
						deps_options += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
					}
					
				} 
				$('.load_department').html(deps_options);
			}
		}
	});
});