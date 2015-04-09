package org.svtcc.online.management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.svtcc.online.management.dto.UploadFile;
import org.svtcc.online.management.util.FileUpLoadUtil;


@Controller
public class FileUploadController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody
	List<UploadFile> upload(@RequestParam(value = "files") MultipartFile[] files, @RequestParam(value = "id") Integer id, 
			HttpServletRequest request) {
		FileUpLoadUtil fileUpLoadUtil = new FileUpLoadUtil();
		List<UploadFile> uploadedFiles = fileUpLoadUtil.uploadFile(files,
				"/upload", request);
		// TODO: 存储返回图片信息到数据库
		return uploadedFiles;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> delete(Integer id, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// TODO: 根据ID从数据库获取图片信息记录
		UploadFile image = new UploadFile();
		
		// 删除图片
		FileUpLoadUtil fileUpLoadUtil = new FileUpLoadUtil();
		boolean deleteImage = fileUpLoadUtil.removeFile(request, image.getStoreFolder(), image.getRealName());
		if(!deleteImage) {
			// 删除失败
			// TODO：由于现在没法测试，所以这个地方写成true，后面改为注释掉的代码
//			result.put("status", deleteImage);
			
			result.put("status", true);
		} else {
			// 删除实际图片成功，再删除数据库记录
			// TODO: 根据ID删除图片数据库中的记录
			// 伪代码：
			// boolean deleteSuccess = service.delete(id);
			// result.put("status", deleteSuccess); 
		}
		
		return result;
	}

}
