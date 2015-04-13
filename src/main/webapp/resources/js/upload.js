$(document).ready(function() {	
	var uploader = new plupload.Uploader({
	    runtimes : 'html5,flash,silverlight,html4',
	     
	    browse_button : 'pickfiles', // 选择文件按钮
	    container: document.getElementById('image-wrapper'), // 必须定义一个容器放swf等，同时此处只能用js原生获取节点方式
	    file_data_name: "files", // 后端接收数据时使用的名字，类似于form表单中的name
	     
	    url : "/edu-admin/upload/?id=" + $('#current_id').val(), // 后面的id可以在controller里面接收，表示这个是哪个产品的。
	     
	    filters : {
	        max_file_size : '10mb', // 限制文件大小
	        mime_types: [
	            {title : "Image files", extensions : "jpg,gif,png"} // 过滤文件类型
	       ]
	    },
	 
	    // Flash settings
	    flash_swf_url : '/resources/js/plupload/Moxie.swf',
	 
	    // Silverlight settings
	    silverlight_xap_url : '/resources/js/plupload/Moxie.xap',
	     
	 
	    init: {
	        PostInit: function() {
	            console.log("Init Plupload...");
	        },
	 
	        FilesAdded: function(up, files) {
	            $('.prepare-img').hide(); // 此处需要修改
	            uploader.start();
	        },
	 
	        Error: function(up, err) {
	            console.log("\nError #" + err.code + ": " + err.message + ": " + err.responseHeaders + "\n" + err.file);
	        },
	        FileUploaded: function(up, file, res) {
	        	var response = $.parseJSON(res.response),
	        		container = $('.image'),
	        		img_html = "", // 显示图片的html
	        		img_title = "", // 显示图片的标题
	        		img_id, // 图片的id，用于删除
	        		img_url; // 图片显示的位置
	        	
	        	// 如果后面返回的不是数组  就不用for循环，直接从response里面取出对应的属性
	        	for(var data in response) {
	        		img_url = response[data].storeFolder + "/" + response[data].realName;
	        		img_url = img_url.substring(1, img_url.length);
	        		img_title = response[data].originName;
	        		img_id = response[data].id;
	        		img_html += generateImg(img_url, img_title, img_id);
	        	}
	        	// 这个地方就是生成显示图片的html代码的方法
	        	container.append(img_html);
	        }
	    }
	});
	
	// the format String function
	function formatString() {
		if (arguments.length == 0)
			return null;

		var str = arguments[0];
		for (var i = 1; i < arguments.length; i++) {
			var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
			str = str.replace(re, arguments[i]);
		}
		return str;
	}
	
	function generateImg(img_url, title, id) {
		// 显示图片样式可以自定义
		var template = '<div class="img-box">' +
							'<img src="{0}"/>' + 
							'<a href="javascript:void(0);" onclick="deleteImage(this, {1})"><img src="resources/images/trash.png"/></a>' +
							'<div class="img_title">{2}</div>' +
						'</div>';
		
		return formatString(template, img_url, id, title);
	}
	 
	uploader.init();
});

function deleteImage(obj, id) {
	var url = "/edu-admin/delete?id=" + id;
	$.get(url, function(data) {
		if(data.status) {
			$(obj).parent().remove();
			recoveryOrigin(obj);
		}
		// TODO: popup a warning message, if delete failed.
	});
}

function recoveryOrigin(obj) {
	var image_wrapper_node = $('#image-wrapper');
	var images = image_wrapper_node.find('.img-box');
	
	if(images.length == 0) {
		 $('.prepare-img').show();
		// 根据初始化的时候代码生成，此段代码自定义在页面中。
//		var template = '<div class="image">' + 
//							'<img class="prepare-img" src="resources/images/image.png"/>' + 
//						'</div>';
//		
//		$(image_wrapper_node).html(template);
	}
}