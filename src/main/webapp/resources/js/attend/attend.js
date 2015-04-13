$(document).ready(function() {
	
	
	// 查看
	$('.view').click(function() {
		
		// 获取数据
		var id = $(this).next('input[type="hidden"]').val();
		$.ajax({
			url: $('#query_attend_url').val() + '/' + id,
			type: "GET",
			success: function(data) {
				
				console.log(data);
				if(data) {
					$('.v_courseName').html(data.course.courseName);
					$('.v_begintime').html(data.beginTime);
					$('.v_endtime').html(data.endTime);
					$('.v_classroom').html(data.course.classRoom);
					$('.v_totalStudent').html(data.totalStudent);
					$('.v_actualStudent').html(data.actualStudent);
					$('.v_lateStudent').html(data.lateStudent);
					$('.v_absentStudent').html(data.absentStudent);
				}
			}
		});
		
		easyDialog.open({
			container : 'view_dialog',
			fixed : false
		});
	});
	
	$('.a_edit').click(function() {
		// 获取数据
		var id = $(this).next('input[type="hidden"]').val();
		$.ajax({
			url: $('#query_attend_url').val() + '/' + id,
			type: "GET",
			success: function(data) {
				if(data) {
					$('.c_courseName').html(data.course.courseName);
					
					// begin
					var beginArray = generateBeginTime(data.course.beginDate);
					var beginRadios = '';
					for(var i = 0; i < beginArray.length; i++) {
						beginRadios += '<input type="radio" name="beginTime" value="' + beginArray[i] + '" class="ipt_radio" ' + (i == 0 ? 'checked=checked' : '')  + '/><label for="type1">' + beginArray[i] + '</label> &nbsp;&nbsp;&nbsp;'
					}
					$('.c_begin_radios').html(beginRadios);
					
					// end time
					var endTimeArray = generateEndTime(beginArray);
					var endTimeRadios = '';
					for(var i = 0; i < endTimeArray.length; i++) {
						endTimeRadios += '<input type="radio" name="endTime" value="' + endTimeArray[i] +'" class="ipt_radio" ' + (i == 0 ? 'checked=checked' : '')  + '/><label for="type2">' + endTimeArray[i] + '</label> &nbsp;&nbsp;&nbsp;'
					}
					$('.c_endtime_radios').html(endTimeRadios);
					
					$('.c_classroom').html(data.course.classRoom);
					
					$('.c_op_id').val(data.id);
				}
			}
		});
		
		easyDialog.open({
			container : 'edit_dialog',
			fixed : false
		});
	});
	
	$('#edit_closeBtn').click(function() {
		var post_data = $('#operation_form').serializeArray();
		console.log(post_data);
		
		$.ajax({
			url: $('.c_check_url').val(),
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
	
	function generateBeginTime(beginTime) {
		var begin = new Date(beginTime).format('hh:mm');
		var beginArray = [];
		beginArray.push(begin);
		
		for(var i = 1; i <= 3; i++) {
			beginArray.push(addTime(begin, i * 30));
		}
		return beginArray;
	}
	
	function generateEndTime(beginTimeArray) {
		var endTimeArray = [];
		for(var i = 0; i < beginTimeArray.length; i++) {
			endTimeArray.push(addTime(beginTimeArray[i], 40));
		}
		
		return endTimeArray;
	}
	
	function addTime(begin, addTime) {
		var hour_min = begin.split(':');
		var added_min = Number(hour_min[1]) + addTime;
		var hour = Number(hour_min[0]);
		
		if(added_min >= 60) {
			hour = hour + 1;
			added_min = added_min - 60;
			hour = (hour >= 24 ? hour - 24 : hour); 
		} 
		
		// handle the time
		hour = hour < 10 ? (hour == 0 ? '00' : '0' + hour) : hour;
		added_min = added_min < 10 ? (added_min == 0 ? '00' : '0' + added_min) : added_min;
		return hour + ':' + added_min;
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