package org.svtcc.online.management.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.svtcc.online.management.dto.UploadFile;

/**
 * 图片操作
 * 
 * @author troyyang
 * 
 */
public class FileUpLoadUtil {
	private static final Logger LOG = Logger.getLogger(FileUpLoadUtil.class);
	private String ALLOW_TYPE = ".png.gif.jpg.jpeg"; // 用户可以自定义。N.B. 可以定义到配置文件通过spring注解获取，调用本类中的set方法赋值
	private Long ALLOW_SIZE = 3 * 1024 * 1024L; // 用户可自定义。N.B. 可以定义到配置文件通过spring注解获取，调用本类中的set方法赋值
	private static final String INVALID_FILE_TYPE = "invalid file type";
	private static final String INVALID_FILE_SIZE = "invalid file size";

	/**
	 * 上传图片
	 * @param files
	 * @param storeFolder 上传存储文件夹。例如: /upload
	 * @param request
	 * @return
	 */
	public List<UploadFile> uploadFile(MultipartFile[] files,
			String storeFolder, HttpServletRequest request) {
		List<UploadFile> uploadImages = new ArrayList<UploadFile>();

		String storePath = extractStorePath(storeFolder, request);
		
		for (MultipartFile multipartFile : files) {
			String fileName = multipartFile.getOriginalFilename();
			String type = extractFileType(fileName);

			// check file type
			boolean isValidType = checkFileType(ALLOW_TYPE, type);

			if (!isValidType) {
				UploadFile failedImage = new UploadFile(fileName, false,
						INVALID_FILE_TYPE);
				uploadImages.add(failedImage);
				// once invalid, do not execute the below flow
				continue;
			}

			// check file size
			Long fileSize = multipartFile.getSize();
			boolean isValidSize = checkFileSize(ALLOW_SIZE, fileSize);
			if (!isValidSize) {
				UploadFile failedImage = new UploadFile(fileName, false,
						INVALID_FILE_SIZE);
				uploadImages.add(failedImage);
				// once invalid, do not execute the below flow
				continue;
			}

			// type and size is valid, really upload
			try {
				// use the time stamp as the real name
				Long currentlyTime = System.currentTimeMillis();
				String realName = currentlyTime + type;
				String storeRealPath = storePath + "/" + realName;
				multipartFile.transferTo(new File(storeRealPath));

				// success
				UploadFile uploadImage = new UploadFile(0, storeFolder,
						fileName, realName, type, fileSize, true, null);
				uploadImages.add(uploadImage);
				
				if(LOG.isDebugEnabled()) {
					LOG.debug(String.format("Upload File Success, File Name: [%s]", fileName));
				}
			} catch (IllegalStateException e) {
				UploadFile failedImage = new UploadFile(fileName, false,
						e.getMessage());
				uploadImages.add(failedImage);
				LOG.error(e.getMessage());
			} catch (IOException e) {
				UploadFile failedImage = new UploadFile(fileName, false,
						e.getMessage());
				uploadImages.add(failedImage);
				LOG.error(e.getMessage());
			}
		}
		return uploadImages;
	}
	
	/**
	 * 删除图片
	 * @param request
	 * @param storeFolder 上传存储文件夹，例如:/upload
	 * @param realName
	 * @return
	 */
	public boolean removeFile(HttpServletRequest request, String storeFolder, String realName) {
		String storePath = extractStorePath(storeFolder, request);
		String filePath = storePath + "/" + realName;
		
		File file = new File(filePath);
		if(file.exists()) {
			return file.delete();
		} 
		
		return false;
	}
	
	/**
	 * 获取文件格式
	 * @param fileName
	 * @return
	 */
	private String extractFileType(String fileName) {
		String type = fileName.substring(fileName.lastIndexOf("."),
				fileName.length());
		return type;
	}

	/**
	 * 通过HttpServletRequest生成存储路径
	 * 
	 * @param storeFolder
	 * @param request
	 * @return
	 */
	private String extractStorePath(String storeFolder,
			HttpServletRequest request) {
		String servletAbsolutePath = request.getSession().getServletContext()
				.getRealPath("/") + "/";
		StringBuilder storePath = new StringBuilder(servletAbsolutePath);

		if (!StringUtils.isEmpty(storeFolder)) {
			storePath.append(storeFolder);
		}

		String targetPath = storePath.toString();
		File targetFolder = new File(targetPath);

		if (!targetFolder.exists()) {
			// mkdirs 创建多级目录
			targetFolder.mkdirs();
		}

		return targetPath;
	}

	/**
	 * 检测文件类型是否合法
	 * 
	 * @param allowType
	 * @param targetType
	 * @return
	 */
	private boolean checkFileType(String allowType, String targetType) {
		if (StringUtils.isEmpty(allowType) || StringUtils.isEmpty(targetType)) {
			return false;
		}

		if (!allowType.contains(targetType) && !allowType.toUpperCase().contains(targetType)) {
			return false;
		}

		return true;
	}

	/**
	 * 检查文件大小是否合法
	 * 
	 * @param allowSize
	 * @param targetSize
	 * @return
	 */
	private boolean checkFileSize(Long allowSize, Long targetSize) {
		if (allowSize < targetSize) {
			return false;
		}

		return true;
	}
	
	/**
	 * 设置允许上传文件格式
	 * @param allowTypes
	 */
	public void setAllowTypes(String allowTypes) {
		this.ALLOW_TYPE = allowTypes;
	}
	
	/**
	 * 设置允许上传大小
	 * @param allowSize
	 */
	public void setAllowSize(Long allowSize) {
		this.ALLOW_SIZE = allowSize;
	}
}
